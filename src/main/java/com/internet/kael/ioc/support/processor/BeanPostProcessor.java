// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.processor;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 8.0
 */
public interface BeanPostProcessor extends PostProcessor {

    /**
     * 属性设置前
     * @param beanName Bean名称
     * @param instance Bean实例
     * @return 结果
     * @since 8.0
     */
    Object beforePropertySet(final String beanName, final Object instance);

    /**
     * 属性设置后
     * @param beanName Bean名称
     * @param instance Bean实例
     * @return 结果
     * @since 8.0
     */
    Object afterPropertySet(final String beanName, final Object instance);

}
