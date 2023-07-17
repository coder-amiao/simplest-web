package cn.soboys.restapispringbootstarter.authorization;

import cn.soboys.restapispringbootstarter.Assert;
import cn.soboys.restapispringbootstarter.HttpStatus;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/16 12:12
 * @webSite https://github.com/coder-amiao
 */

public class LoginAuthorizationSubject implements LoginAuthorization {

    @Autowired
    private UserJwtToken userJwtToken;

    @Override
    public Boolean authorization(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader(userJwtToken.getJwtProperties().getTokenHeader());
        Assert.isFalse(StrUtil.isEmpty(token),HttpStatus.UNAUTHORIZED);
        String userId = userJwtToken.getUserId(token);  //验证token有效合法性。

        //其他数据库 或者业务操作
        return true;
    }
}
