// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.internet.kael.ioc.model.DefaultBeanDefinition;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.JsonConverter;
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
    protected List<? extends DefaultBeanDefinition> buildBeanDefinitions() {
        InputStream in = ClassUtils.currentClassLoader().getResourceAsStream(fileName);
        if (Objects.isNull(in)) return Collections.emptyList();
        try {
            // 读取配置文件文件，并转化成BeanDefinition对象
            String jsonConfig = IOUtils.toString(in);
            return JsonConverter.deserializeList(jsonConfig, DefaultBeanDefinition.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
