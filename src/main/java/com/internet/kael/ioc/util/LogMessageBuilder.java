// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 2.0
 */
public class LogMessageBuilder {

    private String message;

    private List<String> parameters = Lists.newArrayList();

    public LogMessageBuilder() {
    }

    public LogMessageBuilder(String message) {
        this.message = message;
    }

    public LogMessageBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public LogMessageBuilder withParameter(String name, Object value) {
        parameters.add(name + "=" + value);
        return this;
    }

    public LogMessageBuilder withParameters(String name, Object... values) {
        return this.withParameters(name, Stream.of(values)
                .flatMap(Stream::of)
                .collect(Collectors.toList()));
    }

    public LogMessageBuilder withParameters(String name, List<Object> values) {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        values.forEach(value -> joiner.add(value.toString()));
        parameters.add(name + "=" + joiner.toString());
        return this;
    }

    public String build() {
        Preconditions.checkNotNull(message);

        if (parameters.isEmpty()) {
            return message;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ensureNotEndWithAlphanumeric(message));
            stringBuilder.append(" Parameters: ");
            stringBuilder.append(StringUtils.join(parameters, ","));
            return stringBuilder.toString();
        }
    }

    private String ensureNotEndWithAlphanumeric(String message) {
        message = StringUtils.trimToEmpty(message);
        if (StringUtils.isAlphanumeric(StringUtils.right(message, 1))) {
            message += ".";
        }
        return message;
    }

    @Override
    public String toString() {
        return build();
    }

}

