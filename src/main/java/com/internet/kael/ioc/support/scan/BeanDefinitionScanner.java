// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.model.BeanDefinition;

import java.util.Set;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 10.1
 */
public interface BeanDefinitionScanner {

    /**
     * 扫描路径下的文件，并转换成为Bean定义.
     * @param packageNames 众多包名
     * @return Bean定义
     * @since 10.1
     */
    Set<BeanDefinition> scan(final String... packageNames);

}
