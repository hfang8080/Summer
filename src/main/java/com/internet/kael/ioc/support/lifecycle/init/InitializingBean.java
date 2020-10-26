// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.init;

/**
 * @author Kael He (kael.he@alo7.com)
 */
public interface InitializingBean {
    /**
     * 在属性设置后执行
     * @since 4.0
     */
    void afterPropertiesSet();
}
