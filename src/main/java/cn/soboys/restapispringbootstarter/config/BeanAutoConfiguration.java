package cn.soboys.restapispringbootstarter.config;


import cn.soboys.restapispringbootstarter.ApplicationRunner;
import cn.soboys.restapispringbootstarter.ExceptionHandler;
import cn.soboys.restapispringbootstarter.ResultHandler;
import cn.soboys.restapispringbootstarter.aop.LimitAspect;
import cn.soboys.restapispringbootstarter.aop.LogAspect;

import cn.soboys.restapispringbootstarter.cache.SpringCacheUtil;
import cn.soboys.restapispringbootstarter.i18n.I18NMessage;


import cn.soboys.restapispringbootstarter.utils.RestFulTemp;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.dromara.hutool.extra.spring.EnableSpringUtil;
import org.hibernate.validator.HibernateValidator;
import org.springframework.cache.Cache;
import org.springframework.context.annotation.Bean;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.nio.charset.Charset;
import java.util.List;


/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/27 11:36
 * @webSite https://github.com/coder-amiao
 */
//@Configuration
//@ConditionalOnProperty(name = "rest-api.enabled", havingValue = "true")
@EnableSpringUtil
public class BeanAutoConfiguration {


    @Bean
    public I18NMessage i18NMessage() {
        return new I18NMessage();
    }

    @Bean
    public ResultHandler resultHandler() {
        return new ResultHandler();
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler();
    }

    @Bean
    public StartupApplicationListener startupApplicationListener() {
        return new StartupApplicationListener();
    }


    @Bean
    public RestApiProperties restApiProperties() {
        return new RestApiProperties();
    }

    @Bean
    public RestApiProperties.LoggingProperties loggingProperties(RestApiProperties restApiProperties) {
        return restApiProperties.new LoggingProperties();
    }

    @Bean
    public RestApiProperties.Ip2regionProperties ip2regionProperties(RestApiProperties restApiProperties) {
        return restApiProperties.new Ip2regionProperties();
    }

    @Bean
    public RestApiProperties.JwtProperties jwtProperties(RestApiProperties restApiProperties) {
        return restApiProperties.new JwtProperties();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect();
    }

    @Bean
    public LimitAspect limitAspect() {
        return new LimitAspect();
    }

//    @Bean
//    public SpringCacheUtil springCacheUtil() {
//        return new SpringCacheUtil();
//    }


    /**
     * 参数校验快速失败返回 提升性能
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    public class RestTemplateConfig {

        /**
         * 第三方请求要求的默认编码
         */
        private final Charset thirdRequest = Charset.forName("utf-8");

        @Bean
        public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
            RestTemplate restTemplate = new RestTemplate(factory);
            // 处理请求中文乱码问题
            List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
            for (HttpMessageConverter<?> messageConverter : messageConverters) {
                if (messageConverter instanceof StringHttpMessageConverter) {
                    ((StringHttpMessageConverter) messageConverter).setDefaultCharset(thirdRequest);
                }
                if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
                    ((MappingJackson2HttpMessageConverter) messageConverter).setDefaultCharset(thirdRequest);
                }
                if (messageConverter instanceof AllEncompassingFormHttpMessageConverter) {
                    ((AllEncompassingFormHttpMessageConverter) messageConverter).setCharset(thirdRequest);
                }
            }
            return restTemplate;
        }

        @Bean
        public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
            SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
            factory.setConnectTimeout(15000);
            factory.setReadTimeout(5000);
            return factory;
        }


        @Bean
        public RestFulTemp restFulTemp() {
            return new RestFulTemp();
        }
    }
}
