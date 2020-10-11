// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.bean.NamedApple;
import com.internet.kael.ioc.context.JsonApplicationContext;
import com.internet.kael.ioc.exception.IocRuntimeException;
import org.junit.Test;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 10.0
 */
public class CircleTest {

    @Test(expected = IocRuntimeException.class)
    public void directCircleTest() {
        BeanFactory beanFactory = new JsonApplicationContext("circle/direct-circle.json");
        NamedApple apple = beanFactory.getBean("namedApple", NamedApple.class);
    }


    @Test(expected = IocRuntimeException.class)
    public void inDirectCircleTest() {
        BeanFactory beanFactory = new JsonApplicationContext("circle/in-direct-circle.json");
        NamedApple apple = beanFactory.getBean("namedApple", NamedApple.class);
    }
}
