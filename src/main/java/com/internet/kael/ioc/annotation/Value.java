// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 21.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Value {

    /**
     * 注入的值或者表达式
     * @since 21.0
     */
    String value();
}
