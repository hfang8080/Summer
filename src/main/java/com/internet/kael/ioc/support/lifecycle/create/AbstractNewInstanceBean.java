// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.create;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;

import java.util.Optional;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 6.0
 */
public abstract class AbstractNewInstanceBean extends DefaultNewInstanceBean {

    protected abstract Optional<Object> newInstanceOpt(
            final BeanFactory beanFactory, final BeanDefinition beanDefinition, final Class beanClass);

    @Override
    public Object instance(final BeanFactory beanFactory, final BeanDefinition beanDefinition) {
        Preconditions.checkNotNull(beanFactory);
        Preconditions.checkNotNull(beanDefinition);
        Class beanClass = ClassUtils.getClass(beanDefinition.getClassName());
        return newInstanceOpt(beanFactory, beanDefinition, beanClass).orElse(null);
    }
}
