package com.springboot.dbunit;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * dbunit单元测试需要生成表对应的xml，可采用一些工具类（待补充）。
 * 测试流程大概是这样的，建立数据库连接 -> 备份表 -> 清空数据表 -> 插入准备的数据 -> 调用 Dao 层接口 ->
 * 从数据库取实际结果-> 事先准备的期望结果 -> 断言 -> 回滚数据库 -> 关闭数据库连接
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
@ActiveProfiles("test")
//@Rollback(value = false)  //为true的时候测试方法增删改会回滚
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestDBunitTransactional {

    //注入数据源
    @Resource
    private  DataSource dataSource;
    private DbunitInitConfig config;
    @Autowired
    UserService userService;

    /**
     * 如果开启注解，该注解会在测试方法之前执行，用于测试单个测试方法。
     * 否则会在每个测试方法之前执行该初始化方法
     * @throws Exception
     */
    @Before//建立连接，设置数据库初始态
    public void init() throws Exception {
        //初始化数据库，建立数据据连接
        this.config=new DbunitInitConfig(dataSource);
        //备份数据库表，全部备份，或者根据表名备份
        config.loadData();
        //备份指定表名数据
        //config.backSpecified("user");
        //获取数据集,可指定多种方式，testCase.getQueryDataSet(),testCase.getXlsDataSet("");testCase.getXmlDataSet("");
       // IDataSet xmlDataSet = config.getXmlDataSet("testMapper.xml");
        //初始化数据库状态
       // config.insertDB(xmlDataSet);
    }

    /**
     * 添加了@Transactional，遇到异常，插入失败，数据回滚
     */
    @Test
    @Transactional //添加事务注解，不会提交数据到数据库，避免脏数据，如果想提交数据到数据库，不添加就可以
    //@Rollback(false) //在测试完成后是否回滚，true回滚，false提交，默认true
    public void test1() throws Exception {
        //测试service业务逻辑，模拟dao层返回结果，并不会走真实dao层，但会的user1对象是上面定义的user
        List<User> all=userService.findAllUser();
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        userService.saveUser(user);
        List<User> users1=userService.findAllUser();
        //userService.deleteUser(1);
        //List<User> users=userService.findAllUser();
        System.out.println("123");
    }

    /**
     * 添加了@Rollback(false)，遇到异常测试方法终止，但是数据插入成功
     * @throws Exception
     */
    @Test
    //@Rollback(false)
    @Transactional
    public void test2() throws Exception {
        //测试service业务逻辑，模拟dao层返回结果，并不会走真实dao层，但会的user1对象是上面定义的user
        List<User> users=userService.findAllUser();
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        userService.saveUser(user);
        List<User> users1=userService.findAllUser();
        //userService.deleteUser(1);
        //List<User> user2=userService.findAllUser();
        System.out.println("123");

    }

    /**
     * 用之前备份的数据库临时文件。回滚数据库
     */
    @After
    public void rollBack() throws FileNotFoundException, DatabaseUnitException, SQLException {
        config.dataRollback();
    }
}
