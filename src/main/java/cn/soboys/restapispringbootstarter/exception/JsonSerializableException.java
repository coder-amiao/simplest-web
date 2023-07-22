package cn.soboys.restapispringbootstarter.exception;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/22 21:16
 * @webSite https://github.com/coder-amiao
 */
public class JsonSerializableException extends  RuntimeException{
    public JsonSerializableException(String message) {
        super(message);
    }
}
