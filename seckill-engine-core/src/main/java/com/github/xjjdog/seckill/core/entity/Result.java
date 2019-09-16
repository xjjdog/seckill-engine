package com.github.xjjdog.seckill.core.entity;

import lombok.Data;

@Data
public class Result<T> {
    int code;
    String msg;

    T e;
}
