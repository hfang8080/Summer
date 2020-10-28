// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.destroy;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 4.0
 */
public interface DisposableBean {

    /**
     * 做销毁内容
     * @since 4.0
     */
    void destroy();
}
