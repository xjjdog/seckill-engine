package com.github.xjjdog.seckill.demo.security.exception;

import lombok.Getter;


@Getter
public class BaseException extends RuntimeException {

    private Integer code;
    private String module;
    private Object args;
    private String msg;


    public BaseException(String module, Integer code, Object args, String msg)
    {
        this.module = module;
        this.code = code;
        this.args = args;
        this.msg = msg;
    }

    public BaseException(String module, Integer code, Object args)
    {
        this(module, code, args, null);
    }

    public BaseException(String module, Integer code, String msg)
    {
        this(module, code, null, msg);
    }

    public BaseException(String module, String msg)
    {
        this(module, null, null, msg);
    }

    public BaseException(Integer code, Object args)
    {
        this(null, code, args, null);
    }

    public BaseException(String msg)
    {
        this(null, null, null, msg);
    }

    @Override
    public String getMessage()
    {
        return this.msg;
    }
}
