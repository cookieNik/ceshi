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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;

/**
 * 使用模拟环境测试，这种方式会构建完整的spring context，但是不会启动web容器
 */
@RunWith(SpringRunner.class)
@SpringBootTest
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
                .param("id","1")) //查询参数

                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("张三"));
    }
}
