// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

import java.util.Map;
import java.util.Properties;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 21.0
 */
public class PropertiesPropertySource extends MapPropertySource {

    @SuppressWarnings("unchecked")
    public PropertiesPropertySource(String name, Properties source) {
        super(name, (Map) source);
    }
}
