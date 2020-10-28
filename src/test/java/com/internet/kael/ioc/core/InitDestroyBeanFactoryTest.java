// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.context.JsonApplicationContext;
import org.junit.Test;

/**
 * @author Kael He(h_fang8080@163.com)
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
