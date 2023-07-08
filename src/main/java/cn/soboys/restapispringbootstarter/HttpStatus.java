package cn.soboys.restapispringbootstarter;

import cn.soboys.restapispringbootstarter.i18n.I18NKey;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 11:01
 * @webSite https://github.com/coder-amiao
 */
public enum HttpStatus implements ResultCode, I18NKey {
    /**
     * 系统内部错误
     */
    INTERNAL_SERVER_ERROR("500", "internal_server_error"),
    BAD_GATEWAY("502", "bad_gateway"),
    NOT_FOUND("404", "not_found"),
    UNAUTHORIZED("401", "unauthorized"),
    FORBIDDEN("403", "forbidden"),
    METHOD_NOT_ALLOWED("405", "method_not_allowed"),
    REQUEST_TIMEOUT("408", "request_timeout"),

    INVALID_ARGUMENT("10000", "invalid_argument"),
    ARGUMENT_ANALYZE("10001", "argument_analyze"),

    BUSINESS_EXCEPTION("20000", "business_exception"),
    CACHE_EXCEPTION("20001", "CACHE_EXCEPTION");

    private final String value;

    private final String message;

    HttpStatus(String value, String message) {
        this.value = value;
        this.message = message;
    }


    @Override
    public String getCode() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }


    @Override
    public String key() {
        return message;
    }
}
