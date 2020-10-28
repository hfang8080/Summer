// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.bean;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 6.0
 */
public class WeightedApple {
    private Apple apple;
    private Integer weight;

    public WeightedApple() {
    }

    public WeightedApple(Apple apple, Integer weight) {
        this.apple = apple;
        this.weight = weight;
    }

    public Apple getApple() {
        return apple;
    }

    public void setApple(Apple apple) {
        this.apple = apple;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "WeightedApple{" +
                "apple=" + apple +
                ", weight=" + weight +
                '}';
    }
}
