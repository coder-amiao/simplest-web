package cn.soboys.restapispringbootstarter.cache;

import cn.soboys.restapispringbootstarter.exception.CacheException;
import cn.soboys.restapispringbootstarter.utils.RedisTempUtil;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/2 11:04
 * @webSite https://github.com/coder-amiao
 * 缓存枚举
 */
@Getter
public enum CacheKey implements CacheTmp {


    // 密码的重置码
    PWD_RESET_CODE("reset:code:", true),
    ;

    private String key;

    /**
     * Key是否是Key前缀, true时直接取key=key，如果false时key=key+suffix
     */
    private boolean hasPrefix;

    CacheKey(String key, boolean hasPrefix) {
        this.key = key;
        this.hasPrefix = hasPrefix;
    }


    @Override
    public Boolean getHasPrefix() {
        return this.hasPrefix;
    }

    @Override
    public String getKey() {
        return this.key;
    }

}
