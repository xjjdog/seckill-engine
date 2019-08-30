package com.github.xjjdog.sf.core;

import com.github.xjjdog.sf.core.components.stock.StockService;
import com.github.xjjdog.sf.core.entity.ActionSell;
import com.github.xjjdog.sf.core.components.stock.StockServiceMock;
import com.github.xjjdog.sf.core.entity.Result;
import com.github.xjjdog.sf.core.target.Target;

import java.util.concurrent.TimeUnit;

public abstract class MainSaleFastService {
    /**
     * 专职负责库存
     */
    StockService stockService = new StockServiceMock();

    /**
     * 售卖动作
     */
    public Result sell(Target target, ActionSell sell) {
        Result result = new Result();

        long current = System.currentTimeMillis();
        //判断是否是lazy，如果是，则首先加载内容
        //并发时进行lazy是非常慢的，所以需要预先加载。加载过程中，需要分布式锁
        if (target.getPre().isLazy()) {
            if (target.getOnSaleTime() - current <
                    TimeUnit.SECONDS.toMillis(
                            target.getPre().getBeforehandSecond())) {
                stockService.fillStock(target);
            }
        }

        //时间未开始
        if (current < target.getOnSaleTime()) {
            result.setCode(0);
            result.setMsg("be patient!");
            return result;
        }

        // 判断是否有库存
        if (!onStock(target, sell)) {
            result.setCode(0);
            result.setMsg("no enough stock left");
            return result;
        }

        // 判断处理队列是否占满
        if (!onQueue(target, sell)) {
            result.setCode(0);
            result.setMsg("no enough stock left");
            return result;
        }

        // 判断是否有全局限流
        if (!onLimit(target, sell)) {
            result.setCode(0);
            result.setMsg("try again after a few second");
            return result;
        }


        boolean cacheSellResult = stockService.subStockNumber(target, sell.getCount());
        if (!cacheSellResult) {
            result.setCode(0);
            result.setMsg("500 error");
            return result;
        }
        return result;

    }

    /**
     * 库存判断
     * true 有库存
     * false 无库存
     */
    boolean onStock(Target target, ActionSell sell) {
        int number = stockService.stockNumber(target);
        return number >= sell.getCount();
    }


    boolean onQueue(Target target, ActionSell sell) {
        return true;
    }

    boolean onLimit(Target target, ActionSell sell) {
        return true;
    }
}
