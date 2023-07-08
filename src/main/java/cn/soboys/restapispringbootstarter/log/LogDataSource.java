package cn.soboys.restapispringbootstarter.log;

import org.springframework.scheduling.annotation.Async;

import java.util.Map;

/**
 * @Author: kenx
 * @Since: 2021/6/23 13:55
 * @Description:
 */
public interface LogDataSource {

    /**
     * 获取拓展数据
     * @return
     * @param logEntry
     */
    @Async
    void  save(LogEntry logEntry);
}
