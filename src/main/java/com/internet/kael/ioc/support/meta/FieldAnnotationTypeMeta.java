// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.meta;

import com.google.common.base.Preconditions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 字段注解类型信息
 * <p> project: Summer-AnnotationMeta </p>
 * <p> create on 2019/11/29 23:04 </p>
 *
 * @author Kael He (h_fang8080@163.com)
 * @since 0.1.55
 */
public class FieldAnnotationTypeMeta extends AbstractAnnotationTypeMeta {

    /**
     * 注解信息
     * @since 0.1.55
     */
    private Annotation[] annotations;

    public FieldAnnotationTypeMeta(Field field) {
        Preconditions.checkNotNull(field);

        annotations = field.getAnnotations();
    }

    @Override
    protected Annotation[] getAnnotations() {
        return annotations;
    }

}
