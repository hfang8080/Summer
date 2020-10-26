// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.environment;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 19.0
 */
public interface ConfigurableEnvironment extends Environment {

    /**
     * 设置活跃的环境
     * @param profiles 环境
     * @since 19.0
     */
    void setActiveProfiles(String... profiles);
}
