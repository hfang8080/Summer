// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.property;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.PropertyArgsDefinition;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 7.0
 */
public interface BeanPropertyProcessor {

    /**
     * 对象初始化之后调用
     * @param beanFactory Bean工厂
     * @param instance 实例
     * @param propertyArgsDefinitions 属性的定义
     * @since 7.0
     */
    void propertyProcessor(final BeanFactory beanFactory,
                           final Object instance,
                           final List<PropertyArgsDefinition> propertyArgsDefinitions);
}
