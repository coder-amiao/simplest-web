<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**  *generated with [DocToc](https://github.com/thlorenz/doctoc)*

- [RestFull API使用](#restfull-api%E4%BD%BF%E7%94%A8)
- [全局错误拦截，参数校验](#%E5%85%A8%E5%B1%80%E9%94%99%E8%AF%AF%E6%8B%A6%E6%88%AA%E5%8F%82%E6%95%B0%E6%A0%A1%E9%AA%8C)
- [错误国际化](#%E9%94%99%E8%AF%AF%E5%9B%BD%E9%99%85%E5%8C%96)
- [自定义错误响应](#%E8%87%AA%E5%AE%9A%E4%B9%89%E9%94%99%E8%AF%AF%E5%93%8D%E5%BA%94)
- [业务异常断言](#%E4%B8%9A%E5%8A%A1%E5%BC%82%E5%B8%B8%E6%96%AD%E8%A8%80)
- [Redis 工具库使用](#redis-%E5%B7%A5%E5%85%B7%E5%BA%93%E4%BD%BF%E7%94%A8)
- [RestTemplate 请求工具](#resttemplate-%E8%AF%B7%E6%B1%82%E5%B7%A5%E5%85%B7)
- [日志使用](#%E6%97%A5%E5%BF%97%E4%BD%BF%E7%94%A8)
- [集成mybatisPlus一键代码生成](#%E9%9B%86%E6%88%90mybatisplus%E4%B8%80%E9%94%AE%E4%BB%A3%E7%A0%81%E7%94%9F%E6%88%90)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

之前我们已经，出了一些列文章。 讲解如何封统一全局响应Restful API。

感兴趣的可以看我前面几篇文章 (整个starter项目发展史) 文章都在公众号 程序员三时 上面。

[SpringBoot定义优雅全局统一Restful API 响应框架](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483741&idx=1&sn=2734d2ef008edcf1369dd7a31a88a142&chksm=cfe5f27bf8927b6d468a411fe2eaeeeb6dbdd5527dec77a77a0e580ea32b2b58f38922836552#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架二](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483752&idx=1&sn=eab94282e3f1e62682d2106cfb2949d1&chksm=cfe5f24ef8927b582e01863881a88452dcbcb102afdb9b50985304b97dfd74cd0c3ed0b8c2c3#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架三](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483761&idx=1&sn=dbc516d0ba14228c1f091dfa39d85209&chksm=cfe5f257f8927b41635fe1508c2829d73e2b788105b145bc51525be4574b83a1a8da23c49ec2#rd)


[SpringBoot定义优雅全局统一Restful API 响应框架四](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483887&idx=1&sn=cb737d573adca7eaea7a59cad2a7bbfe&chksm=cfe5f2c9f8927bdfaf7e46fd38d26f56fffbb1ece8460eaffe2addee592e42ffee6ba49530b7#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架五](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247484102&idx=1&sn=e17772a12e6548755c341c2d9300e235&chksm=cfe5f1e0f89278f669c4c89548b3fdeec4dd58d6d7d7580315d07f20c9b52ffdd21ef5a7c232&token=691863430&lang=zh_CN#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架六](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247484160&idx=1&sn=37eea0079dd175634437f01dde38bb4c&chksm=cfe5f026f892793045fb242a556ce4e3010f4a4aae867a84fe3542ed18ac3c64b85e87fa536a#rd)


后续我萌生里新的想法，SpringBoot 不是提供了自己的starter。我们也可以自定义**starter**吗，于是我定义了**rest-api-spring-boot-starter**，已经发布到maven中央仓库，对之前Restful API 响应框架 做了集成和重构，

在这个基础上我又总结封装了我自己工作以常用的很多工具，结合SpringBoot 封装了全能的工具。 已经更新到了1.2.0 不耦合任何依赖

![](https://images.soboys.cn/202307032138181.png)

目前更新版本1.2.0 功能如下
1. 支持一键配置自定义RestFull API 统一格式返回
2. 支持RestFull API 错误国际化
3. 支持全局异常处理，全局参数验证处理
4. 业务错误断言工具封装，遵循错误优先返回原则
5. redis工作封装。支持所有key操作工具
6. RestTemplate 封装 POST,GET 请求工具
7. 日志集成。自定义日志路径，按照日志等级分类，支持压缩和文件大小分割。按时间显示
8. 工具库集成 集成了lombok，hutool，commons-lang3，guava。不需要自己单个引入
9. 集成mybatisPlus一键代码生成

[github 地址](https://github.com/coder-amiao/rest-api-spring-boot-starter)

下面我讲一下怎么在项目中去使用

我们新建一个SpringBoot Web项目

我们只需要在pom中引入即可
```xml
 <dependency>
            <groupId>cn.soboys</groupId>
            <artifactId>rest-api-spring-boot-starter</artifactId>
            <version>1.2.0</version>
        </dependency>
```

在启动类或者配置类中加上` @EnableRestFullApi` 注解即可
![](https://images.soboys.cn/202307050228893.png)


# RestFull API使用

这样在项目controller中我们写普通的请求如:
```java
 @PostMapping("/chat")
    public HashMap chatDialogue() {
        HashMap m = new HashMap();
        m.put("age", 26);
        m.put("name", "Judy");
        return m;
    }
```
返回的就是全局统一RestFull API

![](https://images.soboys.cn/202307032213908.png)

我们也可以这么写

![](https://images.soboys.cn/202307032217629.png)

提供了很多返回方法。

当然如果你这个接口不想包装成全局返回，想自定义单独返回 如我们只需要在方法上加上`@NoRestFulApi` 注解即可
```java
   @PostMapping("/chat")
    @NoRestFulApi
    public HashMap chatDialogue() {
        HashMap m = new HashMap();
        m.put("age", 26);
        m.put("name", "Judy");
        return m;
    }
```
就不会对返回内容进行任何包装处理。
![](https://images.soboys.cn/202307032222002.png)

# 全局错误拦截，参数校验

帮你封装好了所有http常见错误，和所有请求类型和参数错误。

如请求错误
```java
{
    "success": false,
    "code": "405",
    "msg": "方法不被允许",
    "timestamp": "2023-07-03 22:36:47",
    "data": "Request method 'GET' not supported"
}
```
请求资源不存在
```java
{
    "success": false,
    "code": "404",
    "msg": "请求资源不存在",
    "timestamp": "2023-07-03 22:42:35",
    "data": "/api"
}
```
参数校验错误

验证Studen对象参数
```java
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
```


```JAVA
    @PostMapping("/chat")
    public HashMap chatDialogue(@Validated  Student student) {
        HashMap m = new HashMap();
        m.put("age", 26);
        m.put("name", "Judy");
        return m;
    }
```

请求结果
![](https://images.soboys.cn/202307032241499.png)

JSON Body参数

```java
    @PostMapping("/chat")
    public HashMap chatDialogue(@RequestBody @Validated  Student student) {
        HashMap m = new HashMap();
        m.put("age", 26);
        m.put("name", "Judy");
        return m;
    }
```
![](https://images.soboys.cn/202307032242703.png)

![](https://images.soboys.cn/202307032251907.png)

# 错误国际化
内置封装错误默认支持英文和中文两种国际化。你不做任何配置自动支持

如果需要内置支持更多语言，覆盖即可。

自定义自己错误国际化和语言
```yml
  i18n:
    # 若前端无header传参则返回中文信息
    i18n-header: Lang
    default-lang: cn
    message:
      # admin
      internal_server_error:
        en: Internal Server Error
        cn: 系统错误
      not_found:
        en: Not Found
        cn: 请求资源不存在
```
message 对应错误提示
对应internal_server_error 自定义
下面语言自己定义 和前端传入i18n-header 对应上，就显你定义错误语言

我不传错误国际化默认就是中文在 **default-lang: cn**
进行配置

![](https://images.soboys.cn/202307040014221.png)

当我传入 指定语言 就会按照你配置的国际化自定义返回错误提示

![](https://images.soboys.cn/202307040016178.png)



# 自定义错误响应
如果我内置错误无法满足你业务需求，你也可以自定义自己错误码

你自定义错误枚举 只需要实现**ResultCode**接口即可

```java
package cn.soboys.restapispringbootstarter;

import cn.soboys.restapispringbootstarter.i18n.I18NKey;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 10:21
 * @webSite https://github.com/coder-amiao
 * 响应码接口，自定义响应码，实现此接口
 */
public interface ResultCode extends I18NKey {

    String getCode();

    String getMessage();

}
````

如果要支持国际化还需要实现国际化接口**I18NKey** 参考我内部**HttpStatus**实现即可

```java
package cn.soboys.restapispringbootstarter;

import cn.soboys.restapispringbootstarter.i18n.I18NKey;

/**
 * @author 公众号 程序员三时
 * @version 1.0
 * @date 2023/6/26 11:01
 * @webSite https://github.com/coder-amiao
 */
public enum HttpStatus implements ResultCode, I18NKey {
    /**
     * 系统内部错误
     */
    INTERNAL_SERVER_ERROR("500", "internal_server_error"),
    BAD_GATEWAY("502", "bad_gateway"),
    NOT_FOUND("404", "not_found"),
    UNAUTHORIZED("401", "unauthorized"),
    FORBIDDEN("403", "forbidden"),
    METHOD_NOT_ALLOWED("405", "method_not_allowed"),
    REQUEST_TIMEOUT("408", "request_timeout"),

    INVALID_ARGUMENT("10000", "invalid_argument"),
    ARGUMENT_ANALYZE("10001", "argument_analyze"),
    BUSINESS_EXCEPTION("20000", "business_exception");


    private final String value;

    private final String message;

    HttpStatus(String value, String message) {
        this.value = value;
        this.message = message;
    }


    @Override
    public String getCode() {
        return value;
    }

    @Override
    public String getMessage() {
        return message;
    }


    @Override
    public String key() {
        return message;
    }
}


```

```yml
rest-api:
  enabled: false
  i18n:
    # 若前端无header传参则返回中文信息
    i18n-header: Lang
    default-lang: cn
    message:
      # admin
      internal_server_error:
        en: Internal Server Error
        cn: 系统错误
      bad_gateway:
        en: Bad Gateway
        cn: 错误的请求
      unauthorized:
        en: Unauthorized
        cn: 未授权
      forbidden:
        en: Forbidden
        cn: 资源禁止访问
      method_not_allowed:
        en: Method Not Allowed
        cn: 方法不被允许
      request_timeout:
        en: Request Timeout
        cn: 请求超时
      invalid_argument:
        en: Invalid Argument {}
        cn: 参数错误 {}
      argument_analyze:
        en: Argument Analyze {}
        cn: 参数解析异常 {}
      business_exception:
        en: Business Exception
        cn: 业务错误
      not_found:
        en: Not Found
        cn: 请求资源不存在

```

![](https://images.soboys.cn/202307040127708.png)

![](https://images.soboys.cn/202307040127574.png)

![](https://images.soboys.cn/202307042336110.png)

![](https://images.soboys.cn/202307042337644.png)

内部错误不需要做任何配置，自动支持国际化。如果需要支持更多语言，可以自定义进行覆盖。

# 业务异常断言

在项目开发中我们有时需要封装自己异常类，信息我封装了统一的错误异常类。
**BusinessException** 对业务异常类做了全局错误拦截，

封装·了统一业务异常断言工具，遵循错误优先返回原则。代码更优雅

![](https://images.soboys.cn/202307040915661.png)

```java
    @GetMapping("/exception")
    public Result exception(){
        Student s=null;
        Assert.isFalse(s==null,"学生不能为空");
        return Result.buildSuccess();
    }
```
抛出统一业务异常


![](https://images.soboys.cn/202307040920873.png)

当然如果你要定义自己的异常类。可以定义自己异常类·继承我的**BusinessException**


# Redis 工具库使用
进一步封装的对**Redis**所以相关key，value操作，在使用redis工具库时候。我们需要引入
```pom
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
```

默认不会帮你引入。

然后在使用时候注入就行
````
@Autowired
    private RedisTempUtil redisTempUtil;
````

![](https://images.soboys.cn/202307040939790.png)

```java
    @GetMapping("/redis")
    public Result redis() {
        redisTempUtil.set("test", "123456");
        return Result.buildSuccess();
    }


    @GetMapping("/redis/get")
    public Result redisGet() {
        String value = redisTempUtil.get("test").toString();
        log.info("redis值{}", value);
        return Result.buildSuccess();
    }
```
# RestTemplate 请求工具
进一步封装了RestTemplate请求 Post和GET
项目中使用时注入
```java
 @Autowired
    private RestFulTemp restFulTemp;
```

![](https://images.soboys.cn/202307040957101.png)

```java
    @GetMapping("/doGet")
    public Result doGet() {
        ResponseEntity<String> response = restFulTemp.doGet("http://127.0.0.1:8000/redis/get");
        return Result.buildSuccess(response.getBody());
    }
```

# 日志使用
进一步封装了 日志处理达到开箱即用。在属性文件中配置相关日志配置即可
```yml
rest-api:
  enabled: false
  logging:
    path: ./logs   #日志存储路径（服务器上绝对）
    max-history: 90 # 保存多少天
    max-file-size: 3MB  # 每个文件大小
    max-total-size-cap: 1GB  #总文件大小超过多少压缩
    level-root: INFO    # 这里的INFO可以替换为其他日志等级，如DEBUG, WARN, ERROR, TRACE, FATAL, OFF等。 日志等级由低到高分别是debugger-info-warn-error
```

如果你的属性文件不做任何日志配置，默认日志就是上面这样配置。

# 集成mybatisPlus一键代码生成
在项目中我们会频繁使用到mybatisPlus 但是简单的模板代码我们一键生成就好。 默认不依赖mybatisPlus任何相关包。如果需要使用自动代码生成引入mybatisPlus 代码生成依赖包即可。

```pom
 <!--生成器依赖-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.4.1</version>
            <optional>true</optional>
        </dependency>
        <!-- MySQL -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.28</version>
            <optional>true</optional>
        </dependency>
        <!--代码生成依赖的模板引擎-->
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.31</version>
            <optional>true</optional>
        </dependency>
```
你可以直接写测试类，然后直接去调用代码生成即可

```java
public class Test {
    public static void main(String[] args) {
        GenerateCodeConfig config=new GenerateCodeConfig();
        config.setDriverName("com.mysql.cj.jdbc.Driver");
        config.setUsername("root");
        config.setPassword("root");
        config.setUrl("jdbc:mysql://127.0.0.1:3306/ry?useUnicode=true&useSSL=false&characterEncoding=utf8");
        //生成代码保存路径，不设置就是当前项目下路径，如何设置请使用绝对路径
        config.setProjectPath("superaide");
        config.setPackages("cn.soboys.superaide");
        MyBatisPlusGenerator.generate(config);
    }
}

```

效果如下
![](https://images.soboys.cn/202307050049316.png)


















