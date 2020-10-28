// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.model;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 12.0
 */
public class DefaultAnnotationBeanDefinition extends DefaultBeanDefinition implements AnnotationBeanDefinition {

    private String configurationName;
    private String configurationBeanMethod;
    private Class[] classes;
    private List<String> refs;

    @Override
    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Override
    public String getConfigurationName() {
        return configurationName;
    }

    @Override
    public void setConfigurationBeanMethod(String configurationBeanMethod) {
        this.configurationBeanMethod = configurationBeanMethod;
    }

    @Override
    public String getConfigurationBeanMethod() {
        return configurationBeanMethod;
    }

    @Override
    public void setConfigBeanMethodParamTypes(Class[] classes) {
        this.classes = classes;
    }

    @Override
    public Class[] getConfigBeanMethodParamTypes() {
        return classes;
    }

    @Override
    public void setConfigBeanMethodParamRefs(List<String> refs) {
        this.refs = refs;
    }

    @Override
    public List<String> getConfigBeanMethodParamRefs() {
        return refs;
    }

}
