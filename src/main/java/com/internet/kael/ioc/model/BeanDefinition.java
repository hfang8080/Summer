// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.model;

import com.internet.kael.ioc.constant.BeanSourceType;

import java.util.List;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 1.0
 */
public interface BeanDefinition {
    /**
     * 获取Bean名称
     *
     * @return Bean名称
     * @since 1.0
     */
    String getName();

    /**
     * 设置Bean名称
     *
     * @param name Bean名称
     * @since 1.0
     */
    void setName(final String name);

    /**
     * 获取Bean的类名称
     *
     * @return 类名称
     * @since 1.0
     */
    String getClassName();

    /**
     * 设置类名称
     *
     * @param className 类名称
     * @since 1.0
     */
    void setClassName(final String className);

    /**
     * 获取作用域
     *
     * @return 作用域
     * @since 3.0
     */
    String getScope();

    /**
     * 设置作用域
     *
     * @param scope 作用域
     * @since 3.0
     */
    void setScope(final String scope);

    /**
     * 是否是延迟加载
     *
     * @return 是否
     * @since 3.0
     */
    boolean isLazyInit();

    /**
     * 设置是否是延迟加载
     *
     * @param lazyInit 是否是延迟加载
     * @since 3.0
     */
    void setLazyInit(final boolean lazyInit);

    /**
     * 获取初始化的方法名
     *
     * @return 初始化的方法名
     * @since 4.0
     */
    String getInitMethodName();

    /**
     * 设置初始化的方法名
     *
     * @param initMethodName 方法名
     * @since 4.0
     */
    void setInitMethodName(String initMethodName);

    /**
     * 获取销毁的方法名
     *
     * @return 销毁的方法名
     * @since 4.0
     */
    String getDestroyMethodName();

    /**
     * 设置销毁的方法名
     *
     * @param destroyMethodName 销毁的方法名
     * @since 4.0
     */
    void setDestroyMethodName(String destroyMethodName);

    /**
     * 设置Bean工厂的方法名
     *
     * @param beanFactoryMethodName Bean工厂的方法名
     * @since 6.0
     */
    void setBeanFactoryMethodName(final String beanFactoryMethodName);

    /**
     * 获取Bean工厂的方法名
     * @return Bean工厂的方法名
     * @since 6.0
     */
    String getBeanFactoryMethodName();

    /**
     * 设置构造函数所有参数的定义
     * @param constructorArgsDefinitions 构造函数的所有参数的定义
     * @since 6.0
     */
    void setConstructorArgsDefinitions(List<ConstructorArgsDefinition> constructorArgsDefinitions);

    /**
     * 获取函数的所有参数定义
     * @return 所有构造函数的定义
     * @since 6.0
     */
    List<ConstructorArgsDefinition> getConstructorArgsDefinitions();

    /**
     * 设置所有属性的定义
     * @param propertyArgsDefinitions 所有的属性的定义
     * @since 7.0
     */
    void setPropertyArgsDefinitions(final List<PropertyArgsDefinition> propertyArgsDefinitions);

    /**
     * 获取所有的属性定义
     * @return 所有的属性定义
     * @since 7.0
     */
    List<PropertyArgsDefinition> getPropertyArgsDefinitions();

    /**
     * 是否是抽象类
     * @return 是否
     * @since 8.0
     */
    boolean isAbstractClass();

    /**
     * 设置是否是抽象类
     * @param abstractClass 抽象类
     * @since 8.0
     */
    void setAbstractClass(boolean abstractClass);

    /**
     * 获取父类名称
     * @return 父类名称
     * @since 8.0
     */
    String getParentName();

    /**
     * 设置父类名
     * @param parentName 父类名称
     * @since 8.0
     */
    void setParentName(String parentName);

    /**
     * 复制一份Bean定义
     * @return Bean定义
     * @since 8.0
     */
    BeanDefinition clone();

    /**
     * 获取Bean的来源
     * @return Bean的来源
     * @since 12.0
     */
    BeanSourceType getBeanSourceType();

    /**
     * 设置Bean的来源
     * @param beanSourceType Bean的来源
     * @since 12.0
     */
    void setBeanSourceType(final BeanSourceType beanSourceType);

    /**
     * 判断是否是主Bean
     * @return 是否
     * @since 17.0
     */
    boolean isPrimary();

    /**
     * 这是是否为主Bean
     * @param primary 是否为主Bean
     * @since 17.0
     */
    void setPrimary(boolean primary);

}
