package com.github.xjjdog.seckill.core;

import com.github.xjjdog.seckill.core.target.Target;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class TargetService {
    private static LoadingCache<String, Target> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, Target>() {
                @Override
                public Target load(String key) {
                    return new Target();
                }
            });
}
