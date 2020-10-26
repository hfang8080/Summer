// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.environment;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 19.0
 */
public interface Environment {
    /**
     * 获取活跃的环境
     * @return 活跃的环境
     * @since 19.0
     */
    String[] getActiveProfiles();
}
