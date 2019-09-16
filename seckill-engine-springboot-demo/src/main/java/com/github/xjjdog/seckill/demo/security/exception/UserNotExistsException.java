package com.github.xjjdog.seckill.demo.security.exception;


import java.util.Optional;

public class UserNotExistsException extends UserException {

    public UserNotExistsException() {
        this(null);
    }

    public UserNotExistsException(String msg) {
        super(Integer.valueOf(401), Optional.ofNullable(msg).orElse("该用户不存在"));
    }
}