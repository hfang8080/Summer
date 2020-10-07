// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.aware;

import com.internet.kael.ioc.exception.IocRuntimeException;

/**
 * 对象监听
 * @author Kael He (kael.he@alo7.com)
 * @since 8.0
 */
public interface BeanCreateAware extends Aware {

    /**
     * 设置创建的对象
     * @param name Bean名称
     * @param instance 实例名
     * @throws IocRuntimeException 运行时异常
     * @since 8.0
     */
    void setBeanCreate(String name, final Object instance) throws IocRuntimeException;
}
