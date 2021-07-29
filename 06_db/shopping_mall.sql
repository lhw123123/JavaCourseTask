/*
 Navicat MySQL Data Transfer

 Source Server         : 82.156.201.64_lhw
 Source Server Type    : MySQL
 Source Server Version : 50733
 Source Host           : 82.156.201.64
 Source Database       : shopping_mall

 Target Server Type    : MySQL
 Target Server Version : 50733
 File Encoding         : utf-8

 Date: 07/29/2021 15:46:12 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `tb_delivery_address`
-- ----------------------------
DROP TABLE IF EXISTS `tb_delivery_address`;
CREATE TABLE `tb_delivery_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `address` varchar(200) NOT NULL DEFAULT '' COMMENT '收获地址',
  `default` tinyint(4) NOT NULL DEFAULT '0' COMMENT '默认地址（0否，1是）',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0正常，1删除）',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人id',
  `updater_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户收获地址表';

-- ----------------------------
--  Table structure for `tb_goods`
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods`;
CREATE TABLE `tb_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `stock_count` int(11) NOT NULL DEFAULT '0' COMMENT '库存数量',
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '商品状态（0上架，1下架）',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0正常，1删除）',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人id',
  `updater_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
--  Table structure for `tb_goods_detail`
-- ----------------------------
DROP TABLE IF EXISTS `tb_goods_detail`;
CREATE TABLE `tb_goods_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `goods_id` int(11) NOT NULL COMMENT '商品id',
  `introduce` varchar(200) NOT NULL COMMENT '商品介绍',
  `producing_area` varchar(32) NOT NULL DEFAULT '' COMMENT '商品产地',
  `produced_date` datetime NOT NULL COMMENT '生产日期',
  `expiration_date` datetime DEFAULT NULL COMMENT '过期时间',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0正常，1删除）',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人id',
  `updater_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品详情表';

-- ----------------------------
--  Table structure for `tb_order`
-- ----------------------------
DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `total_amount` decimal(10,2) NOT NULL COMMENT '订单总金额',
  `coupon_amount` decimal(10,2) DEFAULT NULL COMMENT '优惠金额',
  `receivable_amount` decimal(10,2) NOT NULL COMMENT '应收金额',
  `pay_amount` decimal(10,2) NOT NULL COMMENT '支付金额',
  `delivery_address_id` int(11) NOT NULL COMMENT '收获地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '订单状态（0待支付，1已支付2，已取消）',
  `refund_status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '退费状态（0未退款，1退款中，2已退款，3退款失败）',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '支付类型（1微信付支付，2支付宝支付，3刷卡支付，4货到付款）',
  `pay_time` datetime DEFAULT NULL COMMENT '订单支付时间',
  `close_time` datetime DEFAULT NULL COMMENT '订单取消时间',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0正常，1删除）',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人id',
  `updater_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
--  Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(32) NOT NULL DEFAULT '' COMMENT '用户姓名',
  `email` varchar(32) NOT NULL DEFAULT '' COMMENT '邮箱',
  `phone` varchar(32) NOT NULL DEFAULT '' COMMENT '手机号',
  `deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除（0正常，1删除）',
  `creator_id` varchar(32) NOT NULL DEFAULT '' COMMENT '创建人id',
  `updater_id` varchar(32) NOT NULL DEFAULT '' COMMENT '更新人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
