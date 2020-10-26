// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.support.environment.Environment;
import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 18.0
 */
public class DefaultConditionContext implements ConditionContext {

    private final BeanFactory beanFactory;
    private final AnnotationTypeMeta typeMeta;
    private final Environment environment;

    public DefaultConditionContext(BeanFactory beanFactory, AnnotationTypeMeta typeMeta, Environment environment) {
        this.beanFactory = beanFactory;
        this.typeMeta = typeMeta;
        this.environment = environment;
    }

    @Override
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public AnnotationTypeMeta getAnnotationTypeMeta() {
        return typeMeta;
    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }
}
