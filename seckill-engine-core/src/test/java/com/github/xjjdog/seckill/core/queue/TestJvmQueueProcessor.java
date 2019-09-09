package com.github.xjjdog.seckill.core.queue;

import com.github.xjjdog.seckill.core.Factory;
import com.github.xjjdog.seckill.core.components.queue.JvmQueueProcessor;
import com.github.xjjdog.seckill.core.components.queue.QueueProcessor;
import com.github.xjjdog.seckill.core.components.stock.StockService;
import com.github.xjjdog.seckill.core.components.stock.StockServiceMock;
import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestJvmQueueProcessor {
    static StockService stockService;
    static QueueProcessor queueProcessor;

    @BeforeAll
    public static void start() throws Exception {
        stockService = new StockServiceMock();
        queueProcessor = new JvmQueueProcessor(stockService);
        queueProcessor.start();
    }

    @AfterAll
    public static void stop() throws Exception {
        queueProcessor.stop();
    }

    @Test
    public void testProducer() throws Exception {
        Target target = Factory.getTarget();
        int initStock = Factory.InitStock;
        ActionSell actionSell = new ActionSell();
        actionSell.setCount(10);

        stockService.fillStock(target);
        boolean result = queueProcessor.producer(target, actionSell);
        assertEquals(result, true);


        //wait to consumer
        int stockNumber = stockService.stockNumber(target);
        Thread.sleep(1000);
        assertEquals(Factory.InitStock - 10, stockNumber);

        stockService.cleanup(target);


        actionSell.setCount(101);
        stockService.fillStock(target);
        result = queueProcessor.producer(target, actionSell);
        assertEquals(result, false);

    }
}
