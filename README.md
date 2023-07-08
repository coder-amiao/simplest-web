基于SpringBoot Web 快速构建API脚手架 解决重复繁琐工作 高效开发SpringBoot Web API 接口

之前我们已经，出了一些列文章。 讲解如何封统一全局响应Restful API。

感兴趣的可以看我前面几篇文章 (整个starter项目发展史) 文章都在公众号 程序员三时 上面。

[SpringBoot定义优雅全局统一Restful API 响应框架](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483741&idx=1&sn=2734d2ef008edcf1369dd7a31a88a142&chksm=cfe5f27bf8927b6d468a411fe2eaeeeb6dbdd5527dec77a77a0e580ea32b2b58f38922836552#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架二](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483752&idx=1&sn=eab94282e3f1e62682d2106cfb2949d1&chksm=cfe5f24ef8927b582e01863881a88452dcbcb102afdb9b50985304b97dfd74cd0c3ed0b8c2c3#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架三](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483761&idx=1&sn=dbc516d0ba14228c1f091dfa39d85209&chksm=cfe5f257f8927b41635fe1508c2829d73e2b788105b145bc51525be4574b83a1a8da23c49ec2#rd)


[SpringBoot定义优雅全局统一Restful API 响应框架四](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247483887&idx=1&sn=cb737d573adca7eaea7a59cad2a7bbfe&chksm=cfe5f2c9f8927bdfaf7e46fd38d26f56fffbb1ece8460eaffe2addee592e42ffee6ba49530b7#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架五](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247484102&idx=1&sn=e17772a12e6548755c341c2d9300e235&chksm=cfe5f1e0f89278f669c4c89548b3fdeec4dd58d6d7d7580315d07f20c9b52ffdd21ef5a7c232&token=691863430&lang=zh_CN#rd)

[SpringBoot定义优雅全局统一Restful API 响应框架六](https://mp.weixin.qq.com/s?__biz=Mzg4OTkwNjc2MQ==&mid=2247484160&idx=1&sn=37eea0079dd175634437f01dde38bb4c&chksm=cfe5f026f892793045fb242a556ce4e3010f4a4aae867a84fe3542ed18ac3c64b85e87fa536a#rd)


后续我萌生里新的想法，SpringBoot 不是提供了自己的starter。我们也可以自定义**starter**吗，于是我定义了**rest-api-spring-boot-starter**，已经发布到maven中央仓库，对之前Restful API 响应框架 做了集成和重构，

在这个基础上我又总结封装了我自己工作常用的很多工具，结合SpringBoot 封装了全能的工具。 已经更新到了1.3.0 不耦合任何依赖

![](https://images.soboys.cn/202307051540748.png)

目前更新版本到1.3.0 支持功能如下
1. 支持一键配置自定义RestFull API 统一格式返回
2. 支持RestFull API 错误国际化
3. 支持全局异常处理，全局参数验证处理
4. 业务错误断言工具封装，遵循错误优先返回原则
5. 封装Redis key,value 操作工具类。统一key管理 spring cache缓存实现
6. RestTemplate 封装 POST,GET 请求工具
7. 日志集成。自定义日志路径，按照日志等级分类，支持压缩和文件大小分割。按时间显示
8. 工具库集成 集成了lombok，hutool，commons-lang3，guava。不需要自己单个引入
9. 集成mybatisPlus一键代码生成
10. 日志记录，服务监控，支持日志链路查询。自定义数据源


[详细使用文档官网](https://rest-api-boot.soboys.cn/doc-rest-api-springboot-starter/)

在使用过程中尽量使用最新版本。我会持续更新更多的内容。 会第一时间发布在我的公众号
程序员三时。 全网同名


可以关注 公众号 程序员三时。用心分享持续输出优质内容。


![](https://images.soboys.cn/202307052317593.jpg)















