// Copyright 2020 EQUATION Inc. All rights reserved.

package com.internet.kael.ioc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Kael He(h_fang8080@163.com)
 * @since 2.0
 */
public class ThreadUtils {

    private static final Log log = LogFactory.getLog(ThreadUtils.class);

    /**
     * Check whether the thread is interrupted.
     *
     * @throws ThreadInterruptedException
     */
    public static void checkInterrupted() throws ThreadInterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new ThreadInterruptedException("The thread is interrupted.");
        }
    }

    /**
     * Called when catch an {@link InterruptedException}.
     *
     * @param e The interrupted exception.
     * @throws ThreadInterruptedException
     */
    public static void processInterruptedException(InterruptedException e) throws ThreadInterruptedException {
        Thread.currentThread().interrupt();
        throw new ThreadInterruptedException("The thread is interrupted.", e);
    }

    public static boolean sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
            return true;
        } catch (InterruptedException e) {
            log.warn("Thread is interrupted.", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }
}