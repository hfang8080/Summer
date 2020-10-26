// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.create;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * 基于Bean工厂来实例化Bean
 * @author Kael He (kael.he@alo7.com)
 * @since 6.0
 */
public class BeanFactoryNewInstanceBean extends AbstractNewInstanceBean {

    private static BeanFactoryNewInstanceBean beanFactoryNewInstanceBean = new BeanFactoryNewInstanceBean();
    public static BeanFactoryNewInstanceBean getInstance() {
        return beanFactoryNewInstanceBean;
    }

    @Override
    protected Optional<Object> newInstanceOpt(BeanFactory beanFactory, BeanDefinition beanDefinition, Class beanClass) {
        Preconditions.checkNotNull(beanFactory);
        Preconditions.checkNotNull(beanDefinition);
        Preconditions.checkNotNull(beanClass);
        // 通过配置创建
        String beanFactoryMethodName = beanDefinition.getBeanFactoryMethodName();
        if (StringUtils.isEmpty(beanFactoryMethodName)) {
            return newInstance(beanClass);
        }
        // 通过注解创建
        return newInstance(beanFactoryMethodName, beanClass);
    }

    private Optional<Object> newInstance(final String beanFactoryMethodName, final Class beanClass) {
        Optional<Method> optionalMethod = ClassUtils.getMethod(
                beanClass, beanFactoryMethodName);

        if (optionalMethod.isPresent()) {
            Object result = ClassUtils.invokeNoArgsMethod(beanClass, optionalMethod.get());
            if (Objects.nonNull(result)) {
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

    private Optional<Object> newInstance(final Class beanClass) {
        Optional<Method> optionalMethod = ClassUtils.getMethod(
                beanClass, com.internet.kael.ioc.annotation.BeanFactory.class);
        Object instance = ClassUtils.newInstance(beanClass);

        if (optionalMethod.isPresent()) {
            Object result = ClassUtils.invokeNoArgsMethod(instance, optionalMethod.get());
            if (Objects.nonNull(result)) {
                return Optional.of(result);
            }
        }
        return Optional.empty();
    }

}
