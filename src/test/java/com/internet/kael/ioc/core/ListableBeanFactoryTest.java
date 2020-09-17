// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.core;

import com.internet.kael.ioc.bean.Apple;
import com.internet.kael.ioc.context.JsonApplicationContext;
import org.junit.Test;

import java.util.List;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 2.0
 */
public class ListableBeanFactoryTest {
    private static final ListableBeanFactory BEAN_FACTORY = new JsonApplicationContext("apples.json");

    @Test
    /**
     * @since 2.0
     */
    public void getBeans() {
        List<Apple> apples = BEAN_FACTORY.getBeans(Apple.class);
        apples.forEach(Apple::introduce);
    }
}
