package cn.soboys.restapispringbootstarter.log;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: kenx
 * @Since: 2021/6/23 13:55
 * @Description:
 */
@Slf4j
public class LogFileDefaultDataSource implements LogDataSource {

    /**
     * 自定义保存数据源
     *
     * @param
     * @return LogEntry
     */
    @Override
    public void save(LogEntry logEntry) {
        log.info(JSONUtil.toJsonPrettyStr(logEntry));
    }
}
