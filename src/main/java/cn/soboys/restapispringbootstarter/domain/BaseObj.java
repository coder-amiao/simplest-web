package cn.soboys.restapispringbootstarter.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class BaseObj implements Serializable {


    private static final long serialVersionUID = 5851377280115218282L;
}
