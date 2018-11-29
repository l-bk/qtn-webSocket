package com.qtn.modules.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;


public class RedisUtils {
    public static final String KEY_PREFIX ="GIMP";
    private static Logger logger  = LoggerFactory.getLogger(RedisUtils.class);

    public static long listAdd(String key, String... value) {
        long result = 0;
        Jedis jedis = null;
        try {
            jedis = new Jedis("localhost");
            if (jedis.exists(key)) {
                result = jedis.lpush(key, value);
                logger.debug("listAdd {} = {}", key, value);
            }
        } catch (Exception e) {
            logger.warn("listAdd {} = {}", key, value, e);
        } finally {
           jedis.close();
        }
        return result;
    }

    /**
     * 取list中先添加的第一个元素并在list中删除
     * @param key
     * @return
     */
    public static String lpopList(String key) {
        String value = null;
        Jedis jedis = null;
        try {
            jedis = new Jedis("localhost");
            if (jedis.exists(key)) {
                value = jedis.lpop(key);
                logger.debug("lpopList ", key, value);
            }
        } catch (Exception e) {
            logger.warn("lpopList", key, value, e);
        } finally {
            jedis.close();
        }
        return value;
    }

}
