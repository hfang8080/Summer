// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 18.0
 */
public class DefaultConditionContext implements ConditionContext {

    private final BeanFactory beanFactory;
    private final AnnotationTypeMeta typeMeta;

    public DefaultConditionContext(BeanFactory beanFactory, AnnotationTypeMeta typeMeta) {
        this.beanFactory = beanFactory;
        this.typeMeta = typeMeta;
    }

    @Override
    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    @Override
    public AnnotationTypeMeta getAnnotationTypeMeta() {
        return typeMeta;
    }
}
