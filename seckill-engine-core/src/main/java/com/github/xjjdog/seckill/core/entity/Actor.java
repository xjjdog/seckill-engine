package com.github.xjjdog.seckill.core.entity;

import lombok.Data;

/**
 * 购买主体，比如购买的某个人，或者手机号等
 */
@Data
public class Actor {
    /**
     * 唯一id，比如userid，手机号等
     */
    String id;
    /**
     * 关联的外部实体，可不设置
     */
    Object ref;
}
