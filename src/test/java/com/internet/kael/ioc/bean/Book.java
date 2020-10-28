// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.bean;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 17.0
 */
public class Book {
    private String name;
    public Book() {
    }

    public Book(String name) {
        this.name = name;
    }
    public String name() {
        return name;
    }
}
