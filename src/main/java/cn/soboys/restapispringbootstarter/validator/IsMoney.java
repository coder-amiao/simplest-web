package cn.soboys.restapispringbootstarter.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;


/**
 * @author kenx
 * @version 1.0
 * @date 2021/1/21 20:49
 * 自定义金额认证
 */
@Target({ElementType.METHOD,ElementType.FIELD, ElementType.TYPE,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsMoneyValidator.class })
public @interface IsMoney {

    boolean required() default true;

    String message() default "金额格式错误";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
