package cn.soboys.restapispringbootstarter.test;

import org.dromara.hutool.core.date.DateUnit;
import org.dromara.hutool.core.date.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;
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
    public Entity test() {
        Entity entity = new Entity(DateUtil.now(), new BigDecimal(12.989), new Double(20.469));
        return entity;
    }
}
