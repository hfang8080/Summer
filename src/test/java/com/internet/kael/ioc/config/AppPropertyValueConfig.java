// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Value;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 21.0
 */
@Configuration
public class AppPropertyValueConfig {
    /**
     * 名称
     * @since 0.1.10
     */
    @Value("${name:kael}")
    private String name;

    public String getName() {
        return this.name;
    }
}
