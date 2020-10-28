// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.bean.Apple;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 16.0
 */
@Configuration
public class MultiBeanConfig {
    @Bean
    public Apple apple1() {
        return new Apple();
    }

    @Bean
    public Apple apple2() {
        return new Apple();
    }
}
