// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.support.environment.EnvironmentCapable;
import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

/**
 * @author Kael He (kael.he@alo7.com)
 */
public interface ConditionContext extends EnvironmentCapable {
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

}
