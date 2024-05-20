package com.team.demo.generator.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.demo.config.Result;
import com.team.demo.config.TokenEncryption;
import com.team.demo.generator.controller.jwt.JwtUtil;
import com.team.demo.generator.dao.DetailedDataMapper;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.entity.DetailedData;
import com.team.demo.generator.entity.Location;
import com.team.demo.generator.entity.User;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.service.DataService;
import com.team.demo.generator.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        user.setPassword("you can't know!!!");
        List<Image> imageL = imageMapper.findUserImages(id);
        user.setImageNum(imageL.size());
        //Integer userId = (Integer) request.getAttribute("id");
        return user;
    }

    @PutMapping("/updateuser")
    public Result<?> getUser(@RequestBody User user, HttpServletRequest request) {
        // 在 Controller 方法中直接使用 request 对象
        Integer id1 = (Integer) request.getAttribute("id");
        Integer id2 = user.getId();
        if(id2 != id1)
        {
            return Result.error("403","无权限");
        }
        userMapper.updateById(user);
        return Result.success();
    }





    /*@GetMapping
    public Result<?> findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize,@RequestParam String search)
    {
        userMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<User>lambdaQuery().like(User::getUsername,search));
        return Result.success();
    }*/



    @GetMapping("/getLocation")
    public List<DetailedData> locateLink(@RequestParam double latitude,
                                         @RequestParam double longitude,@RequestParam double radius)
    {
        List<DetailedData> dataL = detailedMapper.findAll();
        for(DetailedData detailedData : dataL)
        {
            detailedData.setLatitude(detailedMapper.addLocate(detailedData).getLatitude());
            detailedData.setLongitude(detailedMapper.addLocate(detailedData).getLongitude());
            //detailedMapper.addLocate(detailedData);
        }
        return dataService.around(116,36,dataL,500000);
    }

    @GetMapping("/poi/all")
    public List<DetailedData> getpoi()
    {
        List<DetailedData> detailedDataL = detailedMapper.findAll();
        for(DetailedData detailedData : detailedDataL)
        {
            Location temp = detailedMapper.addLocate(detailedData);
            detailedData.setLatitude(temp.getLatitude());
            detailedData.setLongitude(temp.getLongitude());
            //detailedMapper.addLocate(detailedData);
        }
        return detailedDataL;

    }

    @PostMapping("/poi/add")
    public Result<?> addPoi(@RequestBody DetailedData detailedData,@RequestBody Location location,HttpServletRequest request)
    {
        Integer id1 = (Integer) request.getAttribute("id");
        if(id1 != 1)
        {
            return Result.error("403","无权限");
        }
        detailedMapper.insert(detailedData);
        detailedMapper.insertLocation(location.getLongitude(),location.getLatitude(),detailedData.getCode());
        return Result.success();
    }

    @DeleteMapping("/poi/delete")
    public Result<?> deletePoi(@RequestParam String code,HttpServletRequest request)
    {
        Integer id1 = (Integer) request.getAttribute("id");
        if(id1 != 1)
        {
            return Result.error("403","无权限");
        }
        DetailedData detailedData = detailedMapper.findBycode(code);
        //detailedMapper.addLocate(detailedData);
        detailedMapper.deleteById(detailedData);
        detailedMapper.deleteBycode(code);
        return Result.success();
    }

    @PostMapping("/poi/update")
    public Result<?> updatePoi(@RequestBody List<DetailedData> list)
    {
        for(DetailedData data : list)
        {
            detailedMapper.insertLocation(data.getLongitude(),data.getLatitude(), data.getCode());
        }
        return Result.success();
    }

}

