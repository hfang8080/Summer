// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.property;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.PropertyArgsDefinition;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 7.0
 */
public interface SingleBeanPropertyProcessor {

    /**
     * 对象初始化之后的单个属性设置
     * @param beanFactory Bean工厂
     * @param instance Bean实例
     * @param propertyArgsDefinition 属性的定义
     * @since 7.0
     */
    void propertyProcessor(final BeanFactory beanFactory,
                           final Object instance,
                           PropertyArgsDefinition propertyArgsDefinition);
}
