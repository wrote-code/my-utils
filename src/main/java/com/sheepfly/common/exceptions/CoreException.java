package com.sheepfly.common.exceptions;

/**
 * 异常。
 *
 * @author sheepfly
 */
public class CoreException extends Throwable{
    public CoreException(String message) {
        super(message);
    }

    public CoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoreException(Throwable cause) {
        super(cause);
    }
}
