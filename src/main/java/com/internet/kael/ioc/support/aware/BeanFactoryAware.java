// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.aware;

import com.internet.kael.ioc.core.BeanFactory;

/**
 * 这个位置声明作Aware，可能会让人误解为当BeanFactory创建成功之后调用，但是本处未支持。
 * 这个地方仅仅用来像需要注入BeanFactory的地方注入Bean。
 * // TODO 思考这个位置是否合理
 * @author Kael He (kael.he@alo7.com)
 * @since 16.0
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 设置Bean工厂
     * @param beanFactory Bean工厂
     * @since 16.0
     */
    void setBeanFactory(BeanFactory beanFactory);
}
