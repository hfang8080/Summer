// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 9.0
 */
public class User {
    private Book book;

    private String name;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "book=" + book +
                ", name='" + name + '\'' +
                '}';
    }
}
