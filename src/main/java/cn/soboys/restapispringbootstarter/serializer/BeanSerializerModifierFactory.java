package cn.soboys.restapispringbootstarter.serializer;

import cn.soboys.restapispringbootstarter.config.RestApiProperties;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/22 16:12
 * @webSite https://github.com/coder-amiao
 */
@EnableConfigurationProperties(RestApiProperties.JsonSerializeProperties.class) //开启属性绑定
public class BeanSerializerModifierFactory extends BeanSerializerModifier {

    @Autowired
    private RestApiProperties.JsonSerializeProperties jsonSerializeProperties;

    @Override
    public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
        if (!jsonSerializeProperties.getNullAble().getHasNullAble()) return beanProperties; //不开启空序列化
        for (int i = 0; i < beanProperties.size(); i++) {
            BeanPropertyWriter writer = (BeanPropertyWriter) beanProperties.get(i);
            SerializableType type = new SerializableType();
            RestApiProperties.NullAble nullAble = jsonSerializeProperties.getNullAble();
            // 判断字段的类型，如果是array，list，set则注册nullSerializer
            if (isArrayType(writer)) { //给writer注册一个自己的nullSerializer
                type.setType(nullAble.getArrayType());

            } else if (isIntegerType(writer)) {
                type.setType(nullAble.getNumberType());

            } else if (isStringType(writer)) {
                type.setType(nullAble.getObjectType());
            } else if (isDoubleType(writer)) {
                type.setType(nullAble.getDoubleType());
            }
            if (StrUtil.isNotEmpty(type.getType()) && !type.getType().equals("original")) {
                NullAbleSerializer nullAbleSerializer = new NullAbleSerializer();
                nullAbleSerializer.setSerializableType(type);
                writer.assignNullSerializer(nullAbleSerializer);
            }
        }
        return beanProperties;
    }

    protected boolean isArrayType(BeanPropertyWriter writer) {
        Class clazz = writer.getPropertyType();
        return clazz.isArray() || clazz.equals(List.class) || clazz.equals(Set.class);
    }

    protected boolean isStringType(BeanPropertyWriter writer) {
        Class clazz = writer.getPropertyType();
        return clazz.equals(String.class) || clazz.equals(Date.class);
    }

    protected boolean isIntegerType(BeanPropertyWriter writer) {
        Class clazz = writer.getPropertyType();
        return clazz.equals(Integer.class) || clazz.equals(int.class) || clazz.equals(Long.class) || clazz.equals(long.class);
    }

    protected boolean isDoubleType(BeanPropertyWriter writer) {
        Class clazz = writer.getPropertyType();
        return clazz.equals(Double.class) || clazz.equals(BigDecimal.class) || clazz.equals(Float.class);
    }

}
