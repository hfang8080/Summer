// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.circle;

import com.internet.kael.ioc.model.BeanDefinition;

import java.util.List;

/**
 * Bean循环依赖判断者
 * @author Kael He(h_fang8080@163.com)
 * @since 10.0
 */
public interface BeanDependenceChecker {

    /**
     * 判断是否存在循环依赖
     * @param beanName Bean名称
     * @return 是否
     * @since 10.0
     */
    boolean isCyclicDependence(String beanName);

    /**
     * 注册所有Bean的定义
     * @param beanDefinitions 所有Bean的定义
     * @since 10.0
     */
    void registerBeanDefinition(List<BeanDefinition> beanDefinitions);
}
