package cn.soboys.restapispringbootstarter.test;

import cn.soboys.restapispringbootstarter.config.OpenApiConfig;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/19 22:18
 * @webSite https://github.com/coder-amiao
 */
//@ImportAutoConfiguration(OpenApiConfig.class)
@Configuration
public class Config implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
