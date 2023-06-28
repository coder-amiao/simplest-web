package cn.soboys.restapispringbootstarter.exception;

import cn.soboys.restapispringbootstarter.HttpStatus;
import cn.soboys.restapispringbootstarter.ResultCode;
import lombok.Data;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 16:45
 * @webSite https://github.com/coder-amiao
 */
@Data
public class BusinessException extends RuntimeException {

    /**
     * 错误码
     */
    private String code="20000";

    /**
     * 错误提示
     */
    private String message;


    public BusinessException(String message) {
        this.message = message;

    }

    public BusinessException(String message, String code) {
        this.message = message;
        this.code = code;

    }

    public BusinessException(ResultCode resultCode) {
        this.message = resultCode.getMessage();
        this.code = resultCode.getCode();

    }
}
