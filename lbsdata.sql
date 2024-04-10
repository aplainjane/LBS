/*
 Navicat Premium Data Transfer

 Source Server         : sonar
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : lbsdata

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 09/04/2024 22:48:05
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'Alice', 'password123');
INSERT INTO `user` VALUES (2, 'Bob', 'password456');
INSERT INTO `user` VALUES (3, 'Charlie', 'password789');
INSERT INTO `user` VALUES (4, 'Diana', 'password012');
INSERT INTO `user` VALUES (5, 'Lucy', 'Team A');

SET FOREIGN_KEY_CHECKS = 1;
