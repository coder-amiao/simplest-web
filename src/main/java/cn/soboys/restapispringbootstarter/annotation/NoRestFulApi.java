package cn.soboys.restapispringbootstarter.annotation;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/27 21:16
 * @webSite https://github.com/coder-amiao
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不进行结果封装的注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoRestFulApi {

}
