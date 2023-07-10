package cn.soboys.restapispringbootstarter.test;

import cn.soboys.restapispringbootstarter.Result;
import cn.soboys.restapispringbootstarter.annotation.NoRestFulApi;
import cn.soboys.restapispringbootstarter.domain.EntityParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/10 19:02
 * @webSite https://github.com/coder-amiao
 */
@RestController
@Validated
@Slf4j
@Tag(name = "用户接口")
public class TestController {

    @PostMapping("/test")
    @Operation(summary = "分页查询")
    public Map chatDialogue() {
        Map  m= new HashMap<>();
        m.put("name","judy");
        m.put("age",26);
        return m;
    }
}
