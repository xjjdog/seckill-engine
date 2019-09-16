package com.github.xjjdog.seckill.demo.security.exception;


public class PermissionException extends UserException {

    public PermissionException() {
        this("没有权限进行操作，请联系管理员授权");
    }

    public PermissionException(String msg) {
        super(401, msg);
    }
}