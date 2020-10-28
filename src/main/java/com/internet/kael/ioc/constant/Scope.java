// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.constant;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 3.0
 */
public enum Scope {
    // 单例
    SINGLETON(SummerConstant.SINGLETON),
    // 多例
    PROTOTYPE(SummerConstant.PROTOTYPE);

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
