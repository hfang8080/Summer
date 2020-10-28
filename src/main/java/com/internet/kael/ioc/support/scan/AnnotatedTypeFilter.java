// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

import java.util.List;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 22.0
 */
public class AnnotatedTypeFilter implements TypeFilter {
    @Override
    public boolean matches(Class clazz, AnnotationTypeMeta typeMeta, List<Class> classes) {
        return typeMeta.isAnnotatedOrRef(classes);
    }
}
