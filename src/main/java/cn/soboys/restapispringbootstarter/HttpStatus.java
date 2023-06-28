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
    BAD_GATEWAY("502", "错误的请求"),
    NOT_FOUND("404", "not_found"),
    UNAUTHORIZED("401", "未授权"),
    FORBIDDEN("403", "资源禁止访问"),
    METHOD_NOT_ALLOWED("405", "方法不被允许"),
    REQUEST_TIMEOUT("408", "请求超时"),

    INVALID_ARGUMENT("10000", "参数错误{}"),
    ARGUMENT_ANALYZE("10001", "参数解析异常"),
    BUSINESS_EXCEPTION("20000", "业务错误");


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
