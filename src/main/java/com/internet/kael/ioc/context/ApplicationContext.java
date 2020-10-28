// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.support.environment.EnvironmentCapable;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 4.0
 */
public interface ApplicationContext extends EnvironmentCapable {
    /**
     * 获取应用的名称
     * @return 应用名称
     * @since 4.0
     */
    String getApplicationName();
}
