package cn.soboys.restapispringbootstarter.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.json.JSON;
import org.dromara.hutool.json.JSONObject;
import org.dromara.hutool.json.JSONUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/11 17:58
 * @webSite https://github.com/coder-amiao
 */
@Slf4j
public class RequestUtil {

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getReq() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = null;
        if (attributes != null) {
            httpServletRequest = attributes.getRequest();
        }
        return httpServletRequest;
    }

    /**
     * 获取post请求的body中的内容
     *
     * @param request
     * @return
     * @throws IOException
     */
    public static JSON getParam(HttpServletRequest request) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String str = "";
        StringBuffer wholeStr = new StringBuffer();
        //读取body体里面的内容；
        while ((str = reader.readLine()) != null) {
            wholeStr.append(str);
        }
        //判断是否是数组
        String text = wholeStr.toString();
        if (StringUtils.isBlank(text)) {
            return new JSONObject();
        }
        if (StringUtils.startsWith(text, "[")) {
            return JSONUtil.parseArray(text);
        }
        return JSONUtil.parseObj(text);
    }

    public static JSON getRequestParams(HttpServletRequest request) throws IOException {
        JSONObject jsonObject = new JSONObject();
        //post或get 普通提交
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (MapUtil.isNotEmpty(parameterMap)) {
            parameterMap.forEach((key, value) -> {
                jsonObject.put(key, value[0]);
            });
            return jsonObject;
        }
        //JSON提交
        return getParam(request);
    }

    /**
     * 根据key获取value，会从header，paramter，cookie中查找。
     */
    public static String getValue(HttpServletRequest request, String name) {
        String value = request.getHeader(name);
        if (value == null) {
            value = request.getParameter(name);
        }
        if (value == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        value = cookie.getValue();
                        try {
                            value = URLDecoder.decode(value, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            log.error("Cookie 解码失败，编码=UTF-8，值=" + value);
                        }
                        break;
                    }
                }
            }
        }
        return value;
    }

    public static boolean hasAjaxRequest() {
        return hasAjaxRequest(getReq());
    }

    public static boolean hasLocalRequest() {
        return "127.0.0.1".endsWith(HttpUserAgent.getIpAddr()) ? true : false;
    }

    public static String getHost() {
        HttpServletRequest request = RequestUtil.getReq();
        return request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));
    }

    /**
     * 将name转化为可访问的url地址，前端自动加上http头和域名端口信息，name自动用／连接
     *
     * @param names
     * @return
     */
    public static String getHttpUrl(String... names) {
        return getHttpUrl(getReq(), names);
    }

    private static String getHttpUrl(HttpServletRequest request, String... names) {
        String httpUrl = request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));
        if (names != null) {
            for (String name : names) {
                httpUrl = httpUrl + "/" + (name.startsWith("/") ? name.substring(1) : name);
            }
        }
        return httpUrl;
    }

    public static boolean hasAjaxRequest(HttpServletRequest request) {
        String requestedWith = request.getHeader("x-requested-with");
        if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else {
            return false;
        }
    }




    /**
     * 获取请求地址中的某个参数
     * @param url
     * @param name
     * @return
     */
    public static String getParam(String url, String name) {
        return urlSplit(url).get(name);
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     * @param url url地址
     * @return url请求参数部分
     */
    private static String truncateUrlPage(String url) {
        String strAllParam = null;
        String[] arrSplit = null;
        url = url.trim().toLowerCase();
        arrSplit = url.split("[?]");
        if (url.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 将参数存入map集合
     * @param url  url地址
     * @return url请求参数部分存入map集合
     */
    public static Map<String, String> urlSplit(String url) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = truncateUrlPage(url);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }

    public static class WebRequest<T> {

        private T request;

        public WebRequest(T request) {
            this.request = request;
        }

        public String getParameter(String paramName) {
            try {
                Method method = request.getClass().getMethod("getParameter", String.class);
                Object value = method.invoke(request, paramName);
                return (String) value;
            } catch (Exception e) {
                return null;
            }
        }

        public Map<String, String[]> getParameterMap() {
            try {
                Method method = request.getClass().getMethod("getParameterMap");
                Object value = method.invoke(request);
                return (Map<String, String[]>) value;
            } catch (Exception e) {
                return null;
            }
        }

    }
}
