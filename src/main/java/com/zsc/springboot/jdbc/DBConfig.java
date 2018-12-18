package com.zsc.springboot.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author shichen
 * @create 2018-12-14
 * @desc
 */
@Slf4j
//@Component
//@PropertySource("classpath:application-db.properties")
public class DBConfig {

    @Autowired
    private Environment env;

    /**
     * 定义mysql模板路径
     */
    private static final String MY_SQL_URL_TEMPLATE = "jdbc:mysql://%s/%s?useUnicode=true&characterEncoding=utf-8&useSSL=true";

    /**
     * 主库数据源
     *
     * @return
     */
    @Primary
    @Bean(name = "masterDb")
    public DataSource master() {
        String host = env.getProperty("datasource.master.host");
        String schema = env.getProperty("datasource.schema");
        String userName = env.getProperty("datasource.username");
        String password = env.getProperty("datasource.password");
        return createDataSource(host, schema, userName, password, env);
    }

    /**
     * 创建数据源
     *
     * @param host
     * @param schema
     * @param userName
     * @param password
     * @param env
     * @return
     */
    public static DataSource createDataSource(String host, String schema, String userName, String password, Environment env) {
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

        dataSource.setUrl(String.format(MY_SQL_URL_TEMPLATE, host, schema));
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        try {
            dataSource.setFilters("wall,stat,slf4j");
        } catch (SQLException e) {
            log.error("【初始化数据源】init datasource error", e);
        }

        return dataSource;
    }

    /**
     * 声明事务管理
     *
     * @param ds
     * @return
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("masterDb") DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }
}
