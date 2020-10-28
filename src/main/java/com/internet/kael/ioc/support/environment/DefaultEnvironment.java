// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.environment;

import com.internet.kael.ioc.constant.ProfileConstant;

/**
 * 默认的环境
 * @author Kael He (h_fang8080@163.com)
 * @since 19.0
 */
public class DefaultEnvironment implements ConfigurableEnvironment {

    private String[] profiles = new String[] {ProfileConstant.DEFAULT};

    @Override
    public void setActiveProfiles(String... profiles) {
        this.profiles = profiles;
    }

    @Override
    public String[] getActiveProfiles() {
        return profiles;
    }
}
