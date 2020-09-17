// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class DefaultBeanDefinition implements BeanDefinition {

    private String name;
    private String className;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public void setClassName(String className) {
        this.className = className;
    }
}
