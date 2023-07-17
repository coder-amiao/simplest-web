package cn.soboys.restapispringbootstarter.annotation;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/16 12:18
 * @webSite https://github.com/coder-amiao
 */

import cn.soboys.restapispringbootstarter.utils.Strings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 判断用户是否拥有某个角色
 *
 * @param role 角色CODE
 * @return 用户是否具备某角色
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface hasRole {
    String role() default Strings.EMPTY;
}
