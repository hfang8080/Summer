// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.constant.Scope;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class DefaultBeanFactory implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();
    private Map<Class, Set<String>> typeBeanNamesMap = new ConcurrentHashMap<>();

    /**
     * 注册Bean
     *
     * @param beanName Bean名称
     * @param beanDefinition Bean的配置
     * @since 1.0
     */
    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        // 将BeanName -> BeanDefinition 定义存入Map
        beanDefinitionMap.put(beanName, beanDefinition);
        // 注册Type -> BeanNames 存入Map
        registerTypeBeanNames(beanName, beanDefinition);
        boolean lazyInit = beanDefinition.isLazyInit();
        if (!lazyInit) {
            // 如果不是lazy的，默认创建单例Bean
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

    /**
     * 创建Bean实例
     *
     * @param beanDefinition Bean的定义信息
     * @return Bean实例
     * @since 1.0
     */
    private Object createBean(final BeanDefinition beanDefinition) {
        String className = beanDefinition.getClassName();
        Class clazz = ClassUtils.getClass(className);
        return ClassUtils.newInstance(clazz);
    }

    /**
     * 注册单例对象，如果已经存在则直接返回
     * @param beanName Bean名称
     * @param bd 对象对应
     * @return 3.0
     */
    private Object registerSingletonBean(final String beanName, final BeanDefinition bd) {
        Object bean = beanMap.get(beanName);
        if (Objects.isNull(bean)) {
            Object instance = createBean(bd);
            beanMap.put(beanName, instance);
        }
        return bean;
    }

    /**
     * 获取类型信息
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
     * @param beanName Bean名称
     * @param bd Bean定义
     * @since 3.0
     */
    private void registerTypeBeanNames(final String beanName, final BeanDefinition bd) {
        Class type = getType(bd);

        // @since 2.0 类型信息
        if (!typeBeanNamesMap.containsKey(type)) {
            typeBeanNamesMap.put(type, new HashSet<>());
        }
        Set<String> beanNames = typeBeanNamesMap.get(type);
        beanNames.add(beanName);
        typeBeanNamesMap.put(type, beanNames);
    }
}
