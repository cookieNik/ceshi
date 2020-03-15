package com.springboot.ceshi.conifg;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {
    @Autowired
    private DataBase1Properties dataBase1Properties;
   /* @Autowired
    private DataBase2Properties dataBase2Properties;
*/
    @Bean(name = "dataSource")     //声明其为Bean实例
    //@Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource1() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(dataBase1Properties.getUrl());
        datasource.setUsername(dataBase1Properties.getUsername());
        datasource.setPassword(dataBase1Properties.getPassword());
        datasource.setDriverClassName(dataBase1Properties.getDriverClassName());

        //configuration
        datasource.setInitialSize(dataBase1Properties.getInitialSize());
        datasource.setMinIdle(dataBase1Properties.getMinIdle());
        datasource.setMaxActive(dataBase1Properties.getMaxActive());
        datasource.setMaxWait(dataBase1Properties.getMaxWait());
        datasource.setTimeBetweenEvictionRunsMillis(dataBase1Properties.getTimeBetweenEvictionRunsMillis());
        datasource.setMinEvictableIdleTimeMillis(dataBase1Properties.getMinEvictableIdleTimeMillis());
        datasource.setValidationQuery(dataBase1Properties.getValidationQuery());
        datasource.setTestWhileIdle(dataBase1Properties.isTestWhileIdle());
        datasource.setTestOnBorrow(dataBase1Properties.isTestOnBorrow());
        datasource.setTestOnReturn(dataBase1Properties.isTestOnReturn());
        datasource.setPoolPreparedStatements(dataBase1Properties.isPoolPreparedStatements());
        datasource.setMaxPoolPreparedStatementPerConnectionSize(dataBase1Properties.getMaxPoolPreparedStatementPerConnectionSize());
        try {
            datasource.setFilters(dataBase1Properties.getFilters());
        } catch (SQLException e) {
            System.err.println("druid configuration initialization filter: " + e);
        }
        datasource.setConnectionProperties(dataBase1Properties.getConnectionProperties());
        return datasource;
    }

    /*@Bean(name = "dataSource2")    //声明其为Bean实例
    public DataSource dataSource2() {
        HikariDataSource datasource=new HikariDataSource();
        //DruidDataSource datasource = new DruidDataSource();
        datasource.setJdbcUrl(dataBase2Properties.getUrl());
        datasource.setUsername(dataBase2Properties.getUsername());
        datasource.setPassword(dataBase2Properties.getPassword());
        datasource.setDriverClassName(dataBase2Properties.getDriverClassName());
        return datasource;
    }*/


}
