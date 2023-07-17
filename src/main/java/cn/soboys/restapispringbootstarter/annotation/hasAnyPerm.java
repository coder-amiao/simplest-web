package cn.soboys.restapispringbootstarter.annotation;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/16 12:19
 * @webSite https://github.com/coder-amiao
 *
 */

import cn.soboys.restapispringbootstarter.utils.Strings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证用户是否具有以下任意一个权限
 *
 * @param permissions 以 PERMISSION_SEPARATOR 为分隔符的权限列表
 * @return 用户是否具有以下任意一个权限
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface hasAnyPerm {
    String permission() default Strings.EMPTY;
}
