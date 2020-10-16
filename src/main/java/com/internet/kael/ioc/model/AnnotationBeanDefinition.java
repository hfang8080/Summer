// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 12.0
 */
public interface AnnotationBeanDefinition extends BeanDefinition {
    /**
     * 设置配置对象名称
     * @param configurationName 配置对象名称
     */
    void setConfigurationName(final String configurationName);

    /**
     * 获取配置名称
     * @return 配置名称
     * @since 12.0
     */
    String getConfigurationName();

    /**
     * 设置创建实例的方法名
     * @param configurationBeanMethod 方法名称
     * @since 12.0
     */
    void setConfigurationBeanMethod(final String configurationBeanMethod);

    /**
     * 获取创建实例的名称
     * @return 实例的名称
     * @since 12.0
     */
    String getConfigurationBeanMethod();
}
