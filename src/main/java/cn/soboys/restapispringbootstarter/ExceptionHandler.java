package cn.soboys.restapispringbootstarter;


import cn.soboys.restapispringbootstarter.exception.BusinessException;
import cn.soboys.restapispringbootstarter.exception.CacheException;
import cn.soboys.restapispringbootstarter.exception.LimitAccessException;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.collection.ListUtil;
import org.dromara.hutool.core.exception.ExceptionUtil;
import org.dromara.hutool.core.stream.CollectorUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 16:44
 * @webSite https://github.com/coder-amiao
 */
@RestControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@Slf4j
public class ExceptionHandler {


    /**
     * 未知异常全局捕获
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result error(Exception e) {
        log.error("未知异常{}", ExceptionUtil.stacktraceToString(e));
        return Result.buildFailure(HttpStatus.INTERNAL_SERVER_ERROR, ExceptionUtil.stacktraceToString(e));
    }


    /**
     * 统一业务异常处理
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(BusinessException.class)
    public Result error(BusinessException e) {
        return Result.buildFailure(e.getCode(), e.getMessage());
    }


    /**
     * get 请求是没有参数体，post请求有参数体，支持表单，json，同时支持url参数
     */

    /**
     * 验证  对象类型参数
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(BindException.class)
    public Result BindExceptionHandler(BindException e, HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> collect = fieldErrors.stream()
                .map(o -> o.getField() + o.getDefaultMessage())
                .collect(Collectors.toList());
        request.setAttribute("argument_error", CollUtil.join(collect, ";"));
        return Result.buildFailure(HttpStatus.INVALID_ARGUMENT.getCode(),
                StrUtil.format(HttpStatus.INVALID_ARGUMENT.getMessage(), CollUtil.join(collect, ";")));
    }

    /**
     * 验证 单个参数类型
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    public Result ConstraintViolationExceptionHandler(ConstraintViolationException e, HttpServletRequest request) {
        List errorList = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            StringBuilder message = new StringBuilder();
            Path path = violation.getPropertyPath();
            String msg = message.append(((PathImpl) path).getLeafNode()).append(violation.getMessage()).toString();
            errorList.add(msg);
        }
        request.setAttribute("argument_error", CollUtil.join(errorList, ";"));
        return Result.buildFailure(HttpStatus.INVALID_ARGUMENT.getCode(),
                StrUtil.format(HttpStatus.INVALID_ARGUMENT.getMessage(), CollUtil.join(errorList, ";")));
    }


    /**
     * 验证  对象类型参数 JSON body 参数
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Result jsonParamsException(MethodArgumentNotValidException e, HttpServletRequest request) {
        BindingResult bindingResult = e.getBindingResult();
        List errorList = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = String.format("%s%s；", fieldError.getField(), fieldError.getDefaultMessage());
            errorList.add(msg);
        }
        request.setAttribute("argument_error", CollUtil.join(errorList, ";"));
        return Result.buildFailure(HttpStatus.INVALID_ARGUMENT.getCode(),
                StrUtil.format(HttpStatus.INVALID_ARGUMENT.getMessage(), CollUtil.join(errorList, ";")));
    }


    /**
     * 接口不存在
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(NoHandlerFoundException.class)
    public Result error(NoHandlerFoundException e) {
        return Result.buildFailure(HttpStatus.NOT_FOUND, e.getRequestURL());
    }

    /**
     * 请求方法不被允许
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return Result.buildFailure(HttpStatus.METHOD_NOT_ALLOWED, ExceptionUtil.stacktraceToString(e));
    }

    /**
     * 请求与响应媒体类型不一致 异常
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        return Result.buildFailure(HttpStatus.BAD_GATEWAY, ExceptionUtil.stacktraceToString(e));
    }

    /**
     * body json参数解析异常
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(HttpMessageNotReadableException.class)
    public Result HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return Result.buildFailure(HttpStatus.INVALID_ARGUMENT.getCode(),
                StrUtil.format(HttpStatus.INVALID_ARGUMENT.getMessage(), "JSON参数格式数据类型不对"), ExceptionUtil.stacktraceToString(e));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(LimitAccessException.class)
    public Result LimitAccessExceptionException(LimitAccessException e) {
        return Result.buildFailure(HttpStatus.REQUEST_TIMEOUT.getCode(),
                StrUtil.format(HttpStatus.REQUEST_TIMEOUT.getMessage() + "{}", e.getMessage()), ExceptionUtil.stacktraceToString(e));
    }


    /**
     * 统一业务异常处理
     *
     * @param e
     * @return
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(CacheException.class)
    public Result CacheException(CacheException e) {
        return Result.buildFailure(HttpStatus.CACHE_EXCEPTION.getCode(),
                StrUtil.format(HttpStatus.CACHE_EXCEPTION.getMessage() + "{}", e.getMessage()), ExceptionUtil.stacktraceToString(e));
    }
}
