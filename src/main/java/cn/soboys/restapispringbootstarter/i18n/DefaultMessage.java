package cn.soboys.restapispringbootstarter.i18n;

import cn.soboys.restapispringbootstarter.utils.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/4 14:50
 * @webSite https://github.com/coder-amiao
 */
@Configuration
@PropertySource(value = "classpath:i18n.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "i18n")
@Data
public class DefaultMessage {

    private Map<String, Map<String, String>> message;

}
