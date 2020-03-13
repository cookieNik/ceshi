package com.springboot.dbunit;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.xml.sax.InputSource;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * dbunit单元测试流程：建立数据库连接、备份表、调用接口测试、从数据库获取实际结果、断言对比期望数据、回滚数据、关闭数据库
 */
public class AbstractDbunitTestCase{


    private DatabaseConnection conn;   //这个不是真正的数据库的连接的  封装

    private File tempFile;    //这个就是临时文件

    //private IDataSet dataSetTestData;

    /**
     * 第一步建立数据库连接
     * @param conn1
     */
    public void setConn(Connection conn1) throws DatabaseUnitException {
        conn=new DatabaseConnection(conn1);
    }

    /**
     * 获取数据集
     * @throws DatabaseUnitException
     */
    public IDataSet getXmlDataSet(String name) throws Exception{
        //FlatXmlDataSet build = new FlatXmlDataSetBuilder().build(new File(name));
        FlatXmlDataSet flatXmlDataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(
                AbstractDbunitTestCase.class.getClassLoader().getResourceAsStream(name))));
        return flatXmlDataSet;
    }

    /**
     * 获取数据集
     * @return
     */
    public IDataSet getDBData() throws SQLException {
        return conn.createDataSet();
    }
    /**
     * 获取数据集
     * @return
     */
    public IDataSet getQueryDataSet() throws SQLException {
        return new QueryDataSet(conn);
    }

    /**
     * 获取数据集
     * @param name
     * @return
     * @throws SQLException
     * @throws DataSetException
     * @throws IOException
     */
    public IDataSet getXlsDataSet(String name) throws SQLException, DataSetException,
            IOException {
        InputStream is = new FileInputStream(new File(name));
        return new XlsDataSet(is);
    }

    /**
     * 将数据全部备份到临时文件
     */
    public void backAll() throws Exception {
        IDataSet iDataSet=conn.createDataSet();
        tempFile= File.createTempFile("back",".xml");
        FlatXmlDataSet.write(iDataSet, new FileWriter(tempFile),"UTF-8");
    }
    /**
     * 备份指定的数据到临时文件
     */
    public void backSpecified(String... tableName) throws Exception {
        QueryDataSet queryDataSet=new QueryDataSet(conn);
        for(String tableNa:tableName){
            queryDataSet.addTable(tableNa);
        }
        tempFile=File.createTempFile("back",".xml");
        FlatXmlDataSet.write(queryDataSet,new FileWriter(tempFile),"UTF-8");
    }
    /**
     * 还原表的数据
     */
    public void dataRollback() throws DatabaseUnitException, SQLException, FileNotFoundException {
        IDataSet dataSet=new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
        DatabaseOperation.CLEAN_INSERT.execute(conn,dataSet);
    }


    /**
     * 初始化数据库状态
     * @throws DatabaseUnitException
     * @throws SQLException
     */
    public void initDB(IDataSet iDataSet) throws DatabaseUnitException, SQLException {
        DatabaseOperation.CLEAN_INSERT.execute(conn,iDataSet);
        //DatabaseOperation.CLEAN_INSERT.execute(conn,iDataSet);
    }




}
