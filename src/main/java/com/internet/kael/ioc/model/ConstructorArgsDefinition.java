// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * 构造器的参数的定义信息
 * 使用方式：
 * 1) 当我们传入一个Bean，只需要设置ref，这样会把相应名称的bean注入进来
 * 2) 当我们传入一个值，则同时需要设置value和type两个参数，这样会根据传入的type把value转成相应的数据类型
 *
 * 难点：当传入一个对象时候则需要解决循环依赖问题
 *
 * 还有二种方式（未实现）：
 *  通过参数的索引位置进行默认映射。
 *  通过参数的name进行映射
 *
 * @author Kael He (h_fang8080@163.com)
 */
public interface ConstructorArgsDefinition {
    /**
     * 设置参数引用名称（Bean Name）
     * @param ref 引用名称（Bean Name）
     * @since 6.0
     */
    public void setRef(final String ref);

    /**
     * 获取参数引用名称（Bean Name）
     * @return 参数的引用名称 （Bean Name）
     * @since 6.0
     */
    public String getRef();

    /**
     * 设置参数类型
     * @param type 参数类型
     * @since 6.0
     */
    public void setType(final String type);

    /**
     * 获取参数类型
     * @return 参数类型
     * @since 6.0
     */
    public String getType();

    /**
     * 设置值
     * @param value 值
     * @since 6.0
     */
    public void setValue(final String value);

    /**
     * 获取值
     * @return 值
     * @since 6.0
     */
    public String getValue();
}
