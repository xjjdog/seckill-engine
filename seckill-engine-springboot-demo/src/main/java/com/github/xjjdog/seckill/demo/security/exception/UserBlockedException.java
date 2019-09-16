package com.github.xjjdog.seckill.demo.security.exception;

import org.omg.CORBA.UserException;

import java.util.Optional;

public class UserBlockedException extends UserException {

    public UserBlockedException() {
        this(null);
    }

    public UserBlockedException(String msg) {
        super(Optional.ofNullable(msg).orElse("用户已锁定"));
    }
}