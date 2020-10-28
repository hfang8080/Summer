// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Autowired;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Import;
import com.internet.kael.ioc.bean.Apple;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 16.0
 */
@Configuration
@Import(AppleBeanConfig.class)
public class AppAutowiredConfig {
    @Autowired
    private Apple apple;
    public String introduce() {
        return apple.getColor();
    }
}
