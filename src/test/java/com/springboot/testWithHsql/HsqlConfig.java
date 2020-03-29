package com.springboot.testWithHsql;

import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.hsqldb.HsqldbDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.FileInputStream;

public class HsqlConfig extends DBTestCase {

    @Override
    protected IDataSet getDataSet() throws Exception {
        IDataSet dataSet=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
                new FileInputStream("D:\\IdeaProject\\ceshi\\src\\main\\resources\\testMapper.xml"))));
        return dataSet;
    }
    public HsqlConfig(String name)
    {
        super( name );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "org.hsqldb.jdbcDriver" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:hsqldb:sample" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "sa" );
        System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "" );
         System.setProperty( PropertiesBasedJdbcDatabaseTester.DBUNIT_SCHEMA, "USER" );
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.REFRESH;
    }

    @Override
    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DatabaseOperation.NONE;
    }

    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, new Integer(97));
        config.setFeature(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
        config.setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES,
                Boolean.FALSE);
        config.setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES,  true);
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,new HsqldbDataTypeFactory());
    }

    @Test
    public void test1(){
        System.out.println("123");
    }
}
