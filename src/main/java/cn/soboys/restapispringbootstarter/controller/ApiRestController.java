package cn.soboys.restapispringbootstarter.controller;



import cn.soboys.restapispringbootstarter.Result;
import cn.soboys.restapispringbootstarter.ResultPage;
import cn.soboys.restapispringbootstarter.cache.CacheTmp;
import cn.soboys.restapispringbootstarter.domain.EntityParam;

import cn.soboys.restapispringbootstarter.cache.CacheKey;
import cn.soboys.restapispringbootstarter.cache.RedisTempUtil;
import cn.soboys.restapispringbootstarter.enums.LogApiTypeEnum;
import cn.soboys.restapispringbootstarter.enums.LogCURDTypeEnum;
import cn.soboys.restapispringbootstarter.log.Log;
import cn.soboys.restapispringbootstarter.utils.RestFulTemp;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.map.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    @Log(value = "查询用户数据", apiType = LogApiTypeEnum.USER, CURDType = LogCURDTypeEnum.RETRIEVE)
    public Result page(@Validated EntityParam s) {
        ResultPage<List<EntityParam>> resultPage = new ResultPage<>();
        List a = new ArrayList();
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

    @GetMapping("/redis")
    public Result redis() {
        redisTempUtil.set("bally", "123");
        return Result.buildSuccess();
    }

    @GetMapping("/redisGet")
    public Result redisGet() {
        return Result.buildSuccess(redisTempUtil.get("bally"));
    }

    @GetMapping("/defind")
    public Map defind() {
        Map m= MapUtil.newHashMap();
        m.put("name","judy");
        m.put("age",19);
        return m;
    }
}
