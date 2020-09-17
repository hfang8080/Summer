// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.core.DefaultBeanFactory;
import com.internet.kael.ioc.core.DefaultListableBeanFactory;
import com.internet.kael.ioc.model.DefaultBeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.JsonConverter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class JsonApplicationContext extends DefaultListableBeanFactory {
    private final String fileName;

    public JsonApplicationContext(String fileName) {
        this.fileName = fileName;
        init();
    }

    private void init() {
        InputStream in = ClassUtils.currentClassLoader().getResourceAsStream(fileName);
        if (Objects.isNull(in)) return;
        try {
            // 读取配置文件文件，并转化成BeanDefinition对象
            String jsonConfig = IOUtils.toString(in);
            List<DefaultBeanDefinition> beanDefinitions =
                    JsonConverter.deserializeList(jsonConfig, DefaultBeanDefinition.class);
            if (CollectionUtils.isNotEmpty(beanDefinitions)) {
                for (DefaultBeanDefinition bd: beanDefinitions) {
                    registerBeanDefinition(bd.getName(), bd);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
