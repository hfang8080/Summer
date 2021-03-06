// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * 属性参数定义
 *
 * @author Kael He (h_fang8080@163.com)
 * @since 7.0
 */
public interface PropertyArgsDefinition {

    /**
     * 属性名称
     * @return 属性名称
     * @since 7.0
     */
    String getName();

    /**
     * 设置属性名称
     * @param name
     * @since 7.0
     */
    void setName(final String name);

    /**
     * 获取属性值
     * @return 属性值
     * @since 7.0
     */
    String getValue();

    /**
     * 设置属性值
     * @param value 属性值
     * @since 7.0
     */
    void setValue(final String value);

    /**
     * 获取属性的类型
     * @return 属性的类型
     * @since 7.0
     */
    String getType();

    /**
     * 设置属性类型
     * @param type 属性类型
     * @since 7.0
     */
    void setType(final String type);

    /**
     * 获取属性引用
     * @return 属性引用
     * @since 7.0
     */
    String getRef();

    /**
     * 设置属性引用
     * @param ref 属性引用
     * @since 7.0
     */
    void setRef(final String ref);

    /**
     * 是否基于字段
     * @return 是否
     * @since 21.0
     */
    boolean isFieldBase();

    /**
     * 设置是否基于字段
     * @since 21.0
     */
    void setFieldBase(boolean fieldBase);

}
