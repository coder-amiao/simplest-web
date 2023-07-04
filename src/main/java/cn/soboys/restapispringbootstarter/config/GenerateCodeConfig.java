package cn.soboys.restapispringbootstarter.config;

import lombok.Data;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/5 00:05
 * @webSite https://github.com/coder-amiao
 */
@Data
public class GenerateCodeConfig {
    private String driverName;
    private String username;
    private String password;
    private String url;
    private String projectPath;
    private String packages;
}
