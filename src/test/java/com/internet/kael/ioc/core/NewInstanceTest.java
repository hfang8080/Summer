// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.bean.WeightedApple;
import com.internet.kael.ioc.context.JsonApplicationContext;
import org.junit.Test;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 6.0
 */
public class NewInstanceTest {

    @Test
    public void getBean() {
        JsonApplicationContext ac = new JsonApplicationContext("bean-factory/weighted-apples.json");
        WeightedApple weightedApple = ac.getBean("weightedApple", WeightedApple.class);
        System.out.println(weightedApple);
    }

    @Test
    public void getBeanByFactoryMethod() {
        JsonApplicationContext ac = new JsonApplicationContext("bean-factory/factory-method-apple.json");
        Apple apple = ac.getBean("apple", Apple.class);
        System.out.println(apple);
    }
}
