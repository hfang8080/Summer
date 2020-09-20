// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class Apple {

    public void initMethod() {
        System.out.println("init.");
    }
    public void destroyMethod() {
        System.out.println("destroy.");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Post construct!");
    }

    public void introduce() {
        System.out.println("I'm apple. Welcome to learning Spring's little brother, Summer.");
    }

    @PreDestroy
    public void beforeDestroy() {
        System.out.println("Before destroy.");
    }
}
