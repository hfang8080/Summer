// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support;

import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;

import javax.annotation.PreDestroy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 4.0
 */
public class DefaultDisposableBean implements DisposableBean {

    private final Object bean;
    private final BeanDefinition beanDefinition;

    public DefaultDisposableBean(Object bean, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanDefinition = beanDefinition;
    }

    @Override
    public void destroy() {
        // 执行@PreDestroy配置的无参方法
        preDestroy();
        // 执行实现DisposableBean的destroy方法
        disposeBean();
        // 执行Bean定义的destroy方法
        customDestroy();
    }

    private void preDestroy() {
        Optional<Method> optionalMethod = ClassUtils.getMethod(bean.getClass(), PreDestroy.class);
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

    private void disposeBean() {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
    }

    private void customDestroy() {
        String destroyMethodName = beanDefinition.getDestroyMethodName();
        Method method = null;
        try {
            method = bean.getClass().getMethod(destroyMethodName);
            if (Objects.isNull(method)) return;
            method.invoke(bean);
        } catch (NoSuchMethodException | IllegalAccessException |InvocationTargetException e) {
            throw new IocRuntimeException(e);
        }
    }

}
