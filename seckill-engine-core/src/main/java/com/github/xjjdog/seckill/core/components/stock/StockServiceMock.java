package com.github.xjjdog.seckill.core.components.stock;

import com.github.xjjdog.seckill.core.entity.RuntimeUnit;
import com.github.xjjdog.seckill.core.target.Target;

import java.util.HashMap;
import java.util.Properties;

public class StockServiceMock implements StockService {
    /**
     * 运行时实体
     * 模拟分布式初始化锁
     */
    HashMap<String, RuntimeUnit> runtimeUnitHashMap = new HashMap<>();

    @Override
    public void configure(Properties properties) {

    }

    @Override
    public synchronized int stockNumber(Target target) {
        final String tagertID = target.getId();

        RuntimeUnit runtime = runtimeUnitHashMap.get(tagertID);
        if (null == runtime) {
            return -1;
        }
        return runtime.getStock();
    }

    @Override
    public synchronized boolean incStockNumber(Target target, int number) {
        final String tagertID = target.getId();

        RuntimeUnit runtime = runtimeUnitHashMap.get(tagertID);
        if (null == runtime) {
            return false;
        }

        runtime.setStock(runtime.getStock() + number);
        return true;
    }

    @Override
    public synchronized boolean subStockNumber(Target target, int number) {
        final String tagertID = target.getId();

        RuntimeUnit runtime = runtimeUnitHashMap.get(tagertID);
        if (null == runtime) {
            return false;
        }

        runtime.setStock(runtime.getStock() - number);
        return true;
    }

    @Override
    public synchronized void fillStock(Target target) {
        final String tagertID = target.getId();

        RuntimeUnit runtime = runtimeUnitHashMap.get(tagertID);
        if (null == runtime) {
            runtime = new RuntimeUnit();
            runtime.setStock(target.getStock());
            runtimeUnitHashMap.putIfAbsent(tagertID, runtime);
        }
    }

    @Override
    public void cleanup(Target target) {
        final String tagertID = target.getId();
        runtimeUnitHashMap.remove(tagertID);
    }
}
