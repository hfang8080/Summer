// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.create;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.BeanDefinition;

import java.util.Objects;

/**
 * 默认的用来做实例化的Bean的实现类
 * @author Kael He (kael.he@alo7.com)
 * @since 6.0
 */
public class DefaultNewInstanceBean implements NewInstanceBean {
    public static NewInstanceBean instance = new DefaultNewInstanceBean();

    public static NewInstanceBean getInstance() {
        return instance;
    }

    @Override
    public Object instance(final BeanFactory beanFactory, final BeanDefinition beanDefinition) {
        Preconditions.checkNotNull(beanFactory);
        Preconditions.checkNotNull(beanDefinition);
        Object instance = BeanFactoryNewInstanceBean.getInstance()
                .instance(beanFactory, beanDefinition);
        if (Objects.nonNull(instance)) {
            return instance;
        }
        return ConstructorNewInstanceBean.getInstance().instance(beanFactory, beanDefinition);
    }
}

