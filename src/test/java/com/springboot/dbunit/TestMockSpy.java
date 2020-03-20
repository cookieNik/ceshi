package com.springboot.dbunit;

import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.springboot.ceshi.CeshiApplication;
import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.ProductService;
import com.springboot.ceshi.service.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.BDDMockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CeshiApplication.class)
//指定采用基于内存的测试数据库H2
@ActiveProfiles("test")
public class TestMockSpy {
    //不会走真实实例，走自定义的虚拟实例
    @MockBean
    UserService userService;
    //@SpyBean注释的实例会走真实的实例方法。可以自定义期望返回。
    @SpyBean
    ProductService productService;
    @Test
    public void test1(){
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        BDDMockito.given(this.userService.findById(4)).willReturn(user);
        //返回上面定义的user
        User acUser = userService.findById(4);
        //返回null
        User acUser1 = userService.findById(1);
        System.out.println(acUser);
    }
    @Test
    public void test2(){
        Product product=new Product(1,"水果","西瓜");
        //下面会调用真实的service
        BDDMockito.given(this.productService.findById(1)).willReturn(product);
        //不会走真实的service，直接返回上面定义的user
        Product product1 = productService.findById(1);
        //走真是的service，并返回
        Product product2 = productService.findById(2);
        System.out.println(product1);
    }
}
