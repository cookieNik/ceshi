package com.springboot.dbunit;

import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import junit.framework.TestCase;
import org.dbunit.DBTestCase;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestDBunit1 extends DBTestCase {
    @Autowired
    private DataSource dataSource;
    @Autowired
    UserService userService;

    public TestDBunit1() {
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null;
    }


    protected void setUp() throws Exception
    {
        super.setUp();
        IDataSet databaseDataSet = getConnection().createDataSet();

        // initialize your database connection here
        IDatabaseConnection connection = new DatabaseConnection(dataSource.getConnection());
        // ...
        TestDBUnit testDBUnit=new TestDBUnit("TestDBunit1");
        // initialize your dataset here
        IDataSet dataSet =testDBUnit.getDataSet();
        // ...

        try
        {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        }
        finally
        {
            connection.close();
        }
    }
    @Test
    public void test1(){
        List<User> allUser = userService.findAllUser();
        System.out.println("123");

    }
}
