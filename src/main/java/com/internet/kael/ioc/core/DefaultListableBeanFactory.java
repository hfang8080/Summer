// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.util.CollectionHelper;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 2.0
 */
public class DefaultListableBeanFactory extends DefaultBeanFactory implements ListableBeanFactory {
    @Override
    public <T> List<T> getBeans(Class<T> clazzType) {
        Preconditions.checkNotNull(clazzType);
        Set<String> beanNames = getBeanNames(clazzType);
        if (CollectionUtils.isEmpty(beanNames)) {
            throw new IocRuntimeException(clazzType + " bean names is empty!");
        }

        return CollectionHelper.transform(Lists.newArrayList(beanNames),
                // WTF???
                beanName -> DefaultListableBeanFactory.super.getBean(beanName, clazzType));
    }

}
