package com.r7.core.trigger.config;


import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @author wt
 * @Description
 */
@Slf4j
@Configuration

public class DataSourceConfig {



    @Autowired
    private Environment env;


    public DruidDataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(env.getProperty("spring.datasource.db1.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.db1.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.db1.password"));
        dataSource.setDriverClassName(env.getProperty("spring.datasource.db1.driver-class-name"));
        return dataSource;
    }


    @Bean(name = "dataSource1")
    @Primary
    @ConfigurationProperties("spring.datasource.db1")
    public DruidDataSource dataSource1 () {
        return getDataSource();
    }


    @Bean(name = "dataSourceTask")
    @ConfigurationProperties("spring.datasource.quartz")
    @QuartzDataSource
    public DruidDataSource dataSourceTask () {
        return DruidDataSourceBuilder.create().build();
    }


    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory (@Qualifier("dataSource1") DruidDataSource dataSource1) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource1);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/*Mapper.xml"));
        return factoryBean.getObject();
    }


}
