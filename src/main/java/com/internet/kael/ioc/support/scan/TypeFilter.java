// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

import java.util.List;

/**
 * @author Kael He (h_fang8080@163.com)
 * @since 22.0
 */
public interface TypeFilter {

    boolean matches(final Class clazz, final AnnotationTypeMeta typeMeta, final List<Class> classes);
}
