// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.processor;

/**
 * 适配器类，做默认实现，其他如果想要修改，只需要重写需要的方法即可。
 * @author Kael He (kael.he@alo7.com)
 * @since 16.0
 */
public class BeanPostProcessorAdaptor implements BeanPostProcessor {
    @Override
    public Object beforePropertySet(String beanName, Object instance) {
        return instance;
    }

    @Override
    public Object afterPropertySet(String beanName, Object instance) {
        return instance;
    }
}
