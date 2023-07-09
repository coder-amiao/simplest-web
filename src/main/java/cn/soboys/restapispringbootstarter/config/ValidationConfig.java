package cn.soboys.restapispringbootstarter.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/9 17:51
 * @webSite https://github.com/coder-amiao
 */
//@Configuration
//public class ValidationConfig {
//    /**
//     * 参数校验快速失败返回 提升性能
//     */
//    @Bean
//    public Validator validator() {
//        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
//                .configure()
//                // 快速失败模式
//                .failFast(true)
//                .buildValidatorFactory();
//        return validatorFactory.getValidator();
//    }
//}
