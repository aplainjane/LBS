package com.team.demo.generator.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.demo.generator.entity.DetailedData;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.entity.Location;
import org.apache.ibatis.annotations.Mapper;

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
public interface DetailedDataMapper extends BaseMapper<DetailedData> {


    Location addLocate(DetailedData detailedData);

    List<DetailedData> findAll();


}
