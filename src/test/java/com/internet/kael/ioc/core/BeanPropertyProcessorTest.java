// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.context.JsonApplicationContext;
import org.junit.Test;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 7.0
 */
public class BeanPropertyProcessorTest {

    @Test
    public void test() {
        JsonApplicationContext ac = new JsonApplicationContext(
                "property-processor/property-processor-apple.json");
        Object bean = ac.getBean("namedApple");
        System.out.println(bean);
    }
}
