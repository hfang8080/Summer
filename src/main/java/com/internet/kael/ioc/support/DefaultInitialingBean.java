// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support;

import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 4.0
 */
public class DefaultInitialingBean implements InitializingBean {
    private final Object bean;
    private final BeanDefinition beanDefinition;

    public DefaultInitialingBean(Object bean, BeanDefinition beanDefinition) {
        this.bean = bean;
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
        Optional<Method> optionalMethod = ClassUtils.getMethod(bean.getClass(), PostConstruct.class);
        if (optionalMethod.isPresent()) {
            Method method = optionalMethod.get();
            int count = method.getParameterCount();
            if (count > 0) {
                throw new IocRuntimeException("Method under the @PostConstruct can not with any parameters.");
            }
            try {
                method.invoke(bean);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new IocRuntimeException(e);
            }
        }
    }

    /**
     * 处理实现InitializingBean接口的Bean
     * @since 4.0
     */
    private void afterPropertiesSetInvoke() {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
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
        Class<?> clazz = bean.getClass();
        try {
            Method method = clazz.getMethod(initMethodName);
            if (Objects.isNull(method)) {
                return;
            }
            method.invoke(bean);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new IocRuntimeException(e);
        }
    }

}
