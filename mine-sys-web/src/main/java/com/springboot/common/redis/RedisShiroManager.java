package com.springboot.common.redis;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @author wangshibao
 * @className: RedisShiroManager
 * @description: redis shiro 缓存管理器
 * @dateTime 2018/4/24 15:58
 */
public class RedisShiroManager implements CacheManager {
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new RedisShiroCache<K, V>();
    }
}