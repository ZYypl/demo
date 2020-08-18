/*
Navicat MySQL Data Transfer

Source Server         : demo
Source Server Version : 80018
Source Host           : 172.20.10.11:3306
Source Database       : demo_sxd

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-08-17 16:39:24
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for PEOPLE
-- ----------------------------
DROP TABLE IF EXISTS `PEOPLE`;
CREATE TABLE `PEOPLE` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(11) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
