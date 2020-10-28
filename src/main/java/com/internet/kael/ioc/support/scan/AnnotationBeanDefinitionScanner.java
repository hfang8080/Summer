// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.model.AnnotationBeanDefinition;

import java.util.Set;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 22.0
 */
public interface AnnotationBeanDefinitionScanner {
    /**
     * 扫描指定的信息获取Bean定义集合
     * @param context 扫描所需要的上下文
     * @since 22.0
     */
    Set<AnnotationBeanDefinition> scan(final BeanDefinitionScannerContext context);
}
