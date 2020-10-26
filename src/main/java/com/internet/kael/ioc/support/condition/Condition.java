// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import java.util.Map;

/**
 * 判断Bean是否可以被实例化。
 * @author Kael He (kael.he@alo7.com)
 * @since 18.0
 */
public interface Condition {

    /**
     * 是否匹配
     * @param conditionContext 比较的上下文
     * @param attributes 注解相关的信息
     * @return 结果是否匹配
     * @since 18.0
     */
    boolean matches(final ConditionContext conditionContext, final Map<String, Object> attributes);
}
