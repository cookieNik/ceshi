package com.springboot.rider;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.*;
import com.github.database.rider.core.api.exporter.BuilderType;
import com.github.database.rider.core.api.exporter.DataSetExportConfig;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.dataset.builder.ColumnSpec;
import com.github.database.rider.core.dataset.builder.DataSetBuilder;
import com.github.database.rider.core.dataset.writer.YMLWriter;
import com.github.database.rider.core.exporter.DataSetExporter;
import com.github.database.rider.junit5.api.DBRider;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.mapper.UserMappper;
import com.springboot.ceshi.model.User;
import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
@ActiveProfiles("test")
@DBRider
public class TestRider {

    @Autowired
    private  DataSource dataSource;
    @Autowired
    private UserMappper userMappper;

    /**
     * 如果没有使用负责读取@DataSet注释的@Rule，因此我们必须提供DataSetConfig，以便执行器可以创建数据集
     */
    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.
            instance(() -> dataSource.getConnection());

    /**
     * cleanAfter = true到下一个测试案例前清空数据集，
     * transactional = true下一个案例将按照这个案例的结果进行案例测试，下一个案例的@DataSet新添加的数据集将会失效
     * @DataSet（value:导入数据到数据集，strategy定数据用怎么的方式放进数据集中，transactional添加事务）
     */
    @Test
    //@DataSet(value = "users.yml",strategy = SeedStrategy.INSERT,cleanAfter = true,transactional = true)
    @DataSet(value = "users.yml",strategy = SeedStrategy.INSERT,transactional = true)
    //@ExpectedDataSet(value = "user.yml", compareOperation = CompareOperation.CONTAINS)
    //导出数据到文件
    //@ExportDataSet(format = DataSetFormat.YML,outputName="target/exported/xml/allTables.yml",builderType = BuilderType.DEFAULT)
    public void test1() {
        //提供DataSetConfig，以便执行器可以创建数据集
       /* DataSetConfig dataSetConfig = new DataSetConfig("users.yml");
        executor.createDataSet(dataSetConfig);*/
       User user=new User(6,"张萌",13,"田径");
       userMappper.inseruserInfo(user);
        User byUserid = userMappper.findByUserid(6);
        List<User> all = userMappper.findAll();
        System.out.println(11);

    }

    /**
     * 如果上一个案例加了事务，则strategy =SeedStrategy.CLEAN_INSERT失效
     */
    @Test
    @DataSet(value = "user.yml",strategy =SeedStrategy.CLEAN_INSERT)
    //@ExpectedDataSet(value = "user.yml", compareOperation = CompareOperation.CONTAINS)
    public void test2() {
       /* DataSetConfig dataSetConfig = new DataSetConfig("users.yml");
        executor.createDataSet(dataSetConfig);*/
        User byUserid = userMappper.findByUserid(6);
        List<User> all = userMappper.findAll();
        System.out.println(11);

    }

    /**
     * 通过代码导出是数据集到文件
     * @throws SQLException
     * @throws DatabaseUnitException
     */
    @Test
    @DataSet(value = "user.yml",strategy =SeedStrategy.CLEAN_INSERT)
    //@ExpectedDataSet(value = "user.yml", compareOperation = CompareOperation.CONTAINS)
    public void test3() throws SQLException, DatabaseUnitException {
       /* DataSetConfig dataSetConfig = new DataSetConfig("users.yml");
        executor.createDataSet(dataSetConfig);*/
        User byUserid = userMappper.findByUserid(6);
        List<User> all = userMappper.findAll();
        System.out.println(11);
        DataSetExporter.getInstance().export(dataSource.getConnection(),
                new DataSetExportConfig().outputFileName("target/user.yml"));
    }

}
