package cn.soboys.restapispringbootstarter.annotation;

import cn.soboys.restapispringbootstarter.cache.CacheAutoConfiguration;
import cn.soboys.restapispringbootstarter.cache.RedisConfig;
import cn.soboys.restapispringbootstarter.cache.SpringCacheConfig;
import cn.soboys.restapispringbootstarter.config.BeanAutoConfiguration;

import cn.soboys.restapispringbootstarter.config.OpenApiConfig;
import cn.soboys.restapispringbootstarter.config.UserJwtAutoConfig;
import cn.soboys.restapispringbootstarter.interceptor.WebMvcHandleConfig;
import cn.soboys.restapispringbootstarter.serializer.JsonSerializerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/5 00:10
 * @webSite https://github.com/coder-amiao
 * 开启rest-api 功能
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Import(
        {BeanAutoConfiguration.class,BeanAutoConfiguration.RestTemplateConfig.class,
                SpringCacheConfig.class, CacheAutoConfiguration.class,
                OpenApiConfig.class, WebMvcHandleConfig.class, UserJwtAutoConfig.class,
                JsonSerializerConfig.class})

public @interface EnableRestFullApi {

}
