package cn.soboys.restapispringbootstarter.interceptor;

import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/28 00:21
 * @webSite https://github.com/coder-amiao
 * 封装全局的 资源配置。路由配置 拦截处理
 */
public class WebMvcHandleConfig implements WebMvcConfigurer {


    /**
     * 授权登录拦截器
     * @return
     */
    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(SpringUtil.getBean(JwtTokenInterceptor.class));
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
