// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.aware;

import com.internet.kael.ioc.context.ApplicationContext;
import com.internet.kael.ioc.exception.IocRuntimeException;

/**
 * 可以弱化BeanFactory的存在。
 * 不在支持BeanFactoryAware。
 * @author Kael He (h_fang8080@163.com)
 * @since 8.0
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置application context的信息
     * @param ac 上下文信息
     * @throws IocRuntimeException 发生的异常
     * @since 8.0
     */
    void setApplicationContext(ApplicationContext ac) throws IocRuntimeException;
}
