package cn.soboys.restapispringbootstarter.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/2 11:04
 * @webSite https://github.com/coder-amiao
 * 缓存枚举
 */
@Getter
public enum CacheKey {
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

}
