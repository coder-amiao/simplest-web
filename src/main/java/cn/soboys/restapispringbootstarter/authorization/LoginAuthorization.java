package cn.soboys.restapispringbootstarter.authorization;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/16 10:40
 * @webSite https://github.com/coder-amiao
 * 登录授权接口
 */
public interface LoginAuthorization {

    /**
     * 登录授权验证 通过后才能进行下一步操作
     * @param request
     * @param response
     * @param handler
     * @return
     */
    public Boolean authorization(HttpServletRequest request, HttpServletResponse response, Object handler);
}
