// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.constant.Scope;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.DefaultBeanDefinition;
import com.internet.kael.ioc.support.name.BeanNameStrategy;
import com.internet.kael.ioc.support.name.DefaultBeanNameStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 注解类型的应用上下文
 * @author Kael He (kael.he@alo7.com)
 * @since 11.0
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    /**
     * 配置类信息
     * @since 11.0
     */
    private final Class[] configClasses;

    /**
     * // TODO 使得beanNameStrategy为单例
     * Bean名称的命名策略
     * @since 11.0
     */
    private BeanNameStrategy beanNameStrategy = new DefaultBeanNameStrategy();

    public AnnotationApplicationContext(Class ... configClasses) {
        Preconditions.checkNotNull(configClasses);
        this.configClasses = configClasses;
        super.init();
    }

    @Override
    protected List<BeanDefinition> buildBeanDefinitions() {
        List<BeanDefinition> beanDefinitions = Lists.newArrayList();
        for (Class clazz : configClasses) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                Configuration configuration = (Configuration) clazz.getAnnotation(Configuration.class);
                DefaultBeanDefinition beanDefinition = DefaultBeanDefinition.newInstance();
                beanDefinition.setClassName(clazz.getName());
                beanDefinition.setLazyInit(false);
                beanDefinition.setScope(Scope.SINGLETON.getCode());
                String beanName = configuration.value();
                if (StringUtils.isEmpty(beanName)) {
                    beanName = beanNameStrategy.generateBeanName(beanDefinition);
                }
                beanDefinition.setName(beanName);
                beanDefinitions.add(beanDefinition);
            }
        }
        return beanDefinitions;
    }
}
