// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.context;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.internet.kael.ioc.annotation.Bean;
import com.internet.kael.ioc.annotation.ComponentScan;
import com.internet.kael.ioc.annotation.Conditional;
import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Import;
import com.internet.kael.ioc.annotation.PropertiesResource;
import com.internet.kael.ioc.annotation.Value;
import com.internet.kael.ioc.constant.BeanSourceType;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.AnnotationBeanDefinition;
import com.internet.kael.ioc.model.BeanDefinition;
import com.internet.kael.ioc.model.DefaultAnnotationBeanDefinition;
import com.internet.kael.ioc.model.DefaultPropertyArgsDefinition;
import com.internet.kael.ioc.model.PropertyArgsDefinition;
import com.internet.kael.ioc.support.condition.Condition;
import com.internet.kael.ioc.support.condition.DefaultConditionContext;
import com.internet.kael.ioc.support.environment.DefaultEnvironment;
import com.internet.kael.ioc.support.environment.Environment;
import com.internet.kael.ioc.support.meta.AnnotationTypeMeta;
import com.internet.kael.ioc.support.meta.ClassAnnotationTypeMeta;
import com.internet.kael.ioc.support.meta.MethodAnnotationTypeMeta;
import com.internet.kael.ioc.support.name.BeanNameStrategy;
import com.internet.kael.ioc.support.name.DefaultBeanNameStrategy;
import com.internet.kael.ioc.support.resolver.PropertiesPropertySource;
import com.internet.kael.ioc.support.resolver.PropertyResource;
import com.internet.kael.ioc.support.resolver.PropertySourcesPropertyResolver;
import com.internet.kael.ioc.support.scan.AnnotationBeanDefinitionScanner;
import com.internet.kael.ioc.support.scan.ClassPathAnnotationBeanDefinitionScanner;
import com.internet.kael.ioc.support.scan.DefaultBeanDefinitionScannerContext;
import com.internet.kael.ioc.util.ClassUtils;
import com.internet.kael.ioc.util.Lazies;
import com.internet.kael.ioc.util.Primaries;
import com.internet.kael.ioc.util.Scopes;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 注解类型的应用上下文
 * @author Kael He (kael.he@alo7.com)
 * @since 11.0
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    /**
     * 配置类信息
     * @since 11.0
     */
    private final Class[] configClasses;

    /**
     * 环境
     * @since 19.0
     */
    private final Environment environment;

    /**
     * Bean名称的命名策略
     * @since 11.0
     */
    private BeanNameStrategy beanNameStrategy = new DefaultBeanNameStrategy();

    /**
     * 包扫描器
     * @since 22.0
     */
    private AnnotationBeanDefinitionScanner scanner = new ClassPathAnnotationBeanDefinitionScanner();

    public AnnotationApplicationContext(Class... configClasses) {
        this(new DefaultEnvironment(), configClasses);
    }

    public AnnotationApplicationContext(Environment environment, Class... configClasses) {
        Preconditions.checkNotNull(configClasses);
        Preconditions.checkNotNull(environment);
        this.configClasses = configClasses;
        this.environment = environment;
        super.init();
    }

    @Override
    protected List<BeanDefinition> buildBeanDefinitions() {
        List<BeanDefinition> beanDefinitions = Lists.newArrayList();
        List<Class> configurations = getConfigurations();
        for (Class clazz : configurations) {
            if (clazz.isAnnotationPresent(Configuration.class)) {
                Optional<AnnotationBeanDefinition> optionalBeanDefinition = buildConfigurationBeanDefinition(clazz);
                if (optionalBeanDefinition.isPresent()) {
                    AnnotationBeanDefinition configBeanDefinition = optionalBeanDefinition.get();
                    beanDefinitions.add(configBeanDefinition);
                    List<AnnotationBeanDefinition> bds = buildBeansInConfiguration(configBeanDefinition, clazz);
                    beanDefinitions.addAll(bds);

                    Set<AnnotationBeanDefinition> scannedBeanDefinitions = buildScanBeanDefinitions(clazz);
                    beanDefinitions.addAll(scannedBeanDefinitions);
                }
            }
        }
        return beanDefinitions;
    }

    /**
     * 构建扫描对象集合
     * @param clazz 类
     * @since 22.0
     */
    private Set<AnnotationBeanDefinition> buildScanBeanDefinitions(final Class clazz) {
        Set<AnnotationBeanDefinition> annotationBeanDefinitions = Sets.newHashSet();
        if (!clazz.isAnnotationPresent(ComponentScan.class)) {
            return annotationBeanDefinitions;
        }
        ComponentScan componentScan = (ComponentScan) clazz.getAnnotation(ComponentScan.class);
        DefaultBeanDefinitionScannerContext scannerContext = new DefaultBeanDefinitionScannerContext();
        scannerContext.setScanPackages(Arrays.asList(componentScan.value()));
        scannerContext.setBeanNameStrategy(componentScan.beanNameStrategy());
        scannerContext.setExcludes(Arrays.asList(componentScan.excludes()));
        scannerContext.setIncludes(Arrays.asList(componentScan.includes()));
        return scanner.scan(scannerContext);
    }

    /**
     * 构建参数信息
     * @since 21.0
     */
    private List<PropertyArgsDefinition> buildConfigPropertyArgDefinitions(final Class clazz) {
        List<PropertyArgsDefinition> result = Lists.newArrayList();
        List<PropertyResource> propertyResources = buildPropertyResource(clazz);
        PropertySourcesPropertyResolver resolver = new PropertySourcesPropertyResolver(propertyResources);
        List<Field> fields = ClassUtils.getAllFields(clazz);
        for (Field field : fields) {
            if (field.isAnnotationPresent(Value.class)) {
                Value value = field.getAnnotation(Value.class);
                String expression = value.value();
                String actualValue = resolver.resolveRequiredPlaceholders(expression);
                PropertyArgsDefinition argDefinition = new DefaultPropertyArgsDefinition();
                argDefinition.setName(field.getName());
                argDefinition.setFieldBase(true);
                argDefinition.setType(field.getType().getName());
                argDefinition.setValue(actualValue);
                result.add(argDefinition);
            }
        }
        return result;
    }

    /**
     * 构建属性对
     * @since 21.0
     */
    private List<PropertyResource> buildPropertyResource(final Class clazz) {
        if (!clazz.isAnnotationPresent(PropertiesResource.class)) {
            return Collections.emptyList();
        }

        PropertiesResource propertiesResource = (PropertiesResource) clazz.getAnnotation(PropertiesResource.class);
        String[] resources = propertiesResource.value();
        if (ArrayUtils.isEmpty(resources)) {
            return Collections.emptyList();
        }
        return Stream.of(resources)
                .map(path -> {
                    Properties properties = this.getProperties(path);
                    return new PropertiesPropertySource(path, properties);
                })
                .collect(Collectors.toList());
    }

    /**
     * 加载配置文件信息
     * @param path 路径
     * @return 结果
     * @since 0.1.10
     */
    private Properties getProperties(final String path) {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)){
            Properties properties = new Properties();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
            return properties;
        } catch (IOException e) {
            throw new IocRuntimeException("Load properties file fail of path: " + path);
        }
    }

    /**
     * 获取所有的配置
     * @return 所有的配置类
     * @since 14.0
     */
    private List<Class> getConfigurations() {
        LinkedList<Class> configurations = Lists.newLinkedList();
        for (Class clazz : configClasses) {
            addAllImportClass(configurations, clazz);
        }
        return Lists.newArrayList(configurations);
    }

    /**
     * 增加所有的配置类
     * @param configurations 所有的配置信息
     * @param configClass 配置类
     * @since 14.0
     */
    private void addAllImportClass(final LinkedList<Class> configurations, final Class configClass) {
        if (!configurations.contains(configClass)) {
            configurations.addFirst(configClass);
        }
        if (configClass.isAnnotationPresent(Import.class)) {
            Import annotation = (Import) configClass.getAnnotation(Import.class);
            Class[] configClasses = annotation.value();
            for (Class clazz : configClasses) {
                if (!configurations.contains(clazz)) {
                    configurations.addFirst(clazz);
                    addAllImportClass(configurations, clazz);
                }

            }
        }

    }

    /**
     * 构建@Configuration类到Bean定义中
     * @param clazz 标注为@Configuration的类
     * @return Bean的定义
     */
    private Optional<AnnotationBeanDefinition> buildConfigurationBeanDefinition(final Class clazz) {
        // 验证是否需要实例化对象
        AnnotationTypeMeta meta = new ClassAnnotationTypeMeta(clazz);
        if (!conditionMatches(meta)) {
            return Optional.empty();
        }

        if (!clazz.isAnnotationPresent(Configuration.class)) {
            return Optional.empty();
        }
        Configuration configuration = (Configuration) clazz.getAnnotation(Configuration.class);
        AnnotationBeanDefinition beanDefinition = new DefaultAnnotationBeanDefinition();
        beanDefinition.setClassName(clazz.getName());
        beanDefinition.setLazyInit(Lazies.isLazy(clazz));
        beanDefinition.setScope(Scopes.getScope(clazz));
        beanDefinition.setPrimary(Primaries.isPrimary(clazz));
        beanDefinition.setBeanSourceType(BeanSourceType.CONFIGURATION);
        String beanName = configuration.value();
        if (StringUtils.isEmpty(beanName)) {
            beanName = beanNameStrategy.generateBeanName(beanDefinition);
        }
        beanDefinition.setName(beanName);
        List<PropertyArgsDefinition> propertyArgsDefinitions = buildConfigPropertyArgDefinitions(clazz);
        beanDefinition.setPropertyArgsDefinitions(propertyArgsDefinitions);
        return Optional.of(beanDefinition);
    }

    private List<AnnotationBeanDefinition> buildBeansInConfiguration(
            final BeanDefinition configBeanDefinition, final Class clazz) {
        if (!clazz.isAnnotationPresent(Configuration.class)) {
            return Collections.emptyList();
        }

        List<AnnotationBeanDefinition> beanDefinitions = Lists.newArrayList();
        List<Method> methods = ClassUtils.getMethods(clazz);
        for (Method method : methods) {
            if (method.isAnnotationPresent(Bean.class)) {
                AnnotationTypeMeta meta = new MethodAnnotationTypeMeta(method);
                if (!conditionMatches(meta)) {
                    continue;
                }
                Bean bean = method.getAnnotation(Bean.class);
                String methodName = method.getName();
                Class<?> returnType = method.getReturnType();
                String beanName = methodName;
                if (StringUtils.isNotEmpty(bean.value())) {
                    beanName = bean.value();
                }
                DefaultAnnotationBeanDefinition bd = new DefaultAnnotationBeanDefinition();
                bd.setName(beanName);
                bd.setClassName(returnType.getName());
                bd.setInitMethodName(bean.initMethod());
                bd.setDestroyMethodName(bean.destroyMethod());
                bd.setBeanSourceType(BeanSourceType.CONFIGURATION_BEAN);
                bd.setConfigurationName(configBeanDefinition.getName());
                bd.setConfigurationBeanMethod(methodName);
                bd.setConfigBeanMethodParamTypes(method.getParameterTypes());
                bd.setConfigBeanMethodParamRefs(ClassUtils.getMethodParamNames(method));
                // 这里需要添加property/constructor对应的实现
                bd.setLazyInit(Lazies.isLazy(method));
                bd.setScope(Scopes.getScope(method));
                bd.setPrimary(Primaries.isPrimary(method));
                beanDefinitions.add(bd);
            }
        }
        return beanDefinitions;
    }

    /**
     * 类级别是否匹配
     * @param meta 判断条件是否匹配
     * @return 是否
     * @since 18.0
     */
    private boolean conditionMatches(final AnnotationTypeMeta meta) {
        HashMap<Class<? extends Condition>, Map<String, Object>> map = Maps.newHashMap();
        String conditionalName = Conditional.class.getName();

        // 直接获取注解信息
        if (meta.isAnnotated(conditionalName)) {
            Conditional conditional = (Conditional) meta.getAnnotation(conditionalName);
            map.put(conditional.value(), null);
        }

        // 获取拓展引用的注解信息
        List<Annotation> refs = meta.getAnnotationRefs(conditionalName);
        for (Annotation annotation : refs) {
            Map<String, Object> attributes = ClassUtils.getAnnotationAttributes(annotation);
            String name = annotation.annotationType().getName();
            Annotation annotationReferenced = meta.getAnnotationReferenced(conditionalName, name);
            if (Objects.isNull(annotationReferenced)) {
                continue;
            }
            Conditional conditionalReferenced = (Conditional) annotationReferenced;
            map.put(conditionalReferenced.value(), attributes);
        }

        // 循环处理

        DefaultConditionContext conditionContext = new DefaultConditionContext(this, meta, environment);
        for (Map.Entry<Class<? extends Condition>, Map<String, Object>> entry : map.entrySet()) {
            Condition condition = ClassUtils.newInstance(entry.getKey());
            boolean match = condition.matches(conditionContext, entry.getValue());
            if (!match) {
                return false;
            }
        }
        return true;

    }

    @Override
    public Environment getEnvironment() {
        return environment;
    }
}
