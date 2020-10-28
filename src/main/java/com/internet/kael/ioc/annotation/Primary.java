// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 17.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Primary {
    boolean value() default true;
}
