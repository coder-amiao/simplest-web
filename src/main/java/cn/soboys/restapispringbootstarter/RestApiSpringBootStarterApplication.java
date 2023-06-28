package cn.soboys.restapispringbootstarter;

import cn.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableSpringUtil
public class RestApiSpringBootStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiSpringBootStarterApplication.class, args);
    }

}
