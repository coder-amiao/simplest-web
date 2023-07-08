package cn.soboys.restapispringbootstarter.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.soboys.restapispringbootstarter.Result;
import cn.soboys.restapispringbootstarter.ResultPage;
import cn.soboys.restapispringbootstarter.domain.EntityParam;

import cn.soboys.restapispringbootstarter.cache.CacheKey;
import cn.soboys.restapispringbootstarter.cache.RedisTempUtil;
import cn.soboys.restapispringbootstarter.log.Log;
import cn.soboys.restapispringbootstarter.utils.RestFulTemp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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


    @PostMapping("/chat")
    public Result chatDialogue(@Validated EntityParam s) {
        return Result.buildSuccess(s);
    }


    @PostMapping("/page")
    @Log("查询用户数据")
    public Result page(@Validated EntityParam s) {
        ResultPage<List<EntityParam>> resultPage=new ResultPage<>();
        List a=new ArrayList();
        a.add(s);
        resultPage.setPageData(a);
        return ResultPage.buildSuccess(resultPage);
    }


    @GetMapping("/doDelete")
    public Result doDelete() {
        restFulTemp.doDelete("http://127.0.0.1:8000/chat");
        return Result.buildSuccess();
    }


    @GetMapping("/doPut")
    public Result doPut() {
        EntityParam s = new EntityParam();
        restFulTemp.doPut("http://127.0.0.1:8000/chat", s);
        return Result.buildSuccess(s);
    }


    /**
     * POST FORM 表单参数
     *
     * @return
     */
    @PostMapping("/doPost")
    public Result doPostForm() {
        EntityParam s = new EntityParam();
        s.setAge(19);
        s.setHobby("swing");
        s.setName("judy");

        ResponseEntity<String> response =
                restFulTemp.doPostForm("http://127.0.0.1:8000/chat", BeanUtil.beanToMap(s));
        return Result.buildSuccess(response.getBody());
    }


    @GetMapping("/redis/unifyCache")
    public Result unifyCacheKey() {
        CacheKey.PWD_RESET_CODE.valueSetAndExpire("test", 60l, TimeUnit.SECONDS, "judy");
        CacheKey.PWD_RESET_CODE.valueSetAndExpire("test——", 60l, TimeUnit.SECONDS, "billy");
        return Result.buildSuccess();
    }

    @GetMapping("/redis/unifyCacheGet")
    public Result unifyCacheGet() {
        String a = CacheKey.PWD_RESET_CODE.valueGet("judy");
        return Result.buildSuccess(a);
    }


    @Cacheable(cacheNames = "testCache", keyGenerator = "keyGeneratorStrategy")
    @GetMapping("/redis/springCache")
    public Result springCache() {
        String a = "test cache";
        return Result.buildSuccess(a);
    }
}
