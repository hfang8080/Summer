// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.exception.IocRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 2.0
 */
public class DefaultListableBeanFactory extends DefaultBeanFactory implements ListableBeanFactory {
    @Override
    public <T> List<T> getBeans(Class<T> clazzType) {
        return super.getBeans(clazzType);
    }

    @Override
    public Set<String> getBeanNames(Class clazzType) {
        return super.getBeanNames(clazzType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public  <T> T getRequiredTypeBean(Class<T> requiredType, String beanName) {
        Set<String> beanNames = getBeanNames(requiredType);
        if (CollectionUtils.isEmpty(beanNames)) {
            throw new IocRuntimeException("RequiredType of " + requiredType.getName() + " beans not found!");
        }
        if (beanNames.size() == 1) {
            final String firstBeanName = beanNames.iterator().next();
            return (T) getBean(firstBeanName);
        }
        if (StringUtils.isNotEmpty(beanName)) {
            return (T) getBean(beanName);
        }

        throw new IocRuntimeException("RequiredType of " + requiredType.getName() + " must be unique!");
    }
}
