// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.exception.IocRuntimeException;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class ClassUtils {

    /**
     * 获取当前的ClassLoader
     *
     * @return 当前的ClassLoader
     * @since 1.0
     */
    public static ClassLoader currentClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 根据Class信息
     *
     * @param className 类名称
     * @return Class对象
     * @since 1.0
     */
    public static Class getClass(final String className) {
        Preconditions.checkNotNull(className);
        try {
            return currentClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new IocRuntimeException(e);
        }
    }
    /**
     * 根据Class对象的无参构造创建实例
     *
     * @param clazz Class对象
     * @return 对象实例
     * @since 1.0
     */
    public static Object newInstance(final Class clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IocRuntimeException(e);
        }
    }
}
