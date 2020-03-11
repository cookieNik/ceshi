package com.springboot.dbunit;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;
import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

public class AbstractDbunitTestCase {

    private DatabaseConnection conn;   //这个不是真正的数据库的连接的  封装

    private File tempFile;    //这个就是临时文件

    private IDataSet dataSetTestData;

    public AbstractDbunitTestCase(String testDataName) throws DatabaseUnitException {
         dataSetTestData=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
                 AbstractDbunitTestCase.class.getClassLoader().getResourceAsStream(testDataName))));
    }

    /**
     * 这个方法的作用就是初始化上面的DatabaseConnection
     * @param conn1
     */
    public void setConn(Connection conn1) throws DatabaseUnitException {
       conn=new DatabaseConnection(conn1);
    }

    /**
     * 备份多个表
     * @param tabNames
     */
    public void backManyTable(String... tabNames) throws DataSetException, IOException {

        //保存数据库查询产生的表的集合
        QueryDataSet dataSet = new QueryDataSet(conn);
        //第二步：设置要备份的这个表
        for (String tabName:tabNames) {
            dataSet.addTable(tabName);
        }
        //接下来就是备份了
        tempFile=File.createTempFile("back",".xml");
        //备份
        FlatXmlDataSet.write(dataSet,new FileOutputStream(tempFile));
    }

    /**
     * 备份一张表
     * @param tabName
     */
    public void backOneTable(String tabName) throws IOException, DataSetException {
        backManyTable(tabName);
    }

    /**
     * 插入测试数据
     */
    public void insertTestData() throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(conn,dataSetTestData);
    }

    /**
     * 还原表的数据
     */
    public void dataRollback() throws DatabaseUnitException, SQLException, FileNotFoundException {
        IDataSet dataSet=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
        DatabaseOperation.CLEAN_INSERT.execute(conn,dataSet);
    }

}
