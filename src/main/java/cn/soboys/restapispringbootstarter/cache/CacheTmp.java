package cn.soboys.restapispringbootstarter.cache;

import cn.soboys.restapispringbootstarter.exception.CacheException;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/6 21:55
 * @webSite https://github.com/coder-amiao
 */
public interface CacheTmp {

    String getKey();

    Boolean getHasPrefix();

    /**
     * 调用key()时，keyParts不为null，是空数组
     * 调用key(null)时，keyParts为null
     */
    default String key(String... keyParts) {
        return key(this.getKey(), this.getHasPrefix(), keyParts);
    }

    default boolean valid(String... keyParts) {
        return RedisTempUtil.getInstance().hasKey(this.key(keyParts));
    }

    static String key(String key, boolean hasPrefix, String[] keyParts) {
        if (keyParts == null) {
            throw new CacheException("keyParts不能为null");
        } else if (hasPrefix && keyParts.length == 0) {
            throw new CacheException("有前缀，需要传keyParts");
        } else if (!hasPrefix && keyParts.length > 0) {
            throw new CacheException("无前缀，不需要传keyParts");
        }
        if (keyParts.length == 0) {
            return key;
        } else {
            return key + (key.endsWith(":") ? "" : ":") + StringUtils.join(keyParts, ":");
        }
    }

    default void valueSet(Object value, String... keyParts) {
        RedisTempUtil.getInstance().set(this.key(keyParts), value);
    }

    default void valueSetAndExpire(Object value, long timeout, TimeUnit unit, String... keyParts) {
        RedisTempUtil.getInstance().set(this.key(keyParts), value, timeout, unit);
    }

    default <T> T valueGet(String... keyParts) {
        return (T) RedisTempUtil.getInstance().get(this.key(keyParts));
    }

    default Boolean delete(String... keyParts) {
        return RedisTempUtil.getInstance().delete(this.key(keyParts));
    }
}
