// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.internet.kael.ioc.constant.Scope;
import com.internet.kael.ioc.core.DefaultListableBeanFactory;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.PropertyArgsDefinition;
import com.internet.kael.ioc.support.aware.ApplicationContextAware;
import com.internet.kael.ioc.support.circle.BeanDependenceChecker;
import com.internet.kael.ioc.support.circle.DefaultBeanDependenceChecker;
import com.internet.kael.ioc.support.processor.ApplicationContextPostProcessor;
import com.internet.kael.ioc.util.CollectionHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 4.0
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {

    /**
     * 全部对象的定义Map
     * @since 9.0
     */
    private Map<String, BeanDefinition> beanDefinitionMap = Maps.newHashMap();

    /**
     * 子对象定义表
     * @since 9.0
     */
    private List<BeanDefinition> childrenBeanDefinitions = Lists.newArrayList();

    /**
     * 抽象的对象信息列表
     * @since 9.0
     */
    private List<BeanDefinition> abstractBeanDefinitions = Lists.newArrayList();

    /**
     * 可创建的定义；列表信息
     * @since 9.0
     */
    private List<BeanDefinition> creatableBeanDefinitions = Lists.newArrayList();

    /**
     * Bean依赖的检查器
     * @since 10.0
     */
    private BeanDependenceChecker beanDependenceChecker = new DefaultBeanDependenceChecker();

    /**
     * 初始化Bean定义
     *
     * @since 4.0
     */
    protected void init() {
        List<BeanDefinition> beanDefinitions = buildBeanDefinitions();
        buildCreatableBeanDefinitions(beanDefinitions);
        creatableBeanDefinitions = postProcessor(creatableBeanDefinitions);
        beanDependenceChecker.registerBeanDefinition(creatableBeanDefinitions);
        registerBeanDefinitions(creatableBeanDefinitions);
        registerShutdownHook();
        notifyAllAware();
    }

    /**
     * 循环执行Bean信息处理
     * @param beanDefinitions Bean定义
     * @return BeanDefinitions
     * @since 8.0
     */
    private List<BeanDefinition> postProcessor(List<BeanDefinition> beanDefinitions) {
        List<ApplicationContextPostProcessor> processors = getBeans(ApplicationContextPostProcessor.class);
        for(ApplicationContextPostProcessor processor: processors) {
            beanDefinitions = processor.beforeRegister(beanDefinitions);
        }
        return beanDefinitions;
    }

    /**
     * 通知所有application context aware.
     */
    private void notifyAllAware() {
        List<ApplicationContextAware> awareList = getBeans(ApplicationContextAware.class);
        for (ApplicationContextAware aware : awareList) {
            aware.setApplicationContext(this);
        }
    }

    /**
     * 注册所有的Bean定义
     *
     * @param beanDefinitions 所有的Bean定义
     * @since 4.0
     */
    protected void registerBeanDefinitions(final List<BeanDefinition> beanDefinitions) {
        if (CollectionUtils.isNotEmpty(beanDefinitions)) {
            for (BeanDefinition bd : beanDefinitions) {
                // 填充默认值
                fillDefaultValue(bd);
                // 注册Bean Definition对象
                registerBeanDefinition(bd.getName(), bd);
            }
        }
    }

    /**
     * 填充默认值
     * @param bd Bean定义
     * @since 4.0
     */
    private void fillDefaultValue(BeanDefinition bd) {
        if (StringUtils.isEmpty(bd.getScope())) {
            bd.setScope(Scope.SINGLETON.getCode());
        }
    }

    /**
     * 构建Bean定义的列表
     * @return Bean定义的列表
     * @since 4.0
     */
    protected abstract List<BeanDefinition> buildBeanDefinitions();

    /**
     * 对象定义列表
     * （1）将 abstract 类型的 bean definition 区分开。
     * （2）对 parent 指定的 bean definition 进行属性赋值处理。
     * @param beanDefinitions 所有的对象列表
     * @since 0.0.9
     */
    private void buildCreatableBeanDefinitions(List<BeanDefinition> beanDefinitions) {
        beanDefinitions.forEach(beanDefinition -> {
            String name = beanDefinition.getName();
            String parentName = beanDefinition.getParentName();
            beanDefinitionMap.put(name, beanDefinition);
            if (beanDefinition.isAbstractClass()) {
                abstractBeanDefinitions.add(beanDefinition);
            } else if (StringUtils.isNoneEmpty(parentName)) {
                childrenBeanDefinitions.add(beanDefinition);
            } else {
                creatableBeanDefinitions.add(beanDefinition);
            }
        });
        // 将所有的child Bean Definition 添加进creatable
        addAllChildBeanDefinition();
    }

    /**
     * 构建子节点定义列表并添加进creatable
     */
    private void addAllChildBeanDefinition() {
        for (BeanDefinition child : childrenBeanDefinitions) {
            final String name = child.getName();
            final String parentName = child.getParentName();
            if(StringUtils.isEmpty(name)) {
                throw new IocRuntimeException("name can not be empty!");
            }
            if(name.equals(parentName)) {
                throw new IocRuntimeException(name + " parent bean is ref to itself!");
            }
            BeanDefinition parentDefinition = beanDefinitionMap.get(parentName);
            if (Objects.isNull(parentDefinition)) {
                throw new IocRuntimeException(parentName + " not found !");
            }
            BeanDefinition newChild = buildChildBeanDefinition(child, parentDefinition);
            creatableBeanDefinitions.add(newChild);
        }
    }

    /**
     * 构建新子类的属性定义
     * 注意：
     *      （1）为了简化，只继承property属性信息
     *      （2）父类的属性，子类必须全部拥有，否则就会报错，暂时不做优化
     * 核心流程：
     *      （1）获取child的所有属性
     *      （2）获取parent的所有属性
     *      （3）进行过滤处理，相同以child为准
     * @param child 子类的Bean定义
     * @param parent 父类的Bean定义
     * @return
     */
    private BeanDefinition buildChildBeanDefinition(final BeanDefinition child, final BeanDefinition parent) {
        List<PropertyArgsDefinition> childPropertyDefinitions = child.getPropertyArgsDefinitions();
        if (Objects.isNull(childPropertyDefinitions)) {
            childPropertyDefinitions = Lists.newArrayList();
        }
        Map<String, PropertyArgsDefinition> mapChildNameDefinition =
                CollectionHelper.newHashMap(childPropertyDefinitions, PropertyArgsDefinition::getName, k -> k);
        List<PropertyArgsDefinition> parentPropertyDefinitions = parent.getPropertyArgsDefinitions();
        if (CollectionUtils.isNotEmpty(parentPropertyDefinitions)) {
            for (PropertyArgsDefinition parentPropertyArgsDefinition : parentPropertyDefinitions) {
                String name = parentPropertyArgsDefinition.getName();
                if (mapChildNameDefinition.containsKey(name)) continue;
                // 如果child里面没有这个属性，则将这个属性也添加进来
                childPropertyDefinitions.add(parentPropertyArgsDefinition);
            }
        }
        child.setPropertyArgsDefinitions(childPropertyDefinitions);
        return child;
    }

    /**
     * 注册关闭的钩子
     *
     * @since 4.0
     */
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::destroy));
    }

    @Override
    public String getApplicationName() {
        return "ApplicationName";
    }
}
