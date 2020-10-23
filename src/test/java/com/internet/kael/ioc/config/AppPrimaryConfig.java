// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Primary;
import com.internet.kael.ioc.bean.Book;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 17.0
 */
@Configuration
public class AppPrimaryConfig {

    @Bean
    public Book book1() {
        return new Book();
    }

    @Bean
    @Primary
    public Book book2() {
        return new Book("Hello");
    }
}
