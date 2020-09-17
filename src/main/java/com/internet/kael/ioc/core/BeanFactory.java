// Copyright 2019 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.model.BeanDefinition;

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
     * 获取指定类型的实例
     *
     * @param beanName Bean 名称
     * @param clazz 类型
     * @param <T> 泛型
     * @return 结果
     * @since 1.0
     */
    <T> T getBean(final String beanName, final Class<T> clazz);

    /**
     * 判断是否包含指定名称的Bean.
     *
     * @param beanName Bean 名称
     * @return 是否包含
     * @since 2.0
     */
    boolean containsBean(final String beanName);

    /**
     * 判断指定的Bean名称和需要的类型是否匹配
     *
     * @param beanName Bean 名称
     * @param requireType 待匹配的类型
     * @return 是否匹配
     * @since 2.0
     */
    boolean isTypeMatch(final String beanName, final Class requireType);

    /**
     * 获取Bean对应的类型
     *
     * @param beanName Bean名称
     * @return 类型
     * @since 2.0
     */
    Class<?> getType(final String beanName);
}
