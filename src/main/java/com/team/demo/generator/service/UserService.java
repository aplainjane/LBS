package com.team.demo.generator.service;

import com.team.demo.generator.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */
public interface UserService extends IService<User> {
    String login(User user);
}
