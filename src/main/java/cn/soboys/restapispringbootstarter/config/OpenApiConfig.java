package cn.soboys.restapispringbootstarter.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.dromara.hutool.core.text.StrUtil;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/10 21:14
 * @webSite https://github.com/coder-amiao
 */
@EnableConfigurationProperties(value = {RestApiProperties.OpenApiProperties.class})
public class OpenApiConfig {

    @Autowired
    private RestApiProperties.OpenApiProperties openApiProperties;

    @Bean
    public OpenAPI openApi() {
        Info info = new Info();
        Contact contact = new Contact();
        if (openApiProperties != null) {
            if (StrUtil.isNotEmpty(openApiProperties.getTitle())) {
                info.setTitle(openApiProperties.getTitle());
            }
            if (StrUtil.isNotEmpty(openApiProperties.getDescription())) {
                info.setTitle(openApiProperties.getDescription());
            }

            if (openApiProperties.getContact() != null) {

                if (StrUtil.isNotEmpty(openApiProperties.getContact().getName())) {
                    contact.setName(openApiProperties.getContact().getName());
                }
                if (StrUtil.isNotEmpty(openApiProperties.getContact().getEmail())) {
                    contact.setEmail(openApiProperties.getContact().getEmail());
                }
                if (StrUtil.isNotEmpty(openApiProperties.getContact().getUrl())) {
                    contact.setUrl(openApiProperties.getContact().getUrl());
                }
            }
        }
        return new OpenAPI()
                .info(info
                        .contact(contact)
                        .version(openApiProperties.getVersion()));
    }

    @Bean
    @ConditionalOnMissingBean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("rest")
                .packagesToScan("cn.soboys.restapispringbootstarter.controller")
                .build();
    }

}
