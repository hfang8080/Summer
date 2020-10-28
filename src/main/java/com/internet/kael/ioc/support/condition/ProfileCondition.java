// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.condition;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.support.environment.Environment;

import java.util.Map;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 19.0
 */
public class ProfileCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, Map<String, Object> attributes) {
        Preconditions.checkNotNull(conditionContext);
        Preconditions.checkNotNull(attributes);
        Environment environment = conditionContext.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        String[] profiles = (String[]) attributes.get("value");
        for (String profile : profiles) {
            for (String activeProfile : activeProfiles) {
                if (profile.equalsIgnoreCase(activeProfile)) {
                    return true;
                }
            }
        }
        return false;
    }
}
