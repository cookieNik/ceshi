package com.springboot.ceshi.mapper;

import com.springboot.ceshi.model.User;
import com.springboot.ceshi.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by youfan on 2018/6/6 0006.
 */
@Mapper
@Repository
public interface UserMappper {
    public void inseruserInfo(User user);

    public User findByUserid(int id);


}
