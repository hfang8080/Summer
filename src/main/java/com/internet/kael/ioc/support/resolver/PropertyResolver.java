// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 21.0
 */
public interface PropertyResolver {

    /**
     * 是否包含属性
     * @return 是否
     * @since 21.0
     */
    boolean containsProperty(String property);

    /**
     * 通过属性名获取值
     * @param key 属性名
     * @return 值
     * @since 21.0
     */
    String getProperty(String key);

    /**
     * 通过属性名获取值
     * @param key 属性名
     * @param defaultValue 默认值
     * @return 值
     * @since 21.0
     */
    String getProperty(String key, String defaultValue);

    /**
     * 获取必要的值
     * @return 值
     * @since 21.0
     */
    String getRequiredProperty(String key);

    /**
     * 解析占位符
     * @param text 需要解析的表达式
     * @return 解析过的值
     * @since 21.0
     */
    String resolvePlaceholders(String text);

    /**
     * 解析必要的占位符
     * @param text 需要解析的表达式
     * @return 解析过的值
     * @since 21.0
     */
    String resolveRequiredPlaceholders(String text);
}
