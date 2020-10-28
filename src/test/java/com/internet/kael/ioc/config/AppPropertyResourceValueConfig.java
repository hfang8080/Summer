// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.PropertiesResource;
import com.internet.kael.ioc.annotation.Value;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 21.0
 */
@Configuration
@PropertiesResource("name.properties")
public class AppPropertyResourceValueConfig {
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
