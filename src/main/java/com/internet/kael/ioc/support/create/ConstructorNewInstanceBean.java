// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.create;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.ConstructorArgsDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.StringConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 基于构造器来实例化Bean
 * @author Kael He (kael.he@alo7.com)
 * @since 6.0
 */
public class ConstructorNewInstanceBean extends AbstractNewInstanceBean {
    private static ConstructorNewInstanceBean constructorNewInstanceBean = new ConstructorNewInstanceBean();
    public static ConstructorNewInstanceBean getInstance() {
        return constructorNewInstanceBean;
    }

    @Override
    protected Optional<Object> newInstanceOpt(BeanFactory beanFactory, BeanDefinition beanDefinition, Class beanClass) {
        List<Pair<Class, Object>> pairs = buildPairs(beanFactory, beanDefinition);
        Class[] paramTypes = pairs.stream().map(Pair::getLeft).toArray(Class[]::new);
        if (ArrayUtils.isEmpty(paramTypes)) {
            // 无参构造
            Constructor constructor = ClassUtils.getConstructor(beanClass);
            return Optional.ofNullable(ClassUtils.newInstance(constructor));

        }
        // 有参构造
        Constructor constructor = ClassUtils.getConstructor(beanClass, paramTypes);
        Object[] parameters = pairs.stream().map(Pair::getRight).toArray(Object[]::new);
        return Optional.ofNullable(ClassUtils.newInstance(constructor, parameters));
    }

    // 这个位置只是简单地按照参数的顺序注入参数，其实可以通过配置index 和 name准确映射的方式。会更加合理
    private List<Pair<Class, Object>> buildPairs(BeanFactory beanFactory, BeanDefinition beanDefinition) {
        List<ConstructorArgsDefinition> constructorArgsDefinitions = beanDefinition.getConstructorArgsDefinitions();
        if (CollectionUtils.isEmpty(constructorArgsDefinitions)) {
            return Collections.emptyList();
        }
        return constructorArgsDefinitions.stream()
                .map(bd -> {
                    if (!StringUtils.isEmpty(bd.getRef())) {
                        String ref = bd.getRef();
                        Class clazz = beanFactory.getType(ref);
                        Object bean = beanFactory.getBean(ref);
                        return Pair.of(clazz, bean);
                    } else {
                        Preconditions.checkNotNull(bd.getType());
                        Class clazz = ClassUtils.getClass(bd.getType());
                        Object value = StringConverter.toObject(clazz, bd.getValue());
                        return Pair.of(clazz, value);
                    }

                })
                .collect(Collectors.toList());
    }
}
