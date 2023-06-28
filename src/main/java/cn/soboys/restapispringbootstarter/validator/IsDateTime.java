package cn.soboys.restapispringbootstarter.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * @author kenx
 * @version 1.0
 * @date 2021/1/21 20:49
 * 日期验证
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsDateTimeValidator.class}) // 标明由哪个类执行校验逻辑
public @interface IsDateTime {

    // 校验出错时默认返回的消息
    String message() default "日期格式错误";
    //分组校验
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};



    //下面是我自己定义属性
    boolean required() default true;

    String dateFormat() default "yyyy-MM-dd";


}
