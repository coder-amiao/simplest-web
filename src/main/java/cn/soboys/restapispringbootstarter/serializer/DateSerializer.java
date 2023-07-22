package cn.soboys.restapispringbootstarter.serializer;

import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.dromara.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/21 23:03
 * @webSite https://github.com/coder-amiao
 */
public class DateSerializer extends JsonSerializer<Date> {

    @Autowired
    private RestApiProperties.JsonSerializeProperties jsonSerializeProperties;

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            //DateUtil.format(value, DatePattern.NORM_DATETIME_MS_PATTERN);
            if(jsonSerializeProperties.getDateForm().equals("timestamp")){
                jgen.writeString(String.valueOf(value.getTime()));
            }else {
                jgen.writeString( DateUtil.format(value, jsonSerializeProperties.getDateForm()));
            }

        }
    }
}
