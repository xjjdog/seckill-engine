package com.github.xjjdog.seckill.core;

import com.github.xjjdog.seckill.core.components.stock.StockService;

public class Holder {
    private static Holder holder = new Holder();
    private Holder(){}
    public StockService getStockService(){
        return stockService;
    }
    StockService stockService;
    TargetService targetService;
}
