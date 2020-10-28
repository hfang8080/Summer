// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 21.0
 */
public interface PropertyResource<T> {

    String getName();

    boolean containsProperty(String name);

    Object getProperty(String name);
}
