package com.springboot.ceshi;

import com.springboot.ceshi.dao.UserDao;
import com.springboot.ceshi.mapper.UserMappper;
import com.springboot.ceshi.model.User;
import com.springboot.ceshi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * junit + springboot集成测试。自上而下调用
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class testJunit4 {
	@Autowired
	private UserService userService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserMappper userMappper;
	@Test
	public void contextLoads() {
		User user=userService.findByUserid(1);
		System.out.println(user);
		User user1=userDao.findByUserid(1);
		System.out.println(user1);
		User user2=userMappper.findByUserid(2);
		System.out.println(user2);

	}

}
