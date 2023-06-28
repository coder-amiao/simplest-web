package cn.soboys.restapispringbootstarter;

import cn.soboys.restapispringbootstarter.i18n.I18NKey;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 10:21
 * @webSite https://github.com/coder-amiao
 * 响应码接口，自定义响应码，实现此接口
 */
public interface ResultCode extends I18NKey {

    String getCode();

    String getMessage();

}
