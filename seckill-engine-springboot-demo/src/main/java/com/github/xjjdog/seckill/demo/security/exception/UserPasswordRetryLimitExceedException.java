package com.github.xjjdog.seckill.demo.security.exception;

import java.util.Optional;

public class UserPasswordRetryLimitExceedException extends UserException {
    public UserPasswordRetryLimitExceedException() {
        this(null);
    }

    public UserPasswordRetryLimitExceedException(String msg) {
        super(Integer.valueOf(401), Optional.ofNullable(msg).orElse("密码错误次数过多"));
    }
}