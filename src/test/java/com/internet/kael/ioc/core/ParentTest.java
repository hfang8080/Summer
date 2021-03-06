// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.context.JsonApplicationContext;
import com.internet.kael.ioc.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 9.0
 */
public class ParentTest {

    @Test
    public void test() {
        final BeanFactory beanFactory = new JsonApplicationContext("parent/user-parent.json");
        User copyUser = beanFactory.getBean("copyUser", User.class);
        assertEquals("helen", copyUser.getName());
    }
}
