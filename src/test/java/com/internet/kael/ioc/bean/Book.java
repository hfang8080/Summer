// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.bean;

/**
 * @author Kael He (kael.he@alo7.com)
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
