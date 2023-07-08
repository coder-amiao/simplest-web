package cn.soboys.restapispringbootstarter.log;

import cn.soboys.restapispringbootstarter.Result;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/9 01:02
 * @webSite https://github.com/coder-amiao
 */
@Data
public class LogEntry {
    /**
     * 操作用户
     */
    private String username;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 日志类型 INFO ERROR
     */
    private String logType;

    /**
     * 请求ip
     */
    private String requestIp;

    /**
     * ip地址所对应的物理地址
     */
    private String address;

    /**
     * 请求耗时
     */
    private Long time = 0L;

    /**
     * 异常详细
     */
    private String exceptionDetail;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 接口返回数据
     */
    private Object result;

    /**
     * 日志类型(例如:接口日志,操作日志...各个系统可以自定义)
     */
    private String apiType;

    /**
     * 用户编码
     */
    private Long userId;

    /**
     * 创建日期
     */
    private Date createTime;

    /**
     * 请求唯一id
     */
    private String requestId;

    public LogEntry() {
    }

    public LogEntry(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
