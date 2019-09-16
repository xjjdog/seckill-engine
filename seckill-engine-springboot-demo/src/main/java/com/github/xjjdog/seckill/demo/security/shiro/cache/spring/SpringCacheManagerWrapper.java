package com.github.xjjdog.seckill.demo.security.shiro.cache.spring;

import net.sf.ehcache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;

@Component
public class SpringCacheManagerWrapper implements org.apache.shiro.cache.CacheManager {

    @Autowired
    @Qualifier("springCacheManager")
    private org.springframework.cache.CacheManager cacheManager;

    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> org.apache.shiro.cache.Cache<K, V> getCache(String name) throws CacheException {
        org.springframework.cache.Cache springCache = this.cacheManager.getCache(name);
        return new SpringCacheWrapper(springCache);
    }

    static class SpringCacheWrapper implements org.apache.shiro.cache.Cache {
        private org.springframework.cache.Cache springCache;

        SpringCacheWrapper(org.springframework.cache.Cache springCache) {
            this.springCache = springCache;
        }

        @Override
        public Object get(Object key) throws CacheException {
            Object value = this.springCache.get(key);
            if ((value instanceof SimpleValueWrapper)) {
                return ((SimpleValueWrapper) value).get();
            }
            return value;
        }

        @Override
        public Object put(Object key, Object value)
                throws CacheException {
            this.springCache.put(key, value);
            return value;
        }

        @Override
        public Object remove(Object key)
                throws CacheException {
            this.springCache.evict(key);
            return null;
        }

        @Override
        public void clear()
                throws CacheException {
            this.springCache.clear();
        }

        @Override
        public int size() {
            throw new UnsupportedOperationException("invoke spring cache abstract size method not supported");
        }

        @Override
        public Set keys() {
            throw new UnsupportedOperationException("invoke spring cache abstract keys method not supported");
        }

        @Override
        public Collection values() {
            throw new UnsupportedOperationException("invoke spring cache abstract values method not supported");
        }
    }
}