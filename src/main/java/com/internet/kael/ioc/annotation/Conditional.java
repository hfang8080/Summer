// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.annotation;

import com.internet.kael.ioc.support.condition.Condition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识一个对象是否被初始化。
 * （1）如果这个注解注释在类上，那么他将对该类和他包含的所有方法都将赋予这个属性。
 * （2）如果这个注解在一个方法上，则只有这个方法被赋予这个属性。
 * （3）如果标注在注解上，那么这个注解所标注的注解也将被赋予这个属性。
 * @author Kael He (h_fang8080@163.com)
 * @since 18.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface Conditional {
    Class<? extends Condition> value();
}
