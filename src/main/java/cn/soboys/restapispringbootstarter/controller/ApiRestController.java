package cn.soboys.restapispringbootstarter.controller;


import cn.soboys.restapispringbootstarter.Result;
import cn.soboys.restapispringbootstarter.Student;

import cn.soboys.restapispringbootstarter.utils.RedisTempUtil;
import cn.soboys.restapispringbootstarter.utils.RestFulTemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@Slf4j
public class ApiRestController {

    @Autowired
    private RedisTempUtil redisTempUtil;
    @Autowired
    private RestFulTemp restFulTemp;

    @GetMapping("/api")
    public HashMap index(@Validated Student student) {
        HashMap m = new HashMap();
        m.put("age", 26);
        m.put("name", "Judy");
        return m;
    }


    @GetMapping("/redis")
    public Result redis() {
        redisTempUtil.set("test", "123456");
        return Result.buildSuccess();
    }


    @GetMapping("/redis/get")
    public Result redisGet() {
        String value = redisTempUtil.get("test").toString();
        log.info("redis值{}", value);
        return Result.buildSuccess();
    }

    @GetMapping("/doGet")
    public Result doGet() {
        ResponseEntity<String> response = restFulTemp.doGet("http://127.0.0.1:8000/redis/get");
        return Result.buildSuccess(response.getBody());
    }

}
