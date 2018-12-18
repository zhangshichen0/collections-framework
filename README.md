
## 本项目为学习各种第三方框架与springboot结合使用

* **演示了springboot和apollo结合使用**
    * 使用[apollo](https://github.com/ctripcorp/apollo)实现了刷新redis配置，并重新初始化RedisClient
    * 使用apollo实现了实时刷新配置参数，并使用@ApolloConfigChangeListener实现对配置变化的监控，使用@ApolloJsonValue实现复杂配置的动态变更
    * **更多apollo的使用可以借鉴** [apollo-use-cases](https://github.com/ctripcorp/apollo-use-cases)
* **演示了springboot和xxl-job结合使用**
    * xxl-job与elastic-job实现不同
        * [elastic-job](https://github.com/elasticjob/elastic-job-lite)是轻量级无中心化的分布式定时任务解决方案，依赖于zookeeper实现服务注册，分布式调度，主节点选举等功能
        * [xxl-job](https://github.com/xuxueli/xxl-job)是有中心化的分布式定时任务解决方案，依赖于quartz原生集群支持，实现任务调度，将调度与执行器分离，调度器和执行器之间使用rpc方式调用
    
* **演示spring使用[DeferredResult](https://www.cnblogs.com/shihaiming/p/5856477.html)实现长轮询**
    * 实时通知解决方案
        * 即使用长轮询方式实现
    * 耗时请求异步处理--返回结果依赖于异步处理的结果
        * 能够及时的释放Servlet容器线程，让其处理更多的请求
        
* **集成[swagger2](https://github.com/springfox/springfox)作为api测试工具，替代postman**
    * swagger2能够自动扫描指定包下的api，生成api文档
    * 项目中指定，只有激活的profile中包含swagger时，才加载swagger2资源文件并初始化Swagger2Config Bean
    * [swagger2注解说明](https://github.com/swagger-api/swagger-core/wiki/Annotations)
    * **问题**
        * 本项目集成为spring-boot2.1.0RELEASE + swagger2.9.2,但是spring-boot2.*以上，只能实现WebMvcConfigurer并实现方法addResourceHandlers才能够加载swagger2资源文件😓，解决方案：[springboot2.x以上集成swagger2出现404解决](https://www.jianshu.com/p/04dd5ff82dad)
    
* **集成[cat(Central Application Trace)](https://github.com/dianping/cat)作为日志收集工具，便于排查问题**    
    * cat在没有cat服务端的情况下，并不影响服务的正常运行
    * cat是由大众点评开源，用来记录并分析日志生成报表，便于追查问题的工具
    
* **利用[mybatis-plus](https://mybatis.plus/)继承[mybatis](http://www.mybatis.org/mybatis-3/zh/index.html)**
    * **CodeGenerator.java**为mybatis-plus提供的代码生成器，能够按照用户指定策略将文件生成到指定包下
    
* **集成[sharding-jdbc](https://github.com/sharding-sphere/sharding-sphere)实现分库分表+读写分离**
    * 虽然sharding-jdbc提供了与spring-boot的集成包，但是并非官方提供，切配置混乱，故采用java config的方式进行配置，既能了解其组件间的逻辑关系，又能清楚的了解配置