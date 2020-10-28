// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.exception.IocRuntimeException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @author Kael He(h_fang8080@163.com)
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
        if (beanNames.size() > 0) {
            for (String bn : beanNames) {
                if (StringUtils.equals(bn , beanName)) {
                    return (T) getBean(bn);
                }
            }
        }

        if (StringUtils.isNotEmpty(beanName)) {
            return (T) getBean(beanName);
        }
        return getPrimaryBean(requiredType);
    }

    @Override
    public <T> T getPrimaryBean(Class<T> requireType) {
        return super.getPrimaryBean(requireType);
    }

    @Override
    public String getPrimaryBeanName(Class<?> requireType) {
        return super.getPrimaryBeanName(requireType);
    }
}
