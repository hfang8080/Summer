// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.processor;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.annotation.Autowired;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.core.ListableBeanFactory;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.support.aware.BeanFactoryAware;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

/**
 * 用来处理@Autowired注解需要注入的Bean
 * @author Kael He (kael.he@alo7.com)
 * @since 16.0
 */
public class AutowiredBeanPostProcessor extends BeanPostProcessorAdaptor implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object afterPropertySet(String beanName, Object instance) {
        Preconditions.checkNotNull(instance);
        Class<?> clazz = instance.getClass();
        List<Field> fields = ClassUtils.getAllFields(clazz);
        ListableBeanFactory beanFactory = (ListableBeanFactory) this.beanFactory;
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Autowired.class)) {
                continue;
            }
            Autowired annotation = field.getAnnotation(Autowired.class);
            String filedBeanName = annotation.value();
            Class<?> fieldType = field.getType();
            Object fieldValue;
            if (Collection.class.isAssignableFrom(fieldType)) {
                Type genericType = field.getGenericType();
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                Class<?> filedGenericType = (Class) parameterizedType.getActualTypeArguments()[0];
                List beans = beanFactory.getBeans(filedGenericType);
                if (CollectionUtils.isEmpty(beans)) {
                    throw new IocRuntimeException("Autowired of class type " + filedGenericType
                            + " not found!");
                }
                Collection collection = ClassUtils.createCollection(genericType);
                collection.addAll(beans);
                fieldValue = collection;
            } else {
                // 根据类型和名称获取相应的Bean
                fieldValue = beanFactory.getRequiredTypeBean(fieldType, filedBeanName);
            }
            ClassUtils.setFieldValue(instance, field, fieldValue);
        }

        return instance;
    }
}
