package cn.soboys.restapispringbootstarter.controller;


import cn.soboys.restapispringbootstarter.Student;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 14:29
 * @webSite https://github.com/coder-amiao
 */
@RestController
@Validated
public class ApiRestController {

    @GetMapping("/api")
    public HashMap index(@Validated Student student) {
        HashMap m = new HashMap();
        m.put("age", 26);
        m.put("name", "Judy");
        return m;
    }


}
