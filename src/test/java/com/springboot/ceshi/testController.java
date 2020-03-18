package com.springboot.ceshi;

import com.springboot.ceshi.control.UserOutControl;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * controller层单元测试，使用@MockBean模拟service实例和返回结果，不会走真实的service层
 * 使用mockMvc模拟controller层的http请求
 *
 MockMvcBuilder构造MockMvc的构造器；
 mockMvc调用perform，执行一个RequestBuilder请求，调用controller的业务处理逻辑；
 perform返回ResultActions，返回操作结果，通过ResultActions，提供了统一的验证方式；
 使用StatusResultMatchers对请求结果进行验证；
 使用ContentResultMatchers对请求返回的内容进行验证；
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class testController {

    //注入web容器上下文，依赖web容器创建mockMvc实例。
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    //注入模拟service实例
    @MockBean
    private UserService userService;
    //构造mockMvc
    @Before
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); //初始化MockMvc对象
    }
    @Test
    public void testPostControl() throws Exception{
        //构造模拟service返回结果，并不会走真实的service
        User user=new User();
        user.setUserid(6);
        user.setAddress("北京丰台区");
        user.setAge(122);
        user.setName("张三");
        given(this.userService.findById(1))
                .willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/findByUserid")
                .accept(MediaType.APPLICATION_JSON_UTF8) //设置接受的编码方式
                .contentType(MediaType.APPLICATION_JSON_UTF8) //设置内容的编码方式
                .param("id","1") //查询参数
        )
                .andExpect(MockMvcResultMatchers.status().isOk())//判断http响应状态码是不是200
               /* .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("张三"))//判断查询结果的字段是不是和期望值一样
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("北京丰台区"))*/
                .andDo(MockMvcResultHandlers.print());//打印结果
    }
}
