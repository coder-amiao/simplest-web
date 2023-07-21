package cn.soboys.restapispringbootstarter.test;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/21 18:06
 * @webSite https://github.com/coder-amiao
 */
@Data
@AllArgsConstructor
public class Entity {
    private Date createTime;
    private BigDecimal price;
    private Double sku;
}
