// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Autowired;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Import;
import com.internet.kael.ioc.bean.Apple;

import java.util.List;

/**
 * @author Kael He (kael.he@alo7.com)
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
