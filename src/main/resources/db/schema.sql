/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.13
Source Server Version : 50729
Source Host           : 192.168.1.13:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50729
File Encoding         : 65001

Date: 2020-03-12 20:04:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `age` int(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)
