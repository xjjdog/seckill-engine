package com.github.xjjdog.sf.core.components.stock;

import com.github.xjjdog.sf.core.target.Target;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolAbstract;

import java.util.Map;

public class StockServiceRedis implements StockService {
    JedisPoolAbstract pool;
    JedisCluster cluster;
    boolean isCluster;


    final static String RedisKeyPrefix = "xjjdog:sf:";
    final static String RuntimeCachePrefix = RedisKeyPrefix + ":rt:";

    final static String HashKeyStock = "stock";

    private int toInt(String str) {
        if (null == str || "".equals(str)) {
            return -1;
        }
        return Integer.parseInt(str);
    }

    @Override
    public synchronized int stockNumber(Target target) {
        final String tagertID = target.getId();
        final String key = RuntimeCachePrefix + tagertID;
        Map<String, String> runtime = null;
        if (isCluster) {
            runtime = cluster.hgetAll(key);
        } else {
            try (Jedis jedis = pool.getResource()) {
                runtime = jedis.hgetAll(key);
            }
        }

        //check
        if (null == runtime) {
            return -1;
        } else {
            return toInt(runtime.get(HashKeyStock));
        }
    }

    @Override
    public synchronized boolean incStockNumber(Target target, int number) {
        final String tagertID = target.getId();
        final String key = RuntimeCachePrefix + tagertID;

        if (isCluster) {
            cluster.hincrBy(key, HashKeyStock, number);
        } else {
            try (Jedis jedis = pool.getResource()) {
                jedis.hincrBy(key, HashKeyStock, number);
            }

        }
        return true;
    }

    @Override
    public synchronized boolean subStockNumber(Target target, int number) {
        final String tagertID = target.getId();
        final String key = RuntimeCachePrefix + tagertID;

        if (isCluster) {
            cluster.hincrBy(key, HashKeyStock, number);
        } else {
            try (Jedis jedis = pool.getResource()) {
                jedis.hincrBy(key, HashKeyStock, -number);
            }

        }
        return true;
    }

    @Override
    public synchronized void fillStock(Target target) {
        final String tagertID = target.getId();
        final String key = RuntimeCachePrefix + tagertID;

        final String initStock = String.valueOf(target.getStock());

        if (isCluster) {
            cluster.hset(key, HashKeyStock, initStock);
        } else {
            try (Jedis jedis = pool.getResource()) {
                jedis.hset(key, HashKeyStock, initStock);
            }
        }
    }
}
