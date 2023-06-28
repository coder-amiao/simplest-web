package cn.soboys.restapispringbootstarter.config;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/27 22:35
 * @webSite https://github.com/coder-amiao
 */

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;
import org.springframework.core.env.ConfigurableEnvironment;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class StartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private ConfigurableEnvironment environment;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        MutablePropertySources propertySources = environment.getPropertySources();
        //Object path= propertySources.get("application");

        // Map<String, Object> myMap = new HashMap<>();
        //myMap.put("rest-api.logging.path", System.getProperty("user.dir")+ File.separator+"logs");
        // propertySources.addFirst(new MapPropertySource("MY_MAP", myMap));
    }
}

