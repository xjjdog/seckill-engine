package com.github.xjjdog.seckill.core;

import com.github.xjjdog.seckill.core.target.Target;
import com.github.xjjdog.seckill.core.target.TargetStore;
import com.github.xjjdog.seckill.core.target.TargetStoreMock;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class TargetService {
    //TODO 此部分会被装配
    TargetStore targetStore = new TargetStoreMock();

    private LoadingCache<String, Target> caches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, Target>() {
                @Override
                public Target load(String key) {
                    return targetStore.getTarget(key);
                }
            });

    public Target getTarget(String targetID) {
        try {
            return caches.get(targetID);
        } catch (Exception ex) {
            return null;
        }
    }
}
