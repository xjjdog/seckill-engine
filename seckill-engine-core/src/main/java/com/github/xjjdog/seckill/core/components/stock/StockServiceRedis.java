package com.github.xjjdog.seckill.core.components.stock;

import com.github.xjjdog.seckill.core.components.stock.redis.Config;
import com.github.xjjdog.seckill.core.target.Target;
import com.github.xjjdog.seckill.core.util.PropertiesToBean;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.*;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class StockServiceRedis implements StockService {
    JedisPoolAbstract pool;
    JedisCluster cluster;
    boolean isCluster;


    final static String HashKeyStock = "stock";

    private String propsPrefix = "xjjdog.sf.redis.";
    private String redisKeyPrefix = "xjjdog:sf:";
    private String runtimeCachePrefix = redisKeyPrefix + "rt:";

    public StockServiceRedis() {
        //Noop
    }

    /**
     * set some config path if you need
     *
     * @param propsPrefix
     * @param redisKeyPrefix
     */
    public StockServiceRedis(String propsPrefix, String redisKeyPrefix) {
        if (!propsPrefix.endsWith(".")) {
            this.propsPrefix = propsPrefix + ".";
        } else {
            this.propsPrefix = propsPrefix;
        }
        this.redisKeyPrefix = redisKeyPrefix;
        this.redisKeyPrefix = redisKeyPrefix + "rt:";
    }

    private int toInt(String str) {
        if (null == str || "".equals(str)) {
            return -1;
        }
        return Integer.parseInt(str);
    }

    @Override
    public void configure(Properties properties) {
        Config config = new Config();
        try {
            PropertiesToBean.toBean(propsPrefix, properties, config);
        } catch (Exception ex) {
            log.error("error while config redis . pls check it !!");
        }

        isCluster = false;

        switch (config.getMode().toLowerCase()) {
            case Config.REDIS_MODE_SINGLE: {
                final String[] parts = config.getEndpoint().split(":");
                if (parts.length != 2) {
                    throw new RuntimeException("okmq:redis:config error: ex: 127.0.0.1:6379");
                }

                final String host = parts[0];
                final int port = Integer.valueOf(parts[1]);

                pool = new JedisPool(config.getPoolConfig(), host, port, config.getConnectionTimeout());
            }
            break;

            case Config.REDIS_MODE_SENTINEL: {
                final String[] parts = config.getEndpoint().split(",");

                Set<String> hostAndPorts = Arrays.stream(parts)
                        .map(item -> item.split(":"))
                        .map(item -> new HostAndPort(item[0], Integer.valueOf(item[1])).toString())
                        .collect(Collectors.toSet());

                pool = new JedisSentinelPool(config.getMasterName(), hostAndPorts, config.getPoolConfig());
            }
            break;

            case Config.REDIS_MODE_CLUSTER: {
                final String[] parts = config.getEndpoint().split(",");
                Set<HostAndPort> hostAndPorts = Arrays.stream(parts)
                        .map(item -> item.split(":"))
                        .map(item -> new HostAndPort(item[0], Integer.valueOf(item[1])))
                        .collect(Collectors.toSet());

                cluster = new JedisCluster(hostAndPorts, config.getPoolConfig());
                isCluster = true;
            }
            break;
            default:
                throw new RuntimeException("okmq:redis:no redis mode supply. ex:  single|sentinel|cluster");
        }
    }

    @Override
    public synchronized int stockNumber(Target target) {
        final String tagertID = target.getId();
        final String key = runtimeCachePrefix + tagertID;
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
        final String key = runtimeCachePrefix + tagertID;

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
        final String key = runtimeCachePrefix + tagertID;

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
        final String key = runtimeCachePrefix + tagertID;

        final String initStock = String.valueOf(target.getStock());

        if (isCluster) {
            cluster.hset(key, HashKeyStock, initStock);
        } else {
            try (Jedis jedis = pool.getResource()) {
                jedis.hset(key, HashKeyStock, initStock);
            }
        }
    }

    @Override
    public void cleanup(Target target) {
        final String tagertID = target.getId();
        final String key = runtimeCachePrefix + tagertID;

        if (isCluster) {
            cluster.del(key);
        } else {
            try (Jedis jedis = pool.getResource()) {
                jedis.del(key);
            }
        }
    }
}
