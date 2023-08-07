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
    /**
     * 数据库驱动
     */
    private String driverName;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;
    /**
     * 数据库连接url
     */
    private String url;
    /**
     * 生成代码 保存路径。默认当前项目下。
     * 如需修改，使用绝对得路径
     */
    private String projectPath;
    /**
     * 代码生成包位置
     */
    private String packages;
}
