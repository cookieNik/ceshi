package com.springboot.ceshi.service;

import com.springboot.ceshi.reporisty.UserRepository;
import com.springboot.ceshi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by youfan on 2018/6/6 0006.
 */
@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findById(int id){
        return userRepository.findOne(id);
    }
    public List<User> findAllUser(){

        return userRepository.findAllUser();
    }
    public void delete(int id){
        userRepository.delete(id);
    }
    public void saveUser(User user){
        userRepository.save(user);
    }
    public void updateUser(String name,int userid){
        userRepository.updateUser(name,userid);
    }
    public void deleteUser(int userid){
        userRepository.deleteUser(userid);
    }

}
