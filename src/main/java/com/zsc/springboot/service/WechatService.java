package com.zsc.springboot.service;

import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.ctrip.framework.apollo.spring.annotation.ApolloJsonValue;
import com.zsc.springboot.service.wechat.WechatConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author shichen
 * @create 2018-12-07
 * @desc
 */
@Slf4j
@Service
@PropertySource("classpath:wechat.properties")
public class WechatService {

    @Value("${wechat.appid}")
    private String wechatAppid;

    @Value("${wechat.secret}")
    private String wechatSecret;

    @Value("${wechat.path:http://127.0.0.1:8181}")
    private String wechatPath;

    @ApolloJsonValue("${wechat.config}")
    private WechatConfig wechatConfig;

    /**
     * 配置变化监听器
     *
     * @param configChangeEvent
     */
    @ApolloConfigChangeListener(interestedKeys = {"wechat.appid", "wechat.secret", "wechat.path", "wechat.config"})
    public void wechatOnChangeListener(ConfigChangeEvent configChangeEvent) {
        Set<String> changeKeys = configChangeEvent.changedKeys();
        changeKeys.stream().forEach(key -> {
            ConfigChange configChange = configChangeEvent.getChange(key);
            log.info("【配置变更监听】namespace {}, key {}, old value {}, new value {}, change type {}", configChange.getNamespace(), key, configChange.getOldValue(), configChange.getNewValue(), configChange.getChangeType());
        });
    }
}
