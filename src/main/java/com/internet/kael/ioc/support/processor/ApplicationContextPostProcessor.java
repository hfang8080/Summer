// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.processor;

import com.internet.kael.ioc.model.BeanDefinition;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 8.0
 */
public interface ApplicationContextPostProcessor extends PostProcessor {
    /**
     * 对象属性注册之前
     * @param definitions 对象原始定义信息裂变
     * @return 结果
     * @since 8.0
     */
    List<BeanDefinition> beforeRegister(List<BeanDefinition> definitions);
}
