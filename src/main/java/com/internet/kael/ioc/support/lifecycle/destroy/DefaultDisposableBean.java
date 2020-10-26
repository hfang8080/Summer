// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.destroy;

import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 4.0
 */
public class DefaultDisposableBean implements DisposableBean {

    private final Object instance;
    private final BeanDefinition beanDefinition;

    public DefaultDisposableBean(Object instance, BeanDefinition beanDefinition) {
        this.instance = instance;
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
        Optional<Method> optionalMethod = ClassUtils.getMethod(instance.getClass(), PreDestroy.class);
        if (optionalMethod.isPresent()) {
            Method method = optionalMethod.get();
            int count = method.getParameterCount();
            if (count > 0) {
                throw new IocRuntimeException("Method under the @PostConstruct can not with any parameters.");
            }
            ClassUtils.invokeNoArgsMethod(instance, method);
        }
    }

    private void disposeBean() {
        if (instance instanceof DisposableBean) {
            ((DisposableBean) instance).destroy();
        }
    }

    private void customDestroy() {
        String destroyMethodName = beanDefinition.getDestroyMethodName();
        if (StringUtils.isEmpty(destroyMethodName)) {
            return;
        }
        Method method = null;
        try {
            method = instance.getClass().getMethod(destroyMethodName);
            if (Objects.isNull(method)) return;
            ClassUtils.invokeNoArgsMethod(instance, method);
        } catch (NoSuchMethodException e) {
            throw new IocRuntimeException(e);
        }
    }

}
