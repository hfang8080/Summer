// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.context.JsonApplicationContext;
import org.junit.Test;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 4.0
 */
public class InitDestroyBeanFactoryTest {

    @Test
    public void initDestroy() {
        JsonApplicationContext ac = new JsonApplicationContext("init-method/apple-init-method.json");
        Apple apple = ac.getBean("apple", Apple.class);
        apple.introduce();
    }
}
