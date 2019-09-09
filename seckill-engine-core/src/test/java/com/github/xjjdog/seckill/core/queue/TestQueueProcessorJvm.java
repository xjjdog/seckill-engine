package com.github.xjjdog.seckill.core.queue;

import com.github.xjjdog.seckill.core.Factory;
import com.github.xjjdog.seckill.core.Holder;
import com.github.xjjdog.seckill.core.components.queue.QeueProcessorJvm;
import com.github.xjjdog.seckill.core.components.queue.QueueProcessor;
import com.github.xjjdog.seckill.core.components.stock.StockService;
import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestQueueProcessorJvm {
    static StockService stockService;
    static QueueProcessor queueProcessor;

    @BeforeAll
    public static void start() throws Exception {
        stockService = Holder.getInstance().getStockService();
        queueProcessor = new QeueProcessorJvm();
        queueProcessor.start();
    }

    @AfterAll
    public static void stop() throws Exception {
        queueProcessor.stop();
    }

    @Test
    public void testProducer() throws Exception {
        Target target = Holder.getInstance().getTargetService().getTarget("1");
        int initStock = Factory.InitStock;
        ActionSell actionSell = new ActionSell();
        actionSell.setCount(10);

        stockService.fillStock(target);
        boolean result = queueProcessor.producer(target, actionSell);
        assertEquals(result, true);


        //wait to consumer
        Thread.sleep(1000);
        int stockNumber = stockService.stockNumber(target);
        assertEquals(Factory.InitStock - 10, stockNumber);

        stockService.cleanup(target);


        actionSell.setCount(101);
        stockService.fillStock(target);
        result = queueProcessor.producer(target, actionSell);
        assertEquals(result, false);

    }
}
