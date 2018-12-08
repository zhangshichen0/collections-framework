
## 本项目为学习各种第三方框架与springboot结合使用

* **演示了springboot和apollo结合使用**
    * 使用apollo实现了刷新redis配置，并重新初始化RedisClient
    * 使用apollo实现了实时刷新配置参数，并使用@ApolloConfigChangeListener实现对配置变化的监控，使用@ApolloJsonValue实现复杂配置的动态变更
    * **更多apollo的使用可以借鉴** [apollo-use-cases](https://github.com/ctripcorp/apollo-use-cases)
* **演示了springboot和xxl-job结合使用**
    *
* **演示spring使用DeferredResult实现长轮询**
    *