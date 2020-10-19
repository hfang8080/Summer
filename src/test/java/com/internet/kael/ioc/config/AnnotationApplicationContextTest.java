// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.context.AnnotationApplicationContext;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 12.0
 */
public class AnnotationApplicationContextTest {
    @Test
    public void getBean() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(AppleBeanConfig.class);
        Apple apple = ac.getBean("apple", Apple.class);
        assertNotNull(apple);
    }

    @Test
    public void scopeLazy() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(LazyScopeConfig.class);
        Object bean1 = ac.getBean("weightedApple");
        Object bean2 = ac.getBean("weightedApple");
        assertEquals(bean1, bean2);
        Object apple1 = ac.getBean("apple");
        Object apple2 = ac.getBean("apple");
        assertNotEquals(apple1, apple2);
    }
}
