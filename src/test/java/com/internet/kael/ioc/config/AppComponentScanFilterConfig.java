// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.ComponentScan;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Service;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 22.0
 */
@Configuration
@ComponentScan(value = "com.internet.kael.ioc.bean.scan", excludes = Service.class)
public class AppComponentScanFilterConfig {
}
