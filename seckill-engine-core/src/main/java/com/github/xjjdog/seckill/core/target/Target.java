package com.github.xjjdog.seckill.core.target;

import lombok.Data;

/**
 * @author xjjdog
 */
@Data
public class Target {
    /**
     * 前置配置
     */
    Pre pre;
    /**
     * 后置配置
     */
    Post post;

    /**
     * 目标唯一编号，一旦确定，不可更改
     */
    String id;

    /**
     * 秒杀开始时间
     */
    long onSaleTime;

    /**
     * 每台机器队列长度 <br/>
     * 内存通常会作为第一层缓冲，如果库存有1000个，10个节点。那么，就可以设置一个中间值，进行缓冲。<br/>
     * 比如上面的例子，我们把缓冲长度定为800，则单机处理超过800个库存时，就会拒绝。
     */
    long queueSize;

    /**
     * 持续时间。<br/>
     * 主要是超过这个事件后的回仓操作。<br/>
     * 处理这种数据一般两种方式。<br/>
     * 1、发送定时消息<br/>
     * 2、通过轮询查询状态<br/>
     * 这里我们采用后者。
     */
    long keepTime;

    /**
     * 填充库存<br/>
     * 初始化库存更新
     */
    int stock = 1;


}
