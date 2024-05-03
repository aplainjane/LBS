package com.team.demo.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.demo.generator.dao.DetailedDataMapper;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.entity.DetailedData;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.service.DataService;
import com.team.demo.generator.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ky
 * @since 2024年04月08日
 */
@Service
public class DataServiceImpl extends ServiceImpl<DetailedDataMapper, DetailedData> implements DataService {



    @Override
    public List<DetailedData> around(double centerLon, double centerLat, List<DetailedData> datas,double radius) {

        List<DetailedData> imagesWithinRadius = new ArrayList<>();

        for (DetailedData data : datas) {
            double distance = GeoUtils.calculateDistance(centerLat, centerLon, data.getLatitude(), data.getLongitude());
            if (distance <= radius) {
                imagesWithinRadius.add(data);
            }
        }

        return imagesWithinRadius;

    }
}
