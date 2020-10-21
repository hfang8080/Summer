// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.bean;

import com.internet.kael.ioc.annotation.BeanFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 1.0
 */
public class Apple {
    private String color = "Black";

    public Apple() {
    }

    public Apple(String color) {
        this.color = color;
    }

    public void initMethod() {
        System.out.println("init.");
    }
    public void destroyMethod() {
        System.out.println("destroy.");
    }

    @BeanFactory
    public Apple createBean() {
        System.out.println("Create by factory method.");
        return new Apple();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("Post construct!");
    }

    public void introduce() {
        System.out.println("I'm apple. Welcome to learning Spring's little brother, Summer." + color);
    }

    @PreDestroy
    public void beforeDestroy() {
        System.out.println("Before destroy.");
    }

    @Override
    public String toString() {
        return "Apple{" +
                "color='" + color + '\'' +
                '}';
    }

    public String getColor() {
        return color;
    }
}
