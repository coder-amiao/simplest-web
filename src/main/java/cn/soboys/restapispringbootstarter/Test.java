package cn.soboys.restapispringbootstarter;

import org.dromara.hutool.core.text.StrUtil;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/9 17:27
 * @webSite https://github.com/coder-amiao
 */
public class Test {
    public static void main(String[] args) {
        String path="a.b.c";
        System.out.println( StrUtil.wrapAllWithPairIfMissing(path.toString(),"."));
    }
}
