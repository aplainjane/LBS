package com.team.demo.generator.service.impl;

public class GeoUtils {

    /**
     * 计算两点间的球面距离。
     *
     * @param lat1 第一个点的纬度
     * @param lon1 第一个点的经度
     * @param lat2 第二个点的纬度
     * @param lon2 第二个点的经度
     * @return 两点间的距离，单位为千米
     */
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // 地球半径，单位为千米
        final int R = 6371;

        // 将纬度和经度转换为弧度
        double lat1Radians = Math.toRadians(lat1);
        double lat2Radians = Math.toRadians(lat2);
        double lon1Radians = Math.toRadians(lon1);
        double lon2Radians = Math.toRadians(lon2);

        // 计算纬度和经度之差
        double deltaLat = lat2Radians - lat1Radians;
        double deltaLon = lon2Radians - lon1Radians;

        // 使用Haversine公式计算两点间的球面距离
        double a = Math.pow(Math.sin(deltaLat / 2), 2) +
                Math.cos(lat1Radians) * Math.cos(lat2Radians) *
                        Math.pow(Math.sin(deltaLon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }
}
