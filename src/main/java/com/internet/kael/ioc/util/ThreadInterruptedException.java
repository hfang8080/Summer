// Copyright 2018 ALO7 Inc. All rights reserved.

package com.internet.kael.ioc.util;

/**
 * @author Kael He(kael.he@alo7.com)
 * @since 2.0
 */
public class ThreadInterruptedException extends RuntimeException {

    private static final long serialVersionUID = 9088225711164368803L;

    public ThreadInterruptedException() {
    }

    public ThreadInterruptedException(String message) {
        super(message);
    }

    public ThreadInterruptedException(Throwable cause) {
        super(cause);
    }

    public ThreadInterruptedException(String message, Throwable cause) {
        super(message, cause);
    }
}
