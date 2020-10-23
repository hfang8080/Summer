// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.internet.kael.ioc.annotation.Primary;

import java.lang.reflect.Method;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 17.0
 */
public class Primaries {

    public static boolean isPrimary(Class clazz) {
        if (clazz.isAnnotationPresent(Primary.class)) {
            Primary primary = (Primary) clazz.getAnnotation(Primary.class);
            return primary.value();
        }
        return false;
    }

    public static boolean isPrimary(Method method) {
        if (method.isAnnotationPresent(Primary.class)) {
            Primary primary = method.getAnnotation(Primary.class);
            return primary.value();
        }
        return false;
    }
}
