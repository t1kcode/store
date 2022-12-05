package com.t1k.store.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应结果类
 * @param <E> 响应数据的类型
 */
@Data
public class JsonResult<E> implements Serializable
{
    /** 状态码 */
    private Integer state;
    /** 状态描述信息 */
    private String message;
    /** 数据 */

    private E data;

    public JsonResult()
    {
    }

    public JsonResult(Integer state)
    {
        this.state = state;
    }

    /** 出现异常时调用 */
    public JsonResult(Throwable e)
    {
        // 获取异常对象中的异常信息
        this.message = e.getMessage();
    }

    public JsonResult(Integer state, E data)
    {
        this.state = state;
        this.data = data;
    }

    public JsonResult(Integer state, String message)
    {
        this.state = state;
        this.message = message;
    }
}
