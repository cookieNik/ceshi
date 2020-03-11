package com.springboot.ceshi;

import com.springboot.ceshi.mapper.UserMappper;
import com.springboot.ceshi.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试mapper层，在测试方法上不添加@Rollback(false)，增删改不会产生脏数据，加上后，会真实操作数据库
 */
@RunWith(SpringRunner.class)
//如果不加注解@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)，
// 那么mybatis使用的是内存数据库，并不是真实的tddl的数据库，会报表不存在的错
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@MybatisTest
public class mybatisTestMapper {
    @Autowired
    UserMappper userMappper;

    @Test
    public void test1(){
        User user=userMappper.findByUserid(4);
        System.out.println(user);
    }
    @Test
    //单测完成默认会将数据回滚，如果不想回滚，想保留在数据库中，要加(false)
    //@Rollback(false)
    public void test2(){
        User user=new User();
        user.setId(4);
        user.setName("李四");
        user.setAge(16);
        user.setAddress("北京1");
        userMappper.inseruserInfo(user);
        System.out.println(123);
    }
}
