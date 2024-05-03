package com.team.demo.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.entity.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */
public interface ImageService extends IService<Image> {
    List<Image> around(double longitude,double latitude,List<Image> images,double radius);
}
