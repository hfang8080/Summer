// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.bean.BookNamePrint;
import com.internet.kael.ioc.config.AppAutoWiredListConfig;
import com.internet.kael.ioc.config.AppAutowiredConfig;
import com.internet.kael.ioc.config.AppConditionConfig;
import com.internet.kael.ioc.config.AppConfig;
import com.internet.kael.ioc.config.AppEnvAutowiredConfig;
import com.internet.kael.ioc.config.AppProfileConfig;
import com.internet.kael.ioc.config.AppleBeanConfig;
import com.internet.kael.ioc.config.AppleMethodBeanRefConfig;
import com.internet.kael.ioc.config.ImportAppConfig;
import com.internet.kael.ioc.config.LazyScopeConfig;
import com.internet.kael.ioc.constant.ProfileConstant;
import com.internet.kael.ioc.support.environment.DefaultEnvironment;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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

    @Test
    public void primary() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(BookNamePrint.class);
        BookNamePrint bookNamePrint = ac.getBean("bookNamePrint", BookNamePrint.class);
        assertEquals("Hello", bookNamePrint.name());
    }

    @Test
    public void condition() {
        AnnotationApplicationContext ac = new AnnotationApplicationContext(AppConditionConfig.class);
        assertTrue(ac.containsBean("book1"));
        assertFalse(ac.containsBean("book2"));
    }

    @Test
    public void profile() {
        DefaultEnvironment environment1 = new DefaultEnvironment();
        environment1.setActiveProfiles(ProfileConstant.DEV);
        AnnotationApplicationContext ac = new AnnotationApplicationContext(environment1, AppProfileConfig.class);
        assertTrue(ac.containsBean("book1"));
        assertFalse(ac.containsBean("book2"));

        DefaultEnvironment environment2 = new DefaultEnvironment();
        environment2.setActiveProfiles(ProfileConstant.DEV, ProfileConstant.TEST);
        AnnotationApplicationContext ac2 = new AnnotationApplicationContext(environment2, AppProfileConfig.class);
        assertTrue(ac2.containsBean("book1"));
        assertTrue(ac2.containsBean("book2"));
    }

    @Test
    public void autowireEnv() {
        DefaultEnvironment environment = new DefaultEnvironment();
        environment.setActiveProfiles(ProfileConstant.DEV);
        AnnotationApplicationContext ac = new AnnotationApplicationContext(environment, AppEnvAutowiredConfig.class);
        String profiles = Arrays.toString(ac.getBean("appEnvAutowiredConfig", AppEnvAutowiredConfig.class)
                .getActiveProfiles());
        assertEquals("[develop]", profiles);
    }
}
