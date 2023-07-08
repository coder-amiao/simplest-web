package cn.soboys.restapispringbootstarter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/7/8 22:46
 * @webSite https://github.com/coder-amiao
 * 自定义发挥响应结果
 */
@Slf4j
@Data
public class ResultPage<T>  extends Result {
    /**
     * 当前页
     */
    private Integer previousPage=1;
    /**
     * 下一页
     */
    private  Integer nextPage=1;

    /**
     * 每一页显示条数
     */
    private Integer pageSize=1;

    /**
     * 总条数
     */
    private Integer totalPageSize=1;


    /**
     * 是否有下一页
     */
    private  String hasNext="false";


    private T pageData;

}
