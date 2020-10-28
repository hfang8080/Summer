// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;

import java.util.List;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 22.0
 */
public interface TypeFilter {

    boolean matches(final Class clazz, final AnnotationTypeMeta typeMeta, final List<Class> classes);
}
