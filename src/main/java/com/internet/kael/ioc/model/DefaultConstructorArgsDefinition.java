// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.model;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 6.0
 */
public class DefaultConstructorArgsDefinition implements ConstructorArgsDefinition {
    private String ref;
    private String value;
    private String type;

    @Override
    public void setRef(String ref) {
        this.ref = ref;
    }

    @Override
    public String getRef() {
        return ref;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
