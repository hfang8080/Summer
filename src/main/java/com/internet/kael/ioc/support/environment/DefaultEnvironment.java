// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.environment;

import com.internet.kael.ioc.constant.ProfileConstant;

/**
 * 默认的环境
 * @author Kael He (kael.he@alo7.com)
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
