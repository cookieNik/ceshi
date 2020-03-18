package com.springboot.ceshi.reporisty;

import com.springboot.ceshi.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by youfan on 2018/6/6 0006.
 */

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    @Query(value = "select * from user ", nativeQuery = true)
    List<User> findAllUser();

}
