package com.internet.kael.ioc.support.meta;

import com.google.common.base.Preconditions;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <p> project: Summer-AnnotationMeta </p>
 * <p> create on 2019/11/29 23:04 </p>
 *
 * @author Kael He (kael.he@alo7.com)
 * @since 0.1.52
 */
public class ClassAnnotationTypeMeta extends AbstractAnnotationTypeMeta {

    /**
     * 注解信息
     * （1）这里其实没有必要使用 {@link Map} 因为一般注解数量不会太多，只是数组性能反而更好。
     * @since 0.1.52
     */
    private Annotation[] annotations;

    public ClassAnnotationTypeMeta(Class clazz) {
        Preconditions.checkNotNull(clazz);

        annotations = clazz.getAnnotations();
    }

    @Override
    protected Annotation[] getAnnotations() {
        return annotations;
    }

}
