package cn.soboys.restapispringbootstarter.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author kenx
 * @version 1.0
 * @date 2021/1/22 09:03
 * 枚举类属性字段定义必须是code，desc
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IsEnumValidator.class})
public @interface IsEnum {
    boolean required() default true;

    String message() default "状态值不正确";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends Enum<?>> enumClass();
}
