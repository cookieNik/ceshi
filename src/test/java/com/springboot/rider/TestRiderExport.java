package com.springboot.rider;

import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.*;
import com.github.database.rider.core.api.exporter.BuilderType;
import com.github.database.rider.core.api.exporter.DataSetExportConfig;
import com.github.database.rider.core.api.exporter.ExportDataSet;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.exporter.DataSetExporter;
import com.github.database.rider.junit5.api.DBRider;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.ProductService;
import com.springboot.ceshi.service.UserService;
import org.dbunit.DatabaseUnitException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 导出查询结果到文件
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
@ActiveProfiles("test")
@DBRider
public class TestRiderExport {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserService userService;
    @Autowired
    ProductService productService;
    @Rule
    public DBUnitRule dbUnitRule = DBUnitRule.
            instance(() -> dataSource.getConnection());

    /**
     * @ExportDataSet 导出数据到文件,文件可以是YML, JSON, XML, XLS, CSV, XML_DTD
     * format：生成文件的格式
     * outputName：文件输出目录
     * builderType：构建器(java)文件将在与outputName 相同的路径上生成，但是文件后缀将更改为.java
     */
    @Test
    //@DataSet(value = "user.yml",strategy = SeedStrategy.INSERT)
  /*  @ExportDataSet(format = DataSetFormat.YML,outputName="target/exported/xml/allTables.yml",
            builderType = BuilderType.DEFAULT)*/
    @ExportDataSet(format = DataSetFormat.YML,outputName="target/exported/xml/allTables.yml")
    public void test1() {
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        userService.saveUser(user);
        User user1 = userService.findById(6);
        List<User> all = userService.findAllUser();
        System.out.println(11);
    }
    /**
     * 通过代码导出是数据集到文件
     * @throws SQLException
     * @throws DatabaseUnitException
     */
    @Test
    @DataSet(value = "user.yml",strategy =SeedStrategy.CLEAN_INSERT)
    public void test2() throws SQLException, DatabaseUnitException {
        User byUserid = userService.findById(6);
        List<User> all = userService.findAllUser();
        System.out.println(11);
        DataSetExporter.getInstance().export(dataSource.getConnection(),
                new DataSetExportConfig().outputFileName("target/exported/xml/user.yml"));
    }
    /**
     * 基于java配置的数据集导入
     */
    @Test
    public void test3() throws Exception{
        DataSetConfig dataSetConfig  = new DataSetConfig();
        dataSetConfig.datasetProvider(CustomDataSetProvider.class);
        DataSetExecutor dataSetExecutor = dbUnitRule.getDataSetExecutor();
        dataSetExecutor.createDataSet(dataSetConfig);
        List<Product> all = productService.findAllProduct();
        System.out.println("123");
    }
}
