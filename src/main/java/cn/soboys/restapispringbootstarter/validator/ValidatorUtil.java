package cn.soboys.restapispringbootstarter.validator;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kenx
 * @version 1.0
 * @date 2021/1/21 20:51
 * 验证表达式
 */
public class ValidatorUtil {
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");
    private static final Pattern money_pattern = Pattern.compile("^[0-9]+\\.?[0-9]{0,2}$");

    /**
     * 验证手机号
     *
     * @param src
     * @return
     */
    public static boolean isMobile(String src) {
        if (StrUtil.isBlank(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }


    /**
     * 验证枚举值是否合法 ，所有枚举需要继承此方法重写
     *
     * @param beanClass 枚举类
     * @param status    对应code
     * @return
     * @throws Exception
     */
    public static boolean isEnum(Class<?> beanClass, String status) throws Exception {
        if (StrUtil.isBlank(status)) {
            return false;
        }

        //转换枚举类
        Class<Enum> clazz = (Class<Enum>) beanClass;
        /**
         * 其实枚举是语法糖
         * 是封装好的多个Enum类的实列
         * 获取所有枚举实例
         */
        Enum[] enumConstants = clazz.getEnumConstants();

        //根据方法名获取方法
        Method getCode = clazz.getMethod("getCode");
        Method getDesc = clazz.getMethod("getDesc");
        for (Enum enums : enumConstants) {
            //得到枚举实例名
            String instance = enums.name();
            //执行枚举方法获得枚举实例对应的值
            String code = getCode.invoke(enums).toString();
            if (code.equals(status)) {
                return true;
            }
            String desc = getDesc.invoke(enums).toString();
            System.out.println(StrFormatter.format("实列{}---code:{}desc{}", instance, code, desc));
        }
        return false;
    }

    /**
     * 验证金额0.00
     *
     * @param money
     * @return
     */
    public static boolean isMoney(BigDecimal money) {
        if (StrUtil.isEmptyIfStr(money)) {
            return false;
        }
        if (!NumberUtil.isNumber(String.valueOf(money.doubleValue()))) {
            return false;
        }
        if (money.doubleValue() == 0) {
            return false;
        }
        Matcher m = money_pattern.matcher(String.valueOf(money.doubleValue()));
        return m.matches();
    }


    /**
     * 验证 日期
     *
     * @param date
     * @param dateFormat
     * @return
     */
    public static boolean isDateTime(String date, String dateFormat) {
        if (StrUtil.isBlank(date)) {
            return false;
        }
        try {
            DateUtil.parse(date, dateFormat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
