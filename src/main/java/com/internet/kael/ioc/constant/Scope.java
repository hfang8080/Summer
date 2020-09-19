// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.constant;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 3.0
 */
public enum Scope {
    // 单例
    SINGLETON("singleton"),
    // 多例
    PROTOTYPE("prototype");

    private String code;

    Scope(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
