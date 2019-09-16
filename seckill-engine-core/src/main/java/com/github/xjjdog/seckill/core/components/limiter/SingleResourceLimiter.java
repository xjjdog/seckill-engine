package com.github.xjjdog.seckill.core.components.limiter;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class SingleResourceLimiter {
    /**
     * 支持单机1万资源的访问控制管理
     */
    static LoadingCache<String, FlowController> caches = CacheBuilder.newBuilder()
            .maximumSize(10000)
            .expireAfterWrite(1, TimeUnit.HOURS)
            .build(new CacheLoader<String, FlowController>() {
                @Override
                public FlowController load(String key) {
                    return new FlowController(5, 0);
                }
            });

    /**
     * 获取许可
     *
     * @param key 资源标识
     * @return
     */
    public boolean acquire(String key) {
        try {
            return caches.get(key).acquire();
        } catch (Exception ex) {
            return false;
        }
    }
}
