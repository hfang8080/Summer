// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Profile;
import com.internet.kael.ioc.bean.Book;
import com.internet.kael.ioc.constant.ProfileConstant;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 19.0
 */
@Configuration
@Profile({ProfileConstant.DEV, ProfileConstant.TEST})
public class AppProfileConfig {

    @Bean
    @Profile(ProfileConstant.DEV)
    public Book book1() {
        return new Book();
    }

    @Bean
    @Profile(ProfileConstant.TEST)
    public Book book2() {
        return new Book();
    }
}
