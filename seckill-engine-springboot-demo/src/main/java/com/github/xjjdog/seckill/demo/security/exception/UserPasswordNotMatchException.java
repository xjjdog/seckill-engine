package com.github.xjjdog.seckill.demo.security.exception;


import java.util.Optional;

public class UserPasswordNotMatchException extends UserException {

    public UserPasswordNotMatchException() {
        this(null);
    }

    public UserPasswordNotMatchException(String msg) {
        super(Integer.valueOf(401), Optional.ofNullable(msg).orElse("用户名或密码不正确"));
    }
}