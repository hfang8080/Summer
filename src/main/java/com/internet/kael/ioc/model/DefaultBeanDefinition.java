// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.model;

import java.util.List;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class DefaultBeanDefinition implements BeanDefinition {

    private String name;
    private String className;
    private String scope;
    private boolean lazyInit;
    private String initMethodName;
    private String destroyMethodName;
    private String beanFactoryMethodName;
    private List<ConstructorArgsDefinition> constructorArgsDefinitions;

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

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public boolean isLazyInit() {
        return lazyInit;
    }

    @Override
    public void setLazyInit(boolean lazyInit) {
        this.lazyInit = lazyInit;
    }

    @Override
    public String getInitMethodName() {
        return initMethodName;
    }

    @Override
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    @Override
    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    @Override
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }

    @Override
    public void setBeanFactoryMethodName(String beanFactoryMethodName) {
        this.beanFactoryMethodName = beanFactoryMethodName;
    }

    @Override
    public String getBeanFactoryMethodName() {
        return beanFactoryMethodName;
    }

    @Override
    public void setConstructorArgsDefinitions(List<ConstructorArgsDefinition> constructorArgsDefinitions) {
        this.constructorArgsDefinitions = constructorArgsDefinitions;
    }

    @Override
    public List<ConstructorArgsDefinition> getConstructorArgsDefinitions() {
        return constructorArgsDefinitions;
    }
}
