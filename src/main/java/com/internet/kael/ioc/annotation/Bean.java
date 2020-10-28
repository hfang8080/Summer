// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 12.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {

    /**
     * Bean的名称
     * @return Bean名称
     * @since 12.0
     */
    String value() default "";

    /**
     * 初始化的方法
     * @return 初始化方法
     * @since 12.0
     */
    String initMethod() default "";

    /**
     * 销毁的方法
     * @return 销毁的方法
     * @sicne 12.0
     */
    String destroyMethod() default "";
}
