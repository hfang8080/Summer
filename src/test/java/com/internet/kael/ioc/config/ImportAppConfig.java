// Copyright 2020 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.config;

import com.internet.kael.ioc.annotation.Configuration;
import com.internet.kael.ioc.annotation.Import;
import com.internet.kael.ioc.context.AnnotationApplicationContext;
import org.junit.Test;

/**
 * @author Kael He (kael.he@alo7.com)
 * @since 14.0
 */
@Import(AppleBeanConfig.class)
@Configuration
public class ImportAppConfig {


}
