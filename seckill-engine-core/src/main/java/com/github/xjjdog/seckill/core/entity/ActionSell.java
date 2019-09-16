package com.github.xjjdog.seckill.core.entity;

import lombok.Data;

@Data
public class ActionSell {
    /**
     * 购买数量，默认为1
     */
    int count = 1;


    /**
     * 对象产生时时间戳
     */
    long createTime = System.currentTimeMillis();


    /**
     * 购买的对象
     */
    Actor actor;
}
