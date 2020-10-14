// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.config.AppConfig;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 11.0
 */
public class AnnotationApplicationContextTest {

    @Test
    public void testConfigLoad() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(AppConfig.class);
        AppConfig config = ac.getBean("appConfig", AppConfig.class);
        assertEquals("result", config.doSomething());
    }
}
