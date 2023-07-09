package cn.soboys.restapispringbootstarter;


import cn.soboys.restapispringbootstarter.annotation.NoRestFulApi;
import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 23:27
 * @webSite https://github.com/coder-amiao
 */
@Slf4j
@ControllerAdvice
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ResultHandler implements ResponseBodyAdvice<Object> {

    @Autowired
    private RestApiProperties restApiProperties;

    /**
     * supports方法: 判断是否要执行beforeBodyWrite方法,
     * true为执行,false不执行.
     * 通过该方法可以选择哪些类或那些方法的response要进行处理, 其他的不进行处理.
     *
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(NoRestFulApi.class);
    }

    /**
     * beforeBodyWrite方法: 对response方法进行具体操作处理
     * 实际返回结果业务包装处理
     *
     * @param body
     * @param returnType
     * @param selectedContentType
     * @param selectedConverterType
     * @param request
     * @param response
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            return this.userDefinedResultKey((Result) body);
        } else if (body == null) {
            return this.userDefinedResultKey(Result.buildSuccess());
        } else if (body instanceof String) {
            return body;
        } else {
            return this.userDefinedResultKey(Result.buildSuccess(body));
        }
    }


    /**
     * 用户配置自定义返回结果
     *
     * @return
     */
    private Object userDefinedResultKey(Result r) {
        Map resultMap = new HashMap<>();
        if (restApiProperties != null && r != null && restApiProperties.isEnabled()) {
            String keyCode = restApiProperties.getCode();
            String keyMsg = restApiProperties.getMsg();
            String keySuccess = restApiProperties.getSuccess();
            String keyData = restApiProperties.getData();
            String keyPreviousPage = restApiProperties.getPreviousPage();
            String keyNextPage = restApiProperties.getNextPage();
            String keyPageSize = restApiProperties.getPageSize();
            String keyHasNext = restApiProperties.getHasNext();
            String keyTotalPageSize = restApiProperties.getTotalPageSize();

            if (StrUtil.isNotEmpty(keySuccess)) {
                resultMap.put(keySuccess, r.getSuccess());
            }
            if (StrUtil.isNotEmpty(keyCode)) {
                resultMap.put(keyCode, r.getCode());
            }
            if (StrUtil.isNotEmpty(keyMsg)) {
                resultMap.put(keyMsg, r.getMsg());
            }
            resultMap.put("timestamp", r.getTimestamp());
            resultMap.put("requestId",r.getRequestId());
            if (r.getData() != null && r.getData() instanceof ResultPage) {
                ResultPage resultPage = (ResultPage) r.getData();
                if (StrUtil.isNotEmpty(keyPreviousPage)) {
                    resultMap.put(keyPreviousPage, resultPage.getPreviousPage());
                }
                if (StrUtil.isNotEmpty(keyNextPage)) {
                    resultMap.put(keyNextPage, resultPage.getNextPage());
                }
                if (StrUtil.isNotEmpty(keyPageSize)) {
                    resultMap.put(keyPageSize, resultPage.getPageSize());
                }
                if (StrUtil.isNotEmpty(keyHasNext)) {
                    resultMap.put(keyHasNext, resultPage.getHasNext());
                }
                if (StrUtil.isNotEmpty(keyTotalPageSize)) {
                    resultMap.put(keyTotalPageSize, resultPage.getTotalPageSize());
                }
                if (StrUtil.isNotEmpty(keyData)) {
                    resultMap.put(keyData, resultPage.getPageData());
                }
            }else {
                if (StrUtil.isNotEmpty(keyData)) {
                    resultMap.put(keyData, r.getData());
                }
            }
            return resultMap;
        } else {
            if (r.getData() != null && r.getData() instanceof ResultPage) {
                ResultPage resultPage = (ResultPage) r.getData();
                resultPage.setCode(r.getCode());
                resultPage.setMsg(r.getMsg());
                resultPage.setSuccess(r.getSuccess());
                resultPage.setTimestamp(r.getTimestamp());
                resultPage.setData(resultPage.getPageData());
                resultPage.setPageData(null);
                Map rPage = BeanUtil.beanToMap(resultPage, false, true);
                return rPage;
            } else {
                return r;
            }

        }
    }
}


