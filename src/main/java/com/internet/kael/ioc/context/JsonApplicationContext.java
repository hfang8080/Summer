// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.alibaba.fastjson.JSON;
import com.internet.kael.ioc.core.BeanFactory;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.DefaultBeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class JsonApplicationContext extends AbstractApplicationContext {

    public JsonApplicationContext(String fileName) {
        super(fileName);
    }

    @Override
    protected List<? extends BeanDefinition> buildBeanDefinitions() {
        InputStream in = ClassUtils.currentClassLoader().getResourceAsStream(fileName);
        if (Objects.isNull(in)) return Collections.emptyList();
        try {
            // 读取配置文件文件，并转化成BeanDefinition对象
            String jsonConfig = IOUtils.toString(in);

            return JSON.parseArray(jsonConfig, DefaultBeanDefinition.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
