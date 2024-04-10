package com.team.demo.generator.service.impl;

import com.team.demo.generator.entity.User;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.demo.generator.service.UserService;
import org.springframework.stereotype.Service;

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

}
