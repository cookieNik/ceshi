package com.springboot.dbunit;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.dao.UserDao;
import com.springboot.ceshi.mapper.UserMappper;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.dbunit.DatabaseUnitException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.BDDMockito.given;

/**
 * dbunit单元测试需要生成表对应的xml，可采用一些工具类（待补充）。
 * 测试流程大概是这样的，建立数据库连接 -> 备份表 -> 清空数据表 -> 插入准备的数据 -> 调用 Dao 层接口 ->
 * 从数据库取实际结果-> 事先准备的期望结果 -> 断言 -> 回滚数据库 -> 关闭数据库连接
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class //Spring Teset DbUnit提供了 TransactionDbUnitTestExecutionListener会将事务边界扩大到Spring Test DbUnit执行的整个过程
})
@Transactional //添加事务进行回滚
public class TestUserMapper extends AbstractDbunitTestCase {

    private User user;   //期望返回的数据
    //注入数据源
    @Autowired
    private DataSource dataSource;
    @Autowired
    UserService userService;

    @MockBean
    UserDao userDao;

    @Autowired
    private UserMappper userMappper;

    public TestUserMapper() throws DatabaseUnitException {
        super("testMapper.xml");
    }


    @Before                   //前置通知
    public void init() throws SQLException, DatabaseUnitException, IOException {
        user=new User(4,"李四",16,"李四");
        setConn(dataSource.getConnection());
        backOneTable("user");
        insertTestData();
    }

    /**
     * 模拟dao实例测试。走的非真实dao
     */
    @Test
    public void test1(){
        //模拟dao层返回结果
        User user=new User();
        user.setId(6);
        user.setAddress("北京丰台区");
        user.setAge(122);
        user.setName("张三");
        given(this.userDao.findByUserid(2))
                .willReturn(user);

        //测试service业务逻辑，模拟dao层返回结果，并不会走真实dao层，但会的user1对象是上面定义的user
        User user1 = userService.findByUserid(2);
        System.out.println(user1);
    }

    @Test
    public void testFindUserById(){
        //调用方法
        User acUser = userMappper.findByUserid(4);
        //断言(就是看请求回来的数据 和预期的数据是否一致)
        Assert.assertEquals(user.getId(),acUser.getId());

    }
    //测试插入数据，如果不错还原数据处理destory（），测试数据会提交到真实数据库
    @Test
    public void testInsertUser(){
        User user=new User(9 ,"李四9",169,"李四9");
        //调用方法
        userMappper.inseruserInfo(user);
        User user1=userMappper.findByUserid(6);
        //回滚测试
        //int s=1/0;
        //断言(就是看请求回来的数据 和预期的数据是否一致)
        System.out.println(user1);
    }
    /**
     * 还原数据,不会将脏数据，测试数据实际提交到真实数据库
     */
    @After
    public void destory() throws FileNotFoundException, DatabaseUnitException, SQLException {
        dataRollback();
    }
}
