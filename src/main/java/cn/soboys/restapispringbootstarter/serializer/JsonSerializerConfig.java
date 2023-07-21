package cn.soboys.restapispringbootstarter.serializer;

import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/4/29 23:27
 * @webSite https://github.com/coder-amiao
 * 注册自定义json序列化器
 */
public class JsonSerializerConfig {

    @Bean
    public RestApiProperties.JsonSerializeProperties jsonSerializeProperties() {
        return new RestApiProperties.JsonSerializeProperties();
    }

    @Bean
    public DoubleValueSerializer doubleValueSerializer() {
        return new DoubleValueSerializer();
    }

    @Bean
    public BigDecimalSerializer bigDecimalSerializer() {
        return new BigDecimalSerializer();
    }


    @Bean
    public ObjectMapper objectMapper(DoubleValueSerializer doubleValueSerializer ,BigDecimalSerializer bigDecimalSerializer) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Double.class, doubleValueSerializer);
        module.addSerializer(BigDecimal.class,bigDecimalSerializer);
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
