package com.team.demo;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.demo.generator.dao.CommentMapper;
import com.team.demo.generator.dao.DetailedDataMapper;
import com.team.demo.generator.entity.*;
import com.team.demo.config.Result;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.service.DataService;
import com.team.demo.generator.service.ImageService;
import com.team.demo.generator.service.UserService;
import com.team.demo.generator.service.impl.GeoUtils;
import org.apache.commons.collections.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;

@SpringBootTest
class DemoApplicationTests {

	@Autowired

	private UserMapper userMapper;

	@Autowired
	private UserService userService;

	@Autowired
	private DetailedDataMapper detailedDataMapper;

	@Autowired
	private ImageMapper imageMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private ImageService imageService;

	@Autowired
	private DataService dataService;


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

	/*@Test
	void testUpdateUser2() {
		List<Integer> ids = List.of(1, 2, 4);
		UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
				.setSql("distance = distance + 1" )
				.in("id",ids);
		userMapper.update(null,wrapper);
	}*/

	@Test
    void testLogin()
	{
		User user = new User();

		user.setUsername("Lucy");
		user.setPassword("Team A");

		//业务逻辑: 根据u/p查询数据库 true: token false null
		User find_user = userMapper.findUserByUP(user);
		String token = userService.login(user);

		if(find_user == null){
			//表示后端查询失败,返回用户201
			System.out.println("not found");
		}   //表示有数据,返回值为200
		else {
			System.out.println(find_user);
			System.out.println(token);
		}
	}

	@Test
	void testFindImage()
	{
		Image image = imageMapper.selectById(2);
		System.out.println(image.getPath());

	}

	@Test
	void FindComment()
	{
		List<Comment> a = commentMapper.findComment(6);
		System.out.println(a);
	}


	@Test
	void testFindUserImage()
	{

		System.out.println(imageMapper.findUserImages(1));

	}

	@Test
	void test()
	{
		System.out.println(detailedDataMapper.findBycode("京13").getImageid());
	}

	@Test
	void testLocateImage()
	{

		List<Image> imageL = imageMapper.findAllImages();
		List<Image> image = imageService.around(116,36,imageL,500);
		System.out.println(image);

	}

	@Test
	void testLocateData()
	{

		List<DetailedData> imageL = detailedDataMapper.findAll();
		for(DetailedData detailedData : imageL)
		{
			detailedData.setLatitude(detailedDataMapper.addLocate(detailedData).getLatitude());
			detailedData.setLongitude(detailedDataMapper.addLocate(detailedData).getLongitude());
			//detailedMapper.addLocate(detailedData);
		}
		List<DetailedData> t = dataService.around(116,36,imageL,500000);
		//System.out.println(t);
		for(DetailedData detailedData : t)
		{
			System.out.println(detailedData.getCode());
		}

		/*double distance = GeoUtils.calculateDistance(40.05861561613348, 116.30793520652882, 40.05861561613348, 116.30793520652882);
		System.out.println(distance);
*/
	}


	@Test
	void getPoi()
	{
		String code = "京";
		String type = "森林生态";
		String department = "林业";
		String settime = "19850401";
		List<DetailedData> poiList = detailedDataMapper.findAll();
		List<DetailedData> returnList_code = new ArrayList<>();
		List<DetailedData> returnList_type = new ArrayList<>();
		List<DetailedData> returnList_department = new ArrayList<>();
		List<DetailedData> returnList_settime = new ArrayList<>();
		if(code!=null)
		{
			for(DetailedData poi : poiList)
			{
				if(poi.getCode().contains(code))
				{
					returnList_code.add((poi));
				}
			}
		}
		if(type!=null)
		{
			for(DetailedData poi : poiList)
			{
				if(poi.getType().contains(type))
				{
					returnList_type.add((poi));
				}
			}
		}
		if(department!=null)
		{
			for(DetailedData poi : poiList)
			{
				if(poi.getDepartment().contains(department))
				{
					returnList_department.add((poi));
				}
			}
		}
		if(settime != null)
		{
			for(DetailedData poi : poiList)
			{
				if(Integer.parseInt(poi.getSetTime()) >= Integer.parseInt(settime))
				{
					returnList_settime.add((poi));
				}
			}
		}
		List<DetailedData> intersection = (List<DetailedData>) CollectionUtils.intersection(returnList_code, returnList_type);
		intersection = (List<DetailedData>) CollectionUtils.intersection(intersection ,returnList_department);
		intersection = (List<DetailedData>) CollectionUtils.intersection(intersection ,returnList_settime);
		System.out.println(intersection.get(1).getName());
	}


}
