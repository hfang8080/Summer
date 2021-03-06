// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.support.meta;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.internet.kael.ioc.util.ClassUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p> project: Summer-AnnotationMeta </p>
 * <p> create on 2019/11/29 23:04 </p>
 *
 * @author Kael He (h_fang8080@163.com)
 * @since 18.0
 */
public abstract class AbstractAnnotationTypeMeta implements AnnotationTypeMeta {

    /**
     * 注解引用 map
     *
     * @since 0.1.52
     */
    private Map<String, Annotation> annotationRefMap;

    /**
     * 获取对应的注解信息列表
     * <p>
     * （1）这里其实没有必要使用 {@link Map} 因为一般注解数量不会太多，只是数组性能反而更好。
     *
     * @return 注解数组
     * @since 0.1.52
     */
    protected abstract Annotation[] getAnnotations();

    protected AbstractAnnotationTypeMeta() {
        annotationRefMap = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAnnotated(String annotationName) {
        Annotation annotation = this.getAnnotation(annotationName);
        return Objects.nonNull(annotation);
    }

    @Override
    public Annotation getAnnotation(String annotationName) {
        Preconditions.checkNotNull(annotationName);

        Optional<Annotation> annotationOptional = getAnnotationOpt(getAnnotations(), annotationName);
        return annotationOptional.orElse(null);
    }

    @Override
    public boolean isAnnotatedOrRef(String annotationName) {
        // 直接注解
        if (isAnnotated(annotationName)) {
            return true;
        }

        // 元注解
        List<Annotation> annotationRefs = getAnnotationRefs(annotationName);
        return CollectionUtils.isNotEmpty(annotationRefs);
    }

    @Override
    public boolean isAnnotatedOrRef(List<Class> classList) {
        if (CollectionUtils.isEmpty(classList)) {
            return false;
        }

        for (Class clazz : classList) {
            if (isAnnotatedOrRef(clazz.getName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isAnnotationRef(Class clazz) {
        return isAnnotatedOrRef(clazz.getName()) && !isAnnotated(clazz.getName());
    }

    @Override
    public List<Annotation> getAnnotationOrRefs(String annotationName) {
        Set<Annotation> annotationSet = Sets.newHashSet();

        // 直接注解
        Annotation annotation = getAnnotation(annotationName);
        if (Objects.nonNull(annotation)) {
            annotationSet.add(annotation);
        }

        // 元注解列表
        List<Annotation> annotationRefList = getAnnotationRefs(annotationName);
        annotationSet.addAll(annotationRefList);

        // 构建结果
        return Lists.newArrayList(annotationSet);
    }

    /**
     * 获取注解对应信息
     *
     * @param annotations    注解数组
     * @param annotationName 指定注解名称
     * @return 结果信息
     * @since 0.1.52
     */
    private Optional<Annotation> getAnnotationOpt(final Annotation[] annotations, final String annotationName) {
        List<Annotation> annotationList = Arrays.asList(annotations);
        return getAnnotationOpt(annotationList, annotationName);
    }

    /**
     * 获取注解对应信息
     *
     * @param annotations    注解列表
     * @param annotationName 指定注解名称
     * @return 结果信息
     * @since 18.0
     */
    private Optional<Annotation> getAnnotationOpt(final List<Annotation> annotations, final String annotationName) {
        if (CollectionUtils.isEmpty(annotations)) {
            return Optional.empty();
        }

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().getName().equals(annotationName)) {
                return Optional.ofNullable(annotation);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Annotation> getAnnotationRefs(String annotationName) {
        Set<Annotation> annotationSet = Sets.newHashSet();

        if (ArrayUtils.isNotEmpty(getAnnotations())) {
            for (Annotation annotation : getAnnotations()) {
                Annotation[] annotationRefs = annotation.annotationType().getAnnotations();
                Optional<Annotation> annotationRefOptional = getAnnotationOpt(annotationRefs, annotationName);
                if (annotationRefOptional.isPresent()) {
                    // 添加引用属性（注解全称+引用的注解全称）
                    final String key = annotationName + annotation.annotationType().getName();
                    annotationRefMap.put(key, annotationRefOptional.get());

                    annotationSet.add(annotation);
                }
            }
        }

        return Lists.newArrayList(annotationSet);
    }

    @Override
    public Annotation getAnnotationReferenced(String annotationName, String annotationRefName) {
        // 需要在getAnnotationRefs()之后调用。

        Preconditions.checkNotNull(annotationName);
        Preconditions.checkNotNull(annotationRefName);
        final String key = annotationName + annotationRefName;
        return annotationRefMap.get(key);
    }

    @Override
    public Map<String, Object> getAnnotationAttributes(String annotationName) {
        Preconditions.checkNotNull(annotationName);

        Annotation annotation = this.getAnnotation(annotationName);
        if (Objects.isNull(annotation)) {
            return null;
        }

        return ClassUtils.getAnnotationAttributes(annotation);
    }

    @Override
    public Map<String, Object> getAnnotationOrRefAttributes(String annotationName) {
        Preconditions.checkNotNull(annotationName);

        List<Annotation> annotationList = this.getAnnotationOrRefs(annotationName);
        if (CollectionUtils.isEmpty(annotationList)) {
            return null;
        }

        // 遍历选择第一个直接返回
        Annotation annotation = annotationList.get(0);
        return ClassUtils.getAnnotationAttributes(annotation);
    }

    @Override
    public Object getAnnotationOrRefAttribute(String annotationName, String attrMethodName) {
        Preconditions.checkNotNull(annotationName);
        Preconditions.checkNotNull(attrMethodName);

        Map<String, Object> attrMap = getAnnotationOrRefAttributes(annotationName);
        if (MapUtils.isEmpty(attrMap)) {
            return null;
        }

        return attrMap.get(attrMethodName);
    }

    @Override
    public Object getAnnotationAttr(Annotation annotation, String methodName) {
        Preconditions.checkNotNull(annotation);
        Preconditions.checkNotNull(methodName);

        Map<String, Object> attrs = ClassUtils.getAnnotationAttributes(annotation);
        return attrs.get(methodName);
    }

    @Override
    public Object getAnnotatedAttr(Class<? extends Annotation> clazz, String methodName) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(methodName);

        Annotation annotation = getAnnotation(clazz.getName());
        if (Objects.nonNull(annotation)) {
            return getAnnotationAttr(annotation, methodName);
        }

        return null;
    }

    @Override
    public Object getAnnotationReferencedAttr(Class<? extends Annotation> clazz, String methodName) {
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(methodName);
        final String annotationName = clazz.getName();

        if (ArrayUtils.isNotEmpty(getAnnotations())) {
            for (Annotation annotation : getAnnotations()) {
                Annotation[] annotationRefs = annotation.annotationType().getAnnotations();

                if (ArrayUtils.isNotEmpty(annotationRefs)) {
                    for (Annotation annotationRef : annotationRefs) {
                        if (annotationName.equals(annotationRef.annotationType().getName())) {
                            return getAnnotationAttr(annotationRef, methodName);
                        }
                    }
                }
            }
        }

        return null;
    }

}
