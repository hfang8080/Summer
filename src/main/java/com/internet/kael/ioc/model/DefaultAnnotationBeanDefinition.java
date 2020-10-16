// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 12.0
 */
public class DefaultAnnotationBeanDefinition extends DefaultBeanDefinition implements AnnotationBeanDefinition {

    private String configurationName;
    private String configurationBeanMethod;;

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
}
