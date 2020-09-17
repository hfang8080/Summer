// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import java.util.List;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 2.0
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 获取指定类型的实现
     * (1) 如果对应的类型不存在，则直接抛出异常
     * (2) 反之，返回列表信息
     *
     * @param clazzType 类型
     * @return Bean列表
     * @since 2.0
     */
    <T> List<T> getBeans(final Class<T> clazzType);
}
