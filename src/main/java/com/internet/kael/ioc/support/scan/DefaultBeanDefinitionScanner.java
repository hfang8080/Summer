// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.support.scan;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.internet.kael.ioc.exception.IocRuntimeException;
import com.internet.kael.ioc.model.BeanDefinition;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Set;

/**
 * Bean定义扫描类的默认实现
 * @author Kael He (kael.he@alo7.com)
 * @since 10.1
 */
public class DefaultBeanDefinitionScanner implements BeanDefinitionScanner {

    @Override
    public Set<BeanDefinition> scan(String... packageNames) {
        Preconditions.checkNotNull(packageNames);
        Set<BeanDefinition> beanDefinitions = Sets.newHashSet();
        try {
            for (String packageName : packageNames) {
                String packagePath = packageName.replace(".", "/");
                Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packagePath);
                while (dirs.hasMoreElements()) {
                    URL url = dirs.nextElement();
                    String protocol = url.getProtocol();

                    if ("file".equals(protocol)) {
                        String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                        File file = new File(filePath);
                        recursiveFile(packageName, file, beanDefinitions);
                    }
                }
            }
        } catch (IOException ex) {
            throw new IocRuntimeException(ex);
        }

        return beanDefinitions;
    }

    private void recursiveFile(String packageNamePrefix, final File file, final Set<BeanDefinition> definitions) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            String dirName = file.getName();
            if (ArrayUtils.isNotEmpty(files)) {
                packageNamePrefix = packageNamePrefix + "." + dirName;
                for (File fileEntry : files) {
                    recursiveFile(packageNamePrefix, fileEntry, definitions);
                }
            }
        } else {
            String fileName = file.getName().split("\\.")[0];
            String className = packageNamePrefix + "." + fileName;
            System.out.println(className);
        }
    }

    public static void main(String[] args) {
        DefaultBeanDefinitionScanner scanner = new DefaultBeanDefinitionScanner();
        scanner.scan("com.internet.kael.ioc");
    }
}
