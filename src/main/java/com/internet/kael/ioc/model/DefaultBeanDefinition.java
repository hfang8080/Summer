// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.model;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
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
    private List<PropertyArgsDefinition> propertyArgsDefinitions;
    private boolean abstractClass;
    private String parentName;

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

    @Override
    public void setPropertyArgsDefinitions(List<PropertyArgsDefinition> propertyArgsDefinitions) {
        this.propertyArgsDefinitions = propertyArgsDefinitions;
    }

    @Override
    public List<PropertyArgsDefinition> getPropertyArgsDefinitions() {
        return propertyArgsDefinitions;
    }

    @Override
    public boolean isAbstractClass() {
        return abstractClass;
    }

    @Override
    public void setAbstractClass(boolean abstractClass) {
        this.abstractClass = abstractClass;
    }

    @Override
    public String getParentName() {
        return parentName;
    }

    @Override
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public BeanDefinition clone() {
        DefaultBeanDefinition definition = new DefaultBeanDefinition();
        try {
            BeanUtils.copyProperties(this, definition);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return definition;
    }
}
