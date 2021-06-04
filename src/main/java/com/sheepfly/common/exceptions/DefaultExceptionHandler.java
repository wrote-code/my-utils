package com.sheepfly.common.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认异常处理器
 *
 * @author sheepfly
 */
public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        log.error("发生未知异常:" + t.getName(), e);
    }
}
