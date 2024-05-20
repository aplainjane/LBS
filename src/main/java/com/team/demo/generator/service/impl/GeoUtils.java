package com.team.demo.generator.service.impl;

public class GeoUtils {

    /**
     * 计算两点间的球面距离。
     *
     * @param startLatitude 第一个点的纬度
     * @param startLongitude 第一个点的经度
     * @param endLatitude 第二个点的纬度
     * @param endLongitude 第二个点的经度
     * @return 两点间的距离，单位为千米
     */
    public static double calculateDistance(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
        // 地球半径，单位为米
        final double EARTH_RADIUS = 6371393.0;

        // 将纬度和经度转换为弧度
        double startLatitudeRadians = Math.toRadians(startLatitude);
        double endLatitudeRadians = Math.toRadians(endLatitude);
        double startLongitudeRadians = Math.toRadians(startLongitude);
        double endLongitudeRadians = Math.toRadians(endLongitude);

        // 计算纬度和经度之差
        double deltaLatitude = endLatitudeRadians - startLatitudeRadians;
        double deltaLongitude = endLongitudeRadians - startLongitudeRadians;

        // 使用Haversine公式计算两点间的球面距离
        double a = Math.pow(Math.sin(deltaLatitude / 2), 2) +
                Math.cos(startLatitudeRadians) * Math.cos(endLatitudeRadians) *
                        Math.pow(Math.sin(deltaLongitude / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = EARTH_RADIUS * c;

        //System.out.println("Distance: " + distance);
        return distance;
    }
}
