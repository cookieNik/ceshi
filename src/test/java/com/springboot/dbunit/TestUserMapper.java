package com.springboot.dbunit;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.mapper.UserMappper;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;

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
        TransactionDbUnitTestExecutionListener.class,
        MockitoTestExecutionListener.class
        //Spring Teset DbUnit提供了 TransactionDbUnitTestExecutionListener会将事务边界扩大到Spring Test DbUnit执行的整个过程
})
@Transactional //添加事务进行回滚
//指定采用基于内存的测试数据库H2
@ActiveProfiles("test")
//单独的@Transactional是回滚事务，在添加@Transactional的情况下如果要提交事务，只需要增加@Rollback(false),添加后，该测试类中的测试案例都会互相影响数据，
//直到测试案例跑完，数据回滚
@Rollback(false)
//测试实例按顺序执行
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestUserMapper {

    private User user;   //期望返回的数据
    private AbstractDbunitTestCase testCase=new AbstractDbunitTestCase();
    //注入数据源
    @Autowired
    private DataSource dataSource;
    /*@Autowired
    UserService userService;*/
    //不会走真实实例，走自定义的虚拟实例
    @MockBean
    UserService userService;
    //@SpyBean注释的实例会走真实的实例方法。可以自定义期望返回。
   /* @SpyBean
    UserService userService;*/
    @Autowired
    private UserMappper userMappper;
    /**
     * 目前的H2数据库初始化通过加载classpath:/resources/db/目录下的sql文件，初始化数据。
     * @throws Exception
     */
    @Before//建立连接，设置数据库初始态
    public void init() throws Exception {
        //建立数据库连接
        testCase.setConn(dataSource.getConnection());
        //备份数据库表，全部备份，或者根据表名备份
        testCase.backAll();
        //备份指定表名数据
        //testCase.backSpecified("user");
        //获取数据集,可指定多种方式，testCase.getQueryDataSet(),testCase.getXlsDataSet("");testCase.getXmlDataSet("");
        IDataSet xmlDataSet = testCase.getXmlDataSet("testMapper.xml");
        //初始化数据库状态
        testCase.initDB(xmlDataSet);
    }
    /**
     * 模拟dao实例测试。走的非真实dao
     */
    @Test
    public void test1(){
        //测试service业务逻辑，模拟dao层返回结果，并不会走真实dao层，但会的user1对象是上面定义的user
        User user=new User(6,"试试",123,"321123");
        userMappper.inseruserInfo(user);
        User user4=userMappper.findByUserid(6);
        List<User> users=userMappper.findAll();
        System.out.println("123");
    }

    @Test
    public void test2(){
        User user=new User(4,"12321",234,"234324");
        BDDMockito.given(this.userService.findByUserid(4)).willReturn(user);
        //调用方法
        User acUser = userService.findByUserid(4);
        //Product product=userMappper.findProductByid(1);
        //断言(就是看请求回来的数据 和预期的数据是否一致)
        //Assert.assertEquals(user.getId(),acUser.getId());
        System.out.println(acUser);

    }
    //测试插入数据，如果不错还原数据处理destory（），测试数据会提交到真实数据库
    @Test
    public void test3(){
        List<User> all = userMappper.findAll();
        //回滚测试
        //int s=1/0;
        //断言(就是看请求回来的数据 和预期的数据是否一致)
        System.out.println(all);
    }

    /**
     * 用之前备份的数据库临时文件。回滚数据库
     */
    @After
    public void rollBack() throws FileNotFoundException, DatabaseUnitException, SQLException {
        testCase.dataRollback();
    }
}
