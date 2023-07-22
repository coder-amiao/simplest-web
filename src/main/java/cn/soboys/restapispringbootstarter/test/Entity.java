package cn.soboys.restapispringbootstarter.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/21 18:06
 * @webSite https://github.com/coder-amiao
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Entity {
    private LocalDateTime createTime;
    private BigDecimal price;
    private Double sku;
    private List t;
    private Integer age;
    private String hobby;
}
