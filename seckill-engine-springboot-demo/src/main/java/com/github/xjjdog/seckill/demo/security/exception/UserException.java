package com.github.xjjdog.seckill.demo.security.exception;


public class UserException extends BaseException {

    public UserException(Integer code, String msg) {
        super("sys.user", code, msg);
    }

}