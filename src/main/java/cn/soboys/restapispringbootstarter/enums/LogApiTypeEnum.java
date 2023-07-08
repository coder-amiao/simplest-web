package cn.soboys.restapispringbootstarter.enums;

import lombok.Getter;

@Getter
public enum LogApiTypeEnum {

    USER("用户"),
    SYSTEM("系统");

    private String describe;

    LogApiTypeEnum(String describe){
        this.describe = describe;
    }
}