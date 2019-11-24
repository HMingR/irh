/*
 Navicat Premium Data Transfer

 Source Server         : win_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : school

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 24/11/2019 19:29:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for auth_info
-- ----------------------------
DROP TABLE IF EXISTS `auth_info`;
CREATE TABLE `auth_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动生成的id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父权限id',
  `auth_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限名称',
  `auth_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限描述',
  `create_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建时间',
  `update_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '状态 1:无效 2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_rel`;
CREATE TABLE `auth_role_rel`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色权限表主键',
  `role_id` bigint(10) UNSIGNED NOT NULL COMMENT '角色表中的id',
  `auth_id` bigint(10) UNSIGNED NOT NULL COMMENT '权限表中的id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `create_ management _id` bigint(20) NULL DEFAULT NULL COMMENT '创建人编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for consumer_info
-- ----------------------------
DROP TABLE IF EXISTS `consumer_info`;
CREATE TABLE `consumer_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动生成的id',
  `age` tinyint(1) UNSIGNED NULL DEFAULT 18 COMMENT '年龄',
  `email` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录用的邮箱',
  `password` char(33) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'md5加密的32位密码',
  `alipay_num` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '支付宝账号',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '用户昵称',
  `qq` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'qq账号',
  `gender` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-女 2-男',
  `phone_num` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '手机号',
  `building_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '寝室楼号',
  `building_storey` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '楼层',
  `dorm_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '寝室号',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '真实姓名',
  `certificate_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '证件号码',
  `school_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '学校名称',
  `academy_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '学院',
  `major_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '专业',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 10 COMMENT '10:注销 20:锁定 30:审核中  40:审核通过',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for consumer_interest_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `consumer_interest_tag_rel`;
CREATE TABLE `consumer_interest_tag_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `consumer_id` bigint(20) NULL DEFAULT NULL COMMENT '用户表的主键id',
  `tag_id` bigint(20) NULL DEFAULT NULL COMMENT '标签表的主键id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `score` tinyint(1) UNSIGNED NULL DEFAULT 5 COMMENT '评分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for interest_tag_info
-- ----------------------------
DROP TABLE IF EXISTS `interest_tag_info`;
CREATE TABLE `interest_tag_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '兴趣标签表主键',
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标签名称',
  `manager_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者的id,对应于管理员表中的主键id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态:1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for management_info
-- ----------------------------
DROP TABLE IF EXISTS `management_info`;
CREATE TABLE `management_info`  (
  `id` bigint(20) NOT NULL COMMENT '管理人员表主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '姓名',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:服务人员  20:校园组织 25:公益组织 30:校园社团 40:管理员',
  `desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:注销 20:锁定 30:审核中 40:审核通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for management_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `management_role_rel`;
CREATE TABLE `management_role_rel`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员角色表的主键',
  `staff_id` bigint(10) UNSIGNED NOT NULL COMMENT '工作人员表的id',
  `role_id` bigint(10) UNSIGNED NOT NULL COMMENT '角色表中的id',
  `create_ management _id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) NULL DEFAULT 2 COMMENT '状态 1:无效 2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for news_info
-- ----------------------------
DROP TABLE IF EXISTS `news_info`;
CREATE TABLE `news_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息表主键',
  `receiver_id` bigint(20) NULL DEFAULT NULL COMMENT '接收方id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标题',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '内容',
  `news_type` tinyint(1) NULL DEFAULT NULL COMMENT '消息类型 10:交易失败提示信息 20:提醒卖家发货信息 30:交易成功信息 40:管理员发送卖家警告信息',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `state` tinyint(255) UNSIGNED NULL DEFAULT 30 COMMENT '10:删除  20:发送失败  30:已发送  40:未读  50:已读 ',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单表主键',
  `saler_id` bigint(20) NULL DEFAULT NULL COMMENT '会员表的id',
  `saler_ nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '会员表的nickname字段',
  `buyer_id` bigint(20) NULL DEFAULT NULL COMMENT '会员表的id',
  `product_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `payment_money` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '支付金额',
  `order_remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '订单留言',
  `address` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '送货地址:将楼号、楼层、宿舍号以json格式存储',
  `trade_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 10 COMMENT '10:线上交易 20:线下交易 30:公益捐赠',
  `payment_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `finish_time` timestamp(0) NULL DEFAULT NULL COMMENT '交易完成时间,用户确定收货的时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:订单超时 20:取消订单 30:删除订单 40:交易成功',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for order_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `order_payment_info`;
CREATE TABLE `order_payment_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单支付情况表主键',
  `buyer_id` bigint(20) NOT NULL COMMENT '会员id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单id',
  `platform_transaction _num` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '平台交易号',
  `payment_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '10:支付宝 20:线下',
  `payment_state` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1-失败 2-成功',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_category_info
-- ----------------------------
DROP TABLE IF EXISTS `product_category_info`;
CREATE TABLE `product_category_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品分类信息表的主键',
  `parent_id` bigint(10) UNSIGNED NULL DEFAULT 0 COMMENT '当值为0的时候标识根节点',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分类名称',
  `desc` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类的描述',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1:无效  2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `product_category_rel`;
CREATE TABLE `product_category_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品分类表主键',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `category_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '分类id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_evaluate_info
-- ----------------------------
DROP TABLE IF EXISTS `product_evaluate_info`;
CREATE TABLE `product_evaluate_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品评价表id',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品名称',
  `buyer_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '买家编号',
  `buyer_ nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '买家昵称',
  `order_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '订单id',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '内容',
  `product_quality_evaluate` tinyint(1) UNSIGNED NULL DEFAULT 10 COMMENT '对商品质量的评价,0~10个等级',
  `saler_service_evaluate` tinyint(1) UNSIGNED NULL DEFAULT 10 COMMENT '对卖家服务的评价,0~10个等级',
  `whole_evaluate` tinyint(1) UNSIGNED NULL DEFAULT 10 COMMENT '整体评价等级,0~10个等级',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(255) UNSIGNED NULL DEFAULT 1 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表的主键',
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品名称',
  `main_pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品主图url',
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '计量单位',
  `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品原价',
  `sale_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '售卖价格',
  `old_degree` tinyint(1) NULL DEFAULT 5 COMMENT '商品的新旧程度,1~10数值越大越新',
  `product_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品标题',
  `product_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品描述',
  `product_details_page` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品详情页',
  `trade_type` tinyint(1) NULL DEFAULT 10 COMMENT '10-正常交易  20-公益捐赠',
  `parent_category_id` bigint(20) NULL DEFAULT NULL COMMENT '父级分类id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_message
-- ----------------------------
DROP TABLE IF EXISTS `product_message`;
CREATE TABLE `product_message`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品留言表主键',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `consumer_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户编号',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '回复消息的id，-1表示是新的留言的时候 ',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动递增的id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限描述',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '状态 0:无效  1:有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `create_management _id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
