package cn.soboys.restapispringbootstarter.interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/10 10:33
 * @webSite https://github.com/coder-amiao
 */
public class WhiteApiList {
    public static List<String> list = new ArrayList<>();

    static {
        list.add("/user/token");
    }
}
