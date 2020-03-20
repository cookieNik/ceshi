package com.springboot.dbunit;

import com.springboot.ceshi.CeshiApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
public class backTestData {
    //注入开发数据源，导出用来测试的数据到xml文件
    @Autowired
    private DataSource dataSource;
    private DbunitInitConfig config;
    @Before//建立连接，设置数据库初始态
    public void init() throws Exception {
        this.config = new DbunitInitConfig(dataSource);
    }

    /**
     * 从开发环境备份部分数据到D:\dataFile.xml
     * @throws Exception
     */
    @Test
    public void backDataByTableName() throws Exception {
        config.backDataByTable("user,product,order_info");
    }

    /**
     * 从开发环境备份全部数据到D:\dataFile.xml
     * 要保证备份的数据库表都有对应的实体类，否则备份失败
     * @throws Exception
     */
    @Test
    public void backAllTables() throws Exception {
        config.backAll();
    }
}
