package cn.soboys.restapispringbootstarter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 22:10
 * @webSite https://github.com/coder-amiao
 */
@Data
public class Student {
    @NotBlank
    private String nam;
    @NotBlank
    private String hobby;
}
