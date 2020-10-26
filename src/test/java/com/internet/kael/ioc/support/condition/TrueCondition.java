// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import java.util.Map;

/**
 * 可以实例化
 * @author Kael He (kael.he@alo7.com)
 * @since 18.0
 */
public class TrueCondition implements Condition {
    @Override
    public boolean matches(final ConditionContext conditionContext, Map<String, Object> attributes) {
        return true;
    }
}
