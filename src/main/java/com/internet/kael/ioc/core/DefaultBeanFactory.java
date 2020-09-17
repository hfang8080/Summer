// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;

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
        beanDefinitionMap.put(beanName, beanDefinition);

        // @since 2.0 类型信息
        Class<?> type = getType(beanName);
        if (!typeBeanNamesMap.containsKey(type)) {
            typeBeanNamesMap.put(type, new HashSet<>());
        }
        Set<String> beanNames = typeBeanNamesMap.get(type);
        beanNames.add(beanName);
        typeBeanNamesMap.put(type, beanNames);
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
        Object bean = beanMap.get(beanName);
        if (Objects.nonNull(bean)) {
            // 这里直接返回的是单例，如果用户指定为多例，则每次都需要新建。
            return bean;
        }
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (Objects.isNull(beanDefinition)) {
            throw new IocRuntimeException(beanName + " is not exists in bean definition.");
        }
        Object instance = createBean(beanDefinition);
        beanMap.put(beanName, instance);
        return instance;
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
}
