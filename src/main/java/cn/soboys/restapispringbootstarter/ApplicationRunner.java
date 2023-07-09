package cn.soboys.restapispringbootstarter;


import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/27 23:33
 * @webSite https://github.com/coder-amiao
 */
@Component
@Slf4j
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private RestApiProperties.LoggingProperties restApiProperties;

    @Override
    public void run(String... args) throws Exception {
        String path = restApiProperties.getPath();
        if (StrUtil.isBlank(path)) {
            //log.error("找不大restApi接口日志配置路径请设置路径");
        }
    }
}
