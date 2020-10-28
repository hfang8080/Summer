// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.create;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.BeanDefinition;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 6.0
 */
public interface NewInstanceBean {

    /**
     * 创建一个实例对象
     * @param beanFactory Bean工厂
     * @param beanDefinition Bean定义
     * @return 实例对象
     * @since 6.0
     */
    Object instance(BeanFactory beanFactory, BeanDefinition beanDefinition);
}
