// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.support.name.BeanNameStrategy;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 22.0
 */
public class DefaultBeanDefinitionScannerContext implements BeanDefinitionScannerContext {
    private List<String> scanPackages;
    private List<Class> excludes;
    private List<Class> includes;
    private Class<? extends BeanNameStrategy> beanNameStrategy;

    public void setScanPackages(List<String> scanPackages) {
        this.scanPackages = scanPackages;
    }

    public void setExcludes(List<Class> excludes) {
        this.excludes = excludes;
    }

    public void setIncludes(List<Class> includes) {
        this.includes = includes;
    }

    public void setBeanNameStrategy(Class<? extends BeanNameStrategy> beanNameStrategy) {
        this.beanNameStrategy = beanNameStrategy;
    }

    @Override
    public List<String> getScanPackages() {
        return scanPackages;
    }

    @Override
    public List<Class> getExcludes() {
        return excludes;
    }

    @Override
    public List<Class> getIncludes() {
        return includes;
    }

    @Override
    public Class<? extends BeanNameStrategy> getBeanNameStrategy() {
        return beanNameStrategy;
    }
}
