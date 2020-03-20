package com.springboot.dbunit;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.h2.H2DataTypeFactory;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.operation.DatabaseOperation;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import javax.sql.DataSource;
import java.io.*;

import java.sql.SQLException;
import java.util.Iterator;

/**
 * dbunit单元测试流程：建立数据库连接、备份表、调用接口测试、从数据库获取实际结果、断言对比期望数据、回滚数据、关闭数据库
 */
public class DbunitInitConfig {

    private DataSource dataSource;

    public DbunitInitConfig(DataSource dataSource) throws SQLException, DatabaseUnitException {
        this.dataSource = dataSource;
        this.conn=new DatabaseConnection(dataSource.getConnection());
        conn.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY,  new H2DataTypeFactory());
    }

    private  DatabaseConnection conn;   //这个不是真正的数据库的连接

    private File backFile; //数据中转文件
    final String path="D:\\dataFile.xml"; //文件存储路径


    /**
     * 将数据全部备份到临时文件
     */
    public  void backAll() throws Exception {
        IDataSet iDataSet=conn.createDataSet();
        backFile  = new File(path);
        if(backFile.exists()){
            backFile.delete();
        }
        backFile  = new File(path);
        FlatXmlDataSet.write(iDataSet, new FileWriter(backFile),"UTF-8");
    }

    /**
     * 备份mysql指定的数据表数据临时文件
     */
    public  void backDataByTable(String tableNames) throws Exception {
        QueryDataSet queryDataSet=new QueryDataSet(conn);
        String[] tables=tableNames.split(",");
        for(String tableNa:tables){
            queryDataSet.addTable(tableNa);
        }
        backFile  = new File(path);
        if(backFile.exists()){
            backFile.delete();
        }
        backFile  = new File(path);
        FlatXmlDataSet.write(queryDataSet,new FileWriter(backFile),"UTF-8");
    }

    /**
     * 从中转文件加载数据
     * @throws DatabaseUnitException
     * @throws SQLException
     * @throws FileNotFoundException
     */
    public void loadData() throws Exception{
        IDataSet dataSet=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(path))));
        DatabaseOperation.CLEAN_INSERT.execute(conn,dataSet);
    }

    /**
     * 测试完毕还数据库状态
     */
    public void dataRollback() throws DatabaseUnitException, SQLException, FileNotFoundException {
        /*IDataSet dataSet=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(path))));
        DatabaseOperation.CLEAN_INSERT.execute(conn,dataSet);*/

        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setColumnSensing(true);
        IDataSet ds =builder.build(new FileInputStream(path));

        // recover database
        DatabaseOperation.CLEAN_INSERT.execute(conn, ds);
    }


}
