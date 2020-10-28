// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.annotation.Scope;
import com.internet.kael.ioc.constant.SummerConstant;

import java.lang.reflect.Method;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 13.0
 */
public class Scopes {
    /**
     * 获取当前Class配置的Bean scope
     * @param clazz Class对象
     * @return 配置的Bean scope
     */
    public static String getScope(Class clazz) {
        Preconditions.checkNotNull(clazz);
        if (clazz.isAnnotationPresent(Scope.class)) {
            Scope annotation = (Scope) clazz.getAnnotation(Scope.class);
            return annotation.value();
        }
        return SummerConstant.SINGLETON;
    }

    public static String getScope(Method method) {
        Preconditions.checkNotNull(method);
        if (method.isAnnotationPresent(Scope.class)) {
            Scope annotation = method.getAnnotation(Scope.class);
            return annotation.value();
        }
        return SummerConstant.SINGLETON;
    }
}
