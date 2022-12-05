package com.t1k.store.service.ex;

/** 订单数据不存在的异常*/
public class OrderNotFoundException extends ServiceException
{
    public OrderNotFoundException()
    {
        super();
    }

    public OrderNotFoundException(String message)
    {
        super(message);
    }

    public OrderNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public OrderNotFoundException(Throwable cause)
    {
        super(cause);
    }

    protected OrderNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                       boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
