// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.config.AppAutoWiredListConfig;
import com.internet.kael.ioc.config.AppAutowiredConfig;
import com.internet.kael.ioc.config.AppConfig;
import com.internet.kael.ioc.config.AppleBeanConfig;
import com.internet.kael.ioc.config.AppleMethodBeanRefConfig;
import com.internet.kael.ioc.config.ImportAppConfig;
import com.internet.kael.ioc.config.LazyScopeConfig;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void testImport() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(ImportAppConfig.class);
        Object bean = ac.getBean("apple");
        assertNotNull(bean);
    }

    @Test
    public void getBean() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(AppleBeanConfig.class);
        Apple apple = ac.getBean("apple", Apple.class);
        TestCase.assertNotNull(apple);
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

    @Test
    public void configBeanMethodArgs() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(AppleMethodBeanRefConfig.class);
        Object bean = ac.getBean("weightedApple");
        assertNotNull(bean);
    }

    @Test
    public void autowired() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(AppAutowiredConfig.class);
        AppAutowiredConfig config = ac.getBean("appAutowiredConfig", AppAutowiredConfig.class);
        assertEquals("Black", config.introduce());
    }

    @Test
    public void autowiredList() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(AppAutoWiredListConfig.class);
        AppAutoWiredListConfig config = ac.getBean("appAutoWiredListConfig", AppAutoWiredListConfig.class);
        assertEquals(2, config.getApples().size());
    }
}
