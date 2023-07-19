package cn.soboys.restapispringbootstarter.interceptor;

import cn.soboys.restapispringbootstarter.authorization.LoginAuthorization;
import cn.soboys.restapispringbootstarter.authorization.UrlMatcher;
import cn.soboys.restapispringbootstarter.authorization.UserJwtToken;
import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.text.StrUtil;

import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/16 10:32
 * @webSite https://github.com/coder-amiao
 */
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {


    @Autowired
    private UserJwtToken userJwtToken;

    @Autowired
    private LoginAuthorization loginAuthorization;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RestApiProperties.JwtProperties jwtProperties = userJwtToken.getJwtProperties();
        if (jwtProperties != null && jwtProperties.getAuthorization().getHasAuthorization()
                && StrUtil.isNotEmpty(jwtProperties.getAuthorization().getIncludesUrl())) {


            //Map obj= SpringUtil.getBeansOfType(LoginAuthorization.class);
            //获取第一个实例对象值
            //LoginAuthorization loginAuthorization=

            if(loginAuthorization==null) throw new BeanCreationException("Unable to inject the LoginAuthorization  bean");
            UrlMatcher matcher = new UrlMatcher(jwtProperties.getAuthorization().getIncludesUrl(), jwtProperties.getAuthorization().getExcludesUrl());
            if (matcher.matches(request.getRequestURI())) {
                loginAuthorization.authorization(request, response, handler);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
