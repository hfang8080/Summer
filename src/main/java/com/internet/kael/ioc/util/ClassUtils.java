// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.exception.IocRuntimeException;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

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

    /**
     * 获取指定类拥有指定注解的函数
     * @param clazz 指定类
     * @param annotationClazz 指定注解
     * @return 函数
     */
    public static Optional<Method> getMethod(final Class clazz, final Class<? extends Annotation> annotationClazz) {
        Method[] methods = clazz.getMethods();
        if (ArrayUtils.isEmpty(methods)) {
            return Optional.empty();
        }
        for (Method method: methods) {
            if (method.isAnnotationPresent(annotationClazz)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }

    /**
     * 反射调用指定实例的无参方法
     * @param method 指定函数
     * @param instance 指定实例
     */
    public static void invokeNoArgsMethod(Object instance, Method method) {
        Preconditions.checkNotNull(method);
        Preconditions.checkNotNull(instance);
        int count = method.getParameterCount();
        if (count > 0) {
            throw new IocRuntimeException("Method can not with any parameters.");
        }
        try {
            method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
