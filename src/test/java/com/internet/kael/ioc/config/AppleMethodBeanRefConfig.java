// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.bean.WeightedApple;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 15.0
 */
@Configuration
public class AppleMethodBeanRefConfig {

    @Bean
    public Apple apple() {
        return new Apple();
    }

    @Bean
    public WeightedApple weightedApple(Apple apple) {
        return new WeightedApple(apple, 1);
    }
}
