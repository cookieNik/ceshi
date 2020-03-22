package com.springboot.ceshi.reporisty;

import com.springboot.ceshi.model.User;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query(value = "update user u set u.name =?1 where u.userid =?2",nativeQuery = true)
    @Modifying
    void updateUser(String name,int userid);
    @Query(value = "delete from user where userid =?1",nativeQuery = true)
    @Modifying
    void deleteUser(int userid);

}
