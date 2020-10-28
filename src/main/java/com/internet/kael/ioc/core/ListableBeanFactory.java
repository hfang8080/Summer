// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.core;

import java.util.List;
import java.util.Set;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 2.0
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 获取指定类型的实现
     * (1) 如果对应的类型不存在，则直接抛出异常
     * (2) 反之，返回列表信息
     *
     * @param clazzType 类型
     * @return Bean列表
     * @since 2.0
     */
    <T> List<T> getBeans(final Class<T> clazzType);

    /**
     * 获取Bean名称列表
     * @param clazzType Class类型
     * @return Bean名称列表
     */
    Set<String> getBeanNames(final Class clazzType);

    /**
     * 获取指定类型的Bean
     * @param requiredType 执行的Class类型
     * @param beanName Bean名称
     * @param <T> 泛型
     * @return Bean实例
     */
    <T> T getRequiredTypeBean(Class<T> requiredType, String beanName);

    /**
     * 获取主Bean
     * @param requireType 需要的类型
     * @param <T> 泛型
     * @return 主Bean
     * @since 17.0
     */
    <T> T getPrimaryBean(Class<T> requireType);

    /**
     * 获取主Bean名称
     * @param requireType 所需类型
     * @return 主Bean名称
     * @since 17.0
     */
    String getPrimaryBeanName(Class<?> requireType);
}
