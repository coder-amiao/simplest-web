package cn.soboys.restapispringbootstarter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/27 22:55
 * @webSite https://github.com/coder-amiao
 */
@Configuration
@ConfigurationProperties(prefix = "rest-api")
@Data
public class RestApiProperties {


    private boolean enabled = Boolean.FALSE;


    private String success = "success";

    private String code = "code";

    private String codeSuccessValue = "OK";

    private String msg = "msg";

    private String timestamp = "timestamp";

    private String data = "data";

    /**
     * 当前页
     */
    private String previousPage = "previousPage";
    /**
     * 下一页
     */
    private String nextPage = "nextPage";

    /**
     * 总页数
     */
    private String pageSize = "pageSize";

    private String totalPageSize = "totalPageSize";

    /**
     * 是否有下一页
     */
    private String hasNext = "hasNext";

    /**
     * 是否包装分页结果到data
     */
    private Boolean pageWrap = Boolean.TRUE;

    private String pageData = "pageData";

    /**
     * 排除不需要统一返回的restFull
     */
    private String[] excludePackages;
    /**
     * 添加需要统一返回的restFull
     */
    private String[] includePackages;


    @Configuration
    @ConfigurationProperties(prefix = "rest-api.ip2region")
    @Data
    public class Ip2regionProperties {
        /**
         * 是否使用外部的IP数据文件.
         */
        private boolean external = false;
        /**
         * ip2region.db 文件路径，默认： classpath:ip2region/ip2region.db
         */
        private String location = "classpath:ip2region/ip2region.xdb";

    }


    @Configuration
    @ConfigurationProperties(prefix = "rest-api.jwt")
    @Data
    public class JwtProperties {

        /**
         * 过期时间秒1天后过期=86400  (单位秒)
         */
        private Long expiration = 86400l;

        /**
         * 记住我过期时间 7天后过期=604800（单位秒）
         */
        private Long rememberMeExpiration = 604800l;

        /**
         * 配置用户自定义签名
         */
        private Boolean userSign = Boolean.FALSE;
        /**
         * Header Key
         */
        private String tokenHeader = "Token";

        /**
         * # 密匙KEY
         */
        private String secret = "2af57b969bac152d";


        private Authorization authorization = new Authorization();
    }

    @Data
    public static class Authorization {

        private Boolean hasAuthorization = Boolean.FALSE;

        /**
         * 需要认证的url
         */
        private String includesUrl;

        /**
         * 不需要认证的url
         */
        private String excludesUrl;
    }


    @Configuration
    @ConfigurationProperties(prefix = "rest-api.logging")
    @Data
    public class LoggingProperties {
        private String path;
        private String maxHistory;
        private String maxFileSize;
        private String maxTotalSizeCap;
        private String levelRoot;
        private String logDataSourceClass = "cn.soboys.restapispringbootstarter.log.LogFileDefaultDataSource";
    }

    @Configuration
    @ConfigurationProperties(prefix = "rest-api.redis")
    @Data
    public class RedisProperties {
        /**
         * 全局注册key
         */
        private String keyPrefix;
        /**
         * redis 缓存的默认超时时间(s) 1天超时
         */
        private Long expireTime;
    }

    @Configuration
    @ConfigurationProperties(prefix = "rest-api.openapi")
    @Data
    public class OpenApiProperties {
        /**
         * 是否开启swagger
         */
        private Boolean enabled = true;

        /**
         * 分组名称
         */
        private String groupName;

        /**
         * 文档版本，默认使用 2.0
         */
        private String documentationType = "v2.0";

        /**
         * swagger会解析的包路径
         **/
        private String basePackage = "";

        /**
         * swagger会解析的url规则
         **/
        private List<String> basePath = new ArrayList<>();

        /**
         * 在basePath基础上需要排除的url规则
         **/
        private List<String> excludePath = new ArrayList<>();

        /**
         * 标题
         **/
        private String title = "REST FULL";

        /**
         * 描述
         **/
        private String description = "SpringBoot Web Easy RestFull API";

        /**
         * 版本
         **/
        private String version = "1.5.0";

        /**
         * 许可证
         **/
        private String license = "";

        /**
         * 许可证URL
         **/
        private String licenseUrl = "";

        /**
         * 服务条款URL
         **/
        private String termsOfServiceUrl = "";

        /**
         * host信息
         **/
        private String host = "https://rest-api-boot.soboys.cn/doc-rest-api-springboot-starter/";

        /**
         * 联系人信息
         */
        private Contact contact = new Contact();
    }

    @Data
    public static class Contact {

        /**
         * 联系人
         **/
        private String name = "公众号程序员三时";

        /**
         * 联系人url
         **/
        private String url = "https://github.com/coder-amiao/rest-api-spring-boot-starter";

        /**
         * 联系人email
         **/
        private String email = "xymarcus@163.com";

    }
}
