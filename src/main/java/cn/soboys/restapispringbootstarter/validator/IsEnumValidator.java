package cn.soboys.restapispringbootstarter.validator;




import org.dromara.hutool.core.text.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author kenx
 * @version 1.0
 * @date 2021/1/22 09:05
 * 枚举验证器
 */
public class IsEnumValidator implements ConstraintValidator<IsEnum, String> {
    private Class<? extends Enum<?>> enumClass;
    private final String ENUM_METHOD = "isValidName";
    private boolean required = false;

    @Override
    public void initialize(IsEnum constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (required) {
                return ValidatorUtil.isEnum(enumClass, o);
            } else {
                if (StrUtil.isEmptyIfStr(o)) {
                    return true;
                } else {
                    return ValidatorUtil.isEnum(enumClass, o);
                }
            }
        } catch (Exception e) {
            return Boolean.FALSE;
        }


    }
}
