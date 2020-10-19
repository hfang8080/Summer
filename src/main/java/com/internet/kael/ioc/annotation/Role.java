// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于指定角色的信息
 * @author Kael He (kael.he@alo7.com)
 * @since 14.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Role {

    String value() default "";
}
