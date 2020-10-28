// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.lifecycle.create;

import com.internet.kael.ioc.constant.BeanSourceType;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.core.ListableBeanFactory;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.AnnotationBeanDefinition;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 12.0
 */
public class ConfigurationMethodInstanceBean extends AbstractNewInstanceBean {

    private static ConfigurationMethodInstanceBean instance = new ConfigurationMethodInstanceBean();
    public static ConfigurationMethodInstanceBean getInstance() {
        return instance;
    }

    @Override
    protected Optional<Object> newInstanceOpt(BeanFactory beanFactory, BeanDefinition beanDefinition, Class beanClass) {
        if (!(beanDefinition instanceof AnnotationBeanDefinition)) {
            throw new IocRuntimeException(
                    "BeanDefinition must be instance of AnnotationBeanDefinition " + beanDefinition.getName());
        }
        AnnotationBeanDefinition bd = (AnnotationBeanDefinition) beanDefinition;
        Object configBean = beanFactory.getBean(bd.getConfigurationName());
        Optional<Method> optionalMethod = ClassUtils.getMethod(configBean.getClass(), bd.getConfigurationBeanMethod());
        if (optionalMethod.isPresent()) {
            Object[] paramsObjects = getParams(beanFactory, bd);
            Object bean = ClassUtils.invokeMethod(configBean, optionalMethod.get(), paramsObjects);
            return Optional.ofNullable(bean);
        }
        return Optional.empty();
    }

    private Object[] getParams(final BeanFactory beanFactory, final AnnotationBeanDefinition annotationBeanDefinition) {
        List<String> refNames = annotationBeanDefinition.getConfigBeanMethodParamRefs();
        if (CollectionUtils.isEmpty(refNames)) {
            return null;
        }
        fillRefName(beanFactory, annotationBeanDefinition);
        Object[] objects = new Object[refNames.size()];
        for (int i = 0; i < refNames.size(); i++) {
            String refName = refNames.get(i);
            objects[i] = beanFactory.getBean(refName);
        }
        return objects;
    }

    private void fillRefName(final BeanFactory beanFactory, final AnnotationBeanDefinition annotationBeanDefinition) {
        if (annotationBeanDefinition.getBeanSourceType() == BeanSourceType.CONFIGURATION_BEAN) {
            String beanName = annotationBeanDefinition.getName();
            Class<?>[] paramTypes = annotationBeanDefinition.getConfigBeanMethodParamTypes();
            if (ArrayUtils.isNotEmpty(paramTypes)) {
                List<String> paramRefs = annotationBeanDefinition.getConfigBeanMethodParamRefs();
                for (int i = 0; i < paramTypes.length; i++) {
                    ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
                    String paramBeanName = listableBeanFactory.getPrimaryBeanName(paramTypes[i]);
                    if (StringUtils.isEmpty(paramBeanName)) {
                        throw new IocRuntimeException(
                                beanName + " configuration method param of [" + i + "] not found!");
                    }
                    paramRefs.set(i, paramBeanName);
                }
            }
        }
    }
}
