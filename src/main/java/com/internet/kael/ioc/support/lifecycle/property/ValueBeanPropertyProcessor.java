// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.property;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.PropertyArgsDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.StringConverter;

/**
 * @author Kael He (kael.he@alo7.com)
 */
public class ValueBeanPropertyProcessor implements SingleBeanPropertyProcessor {

    private static final ValueBeanPropertyProcessor INSTANCE = new ValueBeanPropertyProcessor();

    public static ValueBeanPropertyProcessor getInstance() {
        return INSTANCE;
    }

    @Override
    public void propertyProcessor(
            BeanFactory beanFactory, Object instance, PropertyArgsDefinition propertyArgsDefinition) {
        Preconditions.checkNotNull(instance);
        String name = propertyArgsDefinition.getName();
        String type = propertyArgsDefinition.getType();
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(type);

        boolean fieldBase = propertyArgsDefinition.isFieldBase();
        String fieldName = propertyArgsDefinition.getName();
        String value = propertyArgsDefinition.getValue();
        Class typeClazz = ClassUtils.getClass(type);
        Object valueObject = StringConverter.toObject(typeClazz, value);

        if (fieldBase) {
            ClassUtils.setValue(instance, fieldName, valueObject);
        } else {
            ClassUtils.invokeSetterMethod(instance, name, valueObject);
        }
    }
}
