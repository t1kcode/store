package com.t1k.store.controller.ex;

/** 表示验证码错误的异常 */
public class CodeNotMatchException extends RuntimeException
{
    public CodeNotMatchException() {
        super();
    }

    public CodeNotMatchException(String message) {
        super(message);
    }

    public CodeNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeNotMatchException(Throwable cause) {
        super(cause);
    }

    protected CodeNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
