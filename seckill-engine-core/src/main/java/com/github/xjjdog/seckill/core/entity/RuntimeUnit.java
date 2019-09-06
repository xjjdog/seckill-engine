package com.github.xjjdog.seckill.core.entity;

import lombok.Data;

@Data
public class RuntimeUnit {
    private String id;
    /**
     * 当前库存
     */
    private int stock;
}
