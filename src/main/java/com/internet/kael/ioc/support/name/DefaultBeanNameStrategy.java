// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.name;

import com.google.common.base.Preconditions;
import com.internet.kael.ioc.model.BeanDefinition;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 11.0
 */
public class DefaultBeanNameStrategy implements BeanNameStrategy {

    @Override
    public String generateBeanName(BeanDefinition beanDefinition) {
        Preconditions.checkNotNull(beanDefinition);
        if (StringUtils.isNotEmpty(beanDefinition.getName())) {
            return beanDefinition.getName();
        }
        String className = beanDefinition.getClassName();
        Preconditions.checkNotNull(className);
        String pureClassName = className.substring(className.lastIndexOf(".") + 1);
        return StringUtils.uncapitalize(pureClassName);
    }
}
