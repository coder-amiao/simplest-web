package cn.soboys.restapispringbootstarter.utils;

import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import lombok.extern.slf4j.Slf4j;

import org.dromara.hutool.core.exception.ExceptionUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.http.useragent.UserAgentUtil;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Objects;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/2 11:40
 * @webSite https://github.com/coder-amiao
 */
@Slf4j
public class HttpUserAgent {
    private static final String UNKNOWN = "unknown";


    private static RestApiProperties.Ip2regionProperties ip2regionProperties = SpringUtil.getBean(RestApiProperties.Ip2regionProperties.class);


    /**
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @return 请求头信息
     */
    public static String getDevice() {
        HttpServletRequest request = getRequest();
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        return uaStr;
    }

    /**
     * @return 请求头信息
     */
    public static String getDeviceBrowser() {
        HttpServletRequest request = getRequest();
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        String browser = ua.getBrowser().toString();
        return browser;
    }

    /**
     * @return 请求头信息
     */
    public static String getDeviceSystem() {
        HttpServletRequest request = getRequest();
        String uaStr = request.getHeader("User-Agent");
        UserAgent ua = UserAgentUtil.parse(uaStr);
        String platform = ua.getPlatform().toString();
        return platform;
    }

    public static String getIpAddr() {
        String ip = null;
        HttpServletRequest request = RequestUtil.getReq();
        String ipAddresses = request.getHeader("X-Real-IP");
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("X-Forwarded-For");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ipAddresses = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddresses != null && ipAddresses.length() != 0) {
            ip = ipAddresses.split(",")[0];
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 根据ip获取城市地理位置信息
     *
     * @param ip
     * @return
     */
    public static String getIpToCityInfo(String ip) {
        try {

            ResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(ip2regionProperties.getLocation());
            String dbPath = "";
            if (resource.getURI().getScheme().equals("jar")) {

                File file=new File("src/main/resources/"+((ClassPathResource) resource).getPath());
                FileUtil.writeFromStream(resource.getInputStream(),file);
                dbPath=file.getAbsolutePath();
            } else {
                dbPath = resource.getFile().getPath();
            }

//            if (ip2regionProperties.isExternal()) {
//                ClassPathResource resource = new ClassPathResource(ip2regionProperties.getLocation());
//                dbPath = resource.getFile().getAbsolutePath();
//            } else {
//                // 获取当前默认记录地址位置的文件
//                ResourceLoader resourceLoader = new DefaultResourceLoader();
//                Resource resource = resourceLoader.getResource("classpath:resource.txt");
//
//
//                URL u =null; //HttpUserAgent.class.getClass().getResource(dbPath);
//
//                dbPath= HttpUserAgent.class.getClassLoader().getResource("ip2region/ip2region.xdb").getPath();
////                if(u==null||u.getPath().contains("file")){
////                    dbPath =    System.getProperty("user.dir") + "/ip.db";
////                    HttpUserAgent.class.getClassLoader().getResource("ip2region/ip2region.xdb").getPath();
////                    File file=new File(dbPath);
////                    FileUtil.writeFromStream(HttpUserAgent.class.getClassLoader().getResourceAsStream("ip2region/ip2region.xdb"),file);
////                }else {
////                    dbPath=u.getPath();
////                }
//            }

            File file = new File(dbPath);
            //如果当前文件不存在，则从缓存中复制一份
            if (!file.exists()) {
                log.error("ip2region.xdb文件找不到请填写类路径");
                return "UNKNOWN";
            }
            //创建查询对象
            Searcher searcher = Searcher.newWithFileOnly(dbPath);
            //开始查询
            return searcher.search(ip);
        } catch (Exception e) {
            log.error("Ip查询城市地址解析失败{}", ExceptionUtil.stacktraceToString(e));
            e.printStackTrace();
        }
        //默认返回空字符串
        return "UNKNOWN";

    }
}
