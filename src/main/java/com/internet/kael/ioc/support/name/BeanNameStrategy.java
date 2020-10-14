// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.name;

import com.internet.kael.ioc.model.BeanDefinition;

/**
 * Bean名称的命名策略
 * @author Kael He (kael.he@alo7.com)
 * @since 11.0
 */
public interface BeanNameStrategy {

    /**
     * 生成的名称
     * @param beanDefinition Bean的定义
     * @return Bean名称
     * @since 11.0
     */
    String generateBeanName(BeanDefinition beanDefinition);
}
