// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.Conditional;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.bean.Book;
import com.internet.kael.ioc.support.condition.FalseCondition;
import com.internet.kael.ioc.support.condition.TrueCondition;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 18.0
 */
@Configuration
public class AppConditionConfig {

    @Bean
    @Conditional(TrueCondition.class)
    public Book book1() {
        return new Book();
    }

    @Bean
    @Conditional(FalseCondition.class)
    public Book book2() {
        return new Book();
    }
}
