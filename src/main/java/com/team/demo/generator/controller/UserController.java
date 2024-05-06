package com.team.demo.generator.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.demo.config.Result;
import com.team.demo.config.TokenEncryption;
import com.team.demo.generator.dao.DetailedDataMapper;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.entity.DetailedData;
import com.team.demo.generator.entity.User;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.service.DataService;
import com.team.demo.generator.service.UserService;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

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
@RequestMapping("/secure/user")
public class UserController {

    static Map<Integer, User> users = Collections.synchronizedMap(new HashMap<>());



    @Resource
    UserMapper userMapper;

    @Resource
    DetailedDataMapper detailedMapper;

    @Resource
    ImageMapper imageMapper;

    @Resource
    UserService userService;

    @Resource
    DataService dataService;



    @GetMapping("/{id}")
    public User getUser(@PathVariable Integer id) {
        /*if(!tokens.contains(token))
        {
            return null;
        }*/
        User user = userMapper.findById(id);
        List<Image> imageL = imageMapper.findUserImages(id);
        user.setImageNum(imageL.size());
        return user;
    }





    /*@GetMapping
    public Result<?> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String search)
    {
        userMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<User>lambdaQuery().like(User::getUsername,search));
        return Result.success();
    }*/



    @GetMapping("/getLocation/{id}")
    public List<DetailedData> locateLink(@RequestParam double latitude,
                                         @RequestParam double longitude,@RequestParam double radius)
    {
        List<DetailedData> DetailedDataL = detailedMapper.findAll();
        for(DetailedData detailedData : DetailedDataL)
        {
            detailedData.setLatitude(detailedMapper.addLocate(detailedData).getLatitude());
            detailedData.setLongitude(detailedMapper.addLocate(detailedData).getLongitude());
            //detailedMapper.addLocate(detailedData);
        }
        return dataService.around(latitude,longitude,DetailedDataL,radius);
    }
}

