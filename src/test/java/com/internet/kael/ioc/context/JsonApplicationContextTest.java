// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.core.BeanFactory;
import org.junit.Test;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 1.0
 */
public class JsonApplicationContextTest {

    @Test
    public void loadBeans() {
        BeanFactory beanFactory = new JsonApplicationContext("apple.json");
        Apple apple = beanFactory.getBean("apple", Apple.class);
        apple.introduce();

    }

}
