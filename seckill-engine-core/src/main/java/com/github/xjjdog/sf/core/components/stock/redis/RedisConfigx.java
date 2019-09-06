package com.github.xjjdog.sf.core.components.stock.redis;

import lombok.Getter;
import lombok.Setter;
import redis.clients.jedis.JedisPoolConfig;

@Getter
@Setter
public class RedisConfigx {

    public static final String REDIS_MODE_SINGLE = "single";
    public static final String REDIS_MODE_SENTINEL = "sentinel";
    public static final String REDIS_MODE_CLUSTER = "cluster";

    private String mode = REDIS_MODE_SINGLE;
    private String endpoint = "";
    private int connectionTimeout = 2000;
    private int soTimeout = 2000;


    private JedisPoolConfig poolConfig;


    /**
     * Only used by sentinel mode
     */
    private String masterName = "";
}
