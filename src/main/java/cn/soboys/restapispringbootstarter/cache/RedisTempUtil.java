package cn.soboys.restapispringbootstarter.cache;

import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;


import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/1 18:00
 * @webSite https://github.com/coder-amiao
 */
@ImportAutoConfiguration(RedisAutoConfiguration.class)
@Configuration(proxyBeanMethods = true)
public class RedisTempUtil {

    private static RedisTempUtil instance;


    public static RedisTempUtil getInstance() {
        return SpringUtil.getBean(RedisTempUtil.class);
    }



    @Resource
    private RedisTemplate redisTemplate;


    // =============================common============================
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean delete(String key) {
       return redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 返回对应key过去时间
     * @param key
     *返回以秒为单位的剩余过期时间。如果返回值为负数
     * @return
     */
    public Long getRemainingTime(String key) {
        ValueOperations<String, Object> ops = redisTemplate.opsForValue();
        return ops.getOperations().getExpire(key);
    }

    /**
     * 获得某个key剩余时间
     *
     * @param key key
     */
    public Long ttl(final String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }

    /**
     * 获取所有的key
     * @param keys
     * @return
     */
    public  Set<String> getAllKey(String keys) {
        Set<String> key = redisTemplate.keys(keys + "*");
        return key;
    }

    /**
     * 清除所有key
     */
    public   void clean(){
        Set<String> keys = redisTemplate.keys("*");
        redisTemplate.delete(keys);
    }


    /**
     * 清除指定前缀key
     * @param key
     */
    public   void cleanAllKey(String key){
        Set<String> keys = redisTemplate.keys(key + "*");
        redisTemplate.delete(keys);
    }

    /**
     * 删除单个对象
     *
     * @param key
     */
    public  boolean  deleteObject(final String key) {
        return redisTemplate.delete(key);
    }


    /**
     * 值 incr增加
     *
     * @param key key
     */
    public Long incr(final String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        return entityIdCounter.incrementAndGet();
    }

    /**
     * 值 decr减少
     *
     * @param key key
     */
    public Long decr(final String key) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
        return entityIdCounter.incrementAndGet();
    }


    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     * @return
     */
    public long deleteObject(final Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public  Boolean set(String key, Object value, Long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }

    // ============================String=============================
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // ================================List=================================
    public void lPush(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    public Object lPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public List<Object> lRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    // ===============================Set=================================
    public void sAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    public Set<Object> sMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    // ===============================Hash================================
    public void hSet(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    public Map<Object, Object> hGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // ============================ZSet=================================
    public void zAdd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public Set<Object> zRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

}
