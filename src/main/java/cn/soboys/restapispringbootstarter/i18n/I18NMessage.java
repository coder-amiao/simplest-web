package cn.soboys.restapispringbootstarter.i18n;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import java.util.Map;
import java.util.Optional;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 11:55
 * @webSite https://github.com/coder-amiao
 */
//@PropertySource(value = "classpath:i18n.yaml", factory = YamlPropertySourceFactory.class)
@Configuration
@ConfigurationProperties(prefix = "rest-api.i18n")
@Data
public class I18NMessage {
    /**
     * message-key:<lang:message>
     */
    private Map<String, Map<String, String>> message;
    /**
     * Default language setting (Default "cn").
     */
    private String defaultLang = "cn";


    private String i18nHeader = "Lang";


    /**
     * get i18n message
     *
     * @param key
     * @param language
     * @return
     */
    public String message(I18NKey key, String language) {
        return Optional.ofNullable(message.get(key.key()))
                .map(map -> map.get(language == null ? defaultLang : language))
                .orElse(key.key());
    }

    /**
     * get i18n message
     *
     * @param key
     * @param language
     * @return
     */
    public String message(String key, String language) {
        return Optional.ofNullable(message.get(key))
                .map(map -> map.get(language == null ? defaultLang : language))
                .orElse(key);
    }

}
