// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 22.0
 */
public class AnnotatedTypeFilter implements TypeFilter {
    @Override
    public boolean matches(Class clazz, AnnotationTypeMeta typeMeta, List<Class> classes) {
        return typeMeta.isAnnotatedOrRef(classes);
    }
}
