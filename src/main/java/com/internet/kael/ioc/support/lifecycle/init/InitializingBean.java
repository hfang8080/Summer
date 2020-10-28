// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.init;

/**
 * @author Kael He (h_fang8080@163.com)
 */
public interface InitializingBean {
    /**
     * 在属性设置后执行
     * @since 4.0
     */
    void afterPropertiesSet();
}
