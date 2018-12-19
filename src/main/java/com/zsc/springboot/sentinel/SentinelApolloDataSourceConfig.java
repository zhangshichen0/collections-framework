package com.zsc.springboot.sentinel;

import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.apollo.ApolloDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 能够动态配置限流策略 -- 依赖于 apollo配置中心
 *
 * @author shichen
 * @create 2018-12-19
 * @desc
 */
@Component
public class SentinelApolloDataSourceConfig {

    @PostConstruct
    public void loadRules() {
        String namespaceName = "application";
        String flowRuleKey = "flowRules";
        String defaultFlowRules = "[]";
        ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ApolloDataSource<>(namespaceName,
                flowRuleKey, defaultFlowRules, source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
        }));
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
    }

}
