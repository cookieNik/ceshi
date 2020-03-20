package com.springboot.rider;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.SeedStrategy;
import com.github.database.rider.junit5.api.DBRider;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.ProductService;
import com.springboot.ceshi.service.UserService;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 测试事务
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
//激活测试配置文件，配置文件中的
//spring.datasource.data=classpath*:db1/product.sql,classpath*:db1/user.sql
//spring.datasource.schema=classpath*:db1/productsc.sql,classpath*:db1/schema.sql
//用来导入初始数据，数据包括(表结构.sql,表数据.sql)，从数据库导出来的数据需要修改才可以使用。
//在sql文件中添加了主键约束，当下面测试案例新增数据主键冲突的时候，将报错
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DBRider
public class TestRiderTransactional {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.
            instance(() -> dataSource.getConnection());

    /**
     * value:插入数据集的数据文件，文件可以是YML, JSON, XML, XLS, CSV, XML_DTD
     * strategy：数据插入策略
     * NONE：不执行任何操作，是getTearDownOperation的默认返回值。
     * UPDATE：将数据集中的内容更新到数据库中。它假设数据库中已经有对应的记录，否则将失败。
     * INSERT：将数据集中的内容插入到数据库中。它假设数据库中没有对应的记录，否则将失败。
     * REFRESH：将数据集中的内容刷新到数据库中。如果数据库有对应的记录，则更新，没有则插入。
     * DELETE：删除数据库中与数据集对应的记录。
     * DELETE_ALL：删除表中所有的记录，如果没有对应的表，则不受影响。
     * TRUNCATE_TABLE：与DELETE_ALL类似，更轻量级，不能rollback。
     * CLEAN_INSERT：是一个组合操作，是DELETE_ALL和INSERT的组合。是getSetUpOeration的默认返回值。
     * cleanAfter：为true的时候，将在测试结束后尝试删除数据库,谨慎添加
     * transactional：开启事务，测试结束提交数据
     */
    @Test
    @DataSet(value = "user.yml", strategy = SeedStrategy.INSERT, cleanAfter = true, transactional = true)
    @Transactional//添加事务注解，测试结束数据不会提交到数据库。测试结束数据回滚
    public void test1() {
        List<User> all1 = userService.findAllUser();
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        //模拟主键冲突
        //User user1=new User(6,"张三",13,"天津");
        userService.saveUser(user);
        //模拟主键冲突
        //userService.inseruserInfo(user1);
        User byUserid = userService.findById(6);
        List<User> all = userService.findAllUser();
        System.out.println(11);
    }

    /**
     * 如果上面开启了@DataSet事务，下面的@DataSet(value = "user.yml")将失效
     * 如果上面没有开启@DataSet事务，下面的@DataSet按照配置生效
     */
    @Test
    @DataSet(value = "allTables.yml", strategy = SeedStrategy.CLEAN_INSERT)
    public void test2() {
        List<User> all = userService.findAllUser();
        List<Product> allp=productService.findAllProduct();
        System.out.println(11);
    }

    @Test
    @DataSet(value = "user.yml,product.yml", strategy = SeedStrategy.CLEAN_INSERT)
    @Transactional
    public void test3() {
        List<User> all = userService.findAllUser();
        List<Product> products = productService.findAllProduct();
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        userService.saveUser(user);
        Product product = new Product(1, "水果", "橘子");
        productService.saveProduct(product);
        List<User> all1 = userService.findAllUser();
        List<Product> products1 = productService.findAllProduct();
        System.out.println(11);
    }

}
