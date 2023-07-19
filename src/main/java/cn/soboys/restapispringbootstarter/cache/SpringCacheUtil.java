package cn.soboys.restapispringbootstarter.cache;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/7 10:13
 * @webSite https://github.com/coder-amiao
 */

public class SpringCacheUtil {

    @Resource
    private CacheManager cacheManager;

    @SuppressWarnings({"unchecked"})
    public <T> T getCache(String cacheName,String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null) {
            return null;
        }
        return (T)valueWrapper.get();
    }

    public void putCache(String cacheName,String key, Object value) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }



    public void evictCache(String cacheName,String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }
}
