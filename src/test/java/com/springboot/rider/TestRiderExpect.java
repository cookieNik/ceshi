package com.springboot.rider;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.*;
import com.github.database.rider.junit5.api.DBRider;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.reporisty.UserRepository;
import com.springboot.ceshi.model.User;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.util.List;

/**
 * Database Rider通过JUnit规则集成了JUnit和DBUnit，
 * 可以轻松地准备通过yaml、xml、json、xls或csv文件进行测试的数据库状态
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
@ActiveProfiles("test")
@DBRider
public class TestRiderExpect {

    @Autowired
    private  DataSource dataSource;
    @Autowired
    private UserRepository userRepository;

    /**
     * 如果没有使用负责读取@DataSet注释的@Rule，因此我们必须提供DataSetConfig，以便执行器可以创建数据集
     */
    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.
            instance(() -> dataSource.getConnection());

    @Test
    @DataSet(value = "product.yml",strategy = SeedStrategy.INSERT)
    //对比测试结果数据集和预期文件数据集结果是否一致，类型可选EQUALS(相等),CONTAINS(包含)
    @ExpectedDataSet(value = "user.yml", compareOperation = CompareOperation.EQUALS)
    public void test1() {
        //提供DataSetConfig，以便执行器可以创建数据集,注释的代码功能同@DataSet注解类似
        /*DataSetExecutor executor = dbUnitRule.getDataSetExecutor();
        DataSetConfig dataSetConfig = new DataSetConfig("product.yml");
        executor.createDataSet(dataSetConfig);*/
       User user=new User("张萌",13,"田径");
       userRepository.save(user);
        User byUserid = userRepository.findOne(6);
        List<User> all = userRepository.findAllUser();
        System.out.println(11);

    }

}
