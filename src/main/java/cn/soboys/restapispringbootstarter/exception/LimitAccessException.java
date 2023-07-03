package cn.soboys.restapispringbootstarter.exception;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/2 11:38
 * @webSite https://github.com/coder-amiao
 */
public class LimitAccessException extends RuntimeException{
    public LimitAccessException(String message) {
        super(message);
    }
}
