// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.init;

import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 4.0
 */
public class DefaultInitialingBean implements InitializingBean {
    private final Object instance;
    private final BeanDefinition beanDefinition;

    public DefaultInitialingBean(Object instance, BeanDefinition beanDefinition) {
        this.instance = instance;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public void afterPropertiesSet() {
        // @PostConstruct标注的方法
        postConstruct();
        // 实现了InitializingBean接口的afterPropertiesSet方法
        afterPropertiesSetInvoke();
        // Bean配置中配置了init-method的无参方法
        customInit();
    }

    /**
     * 处理注解配置
     * @since 4.0
     */
    private void postConstruct() {
        Optional<Method> optionalMethod = ClassUtils.getMethod(instance.getClass(), PostConstruct.class);
        if (optionalMethod.isPresent()) {
            Method method = optionalMethod.get();
            int count = method.getParameterCount();
            if (count > 0) {
                throw new IocRuntimeException("Method under the @PostConstruct can not with any parameters.");
            }
            ClassUtils.invokeNoArgsMethod(instance, method);
        }
    }

    /**
     * 处理实现InitializingBean接口的Bean
     * @since 4.0
     */
    private void afterPropertiesSetInvoke() {
        if (instance instanceof InitializingBean) {
            ((InitializingBean) instance).afterPropertiesSet();
        }
    }

    /**
     * 处理Bean定义中设置的init-method的无参方法
     * @since 4.0
     */
    private void customInit() {
        String initMethodName = beanDefinition.getInitMethodName();
        if (StringUtils.isEmpty(initMethodName)) {
            return;
        }
        Class<?> clazz = instance.getClass();
        try {
            Method method = clazz.getMethod(initMethodName);
            if (Objects.isNull(method)) {
                return;
            }
            ClassUtils.invokeNoArgsMethod(instance, method);
        } catch (NoSuchMethodException e) {
            throw new IocRuntimeException(e);
        }
    }

}
