// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.core.BeanFactory;
import org.junit.Test;

/**
 * @author Kael He(kael.he@alo7.com)
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
