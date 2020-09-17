// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public interface BeanDefinition {
    /**
     * 获取Bean名称
     *
     * @return Bean名称
     * @since 1.0
     */
    String getName();

    /**
     * 设置Bean名称
     *
     * @param name Bean名称
     * @since 1.0
     */
    void setName(final String name);
    /**
     * 获取Bean的类名称
     *
     * @return 类名称
     * @since 1.0
     */
    String getClassName();

    /**
     * 设置类名称
     *
     * @param className 类名称
     * @since 1.0
     */
    void setClassName(final String className);

}
