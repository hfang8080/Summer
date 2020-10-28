// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.bean.scan;

import com.internet.kael.ioc.annotation.Component;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 22.0
 */
@Component("manager")
public class FooManager {
    public String getName() {
        return "manager";
    }
}
