// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 1.0
 */
public class JsonConverter {

    private static final Log log = LogFactory.getLog(JsonConverter.class);

    public static <T> String serialize(T obj) {
        try {
            return JsonMapperFactory.getDefaultMapper().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Serialize json failed.", e);
            return null;
        }
    }

    public static <T> byte[] serialize(T obj, String encoding) {
        try {
            String json = JsonMapperFactory.getDefaultMapper().writeValueAsString(obj);
            return json.getBytes(encoding);
        } catch (IOException e) {
            log.warn("Serialize json failed.", e);
            return null;
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json, clazz);
        } catch (IOException e) {
            log.warn("Deserialize json failed.", e);
            return null;
        }
    }

    public static <T> T deserialize(byte[] bytes, Class<T> clazz, String encoding) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(
                    new String(bytes, encoding), clazz);
        } catch (IOException e) {
            log.warn("Deserialize json failed.", e);
            return null;
        }
    }

    public static Object deserialize(String json, Type type) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getType(type));
        } catch (IOException e) {
            log.warn("Deserialize json failed.", e);
            return null;
        }
    }

    public static <T> T deserialize(Map<String, Object> map, Class<T> clazz) {
        return JsonMapperFactory.getDefaultMapper().convertValue(map, clazz);
    }

    public static <T> List<T> deserializeList(String json, Class<T> elementClazz) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getListType(elementClazz));
        } catch (IOException e) {
            log.warn("Deserialize json failed.", e);
            return null;
        }
    }

    public static <T, E> Map<T, E> deserializeMap(String json, Class<T> key, Class<E> value) {
        try {
            return JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getMapType(key, value));
        } catch (IOException e) {
            log.warn("Deserialize json failed.", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, E> T deserializeGenerics(String json, Class<T> type, Class<E> parameterType) {
        try {
            return (T) JsonMapperFactory.getDefaultMapper().readValue(json,
                    JsonMapperFactory.getDefaultMapper().getTypeFactory()
                            .constructParametrizedType(type, type, parameterType));
        } catch (IOException e) {
            log.warn("Deserialize json failed.", e);
            return null;
        }
    }

    public static int extractInt(String json, String key) throws IOException {
        JsonNode jsonNode = JsonMapperFactory.getDefaultMapper().readTree(json);
        return jsonNode.get(key).asInt();
    }

}
