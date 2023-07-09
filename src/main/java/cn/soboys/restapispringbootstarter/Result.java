package cn.soboys.restapispringbootstarter;


import cn.soboys.restapispringbootstarter.domain.BaseObj;
import cn.soboys.restapispringbootstarter.i18n.DefaultMessage;
import cn.soboys.restapispringbootstarter.i18n.I18NMessage;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.date.DateUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 09:08
 * @webSite https://github.com/coder-amiao
 */
@Data
@Slf4j
public class Result<T> extends BaseObj{

    public static final String SUCCESS_CODE = "OK";
    public static final String ERROR_CODE = "FAIL";
    public static final String MSG = "操作成功";
    public static final String ERROR_MSG = "操作失败";
    private static final String TIMESTAMP = DateUtil.formatNow();

    private static final String I18N_HEADER = "Lang";




    private static final I18NMessage i18NMessage = SpringUtil.getBean(I18NMessage.class);
    private static final DefaultMessage defaultMessage = SpringUtil.getBean(DefaultMessage.class);


    private Boolean success;

    private String code;

    private String msg;

    private String requestId= IdUtil.nanoId(20);

    private String timestamp = TIMESTAMP;

    private T data;

    public Result() {
    }

    public Result(Boolean success, String code, String msg, T data) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        if (CollUtil.isEmpty(i18NMessage.getMessage())) {
            i18NMessage.setMessage(defaultMessage.getMessage());
        } else {
            /**
             * 系统默认的国际化。加上用户自定义，如果用户覆盖使用用户的
             */
            // 使用 Stream API 合并两个 Map
            Map<String, Map<String, String>> result = Stream.of(i18NMessage.getMessage(), defaultMessage.getMessage())
                    .flatMap(map -> map.entrySet().stream())
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (v1, v2) -> v1  // 如果有重复的键，选择第一个 Map 中的值
                    ));
            i18NMessage.setMessage(result);
        }
        this.success = success;
        this.code = code;
        this.msg = StrUtil.format(i18NMessage.message(msg, request.getHeader(i18NMessage.getI18nHeader())), request.getAttribute("argument_error") == null ? "" : request.getAttribute("argument_error"));
        this.data = data;
    }


    /**
     * 构建返回结果
     *
     * @param success
     * @param resultCode
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Result<T> build(Boolean success, ResultCode resultCode, T result) {
        return new Result(success, resultCode.getCode(), resultCode.getMessage(), result);
    }

    /**
     * 构建返回结果 自定义响应码信息
     *
     * @param success
     * @param msg
     * @param code
     * @param result
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T> Result<T> build(Boolean success, String code, String msg, T result) {
        return new Result(success, code, msg, result);
    }


    /**
     * 构建成功结果
     *
     * @param msg
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildSuccess(String msg, T result) {
        return build(Boolean.TRUE, SUCCESS_CODE, msg, result);
    }


    /**
     * 构建成功结果
     *
     * @param result
     * @return
     */
    public static <T> Result<T> buildSuccess(T result) {
        return build(Boolean.TRUE, SUCCESS_CODE, MSG, result);
    }

    /**
     * 构建成功结果
     *
     * @return
     */
    public static <T> Result<T> buildSuccess() {
        return build(Boolean.TRUE, SUCCESS_CODE, MSG, null);
    }


    /**
     * 构建失败结果
     *
     * @param msg
     * @param code
     * @param result
     * @return
     */
    public static <T> Result<T> buildFailure(String code, String msg, T result) {
        return build(Boolean.FALSE, code, msg, result);
    }


    /**
     * 构建失败结果
     *
     * @param msg
     * @param code
     * @return
     */
    public static <T> Result<T> buildFailure(String code, String msg) {
        return build(Boolean.FALSE, code, msg, null);
    }

    /**
     * 构建失败结果
     *
     * @param resultCode
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildFailure(ResultCode resultCode, T result) {
        return build(Boolean.FALSE, resultCode, result);
    }

    /**
     * 构建失败结果
     *
     * @param resultCode
     * @param <T>
     * @return
     */
    public static <T> Result<T> buildFailure(ResultCode resultCode) {
        return build(Boolean.FALSE, resultCode, null);
    }
}
