// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.circle;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.ConstructorArgsDefinition;
import com.internet.kael.ioc.model.PropertyArgsDefinition;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 10.0
 */
public class DefaultBeanDependenceChecker implements BeanDependenceChecker {

    /**
     * 保存Bean及其依赖的映射关系
     * @since 10.0
     */
    private static final Map<String, Set<String>> beanDependenciesMapping = Maps.newHashMap();

    /**
     * 保存Bean及其被依赖的关系
     * @since 10.0
     */
    private static final Map<String, Set<String>> beanDependenceByMapping = Maps.newHashMap();

    @Override
    public boolean isCyclicDependence(String beanName) {
        Set<String> dependencies = beanDependenciesMapping.get(beanName);
        if (CollectionUtils.isNotEmpty(dependencies)) {
            for (String dependence : dependencies) {
                if (isCyclicDependence(beanName, dependence, null)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void registerBeanDefinition(List<BeanDefinition> beanDefinitions) {
        for (BeanDefinition bd : beanDefinitions) {
            buildDependenceMapping(bd);
        }
        buildDependenceByMapping();
    }

    /**
     * 构建被依赖的映射关系
     * @since 10.0
     */
    private void buildDependenceByMapping() {
        for (Map.Entry<String, Set<String>> entry : beanDependenciesMapping.entrySet()) {
            Set<String> dependencies = entry.getValue();
            if (CollectionUtils.isNotEmpty(dependencies)) {
                for (String dependence : dependencies) {
                    Set<String> dependenciesBy = beanDependenceByMapping.getOrDefault(dependence, Sets.newHashSet());
                    dependenciesBy.add(entry.getKey());
                    beanDependenceByMapping.put(dependence, dependenciesBy);
                }
            }
        }
    }

    /**
     * 构建Bean定义的映射关系
     * @param bd Bean定义
     * @since 10.0
     */
    private void buildDependenceMapping(BeanDefinition bd) {
        Set<String> dependencies = Sets.newHashSet();
        List<ConstructorArgsDefinition> constructorArgsDefinitions = bd.getConstructorArgsDefinitions();
        if (CollectionUtils.isNotEmpty(constructorArgsDefinitions)) {
            for (ConstructorArgsDefinition cad : constructorArgsDefinitions) {
                if (StringUtils.isNoneEmpty(cad.getRef())) {
                    dependencies.add(cad.getRef());
                }
            }
        }

        List<PropertyArgsDefinition> propertyArgsDefinitions = bd.getPropertyArgsDefinitions();
        if (CollectionUtils.isNotEmpty(propertyArgsDefinitions)) {
            for (PropertyArgsDefinition pad : propertyArgsDefinitions) {
                if (StringUtils.isNoneEmpty(pad.getRef())) {
                    dependencies.add(pad.getRef());
                }
            }
        }
        beanDependenciesMapping.put(bd.getName(), dependencies);
    }

    private boolean isCyclicDependence(String beanName, String dependence, Set<String> checked) {
        if (checked == null) {
            checked = Sets.newHashSet();
        }

        // 已经检查过了该Bean，checked为了优化
        if (CollectionUtils.isNotEmpty(checked) && checked.contains(beanName)) {
            return false;
        }
        Set<String> beanDependenceBy = beanDependenceByMapping.get(beanName);
        if (CollectionUtils.isEmpty(beanDependenceBy)) {
            return false;
        }
        // -> 表示依赖
        // bean -> dependence && dependence -> bean
        if (beanDependenceBy.contains(dependence)) {
            return true;
        }
        checked.add(beanName);

        // 查看'依赖BeanName的'是否和'Bean依赖的'存在环行依赖关系
        for (String dependenceBy : beanDependenceBy) {
            // 递归判断
            if (isCyclicDependence(dependenceBy, dependence, checked)) {
                return true;
            }
        }
        return false;

    }

}
