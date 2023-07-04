package cn.soboys.restapispringbootstarter.condition;

import cn.soboys.restapispringbootstarter.annotation.EnableRestFullApi;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/5 00:17
 * @webSite https://github.com/coder-amiao
 * 自定义注解条件注入rest-api 自动配置
 */
public class OnEnableRestFullApiAnnotationCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return context.getBeanFactory().getBeanNamesForAnnotation(EnableRestFullApi.class).length > 0;
    }
}
