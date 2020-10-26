// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.internet.kael.ioc.constant.Scope;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.ConstructorArgsDefinition;
import com.internet.kael.ioc.support.aware.BeanCreateAware;
import com.internet.kael.ioc.support.aware.BeanNameAware;
import com.internet.kael.ioc.support.circle.BeanDependenceChecker;
import com.internet.kael.ioc.support.circle.DefaultBeanDependenceChecker;
import com.internet.kael.ioc.support.lifecycle.create.DefaultNewInstanceBean;
import com.internet.kael.ioc.support.lifecycle.destroy.DefaultDisposableBean;
import com.internet.kael.ioc.support.lifecycle.destroy.DisposableBean;
import com.internet.kael.ioc.support.lifecycle.init.DefaultInitialingBean;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.CollectionHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class DefaultBeanFactory implements BeanFactory, DisposableBean {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private Map<Class, Set<String>> typeBeanNamesMap = new ConcurrentHashMap<>();
    private List<Pair<Object, BeanDefinition>> instanceBeanDefinitionPairs = Lists.newArrayList();
    private BeanDependenceChecker dependenceChecker = new DefaultBeanDependenceChecker();
    private List<String> primaryBeanClassNames = Lists.newArrayList();
    private Map<Class, String> primaryBeanNameMap = new ConcurrentHashMap<>();

    /**
     * 注册Bean
     *
     * @param beanName       Bean名称
     * @param beanDefinition Bean的配置
     * @since 1.0
     */
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        // 将BeanName -> BeanDefinition 定义存入Map
        beanDefinitionMap.put(beanName, beanDefinition);
        // 注册Type -> BeanNames 存入Map
        registerTypeBeanNames(beanName, beanDefinition);
        // 构建primary bean map
        buildPrimaryBeanMapping(beanName, beanDefinition);
        // 通知所有beanName监听器
        notifyAllBeanNameAware(beanName);
        if (needEagerCreateSingletonBean(beanDefinition)) {
            registerSingletonBean(beanName, beanDefinition);
        }
    }

    /**
     * 根据类型信息获取Bean名称
     *
     * @param clazzType Class类型
     * @return Bean名称集合
     * @since 2.0
     */
    protected Set<String> getBeanNames(final Class clazzType) {
        Preconditions.checkNotNull(clazzType);
        return typeBeanNamesMap.get(clazzType);
    }

    @Override
    public Object getBean(String beanName) {
        Preconditions.checkNotNull(beanName);
        // 获取配置信息
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (Objects.isNull(beanDefinition)) {
            throw new IocRuntimeException(beanName + " is not exists in bean definition.");
        }
        // 判断如果不是单例，则创建新对象
        if (!StringUtils.equals(Scope.SINGLETON.getCode(), beanDefinition.getScope())) {
            return createBean(beanDefinition);
        }

        // 单例的流程
        return registerSingletonBean(beanName, beanDefinition);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> clazz) {
        Preconditions.checkNotNull(beanName);
        Object bean = getBean(beanName);
        return (T) bean;
    }

    protected <T> List<T> getBeans(Class<T> clazzType) {
        Preconditions.checkNotNull(clazzType);
        Set<String> beanNames = getBeanNames(clazzType);
        if (CollectionUtils.isEmpty(beanNames)) {
            return Collections.emptyList();
        }

        return CollectionHelper.transform(Lists.newArrayList(beanNames),
                beanName -> getBean(beanName, clazzType));
    }

    @Override
    public boolean containsBean(String beanName) {
        Preconditions.checkNotNull(beanName);
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public boolean isTypeMatch(String beanName, Class requireType) {
        Preconditions.checkNotNull(beanName);
        Class<?> type = getType(beanName);
        return type.equals(requireType);
    }

    @Override
    public Class<?> getType(String beanName) {
        Preconditions.checkNotNull(beanName);
        return getBean(beanName).getClass();
    }

    protected String getPrimaryBeanName(Class<?> requireType) {
        return primaryBeanNameMap.get(requireType);
    }

    protected <T> T getPrimaryBean(Class<T> requireType) {
        return getBean(getPrimaryBeanName(requireType), requireType);
    }

    /**
     * 判断是否需要延迟创建Bean
     * @param beanDefinition Bean定义
     * @return 是否需要
     * @since 8.0
     */
    private boolean needEagerCreateSingletonBean(final BeanDefinition beanDefinition) {
        Preconditions.checkNotNull(beanDefinition);
        if (beanDefinition.isLazyInit()) {
            return false;
        }
        List<ConstructorArgsDefinition> constructorArgsDefinitions = beanDefinition.getConstructorArgsDefinitions();
        if (CollectionUtils.isNotEmpty(constructorArgsDefinitions)) {
            for (ConstructorArgsDefinition definition: constructorArgsDefinitions) {
                String ref = definition.getRef();
                if (StringUtils.isNoneEmpty(ref)) {
                    Object instance = beanMap.get(ref);
                    if (Objects.isNull(instance)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 创建Bean实例
     *
     * @param beanDefinition Bean的定义信息
     * @return Bean实例
     * @since 1.0
     */
    private Object createBean(final BeanDefinition beanDefinition) {
        Preconditions.checkNotNull(beanDefinition);
        String beanName = beanDefinition.getName();

        if (dependenceChecker.isCyclicDependence(beanName)) {
            throw new IocRuntimeException(beanName + "has circle reference.");
        }
        // 初始化相关处理
        Object instance = DefaultNewInstanceBean.getInstance().instance(this, beanDefinition);

        // 初始化完成之后的调用
        DefaultInitialingBean initialingBean = new DefaultInitialingBean(instance, beanDefinition);
        initialingBean.afterPropertiesSet();
        // 保存Bean定义信息
        instanceBeanDefinitionPairs.add(Pair.of(instance, beanDefinition));

        // 通知所有监听者
        notifyAllBeanCreateAware(beanName, instance);
        return instance;
    }

    /**
     * 注册单例对象，如果已经存在则直接返回
     *
     * @param beanName Bean名称
     * @param bd       对象对应
     * @return 3.0
     */
    private Object registerSingletonBean(final String beanName, final BeanDefinition bd) {
        Object bean = beanMap.get(beanName);
        if (Objects.isNull(bean)) {
            bean = createBean(bd);
            beanMap.put(beanName, bean);
        }
        return bean;
    }

    /**
     * 获取类型信息
     *
     * @param beanDefinition Bean的定义
     * @return Bean的类型
     * @since 3.0
     */
    private Class getType(final BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        return ClassUtils.getClass(className);
    }

    /**
     * 注册类型及其对应的名称
     *
     * @param beanName Bean名称
     * @param bd       Bean定义
     * @since 3.0
     */
    private void registerTypeBeanNames(final String beanName, final BeanDefinition bd) {
        Set<Class> typeSet = getTypeSet(bd);

        for (Class type: typeSet) {
            if (!typeBeanNamesMap.containsKey(type)) {
                typeBeanNamesMap.put(type, new HashSet<>());
            }
            Set<String> beanNames = typeBeanNamesMap.get(type);
            beanNames.add(beanName);
            typeBeanNamesMap.put(type, beanNames);
        }
    }

    private void buildPrimaryBeanMapping(final String beanName, final BeanDefinition bd) {
        if (bd.isPrimary()) {
            if (primaryBeanClassNames.contains(bd.getClassName())) {
                throw new IocRuntimeException("Class: " + bd.getClassName() +
                        " has a duplicate primary identity.");
            }
            primaryBeanClassNames.add(bd.getClassName());
            primaryBeanNameMap.put(getType(bd), beanName);
            // TODO bean name 是唯一键的验证
        }
        if (!primaryBeanClassNames.contains(bd.getClassName())) {
            primaryBeanNameMap.put(getType(bd), beanName);
        }

    }

    /**
     * 通知所有BeanCreateAware.
     *
     * @param beanName Bean名称
     * @param instance 实例
     * @since 8.0
     */
    private void notifyAllBeanCreateAware(final String beanName, final Object instance) {
        List<BeanCreateAware> awareList = getBeans(BeanCreateAware.class);
        for (BeanCreateAware aware : awareList) {
            aware.setBeanCreate(beanName, instance);
        }
    }

    /**
     * 通知所有BeanNameAware.
     * @param beanName Bean名称
     * @since 8.0
     */
    private void notifyAllBeanNameAware(final String beanName) {
        List<BeanNameAware> awareList = getBeans(BeanNameAware.class);
        for (BeanNameAware aware: awareList) {
            aware.setBeanName(beanName);
        }
    }

    /**
     * 获取类型信息列表。
     *  1）当前类信息
     *  2) 所有的接口信息
     * @param beanDefinition 对象定义
     * @return 类型集合
     */
    private Set<Class> getTypeSet(final BeanDefinition beanDefinition) {
        HashSet<Class> classSet = Sets.newHashSet();
        Class clazz = getType(beanDefinition);
        Class[] interfaces = clazz.getInterfaces();
        List<Class> classes = Arrays.asList(interfaces);
        classSet.add(clazz);
        if (CollectionUtils.isNotEmpty(classes)) {
            classSet.addAll(classes);
        }
        return classSet;
    }

    /**
     * 销毁所有属性
     *
     * @since 4.0
     */
    @Override
    public void destroy() {
        // 销毁所有的属性信息
        // 这里的销毁严格来说，还有很多事情要做。
        // 比如销毁的顺序：最外层没有依赖的开始销毁，依次往里，直到销毁全部。可能会出现循环依赖，类似于 GC。
        System.out.println("Destroy all beans start.");
        for (Pair<Object, BeanDefinition> pair : instanceBeanDefinitionPairs) {
            DefaultDisposableBean disposableBean = new DefaultDisposableBean(pair.getLeft(), pair.getRight());
            disposableBean.destroy();
        }
        System.out.println("Cleaning done.");
    }
}
