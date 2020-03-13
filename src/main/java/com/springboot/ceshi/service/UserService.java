package com.springboot.ceshi.service;

import com.springboot.ceshi.dao.UserDao;
import com.springboot.ceshi.model.Product;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by youfan on 2018/6/6 0006.
 */
@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public void inseruserInfo(User user){
        userDao.inseruserInfo(user);
    }

    public User findByUserid(int id){
        User user=userDao.findByUserid(id);
        List<User> all = userDao.findAll();
        Product product=userDao.findProductByid(id);
        return user;
    }



}
