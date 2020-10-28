// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.annotation;

import com.internet.kael.ioc.support.name.BeanNameStrategy;
import com.internet.kael.ioc.support.name.DefaultBeanNameStrategy;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 22.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComponentScan {
    /**
     * 扫描包的名称
     * @since 22.0
     */
    String[] value();

    /**
     * 排除的注解
     * @since 22.0
     */
    Class<? extends Annotation>[] excludes() default {};

    /**
     * 包含的注解
     * @since 22.0
     */
    Class<? extends Annotation>[] includes() default {Component.class, Service.class};

    /**
     * 对象的命名策略
     * @since 22.0
     */
    Class<? extends BeanNameStrategy> beanNameStrategy() default DefaultBeanNameStrategy.class;
}
