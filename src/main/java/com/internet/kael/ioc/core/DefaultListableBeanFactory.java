// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import java.util.List;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 2.0
 */
public class DefaultListableBeanFactory extends DefaultBeanFactory implements ListableBeanFactory {
    @Override
    public <T> List<T> getBeans(Class<T> clazzType) {
        return super.getBeans(clazzType);
    }

}
