package com.springboot.ceshi.control;

import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        User user=new User("张三",12,"天津");
        User user2=new User("李四",12,"北京");
        userServive.saveUser(user2);
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
}
