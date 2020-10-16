// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.create;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.AnnotationBeanDefinition;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 12.0
 */
public class ConfigurationMethodInstanceBean extends AbstractNewInstanceBean {

    private static ConfigurationMethodInstanceBean INSTANCE = new ConfigurationMethodInstanceBean();
    public static ConfigurationMethodInstanceBean getInstance() {
        return INSTANCE;
    }

    @Override
    protected Optional<Object> newInstanceOpt(BeanFactory beanFactory, BeanDefinition beanDefinition, Class beanClass) {
        if (!(beanDefinition instanceof AnnotationBeanDefinition)) {
            throw new IocRuntimeException(
                    "BeanDefinition must be instance of AnnotationBeanDefinition " + beanDefinition.getName());
        }
        AnnotationBeanDefinition bd = (AnnotationBeanDefinition) beanDefinition;
        Object configBean = beanFactory.getBean(bd.getConfigurationName());
        Optional<Method> optionalMethod = ClassUtils.getMethod(configBean.getClass(), bd.getConfigurationBeanMethod());
        if (optionalMethod.isPresent()) {
            Object bean = ClassUtils.invokeNoArgsMethod(configBean, optionalMethod.get());
            return Optional.of(bean);
        }
        return Optional.empty();
    }
}
