// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.internet.kael.ioc.annotation.Component;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.constant.BeanSourceType;
import com.internet.kael.ioc.model.AnnotationBeanDefinition;
import com.internet.kael.ioc.model.DefaultAnnotationBeanDefinition;
import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;
import com.internet.kael.ioc.support.meta.ClassAnnotationTypeMeta;
import com.internet.kael.ioc.support.name.BeanNameStrategy;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.CollectionHelper;
import com.internet.kael.ioc.util.Lazies;
import com.internet.kael.ioc.util.PackageUtil;
import com.internet.kael.ioc.util.Scopes;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 22.0
 */
public class ClassPathAnnotationBeanDefinitionScanner implements AnnotationBeanDefinitionScanner {
    private static final AnnotatedTypeFilter TYPE_FILTER = new AnnotatedTypeFilter();
    @Override
    public Set<AnnotationBeanDefinition> scan(BeanDefinitionScannerContext context) {
        Preconditions.checkNotNull(context);
        Set<AnnotationBeanDefinition> beanDefinitions = Sets.newHashSet();
        Set<String> classNames = Sets.newHashSet();
        List<String> scanPackages = context.getScanPackages();
        for (String scanPackage : scanPackages) {
            classNames.addAll(PackageUtil.scan(scanPackage));
        }
        List<Class> classes = CollectionHelper.transform(Lists.newArrayList(classNames), ClassUtils::getClass);
        BeanNameStrategy beanNameStrategy = ClassUtils.newInstance(context.getBeanNameStrategy());
        for (Class clazz : classes) {
            ClassAnnotationTypeMeta typeMeta = new ClassAnnotationTypeMeta(clazz);
            if (isTypeFilterMatch(context, clazz, typeMeta)) {
                beanDefinitions.add(buildComponentBeanDefinition(clazz, typeMeta, beanNameStrategy));
            }
        }
        return beanDefinitions;
    }

    private AnnotationBeanDefinition buildComponentBeanDefinition(
            final Class clazz, final AnnotationTypeMeta typeMeta, final BeanNameStrategy beanNameStrategy) {
        AnnotationBeanDefinition beanDefinition = new DefaultAnnotationBeanDefinition();
        beanDefinition.setClassName(clazz.getName());
        beanDefinition.setLazyInit(Lazies.isLazy(clazz));
        beanDefinition.setScope(Scopes.getScope(clazz));
        beanDefinition.setBeanSourceType(BeanSourceType.COMPONENT);
        String beanName = (String) typeMeta.getAnnotatedAttr(Component.class, "value");
        if (StringUtils.isEmpty(beanName)) {
            beanName = beanNameStrategy.generateBeanName(beanDefinition);
        }
        beanDefinition.setName(beanName);
        return beanDefinition;
    }

    private boolean isTypeFilterMatch(
            final BeanDefinitionScannerContext context, final Class clazz, final AnnotationTypeMeta meta) {
        if (clazz.isAnnotationPresent(Configuration.class)) {
            return false;
        }
        List<Class> includes = context.getIncludes();
        List<Class> excludes = context.getExcludes();
        return TYPE_FILTER.matches(clazz, meta, includes) && !TYPE_FILTER.matches(clazz, meta, excludes);
    }
}
