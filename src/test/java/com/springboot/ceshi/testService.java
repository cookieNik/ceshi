package com.springboot.ceshi;

import com.springboot.ceshi.reporisty.UserRepository;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class testService {
    @Autowired
    UserService userService;
    @MockBean
    UserRepository userRepository;
    @Test
    public void test1(){
        //模拟dao层返回结果
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        given(this.userRepository.findOne(1))
                .willReturn(user);

        //测试service业务逻辑，模拟dao层返回结果，并不会走真实dao层.
        User user1 = userService.findById(2);
        System.out.println(user1);
    }
}
