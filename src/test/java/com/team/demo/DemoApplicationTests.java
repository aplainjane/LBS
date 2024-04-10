package com.team.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	void contextLoads() {

	}



	@Test
	void testFind()
	{
		List<Integer> userL = new ArrayList<>();
		userL.add(1);
		userL.add(2);
		userL.add(3);
		userL.add(4);
		userL.add(5);
		List<User> user = userMapper.queryUserByIds(userL);
		System.out.println("user = " + user);
	}

	@Test
	void testDelete()
	{
		User d_user = new User();
		d_user.setId(5);
		userMapper.deleteById(d_user);
	}


	@Test
	void testInsert()
	{
		User user = new User();
		user.setId(5);
		user.setUsername("Lucy");
		user.setPassword("Team A");
		//user.setDistance(2000);
		userMapper.insert(user);
	}

	@Test
	void testQueryWrapper()
	{
		BigDecimal number1 = new BigDecimal("5");
		QueryWrapper<User> wrapper = new QueryWrapper<User>()
				.select("id","username","password")
				.like("username","o")
				.eq("password","Lucy");//greater equal
		List<User> users = userMapper.selectList(wrapper);
		users.forEach(System.out::println);
	}

	@Test
	void testUpdateUser()
	{
		//更新数值
		User user = new User();

		user.setPassword("2000");

		//条件
		QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("username","Lucy");

		//更新
		userMapper.update(user,wrapper);
	}

	@Test
	void testLogin()
	{
		User user = new User();

		user.setUsername("Lucy");
		user.setPassword("2000");

		User find = userMapper.findUserByUP(user);

		System.out.println("user = " + find);
	}
	/*@Test
	void testUpdateUser2() {
		List<Integer> ids = List.of(1, 2, 4);
		UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
				.setSql("distance = distance + 1" )
				.in("id",ids);
		userMapper.update(null,wrapper);
	}*/


}
