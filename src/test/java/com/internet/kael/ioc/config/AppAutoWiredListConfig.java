// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Autowired;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Import;
import com.internet.kael.ioc.bean.Apple;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
 */
@Configuration
@Import(MultiBeanConfig.class)
public class AppAutoWiredListConfig {

    @Autowired
    private List<Apple> apples;

    public List<Apple> getApples() {
        return apples;
    }
}
