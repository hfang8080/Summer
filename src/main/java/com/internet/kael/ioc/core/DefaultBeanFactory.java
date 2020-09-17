// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class DefaultBeanFactory implements BeanFactory {

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private Map<String, Object> beanMap = new ConcurrentHashMap<>();

    protected void registerBeanDefinition(final String beanName, final BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public Object getBean(String beanName) {
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
        Object bean = getBean(beanName);
        return (T) bean;
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
