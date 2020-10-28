// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.bean;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 7.0
 */
public class NamedApple {
    private String name;
    private Apple apple;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    @Override
    public String toString() {
        return "NamedApple{" +
                "name='" + name + '\'' +
                ", apple=" + apple +
                '}';
    }
}
