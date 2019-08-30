package com.github.xjjdog.sf.core.components.stock;

import com.github.xjjdog.sf.core.target.Target;

public interface StockService {
    /**
     * 获取库存数量
     */
    int stockNumber(Target target);

    /**
     * 增加库存
     * 分布式安全，不用加锁
     */
    boolean incStockNumber(Target target,int number);

    /**
     * 扣减库存
     */
    boolean subStockNumber(Target target,int number);

    /**
     * 填充库存
     * 一般发生在初始化阶段
     */
    void fillStock(Target target);
}
