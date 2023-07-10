package cn.soboys.restapispringbootstarter.controller.test;

import cn.soboys.restapispringbootstarter.Result;
import cn.soboys.restapispringbootstarter.domain.EntityParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result chatDialogue() {
        return Result.buildSuccess("test");
    }
}
