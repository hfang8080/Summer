// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 21.0
 */
public interface ConfigurablePropertyResolver extends PropertyResolver {
    /**
     * 设置前缀
     * @since 21.0
     */
    void setPlaceholderPrefix(String prefix);
    /**
     * 设置后缀
     * @since 21.0
     */
    void setPlaceholderSuffix(String suffix);
    /**
     * 设置分隔符
     * @since 21.0
     */
    void setValueSeparator(String separator);
}
