
## 本项目为学习各种第三方框架与springboot结合使用

* **演示了springboot和apollo结合使用**
    * 使用[apollo](https://github.com/ctripcorp/apollo)实现了刷新redis配置，并重新初始化RedisClient
    * 使用apollo实现了实时刷新配置参数，并使用@ApolloConfigChangeListener实现对配置变化的监控，使用@ApolloJsonValue实现复杂配置的动态变更
    * **更多apollo的使用可以借鉴** [apollo-use-cases](https://github.com/ctripcorp/apollo-use-cases)
* **演示了springboot和xxl-job结合使用**
    * xxl-job与elastic-job实现不同
        * [elastic-job](https://github.com/elasticjob/elastic-job-lite)是轻量级无中心化的分布式定时任务解决方案，依赖于zookeeper实现服务注册，分布式调度，主节点选举等功能
        * [xxl-job](https://github.com/xuxueli/xxl-job)是有中心化的分布式定时任务解决方案，依赖于quartz原生集群支持，实现任务调度，将调度与执行器分离
    
* **演示spring使用DeferredResult实现长轮询**
    * 实时通知解决方案