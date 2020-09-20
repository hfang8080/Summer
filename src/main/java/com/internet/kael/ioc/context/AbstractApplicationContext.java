// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.constant.Scope;
import com.internet.kael.ioc.core.DefaultListableBeanFactory;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.DefaultBeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.JsonConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 4.0
 */
public abstract class AbstractApplicationContext extends DefaultListableBeanFactory implements ApplicationContext {
    protected final String fileName;

    public AbstractApplicationContext(String fileName) {
        this.fileName = fileName;
        init();
    }

    /**
     * 初始化Bean定义
     *
     * @since 4.0
     */
    protected void init() {
        List<? extends DefaultBeanDefinition> beanDefinitions = buildBeanDefinitions();
        registerBeanDefinitions(beanDefinitions);
        registerShutdownHook();
    }

    /**
     * 注册所有的Bean定义
     *
     * @param beanDefinitions 所有的Bean定义
     * @since 4.0
     */
    protected void registerBeanDefinitions(final List<? extends BeanDefinition> beanDefinitions) {
        if (CollectionUtils.isNotEmpty(beanDefinitions)) {
            for (BeanDefinition bd : beanDefinitions) {
                // 填充默认值
                fillDefaultValue(bd);
                // 注册Bean Definition对象
                registerBeanDefinition(bd.getName(), bd);
            }
        }
    }

    /**
     * 填充默认值
     * @param bd Bean定义
     * @since 4.0
     */
    private void fillDefaultValue(BeanDefinition bd) {
        if (StringUtils.isEmpty(bd.getScope())) {
            bd.setScope(Scope.SINGLETON.getCode());
        }
    }

    /**
     * 构建Bean定义的列表
     * @return Bean定义的列表
     * @since 4.0
     */
    protected abstract List<? extends DefaultBeanDefinition> buildBeanDefinitions();

    /**
     * 注册关闭的钩子
     *
     * @since 4.0
     */
    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::destroy));
    }

    @Override
    public String getApplicationName() {
        return "ApplicationName";
    }
}
