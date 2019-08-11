package com.springboot.common.redis;

import com.springboot.common.shiro.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author wangshibao
 * @description: redis管理shiro缓存
 * @dateTime 2018/4/24 16:04
 */
@Slf4j
public class RedisShiroCache<K, V> implements Cache<K, V> {

    /**
     * @description: 获取加工后的key的字节数组
     */
    private byte[] getKey(Object key) {
        return (SessionUtil.SHIRO_CACHE_PREFIX + key).getBytes();
    }

    /**
     * @description: 从缓存中获取数据
     */
    @Override
    public Object get(Object key) throws CacheException {
        if (key == null) {
            return null;
        }
        // 序列化键
        byte[] keyByteArray = getKey(key);
        // 从redis中获取数据
        byte[] valueByteArray = RedisUtil.get(keyByteArray);
        // 返回对应的数据
        return valueByteArray == null ? null : SerializationUtils.deserialize(valueByteArray);
    }

    /**
     * @description: 保存shiro缓存到redis
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        if (key == null || value == null) {
            return null;
        }
        // 序列化
        byte[] keyByteArray = getKey(key);
        byte[] valueByteArray = SerializationUtils.serialize((Serializable) value);
        RedisUtil.set(keyByteArray, valueByteArray);
        // 返回保存的值
        return SerializationUtils.deserialize(valueByteArray);
    }

    /**
     * @description: 从redis中删除
     */
    @Override
    public Object remove(Object key) throws CacheException {
        if (key == null) {
            return null;
        }
        // 序列化
        byte[] keyByteArray = getKey(key);
        byte[] valueByteArray = RedisUtil.get(keyByteArray);
        // 删除
        RedisUtil.del(keyByteArray);
        // 返回删除的数据
        if(valueByteArray.length == 2){
            return null;
        }
        return SerializationUtils.deserialize(valueByteArray);
    }

    /**
     * @description: 清空所有的缓存
     */
    @Override
    public void clear() throws CacheException {
        RedisUtil.delByPrefix(SessionUtil.SHIRO_CACHE_PREFIX);
    }

    /**
     * @description: 缓存个数
     */
    @Override
    public int size() {
        Set<byte[]> keyByteArraySet = RedisUtil.keys(SessionUtil.SHIRO_CACHE_PREFIX);
        return keyByteArraySet.size();
    }

    /**
     * @description: 获取所有的key
     */
    @Override
    public Set keys() {
        Set<byte[]> keyByteArraySet = RedisUtil.keys(SessionUtil.SHIRO_CACHE_PREFIX);
        return keyByteArraySet;
    }

    /**
     * @description: 获取所有的value
     */
    @Override
    public Collection values() {
        Set keySet = this.keys();
        List<Object> valueList = new ArrayList<>(16);
        for (Object key : keySet) {
            byte[] keyByteArray = SerializationUtils.serialize(key);
            byte[] valueByteArray = RedisUtil.get(keyByteArray);
            valueList.add(SerializationUtils.deserialize(valueByteArray));
        }
        return valueList;
    }
}