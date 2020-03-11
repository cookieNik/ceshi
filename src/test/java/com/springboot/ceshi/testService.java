package com.springboot.ceshi;

import com.springboot.ceshi.dao.UserDao;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.BDDMockito.given;

@SpringBootTest
@RunWith(SpringRunner.class)
public class testService {
    @Autowired
    UserService userService;
    @MockBean
    UserDao userDao;
    @Test
    public void test1(){
        //模拟dao层返回结果
        User user=new User();
        user.setId(6);
        user.setAddress("北京丰台区");
        user.setAge(122);
        user.setName("张三");
        given(this.userDao.findByUserid(1))
                .willReturn(user);

        //测试service业务逻辑，模拟dao层返回结果，并不会走真实dao层.
        User user1 = userService.findByUserid(2);
        System.out.println(user1);
    }
}
