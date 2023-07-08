package cn.soboys.restapispringbootstarter.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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


    @Configuration
    @ConfigurationProperties(prefix = "rest-api.logging")
    @Data
    public class LoggingProperties {
        private String path;
        private String maxHistory;
        private String maxFileSize;
        private String maxTotalSizeCap;
        private String levelRoot;
        private String logDataSourceClass="cn.soboys.restapispringbootstarter.log.LogFileDefaultDataSource";
    }
}
