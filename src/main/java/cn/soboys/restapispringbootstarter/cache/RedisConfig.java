package cn.soboys.restapispringbootstarter.cache;

import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;
import java.time.Duration;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/7 09:51
 * @webSite https://github.com/coder-amiao
 */
@Configuration
public class RedisConfig {
    @Resource
    private RestApiProperties.RedisProperties redisProperties;

    /**
     * Jackson序列化  json
     *
     * @param factory
     * @return
     */
    @Bean
    @ConditionalOnClass(RedisOperations.class)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        //序列化包括类型描述 否则反向序列化实体会报错，一律都为JsonObject
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(mapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        // 使用 Jackson2JsonRedisSerializer 作为 value 的序列化器
        jackson2JsonRedisSerializer.setObjectMapper(mapper);
        /// 使用 StringRedisSerializer 作为 key 的序列化器
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用 String的序列化方式 如果有前缀的时候。加入全局前缀
        if(redisProperties!=null&& StrUtil.isNotEmpty(redisProperties.getKeyPrefix())){
            template.setKeySerializer(new PrefixStringRedisSerializer(redisProperties.getKeyPrefix()+":", stringRedisSerializer));
            template.setHashKeySerializer(new PrefixStringRedisSerializer(redisProperties.getKeyPrefix()+":", stringRedisSerializer));
        }else {
            template.setKeySerializer(stringRedisSerializer);
            // hash的 key也采用 String的序列化方式
            template.setHashKeySerializer(stringRedisSerializer);
        }

        // value序列化方式采用 jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        // hash的 value序列化方式采用 jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();

        return template;
    }

    public class PrefixStringRedisSerializer implements RedisSerializer<String> {
        private final String prefix;
        private final StringRedisSerializer serializer;

        public PrefixStringRedisSerializer(String prefix, StringRedisSerializer serializer) {
            this.prefix = prefix;
            this.serializer = serializer;
        }

        @Override
        public byte[] serialize(String s) throws SerializationException {
            return serializer.serialize(prefix + s);
        }

        @Override
        public String deserialize(byte[] bytes) throws SerializationException {
            String key = serializer.deserialize(bytes);
            if (key != null && key.startsWith(prefix)) {
                return key.substring(prefix.length());
            }
            return key;
        }
    }


}
