package com.zsc.springboot.redis;

import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author shichen
 * @create 2018-12-07
 * @desc
 */
@Component
@Slf4j
public class RedisRefreshConfig{

    @Autowired
    private RedisClient redisClient;

    @Autowired(required = false)
    private RefreshScope refreshScope;

    @PostConstruct
    public void init() {
        redisClient.set("test.redis.refresh1", "1");
    }

    /**
     * 监听redis配置变化
     * @param configChangeEvent
     */
    @ApolloConfigChangeListener
    public void redisConfigChangeListener(ConfigChangeEvent configChangeEvent) {
        boolean redisCacheKeysChanged = false;
        Set<String> keys = configChangeEvent.changedKeys();
        for (String key : keys) {
            if (key.startsWith("redis.")) {
                redisCacheKeysChanged = true;
                break;
            }
        }

        if (!redisCacheKeysChanged) {
            return;
        }

        log.info("【redis配置变化】before {}", redisClient);
        refreshScope.refresh("redisClient");
        String value = redisClient.get("test.redis.refresh1");
        log.info("【redis配置变化】after {}, value {}", redisClient, value);
    }

}
