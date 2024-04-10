package com.team.demo.generator.service.impl;

import com.team.demo.generator.entity.User;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.demo.generator.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    @Override
    public String login(User user) {
        String password = user.getPassword();
        byte[] bytes = password.getBytes();
        //1.将密码加密
        String md5Password = DigestUtils.md5DigestAsHex(bytes);
        user.setPassword(md5Password);
        //2.根据用户名和密码查询数据库
        User userDB = userMapper.findUserByUP(user);
        //3.判断userDB是否有值
        if (userDB == null) {
            //用户名和密码查询错误
            return null;
        }
        //程序走到这里,说明用户名和密码正确 返回token
        String token = UUID.randomUUID().toString();
        return token;
    }
}
