// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 1.0
 */
public class JsonMapperFactory {

    private static final ObjectMapper DEFAULT_JSON_MAPPER = new ObjectMapper();

    static {
        DEFAULT_JSON_MAPPER.setVisibility(
                DEFAULT_JSON_MAPPER.getVisibilityChecker().withFieldVisibility(
                        JsonAutoDetect.Visibility.ANY).withGetterVisibility(
                        JsonAutoDetect.Visibility.NONE).withIsGetterVisibility(
                        JsonAutoDetect.Visibility.NONE));

        DEFAULT_JSON_MAPPER
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * Get default json object mapper.
     */
    public static ObjectMapper getDefaultMapper() {
        return DEFAULT_JSON_MAPPER;
    }

    public static CollectionType getListType(Class<?> clazz) {
        return DEFAULT_JSON_MAPPER.getTypeFactory().constructCollectionType(
                List.class, clazz);
    }

    public static MapType getMapType(Class<?> keyClass, Class<?> valueClass) {
        return DEFAULT_JSON_MAPPER.getTypeFactory().constructMapType(HashMap.class,
                keyClass, valueClass);
    }

    public static JavaType getType(Type type) {
        return DEFAULT_JSON_MAPPER.getTypeFactory().constructType(type);
    }
}
