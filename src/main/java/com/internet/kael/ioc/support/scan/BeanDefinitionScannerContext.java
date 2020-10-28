// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.support.name.BeanNameStrategy;

import java.util.List;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 22.0
 */
public interface BeanDefinitionScannerContext {
    /**
     * 获取扫描的包名列表
     * @since 22.0
     */
    List<String> getScanPackages();

    /**
     * 获取排除的Class
     * @since 22.0
     */
    List<Class> getExcludes();

    /**
     * 获取纳入的Class
     * @since 22.0
     */
    List<Class> getIncludes();

    /**
     * 获取Bean名称定义的策略
     * @since 22.0
     */
    Class<? extends BeanNameStrategy> getBeanNameStrategy();
}
