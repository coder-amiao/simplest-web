package cn.soboys.restapispringbootstarter;


import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.extra.spring.EnableSpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableSpringUtil
@EnableAsync
@Slf4j
public class RestApiSpringBootStarterApplication {


    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(RestApiSpringBootStarterApplication.class, args);
        listBeans(ctx);
    }
    public static void listBeans(ApplicationContext ctx) {

        log.info("bean总数:{}", ctx.getBeanDefinitionCount());
        String[] allBeanNames = ctx.getBeanDefinitionNames();
        for (String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }

}
