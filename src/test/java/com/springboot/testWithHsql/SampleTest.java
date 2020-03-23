package com.springboot.testWithHsql;

import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import com.springboot.dbunit.DbunitInitConfig;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.InputSource;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
/*@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)*/
public class SampleTest extends DBTestCase {
    final String path = "D:\\dataFile.xml"; //文件存储路径
    private IDatabaseConnection connection;

 /*   public SampleTest(String name) {
        super(name);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:sample");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
        //System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "" );
    }*/


    @Override
    protected void setUp() throws Exception {
        this.connection = getDatabaseTester().getConnection();
        IDataSet dataSet = getDataSet();
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(path))));
        return dataSet;
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    @Override
    protected void setUpDatabaseConfig(DatabaseConfig config) {
       /* config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);*/
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        connection.getConfig().setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
        connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,  new HsqldbDataTypeFactory());
        //connection.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES,  true);
        connection.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES,  false);
    }

    @Override
    protected IDatabaseTester getDatabaseTester() throws Exception {
        JdbcDatabaseTester tester = new JdbcDatabaseTester("org.hsqldb.jdbcDriver",
                "jdbc:hsqldb:mem:testdb","sa","","user,product,order_info");
        //Connection connection = tester.getConnection().getConnection();
        return tester;
    }

    @Autowired
    UserService userService;

    @Test
    public void test1() {
        List<User> allUser = userService.findAllUser();
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        userService.saveUser(user);
        List<User> allUser1 = userService.findAllUser();
        System.out.println("123");


    }
}