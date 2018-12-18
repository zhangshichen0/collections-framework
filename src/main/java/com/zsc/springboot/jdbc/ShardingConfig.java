package com.zsc.springboot.jdbc;

import com.google.common.collect.Lists;
import com.zsc.springboot.utils.DataSourceUtils;
import io.shardingsphere.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.api.config.ShardingRuleConfiguration;
import io.shardingsphere.core.constant.properties.ShardingPropertiesConstant;
import io.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author shichen
 * @create 2018-12-18
 * @desc
 */
@Slf4j
@Configuration
@PropertySource("classpath:application-sharding.properties")
public class ShardingConfig {

    @Autowired
    private Environment environment;

    /**
     * 创建sharding-jdbc数据源
     *
     * @return
     * @throws SQLException
     */
    @Bean("shardingDataSource")
    public DataSource shardingDataSource() throws SQLException {
        try {
            ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
            shardingRuleConfiguration.setMasterSlaveRuleConfigs(this.getMasterSlaveRuleConfigurations(environment));
            Properties properties = new Properties();
            properties.put(ShardingPropertiesConstant.SQL_SHOW.getKey(), true);
            return ShardingDataSourceFactory.createDataSource(this.createDataSourceMap(), shardingRuleConfiguration, Collections.emptyMap(), properties);
        } catch (SQLException e) {
            log.error("【初始化数据源】init datasource error", e);
            throw e;
        }
    }

    /**
     * 读写分离配置
     *
     * @return
     */
    private List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations(Environment env) {
        String master0 = env.getProperty("sharding.jdbc.datasource.master0.name");
        String master0Slaves = env.getProperty("sharding.jdbc.datasource.master0slaves.names");
        MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration("ds_0", master0, Arrays.asList(master0Slaves.split(",")));
        masterSlaveRuleConfig1.setLoadBalanceAlgorithm(MasterSlaveLoadBalanceAlgorithmType.ROUND_ROBIN.getAlgorithm());
        return Lists.newArrayList(masterSlaveRuleConfig1);
    }

    /**
     * 创建数据源map
     *
     * @return
     */
    private Map<String, DataSource> createDataSourceMap() {
        final Map<String, DataSource> result = new HashMap<>();
        String master0 = environment.getProperty("sharding.jdbc.datasource.master0.name");
        String master0Slaves = environment.getProperty("sharding.jdbc.datasource.master0slaves.names");
        result.put(master0, createDataSource(environment, master0));
        Arrays.stream(master0Slaves.split(",")).forEach(slave -> {
            result.put(slave, createDataSource(environment, slave));
        });
        return result;
    }

    /**
     *
     * @return
     */
    private static DataSource createDataSource(Environment env, String dataSourceName) {
        String url = env.getProperty("sharding.jdbc.datasource." + dataSourceName + ".url");
        String userName = env.getProperty("sharding.jdbc.datasource." + dataSourceName + ".username");
        String password = env.getProperty("sharding.jdbc.datasource." + dataSourceName + ".password");
        return DataSourceUtils.createDataSource(env, url, userName, password);
    }

    /**
     * 声明事务管理
     *
     * @param ds
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("shardingDataSource") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

}
