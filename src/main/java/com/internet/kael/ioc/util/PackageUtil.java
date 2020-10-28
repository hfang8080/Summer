// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.util;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.internet.kael.ioc.exception.IocRuntimeException;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Set;

/**
 * @author Kael He (h_fang8080@163.com)
 */
public class PackageUtil {

    public static Set<String> scan(String... packageNames) {
        Preconditions.checkNotNull(packageNames);
        Set<String> classNames = Sets.newHashSet();
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
                        recursiveFile(packageName, file, classNames);
                    }
                }
            }
        } catch (IOException ex) {
            throw new IocRuntimeException(ex);
        }

        return classNames;
    }

    private static void recursiveFile(String packageNamePrefix, final File file, final Set<String> classNames) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (ArrayUtils.isNotEmpty(files)) {

                for (File fileEntry : files) {
                    if (fileEntry.isDirectory()) {
                        String dirName = fileEntry.getName();
                        packageNamePrefix = packageNamePrefix + "." + dirName;
                    }
                    recursiveFile(packageNamePrefix, fileEntry, classNames);
                }
            }
        } else {
            String fileName = file.getName().split("\\.")[0];
            String className = packageNamePrefix + "." + fileName;
            classNames.add(className);
        }
    }

}
