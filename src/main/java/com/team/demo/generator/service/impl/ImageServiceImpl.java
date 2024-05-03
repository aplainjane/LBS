package com.team.demo.generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.demo.generator.dao.ImageMapper;
import com.team.demo.generator.dao.UserMapper;
import com.team.demo.generator.entity.Image;
import com.team.demo.generator.service.ImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {



    @Override
    public List<Image> around(double centerLon, double centerLat, List<Image> images,double radius) {

        List<Image> imagesWithinRadius = new ArrayList<>();

        for (Image image : images) {
            double distance = GeoUtils.calculateDistance(centerLat, centerLon, image.getLatitude(), image.getLongitude());
            if (distance <= radius) {
                imagesWithinRadius.add(image);
            }
        }

        return imagesWithinRadius;

    }
}
