package com.springboot.ceshi;

import com.springboot.ceshi.control.UserOutControl;
import com.springboot.ceshi.dao.UserDao;
import com.springboot.ceshi.mapper.UserMappper;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;

/**
 * 这个测试是针对controller的单元测试，service采用mockbean模拟，不会访问真实service实例
 * 如果想访问真实service需要添加@Import({UserService.class,UserDao.class}) ，去掉模拟service实例，就可以访问真实service
 */
@RunWith(SpringRunner.class)
//默认使用web模拟环境SpringBootTest.WebEnvironment.MOCK
@WebMvcTest(UserOutControl.class)
//添加该注解，可以测试通过但是不能得到正确的数据从数据库，所以需要使用模拟service。
// 否则报错Property 'sqlSessionFactory' or 'sqlSessionTemplate' are required
@AutoConfigureMybatis // add
@Import({UserService.class,UserDao.class}) // Add
public class testControllerwithWebMvcTest {


    @Autowired
    private MockMvc mockMvc;
    //注入模拟service实例
  /*  @MockBean
    private UserService userService;*/

    @Test
    public void testPostControl() throws Exception{
        //构造模拟service返回结果，并不会走真实的service
      /*  User user=new User();
        user.setId(6);
        user.setAddress("北京丰台区");
        user.setAge(122);
        user.setName("张三");
        given(this.userService.findByUserid(1))
                .willReturn(user);*/

        mockMvc.perform(MockMvcRequestBuilders.get("/findByUserid")
                .accept(MediaType.APPLICATION_JSON_UTF8) //设置接受的编码方式
                .contentType(MediaType.APPLICATION_JSON_UTF8) //设置内容的编码方式
                .param("id","1") //查询参数
        )
                .andExpect(MockMvcResultMatchers.status().isOk())//判断http响应状态码是不是200
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("张三"))//判断查询结果的字段是不是和期望值一样
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("北京丰台区"))
                .andDo(MockMvcResultHandlers.print());//打印结果
    }
}
