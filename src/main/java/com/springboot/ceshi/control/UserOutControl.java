package com.springboot.ceshi.control;

import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * Created by youfan on 2018/6/6 0006.
 */
@RestController
public class UserOutControl {
    @Autowired
    UserService userServive;

    @RequestMapping(value = "/useroutregister",method = RequestMethod.POST)
    public void userregister(@RequestBody User user) {
        userServive.inseruserInfo(user);
        return;
    }
    @RequestMapping(value = "/findByUserid",method = RequestMethod.GET)
    public User findByUserid(@RequestParam int  id){
        User user =  userServive.findByUserid(id);
        return user;
    }
}
