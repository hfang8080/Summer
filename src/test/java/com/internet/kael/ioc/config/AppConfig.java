// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Configuration;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 11.0
 */
@Configuration
public class AppConfig {
    public String doSomething() {
        return "result";
    }
}
