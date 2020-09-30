// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.property;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.PropertyArgsDefinition;
import com.internet.kael.ioc.util.ClassUtils;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 7.0
 */
public class RefBeanPropertyProcessor implements SingleBeanPropertyProcessor {
    private static final RefBeanPropertyProcessor INSTANCE = new RefBeanPropertyProcessor();

    public static RefBeanPropertyProcessor getInstance() {
        return INSTANCE;
    }

    @Override
    public void propertyProcessor(
            BeanFactory beanFactory, Object instance, PropertyArgsDefinition propertyArgsDefinition) {
        String ref = propertyArgsDefinition.getRef();
        String propertyName = propertyArgsDefinition.getName();
        Preconditions.checkNotNull(ref);
        Object refBean = beanFactory.getBean(ref);
        ClassUtils.invokeSetterMethod(instance, propertyName, refBean);
    }
}
