package com.springboot.cache.redis;

import com.springboot.common.util.PropertiesUtil;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;
import java.util.Set;

/**
 * @author wangshibao
 * @className: RedisUtil
 * @description: redis操作工具类
 * @dateTime 2018/4/23 16:12
 */
@Component
public class RedisUtil {

    private static JedisPool jedisPool;

    /**
     * @description: 私有化构造函数
     */
    private RedisUtil() {
    }

    /**
     * @description: 获取jedis
     * @dateTime 2018/4/24 9:47
     */
    public static Jedis getJedis() {
        if(jedisPool == null){
            jedisPool = new JedisPool(
                    PropertiesUtil.getProperty("spring.redis.host"),
                    Integer.parseInt(PropertiesUtil.getProperty("spring.redis.port")));
        }
        return jedisPool.getResource();
    }

    /**
     * @description: 保存到redis
     */
    public static void set(byte[] key, byte[] value) {
        Jedis jedis = getJedis();
        try {
            jedis.set(key, value);
        } finally {
            jedis.close();
        }
    }

    /**
     * @description: 获取所有key
     */
    public static Set getAllKeys() {
        Jedis jedis = getJedis();
        try {
            Set<String> keys = jedis.keys("*");
            return keys;
        } finally {
            jedis.close();
        }
    }

    /**
     * @description: 获取key值
     */
    public static String getByKey(String key) {
        Jedis jedis = getJedis();
        try {
            String value = jedis.get(key);
            return value;
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取map
     * @param <T>
     * @param key
     * @return Map
     */
    public static <T> Map<String,T> getMap(String key){
        if(getJedis() == null || !getJedis().exists(key.getBytes())){
            return null;
        }
        byte[] in = getJedis().get(key.getBytes());
        Map<String,T> map = (Map<String, T>) ObjectTranscoder.deserialize(in);
        return map;
    }

    /**
     * @description: 从redis中获取
     */
    public static byte[] get(byte[] key) {
        Jedis jedis = getJedis();
        try {
            if(jedis.exists(key)){
                return jedis.get(key);
            }else{
                return "{}".getBytes();
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * @description: 从redis中删除
     */
    public static void del(byte[] key) {
        Jedis jedis = getJedis();
        try {
            if(jedis.exists(key)){
                jedis.del(key);
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * @description: 依据前缀删除key
     */
    public static void delByPrefix(String keyPrefix) {
        keyPrefix = keyPrefix + "*";
        Jedis jedis = getJedis();
        try {
            Set<byte[]> keyByteArraySet = jedis.keys(keyPrefix.getBytes());
            for (byte[] keyByteArray : keyByteArraySet) {
                jedis.del(keyByteArray);
            }
        } finally {
            jedis.close();
        }
    }

    /**
     * @description: 设置redis过期时间
     */
    public static void expire(byte[] key, int seconds) {
        Jedis jedis = getJedis();
        try {
            jedis.expire(key, seconds);
        } finally {
            jedis.close();
        }
    }

    /**
     * @description: 从redis中获取指定前缀的key
     */
    public static Set<byte[]> keys(String shiroSessionPrefix) {
        shiroSessionPrefix = shiroSessionPrefix + "*";
        Jedis jedis = getJedis();
        try {
            return jedis.keys(shiroSessionPrefix.getBytes());
        } finally {
            jedis.close();
        }
    }
}