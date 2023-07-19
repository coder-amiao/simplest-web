package cn.soboys.restapispringbootstarter.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/19 22:25
 * @webSite https://github.com/coder-amiao
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public Map test(){
        return new HashMap<>();
    }
}
