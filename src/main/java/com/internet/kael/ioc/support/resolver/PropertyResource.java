// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 21.0
 */
public interface PropertyResource<T> {

    String getName();

    boolean containsProperty(String name);

    Object getProperty(String name);
}
