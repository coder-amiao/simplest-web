package cn.soboys.restapispringbootstarter;

import cn.soboys.restapispringbootstarter.annotation.NoRestFulApi;
import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

        return this.userDefineWrapResult(returnType);
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
        Map resultMap = new LinkedHashMap();
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
            String keyPageData = restApiProperties.getPageData();
            String codeSuccessValue = restApiProperties.getCodeSuccessValue();


            if (StrUtil.isNotEmpty(keySuccess)) {
                resultMap.put(keySuccess, r.getSuccess());
            }
            if (StrUtil.isNotEmpty(keyCode)) {
                if (StrUtil.isNotEmpty(codeSuccessValue) && r.getCode().equals("OK")) {
                    resultMap.put(keyCode, restApiProperties.getCodeSuccessValue());
                } else {
                    resultMap.put(keyCode, r.getCode());
                }
            }
            if (StrUtil.isNotEmpty(keyMsg)) {
                resultMap.put(keyMsg, r.getMsg());
            }
            resultMap.put("requestId", r.getRequestId());
            resultMap.put("timestamp", r.getTimestamp());
            if (r.getData() != null && r.getData() instanceof ResultPage) {
                ResultPage resultPage = (ResultPage) r.getData();

                /**
                 * 分页结果包装到data
                 */
                if (restApiProperties.getPageWrap()) {
                    Map dataMap = new LinkedHashMap();
                    if (StrUtil.isNotEmpty(keyPreviousPage)) {
                        dataMap.put(keyPreviousPage, resultPage.getPreviousPage());
                    }
                    if (StrUtil.isNotEmpty(keyNextPage)) {
                        dataMap.put(keyNextPage, resultPage.getNextPage());
                    }
                    if (StrUtil.isNotEmpty(keyPageSize)) {
                        dataMap.put(keyPageSize, resultPage.getPageSize());
                    }
                    if (StrUtil.isNotEmpty(keyHasNext)) {
                        dataMap.put(keyHasNext, resultPage.getHasNext());
                    }
                    if (StrUtil.isNotEmpty(keyTotalPageSize)) {
                        dataMap.put(keyTotalPageSize, resultPage.getTotalPageSize());
                    }

                    if (StrUtil.isNotEmpty(keyPageData)) {
                        dataMap.put(keyPageData, resultPage.getPageData());
                    }
                    resultMap.put(keyData, dataMap);
                    return resultMap;

                } else {
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
                }
            } else {
                if (StrUtil.isNotEmpty(keyData)) {
                    resultMap.put(keyData, r.getData());
                }
            }
            return resultMap;
        } else {
            if (r.getData() != null && r.getData() instanceof ResultPage) {
                /**
                 * 分页结果包装到`data
                 */
                if (restApiProperties.getPageWrap()) {
                    Map dataMap = BeanUtil.beanToMap(r.getData(), false, true);
                    dataMap.remove("requestId");
                    dataMap.remove("timestamp");
                    r.setData(dataMap);
                    return r;
                }

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


    /**
     * 用户自定义需要包装类
     *
     * @param returnType
     * @return
     */
    private Boolean userDefineWrapResult(MethodParameter returnType) {

        /**
         * 注解优先级别最高
         */
        Boolean flag = !returnType.hasMethodAnnotation(NoRestFulApi.class);
        if (!flag) return flag;

        String cls = returnType.getContainingClass().getTypeName();


        /**
         * 只添加用户需要包装的
         */
        if (ArrayUtil.isNotEmpty(restApiProperties.getIncludePackages())) {
            for (String cla : restApiProperties.getIncludePackages()
            ) {
                if (cls.contains(cla)) {
                    flag = true;
                } else {
                    flag = false;
                }
                break;
            }
        }

        /**
         * 过滤掉用户自定义不需要包装的类
         */
        if (ArrayUtil.isNotEmpty(restApiProperties.getExcludePackages())) {
            for (String cla : restApiProperties.getExcludePackages()
            ) {
                if (cls.contains(cla)) {
                    flag = false;
                } else {
                    flag = true;
                }
                break;
            }
        }

        /**
         * 默认扫描RestController才会统一返回
         */
        Map<String, Object> controllerBeans = SpringUtil.getApplicationContext().getBeansWithAnnotation(RestController.class);
        controllerBeans.remove("openApiResource");
        controllerBeans.remove("swaggerConfigResource");
        for (Object bean : controllerBeans.values()) {
            String packName = bean.getClass().getPackage().getName();
            if (cls.contains(packName)) {
                flag = true;
            } else {
                flag = false;
            }
            break;
        }
        return flag;
    }
}


