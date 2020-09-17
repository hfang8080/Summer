// Copyright 2019 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

/**
 * Bean 工厂
 *
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public interface BeanFactory {

    /**
     * 根据bean名称获取实例信息
     * @param beanName Bean 名称
     * @return 对象实例
     *  @since 1.0
     */
    Object getBean(final String beanName);

    /**
     * 获取制定类型的实例
     *
     * @param beanName Bean 名称
     * @param clazz 类型
     * @param <T> 泛型
     * @return 结果
     * @since 1.0
     */
    <T> T getBean(final String beanName, final Class<T> clazz);
}
