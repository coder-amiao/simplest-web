package cn.soboys.restapispringbootstarter.serializer;

import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/4/29 23:27
 * @webSite https://github.com/coder-amiao
 * 注册自定义json序列化器
 */
@JsonInclude(JsonInclude.Include.ALWAYS)
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
    public DateSerializer dateSerializer() {
        return new DateSerializer();
    }

    @Bean
    public LocalDateTimeSerializer localDateSerializer() {
        return new LocalDateTimeSerializer();
    }

    @Bean
    public BeanSerializerModifierFactory beanSerializerModifierFactory() {
        return new BeanSerializerModifierFactory();
    }


    @Bean
    public ObjectMapper objectMapper(DoubleValueSerializer doubleValueSerializer, BigDecimalSerializer bigDecimalSerializer,
                                     DateSerializer dateSerializer, BeanSerializerModifierFactory beanSerializerModifierFactory,
                                     LocalDateTimeSerializer localDateSerializer
    ) {
        ObjectMapper objectMapper = new ObjectMapper();
        //Jackson 当属性null 不会序列化。
        //objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        SimpleModule module = new SimpleModule();
        module.addSerializer(Double.class, doubleValueSerializer);
        module.addSerializer(BigDecimal.class, bigDecimalSerializer);
        module.addSerializer(Date.class, dateSerializer);
        module.addSerializer(LocalDateTime.class, localDateSerializer);
        objectMapper.registerModule(module);

        // 为mapper注册一个带有SerializerModifier的Factory，此modifier主要做的事情为：判断序列化类型，根据类型指定为null时的值
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(beanSerializerModifierFactory));

        return objectMapper;
    }
}
