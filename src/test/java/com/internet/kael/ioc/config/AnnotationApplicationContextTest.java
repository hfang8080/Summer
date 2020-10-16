// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.context.AnnotationApplicationContext;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;

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
}
