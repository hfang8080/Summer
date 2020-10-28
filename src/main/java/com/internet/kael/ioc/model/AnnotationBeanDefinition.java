// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.model;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
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

    /**
     * 设置配置类中Bean的参数类型列表
     * @param classes Bean的参数类型列表
     * @since 15.0
     */
    void setConfigBeanMethodParamTypes(final Class[] classes);

    /**
     * 获取配置类中Bean的参数类型列表
     * @return Bean的参数类型列表
     * @since 15.0
     */
    Class[] getConfigBeanMethodParamTypes();

    /**
     * 设置配置类依赖的对象列表
     * @param refs 依赖对象列表
     * @since 15.0
     */
    void setConfigBeanMethodParamRefs(final List<String> refs);

    /**
     * 获取配置类依赖对象的列表
     * @return 依赖对象的列表
     * @since 15.0
     */
    List<String> getConfigBeanMethodParamRefs();

}
