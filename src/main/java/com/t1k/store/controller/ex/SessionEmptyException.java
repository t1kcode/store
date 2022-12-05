package com.t1k.store.controller.ex;

/** 表示session错误的异常 */
public class SessionEmptyException extends RuntimeException
{
    public SessionEmptyException() {
        super();
    }

    public SessionEmptyException(String message) {
        super(message);
    }

    public SessionEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionEmptyException(Throwable cause) {
        super(cause);
    }

    protected SessionEmptyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
