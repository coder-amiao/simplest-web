package cn.soboys.restapispringbootstarter.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * @author kenx
 * @version 1.0
 * @date 2021/1/21 20:51
 * 金额验证器
 */
public class IsMoneyValidator implements ConstraintValidator<IsMoney, BigDecimal> {

    private boolean required = false;

    public void initialize(IsMoney constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (required) {
            return ValidatorUtil.isMoney(value);
        } else {
            if (value==null) {
                return true;
            } else {
                return ValidatorUtil.isMoney(value);
            }
        }
    }

}


