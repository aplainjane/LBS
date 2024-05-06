package com.team.demo.generator.controller;


import com.team.demo.config.Result;
import com.team.demo.generator.dao.DetailedDataMapper;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.entity.DetailedData;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.entity.User;
import com.team.demo.generator.service.DataService;
import com.team.demo.generator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */

@CrossOrigin
@RestController
@RequestMapping("/user")
public class LoginController {

    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>());

    @Autowired
    UserService userService;



    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        //业务逻辑: 根据u/p查询数据库 true: token false null
        String token = userService.login(user);
        if(token == null){
            //表示后端查询失败,返回用户201
            return Result.error("201","密码错误");
        }   //表示有数据,返回值为200
        //String A_token = TokenEncryption.generateToken(user.getId().toString(), user.getUsername());
        return Result.success(token);
    }


}

