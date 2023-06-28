package cn.soboys.restapispringbootstarter.serializer;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/4/29 23:19
 * @webSite https://github.com/coder-amiao
 * 自定义对Double 类型json数据序列化返回
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.DecimalFormat;

public class DoubleValueSerializer extends JsonSerializer<Double> {

    /**
     * 科学计算三位逗号分割显示,四舍五入保留2位小数
     */
    private final DecimalFormat format = new DecimalFormat("###,###.##");

    @Override
    public void serialize(Double value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        if (value != null) {
            jgen.writeString(format.format(value));
        }
    }
}
