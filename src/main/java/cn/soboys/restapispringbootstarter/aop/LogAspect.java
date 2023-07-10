package cn.soboys.restapispringbootstarter.aop;


import cn.soboys.restapispringbootstarter.Result;
import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import cn.soboys.restapispringbootstarter.enums.LogTypeEnum;
import cn.soboys.restapispringbootstarter.log.Log;
import cn.soboys.restapispringbootstarter.log.LogDataSource;
import cn.soboys.restapispringbootstarter.log.LogEntry;
import cn.soboys.restapispringbootstarter.log.LogFileDefaultDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.dromara.hutool.core.exception.ExceptionUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/9 00:59
 * @webSite https://github.com/coder-amiao
 */
@Component
@Aspect
@Slf4j
public class LogAspect extends BaseAspectSupport {

    @Autowired
    private RestApiProperties.LoggingProperties loggingProperties;

    /**
     * 配置切入点
     * 该方法无方法体,主要为了让同类中其他方法使用此切入点
     */
    @Pointcut("@annotation(cn.soboys.restapispringbootstarter.log.Log)")
    public void logPointcut() {
    }


    private long currentTime = 0L;

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        currentTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        LogEntry logBean = analyResult(result);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (result != null) {
            logBean.setPath(request.getRequestURI());
        }
        saveLog(joinPoint, logBean);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {

        LogEntry logBean = new LogEntry(LogTypeEnum.ERROR.name(), System.currentTimeMillis() - currentTime);
        logBean.setExceptionDetail(ExceptionUtil.stacktraceToString(e));

    }


    /**
     * 解析正常返回结果
     * 如果状态不为true或者状态码不为200算为失败
     *
     * @param result
     * @return
     */
    private LogEntry analyResult(Object result) {
        //判断返回结果
        LogEntry logBean = new LogEntry(LogTypeEnum.INFO.name(), System.currentTimeMillis() - currentTime);

        if (result instanceof Result) {
            Result res = (Result) result;
            if (!res.getSuccess() || res.getCode() != Result.SUCCESS_CODE) {
                logBean.setLogType(LogTypeEnum.ERROR.name());
                logBean.setExceptionDetail(res.getMsg());
                logBean.setRequestId(res.getRequestId());
            } else {
                logBean.setResult(res);
            }
        }
        return logBean;
    }


    private void saveLog(JoinPoint joinPoint, LogEntry logBean) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Log logAnnotation = method.getAnnotation(cn.soboys.restapispringbootstarter.log.Log.class);
            String methodName = signature.getName();
            //注解入参
            logBean.setDescription(logAnnotation.value());
            logBean.setApiType(logAnnotation.apiType().name());
            //方法全路径
            logBean.setMethod(joinPoint.getTarget().getClass().getName() + "." + methodName + "()");
            //如果请求对象存在则处理请求头和请求参数
            //HttpServletRequest req = HttpUserAgent.getRequest();
            if (loggingProperties != null && StrUtil.isNotEmpty(loggingProperties.getLogDataSourceClass())) {

                Class<?> clazz = Class.forName(loggingProperties.getLogDataSourceClass()); // 使用全类名
                Object instance = clazz.getDeclaredConstructor().newInstance(); // 创建实例
                ((LogDataSource) instance).save(logBean);
            } else {
                LogFileDefaultDataSource fileDefaultDataSource = new LogFileDefaultDataSource();
                fileDefaultDataSource.save(logBean);
            }

        } catch (Exception e) {
            log.error("日志AOP封装log对象异常:", ExceptionUtil.stacktraceToString(e));
        }
    }
}
