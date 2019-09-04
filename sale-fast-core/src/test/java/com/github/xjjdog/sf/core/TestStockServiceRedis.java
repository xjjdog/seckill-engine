package com.github.xjjdog.sf.core;


import com.github.xjjdog.sf.core.components.stock.StockService;
import com.github.xjjdog.sf.core.components.stock.StockServiceRedis;
import com.github.xjjdog.sf.core.target.Target;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import redis.embedded.RedisServer;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStockServiceRedis {
    static RedisServer redisServer;

    static StockService stockService;


    static String PropsPrefix = "p";
    static String RedisPrefix = "dog:";

    static Target target;

    static int initStock = 100;

    @BeforeAll
    public static void start() throws Exception {
        redisServer = new RedisServer(8379);
        redisServer.start();

        stockService = new StockServiceRedis(PropsPrefix, RedisPrefix);


        Properties properties = new Properties();
        properties.put("p.mode", "single");
        properties.put("p.endpoint", "localhost:7379");
        properties.put("p.connectionTimeout", 3000);
        properties.put("p.soTimeout", 3000);

        properties.put("p.poolConfig.maxTotal", 10);
        properties.put("p.poolConfig.maxIdle", 10);
        properties.put("p.poolConfig.minIdle", 1);

        stockService.configure(properties);

        target = new Target();
        target.setStock(initStock);
    }

    @AfterAll
    public static void stop() throws Exception {
        if (null != redisServer) {
            redisServer.stop();
        }
    }

    @Test
    public void testStockNumber() {
        stockService.fillStock(target);
        int stock = stockService.stockNumber(target);
        assertEquals(stock, initStock);
        stockService.cleanup(target);
    }

    @Test
    public void testIncStockNumber() {
        stockService.fillStock(target);
        boolean result = stockService.incStockNumber(target, 2);
        assertEquals(true, result);
        int stock = stockService.stockNumber(target);
        assertEquals(stock, initStock + 2);
        stockService.cleanup(target);
    }

    @Test
    public void testSubStockNumber() {
        stockService.fillStock(target);
        boolean result = stockService.subStockNumber(target, 2);
        assertEquals(true, result);
        int stock = stockService.stockNumber(target);
        assertEquals(stock, initStock - 2);
        stockService.cleanup(target);
    }


}
