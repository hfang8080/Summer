// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import java.util.Map;

/**
 * 不可以实例化
 * @author Kael He (h_fang8080@163.com)
 * @since 18.0
 */
public class FalseCondition implements Condition {
    @Override
    public boolean matches(final ConditionContext conditionContext, Map<String, Object> attributes) {
        return false;
    }
}
