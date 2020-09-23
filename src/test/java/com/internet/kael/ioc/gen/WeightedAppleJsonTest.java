// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.gen;

import com.google.common.collect.ImmutableList;
import com.internet.kael.ioc.model.DefaultBeanDefinition;
import com.internet.kael.ioc.model.DefaultConstructorArgsDefinition;
import com.internet.kael.ioc.util.JsonConverter;
import org.junit.Test;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 6.0
 */
public class WeightedAppleJsonTest {

    @Test
    public void generate() {
        DefaultConstructorArgsDefinition definition = new DefaultConstructorArgsDefinition();
        definition.setRef("apple");
        DefaultConstructorArgsDefinition definition2 = new DefaultConstructorArgsDefinition();
        definition2.setType("java.lang.Integer");
        definition2.setValue("10");
        DefaultBeanDefinition defaultBeanDefinition = new DefaultBeanDefinition();
        defaultBeanDefinition.setConstructorArgsDefinitions(ImmutableList.of(definition, definition2));
        System.out.println(JsonConverter.serialize(defaultBeanDefinition));

    }
}
