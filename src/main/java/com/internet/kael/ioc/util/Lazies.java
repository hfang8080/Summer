// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.annotation.Lazy;

import java.lang.reflect.Method;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 13.0
 */
public class Lazies {

    /**
     * 通过Class来判断Bean是否为lazy
     * @param clazz 是否是懒加载
     * @return 是否懒加载
     */
    public static boolean isLazy(Class clazz) {
        Preconditions.checkNotNull(clazz);
        if (clazz.isAnnotationPresent(Lazy.class)) {
            Lazy annotation = (Lazy) clazz.getAnnotation(Lazy.class);
            return annotation.value();
        }
        return false;
    }

    public static boolean isLazy(Method method) {
        Preconditions.checkNotNull(method);
        if (method.isAnnotationPresent(Lazy.class)) {
            Lazy annotation = method.getAnnotation(Lazy.class);
            return annotation.value();
        }
        return false;
    }
}
