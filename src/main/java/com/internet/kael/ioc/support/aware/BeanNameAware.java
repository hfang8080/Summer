// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.aware;

import com.internet.kael.ioc.exception.IocRuntimeException;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 8.0
 */
public interface BeanNameAware extends Aware {

    /**
     * 设置Bean名称
     * @param name Bean名称
     * @throws IocRuntimeException 运行时异常
     */
    void setBeanName(String name) throws IocRuntimeException;
}
