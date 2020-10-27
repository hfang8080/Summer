// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.resolver;

import java.util.Map;
import java.util.Properties;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 21.0
 */
public class PropertiesPropertySource extends MapPropertySource {

    @SuppressWarnings("unchecked")
    public PropertiesPropertySource(String name, Properties source) {
        super(name, (Map) source);
    }
}
