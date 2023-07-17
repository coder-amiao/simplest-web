package cn.soboys.restapispringbootstarter.test;

import cn.soboys.restapispringbootstarter.Result;
import cn.soboys.restapispringbootstarter.authorization.UserJwtToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private UserJwtToken userJwtToken;

    @PostMapping("/test")
    @Operation(summary = "分页查询")
    public Map chatDialogue() {
        Map m = new HashMap<>();
        m.put("name", "judy");
        m.put("age", 26);
        return m;
    }

    @GetMapping("/loginMap")
    public Result loginMap() {
        UserEntry userEntry = new UserEntry();
        userEntry.setUserId("1");
        userEntry.setUsername("judy");
        userEntry.setHobby("swing");
        Map map=new HashMap();
        map.put("userId",userEntry.getUserId());
        map.put("name",userEntry.getUsername());
        map.put("user",userEntry);
        String token = userJwtToken.createdToken(userEntry.getUserId(), userEntry.getUsername(), map);
        return Result.buildSuccess(token);
    }

    @GetMapping("/login")
    public Result login() {
        UserEntry userEntry = new UserEntry();
        userEntry.setUserId("2");
        userEntry.setUsername("billy");
        userEntry.setHobby("eat");
        userJwtToken.rememberMe=true;
        String token = userJwtToken.createdToken(userEntry.getUserId(), userEntry.getUsername(), userEntry);
        return Result.buildSuccess(token);
    }



    @GetMapping("/user")
    public Result getUser() {
        String token = "eyJhbGciOiJIUzI1NiIsInppcCI6IkRFRiJ9.eNqqViouTVKyUkrKzMmpVNJRyiwuBvKMgKyskkwoK7WiQMnK0MzC0tTUwsDEWEeptDi1SMmqGkx7pkBVgTh5ibmpSIZl5CclVQL5qYklSrW1AAAAAP__.8nWRs40LbRTIQBhJ8jVaANPcvsmX0zoLR66R-b2Uc4M";
        String userId= userJwtToken.getUserId(token);
        return Result.buildSuccess(userId);
    }

    @GetMapping("/register/test")
    public Result register() {

        return Result.buildSuccess("register");
    }

}
