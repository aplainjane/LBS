package com.team.demo.generator.dao;

import com.team.demo.generator.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface ImageMapper extends BaseMapper<Image> {

    void addImage(Image image);

    List<Image> findAllImages();

    List<Image> findUserImages(Integer userId);
    //List<Image> findUserImages(Integer userId);
}
