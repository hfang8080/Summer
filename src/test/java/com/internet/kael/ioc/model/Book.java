// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 0.0.9
 */
public class Book {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                '}';
    }

}
