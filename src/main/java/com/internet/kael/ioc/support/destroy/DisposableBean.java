// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.destroy;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 4.0
 */
public interface DisposableBean {

    /**
     * 做销毁内容
     * @since 4.0
     */
    void destroy();
}
