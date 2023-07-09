package cn.soboys.restapispringbootstarter;


import org.dromara.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableSpringUtil
@EnableAsync
public class RestApiSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiSpringBootStarterApplication.class, args);
    }

}
