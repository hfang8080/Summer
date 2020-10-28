// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Lazy;
import com.internet.kael.ioc.annotation.Scope;
import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.bean.WeightedApple;
import com.internet.kael.ioc.constant.SummerConstant;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 13.0
 */
@Configuration
public class LazyScopeConfig {

    @Bean
    public WeightedApple weightedApple() {
        return new WeightedApple();
    }

    @Bean
    @Scope(SummerConstant.PROTOTYPE)
    @Lazy(true)
    public Apple apple() {
        return new Apple();
    }
}
