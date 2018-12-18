package com.zsc.springboot.utils;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author shichen
 * @create 2018-12-18
 * @desc
 */
@Slf4j
public final class DataSourceUtils {

    /**
     * 创建数据源
     *
     * @return
     */
    public static DataSource createDataSource(Environment env, String url, String userName, String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setMaxActive(env.getProperty("datasource.maxActive", Integer.class));
        dataSource.setInitialSize(env.getProperty("datasource.initialSize", Integer.class));
        dataSource.setMaxWait(env.getProperty("datasource.maxWaitMillis", Integer.class));
        dataSource.setMinIdle(env.getProperty("datasource.minIdle", Integer.class));
        dataSource.setTimeBetweenEvictionRunsMillis(env.getProperty("datasource.timeBetweenEvictionRunsMillis", Integer.class));
        dataSource.setMinEvictableIdleTimeMillis(env.getProperty("datasource.minEvictableIdleTimeMillis", Integer.class));
        dataSource.setValidationQuery(env.getProperty("datasource.validationQuery"));
        dataSource.setTestWhileIdle(env.getProperty("datasource.testWhileIdle", Boolean.class));
        dataSource.setTestOnBorrow(env.getProperty("datasource.testOnBorrow", Boolean.class));
        dataSource.setTestOnReturn(env.getProperty("datasource.testOnReturn", Boolean.class));

        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        try {
            dataSource.setFilters("wall,stat,slf4j");
        } catch (SQLException e) {
            log.error("【初始化数据源】init datasource error", e);
        }

        return dataSource;
    }

}
