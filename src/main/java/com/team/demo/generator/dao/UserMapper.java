package com.team.demo.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.demo.generator.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    List<User> queryUserByIds(@Param("ids") List<Integer> ids);

    /*void updateBalanceByIds(@Param(Constants.WRAPPER) QueryWrapper<User> wrapper, @Param("amount") int amount);

    @Update("UPDATE user SET distance = distance - #{meter} WHERE id = #{id}")
    void deductBalance(@Param("id") int id, @Param("distance")int distance);*/

    List<User> findAll();

    User findById(Integer id);

    void deleteById(Integer id);

    void add(User user);

    int updateById(User user);


    Page<User> findP(Integer page);



}
