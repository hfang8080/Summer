// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.bean;

import com.internet.kael.ioc.annotation.Autowired;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Import;
import com.internet.kael.ioc.config.AppPrimaryConfig;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 17.0
 */
@Configuration
@Import(AppPrimaryConfig.class)
public class BookNamePrint {
    @Autowired
    Book book;

    public String name() {
        return book.name();
    }
}
