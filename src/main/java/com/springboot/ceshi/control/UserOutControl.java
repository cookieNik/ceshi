package com.springboot.ceshi.control;

import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * Created by youfan on 2018/6/6 0006.
 */
@RestController
public class UserOutControl {
    @Autowired
    UserService userServive;

    @RequestMapping(value = "/useroutregister",method = RequestMethod.POST)
    public void userregister(@RequestBody User user) {
        userServive.saveUser(user);
        return;
    }
    @RequestMapping(value = "/insertUser",method = RequestMethod.GET)
    public void userregister() {
        User user=new User("张三",12,"天津",new Date(),new BigDecimal(123),123.12f,123.123);
        userServive.saveUser(user);
        return;
    }
    @RequestMapping(value = "/findByUserid",method = RequestMethod.GET)
    public User findByUserid(@RequestParam int  id){
        User user =  userServive.findById(id);
        return user;
    }
    @RequestMapping(value = "/findAllUser",method = RequestMethod.GET)
    public List<User> findAllUser(){
        List<User> allUser = userServive.findAllUser();
        return allUser;
    }
    @RequestMapping(value = "/updateUser",method = RequestMethod.GET)
    public void updateUser(String name,int userid){
        userServive.updateUser(name, userid);
        System.out.println("123");
        return ;
    }
    @RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
    public void deleteUser(int userid){
        userServive.deleteUser(userid);
        System.out.println("123");
        return ;
    }
}
