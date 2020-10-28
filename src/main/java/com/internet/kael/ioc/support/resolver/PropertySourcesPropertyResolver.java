// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.internet.kael.ioc.exception.IocRuntimeException;

import java.util.List;
import java.util.Objects;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 21.0
 */
public class PropertySourcesPropertyResolver implements ConfigurablePropertyResolver {

    /**
     * 前缀
     *
     * @since 21.0
     */
    private String placeholderPrefix = "${";

    /**
     * 后缀
     *
     * @since 21.0
     */
    private String placeholderSuffix = "}";

    /**
     * 分隔符
     *
     * @since 21.0
     */
    private String valueSeparator = ":";

    /**
     * 可以遍历的资源信息
     *
     * @since 21.0
     */
    private List<PropertyResource> propertyResources = Lists.newArrayList();

    public PropertySourcesPropertyResolver(List<PropertyResource> propertyResources) {
        this.propertyResources = propertyResources;
    }

    @Override
    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    @Override
    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }

    @Override
    public void setValueSeparator(String valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    @Override
    public boolean containsProperty(String property) {
        for (PropertyResource propertySource : propertyResources) {
            if (propertySource.containsProperty(property)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getProperty(String key) {
        return getProperty(key, null);
    }

    @Override
    public String getProperty(String key, String defaultValue) {
        for (PropertyResource propertySource : propertyResources) {
            if (propertySource.containsProperty(key)) {
                return (String) propertySource.getProperty(key);
            }
        }
        return defaultValue;
    }

    @Override
    public String getRequiredProperty(String key) {
        String value = getProperty(key);
        if (Objects.isNull(value)) {
            throw new IocRuntimeException("Can't resolve property for key " + key);
        }
        return value;
    }

    @Override
    public String resolvePlaceholders(String text) {
        Preconditions.checkNotNull(text);
        return resolvePlaceholder(text, false);
    }

    @Override
    public String resolveRequiredPlaceholders(String text) {
        Preconditions.checkNotNull(text);
        return resolvePlaceholder(text, true);
    }

    private String resolvePlaceholder(final String text, final boolean required) {
        if (text.startsWith(placeholderPrefix) && text.endsWith(placeholderSuffix)) {
            String placeholder = text.substring(2, text.length() - 1);

            String[] strings = placeholder.split(valueSeparator);
            String key = strings[0];
            if (strings.length == 1) {
                // 断言必须存在
                return getRequiredProperty(key);
            }

            // 第一个为键，第二个为默认值
            String defaultValue = strings[1];
            return getProperty(key, defaultValue);
        }

        return text;
    }
}
