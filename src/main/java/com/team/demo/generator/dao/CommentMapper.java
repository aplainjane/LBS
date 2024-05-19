package com.team.demo.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.demo.generator.entity.Comment;
import com.team.demo.generator.entity.Image;
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
public interface CommentMapper extends BaseMapper<Comment> {


    List<Comment> findComment(Integer imageid);

    List<Comment> findUserComment(Integer userid);
}
