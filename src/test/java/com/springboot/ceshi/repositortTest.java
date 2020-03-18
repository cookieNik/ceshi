package com.springboot.ceshi;

import com.springboot.ceshi.reporisty.UserRepository;
import com.springboot.ceshi.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 测试dao层，会回滚，不会产生脏数据
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class repositortTest {
    @Autowired
    UserRepository userRepository;
    @Test
    public void test1(){
        User user=userRepository.findOne(1);
        System.out.println(user);
    }
    @Test
    //单测完成默认会将数据回滚，如果不想回滚，想保留在数据库中，要加(false)
    //@Rollback(false)
    //@Transactional //测试结束，回滚数据
    public void test2(){
        List<User> allUser = userRepository.findAllUser();
        User user=new User();
        user.setName("李四");
        user.setAge(16);
        user.setAddress("北京1");
        userRepository.save(user);
        User user1=new User();
        user1.setName("李四1");
        user1.setAge(161);
        user1.setAddress("北京11");
        userRepository.save(user1);
        List<User> allUser1 = userRepository.findAllUser();
        System.out.println(123);
    }

}
