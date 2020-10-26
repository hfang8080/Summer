// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.constant;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 12.0
 */
public enum BeanSourceType {
    /**
     * 通过资源文件配置
     * @since 12.0
     */
    RESOURCE,
    /**
     * 通过注解类
     * @since 12.0
     */
    CONFIGURATION,
    /**
     * 配置直接Bean类
     * @since 12.0
     */
    CONFIGURATION_BEAN,

    /**
     * 用来支持Spring框架的运行
     * @since 20.0
     */
    SUPPORT;
}
