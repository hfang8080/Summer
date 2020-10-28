// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

import java.util.Map;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 21.0
 */
public class MapPropertySource implements PropertyResource<Map<String, Object>> {

    private String name;
    private Map<String, Object> source;

    public MapPropertySource(String name, Map<String, Object> source) {
        this.name = name;
        this.source = source;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean containsProperty(String name) {
        return source.containsKey(name);
    }

    @Override
    public Object getProperty(String name) {
        return source.get(name);
    }
}
