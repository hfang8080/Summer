// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Autowired;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.support.environment.Environment;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 20.0
 */
@Configuration
public class AppEnvAutowiredConfig {

    @Autowired
    private Environment environment;

    public String[] getActiveProfiles() {
        return environment.getActiveProfiles();
    }

}
