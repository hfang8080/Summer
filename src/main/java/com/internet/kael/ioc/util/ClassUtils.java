// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.internet.kael.ioc.exception.IocRuntimeException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class ClassUtils {

    private static final String SET = "set";

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
//    /**
//     * 根据Class对象的无参构造创建实例
//     *
//     * @param clazz Class对象
//     * @return 对象实例
//     * @since 1.0
//     */
//    public static Object newInstance(final Class clazz) {
//        try {
//            return clazz.newInstance();
//        } catch (InstantiationException | IllegalAccessException e) {
//            throw new IocRuntimeException(e);
//        }
//    }

    /**
     * 根据Class对象的无参构造创建实例
     *
     * @param clazz Class对象
     * @return 对象实例
     * @since 1.0
     */
    public static <T> T newInstance(final Class<T> clazz) {
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
     * 获取指定类拥有指定注解的函数
     * @param clazz 指定类
     * @param methodName 方法名
     * @return 函数
     */
    public static Optional<Method> getMethod(final Class clazz, String methodName) {
        Method[] methods = clazz.getMethods();
        if (ArrayUtils.isEmpty(methods)) {
            return Optional.empty();
        }
        for (Method method: methods) {
            if (StringUtils.equals(method.getName(), methodName)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }

    /**
     * 反射调用指定实例的无参方法
     * @param method 指定函数
     * @param instance 指定实例
     * @return 返回值
     */
    public static Object invokeNoArgsMethod(Object instance, Method method) {
        Preconditions.checkNotNull(method);
        Preconditions.checkNotNull(instance);
        int count = method.getParameterCount();
        if (count > 0) {
            throw new IocRuntimeException("Method can not with any parameters.");
        }
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用指定方法。
     * @param instance 实例对象
     * @param method 方法
     * @param params 参数
     * @return 调用的返回结果
     */
    public static Object invokeMethod(Object instance, Method method, Object... params) {
        Preconditions.checkNotNull(method);
        Preconditions.checkNotNull(instance);
        try {
            return method.invoke(instance, params);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 获取构造函数
     * @param clazz Class对象
     * @param paramTypes 参数的Class对象
     * @return 构造函数
     */
    public static Constructor getConstructor(Class clazz, Class<?>... paramTypes) {
        Preconditions.checkNotNull(clazz);
        try {
            return clazz.getConstructor(paramTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过构造函数实例化
     * @param constructor 构造函数
     * @param args 参数
     * @return 实例
     */
    public static Object newInstance(Constructor constructor, Object... args) {
        Preconditions.checkNotNull(constructor);
        try {
            return constructor.newInstance(args);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用setter函数
     * @param instance 需要设置属性的实例
     * @param propertyName 属性名
     * @param value 值
     */
    public static void invokeSetterMethod(final Object instance,
                                          final String propertyName,
                                          final Object value) {
        Preconditions.checkNotNull(instance);
        Preconditions.checkNotNull(propertyName);
        if (Objects.isNull(value)) return;
        Class<?> clazz = instance.getClass();
        String methodName = SET + StringUtils.capitalize(propertyName);
        Class<?> paramType = value.getClass();
        try {
            Method method = clazz.getMethod(methodName, paramType);
            method.invoke(instance, value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取class下面的所有的方法名
     * @param clazz Class对象
     * @return 所有方法名
     */
    public static List<Method> getMethods(Class clazz) {
        Method[] methods = clazz.getMethods();
        return Arrays.asList(methods);
    }

    /**
     * 获取方法的所有参数的名称。
     * @param method 方法
     * @return 方法所有的参数的名称
     */
    public static List<String> getMethodParamNames(Method method) {
        return Stream.of(method.getParameters())
                .map(Parameter::getName)
                .collect(Collectors.toList());
    }

    /**
     * 获取Class对象的所有字段
     * @param clazz Class对象
     * @return 所有的字段
     */
    public static List<Field> getAllFields(Class clazz) {
        Preconditions.checkNotNull(clazz);
        List<Field> fields = Lists.newArrayList();
        for (Class tmpClass = clazz; tmpClass != null; tmpClass = tmpClass.getSuperclass()) {
            fields.addAll(Lists.newArrayList(tmpClass.getDeclaredFields()));
        }
        for (Field field : fields) {
            field.setAccessible(true);
        }
        return fields;
    }

    /**
     * 像指定的实例的字段中设置值
     * @param instance 实例
     * @param field 字段
     * @param value 值
     */
    public static void setFieldValue(Object instance, Field field, Object value) {
        Preconditions.checkNotNull(instance);
        Preconditions.checkNotNull(field);
        field.setAccessible(true);
        try {
            field.set(instance, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定类型的集合
     * @param type 指定的集合类型
     * @return 集合
     */
    public static Collection createCollection(Type type) {
        return createCollection(type, 8);
    }

    private static Collection createCollection(Type type, int size) {
        Class<?> rawClass = getRawClass(type);
        Object list;
        if (rawClass != AbstractCollection.class && rawClass != Collection.class) {
            if (rawClass.isAssignableFrom(HashSet.class)) {
                list = new HashSet(size);
            } else if (rawClass.isAssignableFrom(LinkedHashSet.class)) {
                list = new LinkedHashSet(size);
            } else if (rawClass.isAssignableFrom(TreeSet.class)) {
                list = new TreeSet();
            } else if (rawClass.isAssignableFrom(ArrayList.class)) {
                list = new ArrayList(size);
            } else if (rawClass.isAssignableFrom(EnumSet.class)) {
                Type itemType = getGenericType(type);
                list = EnumSet.noneOf((Class)itemType);
            } else if (rawClass.isAssignableFrom(Queue.class)) {
                list = new LinkedList();
            } else {
                try {
                    list = (Collection)rawClass.newInstance();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    throw new RuntimeException(ex);
                }
            }
        } else {
            list = new ArrayList(size);
        }

        return (Collection)list;
    }

    private static Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        } else if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType)type).getRawType());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static Class getGenericType(Type type) {
        Object itemType;
        if (type instanceof ParameterizedType) {
            itemType = ((ParameterizedType)type).getActualTypeArguments()[0];
        } else {
            itemType = Object.class;
        }

        return (Class)itemType;
    }

    /**
     * 获取对应的注解属性 map
     * https://segmentfault.com/a/1190000011213222?utm_source=tag-newest
     * @param annotation 直接
     * @return map
     * @since 0.1.31
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getAnnotationAttributes(final Annotation annotation) {
        try {
            //获取 annotation 这个代理实例所持有的 InvocationHandler
            InvocationHandler h = Proxy.getInvocationHandler(annotation);
            // 获取 AnnotationInvocationHandler 的 memberValues 字段
            Field hField = h.getClass().getDeclaredField("memberValues");
            // 因为这个字段事 private final 修饰，所以要打开权限
            hField.setAccessible(true);

            // 获取 memberValues
            return (Map<String, Object>) hField.get(h);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
