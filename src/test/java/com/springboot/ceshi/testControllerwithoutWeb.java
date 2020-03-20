package com.springboot.ceshi;

import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.BDDMockito.given;

/**
 * 使用模拟环境测试，这种方式会构建完整的spring context，但是不会启动web容器
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
//使用@AutoConfigureMockMvc则是因为这样可以获得自动配置的MockMvc实例
@AutoConfigureMockMvc
public class testControllerwithoutWeb {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;
    @Test
    public void getAllCities() throws Exception {
        //构造模拟service返回结果，并不会走真实的service
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        given(this.userService.findById(1))
                .willReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/findByUserid")
                .accept(MediaType.APPLICATION_JSON_UTF8) //设置接受的编码方式
                .contentType(MediaType.APPLICATION_JSON_UTF8) //设置内容的编码方式
                .param("id","1")) //查询参数
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }
}
