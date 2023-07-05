package cn.soboys.restapispringbootstarter;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 22:10
 * @webSite https://github.com/coder-amiao
 */
@Data
public class EntityParam {
    @NotBlank
    private String name;
    @NotBlank
    private String hobby;
    @NotNull
    private Integer age;
}
