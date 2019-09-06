package com.github.xjjdog.sf.core.target;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class Target {
    /**
     * 目标唯一编号，一旦确定，不可更改
     */
    String id;


    /**
     * 前置配置
     */
    Pre pre;
    /**
     * 后置配置
     */
    Post post;


    /**
     * 秒杀开始时间
     */
    long onSaleTime;

    /**
     * 内存队列长度
     */
    long queueSize;

    /**
     * 持续时间
     */
    long keepTime;

    /**
     * 填充库存
     */
    int stock = 1;


}
