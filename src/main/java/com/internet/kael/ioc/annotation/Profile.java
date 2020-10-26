// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.annotation;

import com.internet.kael.ioc.support.condition.ProfileCondition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 19.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Conditional(ProfileCondition.class)
public @interface Profile {
    /**
     * 环境名
     * @return 环境名
     * @since 19.0
     */
    String[] value();
}
