package cn.soboys.restapispringbootstarter.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/4/29 23:27
 * @webSite https://github.com/coder-amiao
 * 注册自定义json序列化器
 */
@Configuration
public class JsonSerializerConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Double.class, new DoubleValueSerializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
