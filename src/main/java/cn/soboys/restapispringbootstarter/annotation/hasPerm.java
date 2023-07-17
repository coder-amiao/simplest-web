package cn.soboys.restapispringbootstarter.annotation;

import cn.soboys.restapispringbootstarter.utils.Strings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/16 12:16
 * @webSite https://github.com/coder-amiao
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 验证用户是否具备某权限
 *
 * @param permission 权限字符串
 * @return 用户是否具备某权限
 */
public @interface hasPerm {
    String permission() default Strings.EMPTY;
}
