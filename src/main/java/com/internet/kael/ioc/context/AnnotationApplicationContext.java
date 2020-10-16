// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.constant.BeanSourceType;
import com.internet.kael.ioc.constant.Scope;
import com.internet.kael.ioc.model.AnnotationBeanDefinition;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.DefaultAnnotationBeanDefinition;
import com.internet.kael.ioc.model.DefaultBeanDefinition;
import com.internet.kael.ioc.support.name.BeanNameStrategy;
import com.internet.kael.ioc.support.name.DefaultBeanNameStrategy;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
                Optional<AnnotationBeanDefinition> optionalBeanDefinition = buildConfigurationBeanDefinition(clazz);
                if (optionalBeanDefinition.isPresent()) {
                    AnnotationBeanDefinition configBeanDefinition = optionalBeanDefinition.get();
                    beanDefinitions.add(configBeanDefinition);
                    List<AnnotationBeanDefinition> bds = buildBeansInConfiguration(configBeanDefinition, clazz);
                    beanDefinitions.addAll(bds);
                }
            }
        }
        return beanDefinitions;
    }

    /**
     * 构建@Configuration类到Bean定义中
     * @param clazz 标注为@Configuration的类
     * @return Bean的定义
     */
    private Optional<AnnotationBeanDefinition> buildConfigurationBeanDefinition(final Class clazz) {
        if (!clazz.isAnnotationPresent(Configuration.class)) {
            return Optional.empty();
        }
        Configuration configuration = (Configuration) clazz.getAnnotation(Configuration.class);
        AnnotationBeanDefinition beanDefinition = new DefaultAnnotationBeanDefinition();
        beanDefinition.setClassName(clazz.getName());
        beanDefinition.setLazyInit(false);
        beanDefinition.setScope(Scope.SINGLETON.getCode());
        beanDefinition.setBeanSourceType(BeanSourceType.CONFIGURATION);
        String beanName = configuration.value();
        if (StringUtils.isEmpty(beanName)) {
            beanName = beanNameStrategy.generateBeanName(beanDefinition);
        }
        beanDefinition.setName(beanName);
        return Optional.of(beanDefinition);
    }

    private List<AnnotationBeanDefinition> buildBeansInConfiguration(
            final BeanDefinition configBeanDefinition, final Class clazz) {
        if (!clazz.isAnnotationPresent(Configuration.class)) {
            return Collections.emptyList();
        }

        List<AnnotationBeanDefinition> beanDefinitions = Lists.newArrayList();
        List<Method> methods = ClassUtils.getMethods(clazz);
        for (Method method : methods) {
            if (method.isAnnotationPresent(Bean.class)) {
                Bean bean = method.getAnnotation(Bean.class);
                String methodName = method.getName();
                Class<?> returnType = method.getReturnType();
                String beanName = methodName;
                if (StringUtils.isNotEmpty(bean.value())) {
                    beanName = bean.value();
                }
                DefaultAnnotationBeanDefinition bd = new DefaultAnnotationBeanDefinition();
                bd.setName(beanName);
                bd.setClassName(returnType.getName());
                bd.setInitMethodName(bean.initMethod());
                bd.setDestroyMethodName(bean.destroyMethod());
                bd.setBeanSourceType(BeanSourceType.CONFIGURATION_BEAN);
                bd.setConfigurationName(configBeanDefinition.getName());
                bd.setConfigurationBeanMethod(methodName);
                // 这里需要添加property/constructor对应的实现
                bd.setLazyInit(false);
                bd.setScope(Scope.SINGLETON.getCode());
                beanDefinitions.add(bd);
            }
        }
        return beanDefinitions;
    }
}
