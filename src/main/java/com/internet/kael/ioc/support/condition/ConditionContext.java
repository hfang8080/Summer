// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.support.environment.Environment;
import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

/**
 * @author Kael He (kael.he@alo7.com)
 */
public interface ConditionContext {
    /**
     * 获取对象工厂信息
     * @return 工厂信息
     * @since 18.0
     */
    BeanFactory getBeanFactory();

    /**
     * 获取注解相关元信息
     * @return 注解元信息
     * @since 18.0
     */
    AnnotationTypeMeta getAnnotationTypeMeta();

    /**
     * 获取环境
     * @return 环境信息
     * @since 19.0
     */
    Environment getEnvironment();
}
