package com.team.demo.generator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team.demo.generator.entity.DetailedData;
import com.team.demo.generator.entity.Image;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */
public interface DataService extends IService<DetailedData> {
    List<DetailedData> around(double longitude, double latitude, List<DetailedData> detailedData, double radius);
}
