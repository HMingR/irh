/*
 Navicat Premium Data Transfer

 Source Server         : win_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : irh

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 20/02/2020 15:43:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_collection
-- ----------------------------
DROP TABLE IF EXISTS `article_collection`;
CREATE TABLE `article_collection`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户收藏表主键',
  `article_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '文章id',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-无效  2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_info
-- ----------------------------
DROP TABLE IF EXISTS `article_info`;
CREATE TABLE `article_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章/帖子表主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `article_category` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '文章/帖子分类id',
  `collect_total` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '收藏总数',
  `up_total` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '点赞总数',
  `browser_times` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '浏览次数',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布者id',
  `review_total` int(255) NULL DEFAULT NULL COMMENT '留言总数',
  `main_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图片',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效  2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_info
-- ----------------------------
INSERT INTO `article_info` VALUES (1, '测试', 1, 12, 2222, 23504, 5, 232, NULL, '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', 2, '2020-02-14 15:48:03', '2020-02-16 16:00:48');
INSERT INTO `article_info` VALUES (2, '测试2', 1, 23, 231, 2422, 5, 45, NULL, '靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠靠', 2, '2020-02-15 16:07:35', '2020-02-16 13:51:10');

-- ----------------------------
-- Table structure for article_review
-- ----------------------------
DROP TABLE IF EXISTS `article_review`;
CREATE TABLE `article_review`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章评论表主键',
  `article_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '文章编号id',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `first_class_id` bigint(20) NULL DEFAULT 0 COMMENT '一级留言的id,也就是顶级parent_id,当parent_id为0时该值也为0',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '回复消息的id，0表示是新的留言的时候 ',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '内容',
  `up_total` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '点赞总数',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章分类表主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `category_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '分类id',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效  2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_tag_category
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_category`;
CREATE TABLE `article_tag_category`  (
  `id` bigint(20) NOT NULL COMMENT '文章/帖子标签分类表',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-无效 2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_rel`;
CREATE TABLE `article_tag_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '帖子标签关系表',
  `tag_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '标签id',
  `article_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '文章id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_info
-- ----------------------------
DROP TABLE IF EXISTS `auth_info`;
CREATE TABLE `auth_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动生成的id',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父权限id',
  `auth_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限名称',
  `auth_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限描述',
  `http_method` char(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问该权限的http方法',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '状态 1:删除 2:无效 2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_info
-- ----------------------------
INSERT INTO `auth_info` VALUES (1, 0, '权限', '/auth/**', '*', '2019-12-02 18:34:59', '2020-01-30 17:03:19', 2);
INSERT INTO `auth_info` VALUES (2, 1, '权限:查看', '/auth/list/**', 'post', '2019-12-18 15:28:55', '2020-01-30 17:03:26', 2);
INSERT INTO `auth_info` VALUES (3, 1, '权限:修改', '/auth/edit', NULL, '2019-12-18 19:04:01', '2020-01-30 14:15:54', 2);
INSERT INTO `auth_info` VALUES (4, 0, '用户', '/admin/**', NULL, '2020-01-30 14:15:45', '2020-01-30 14:16:21', 2);
INSERT INTO `auth_info` VALUES (5, 4, '用户:查看所有', '/admin/list/**', NULL, '2020-01-30 14:16:16', '2020-01-30 14:16:42', 2);
INSERT INTO `auth_info` VALUES (6, 4, '用户:查询管理员', '/admin/list/1', NULL, '2020-01-30 14:16:56', '2020-01-30 14:17:19', 2);
INSERT INTO `auth_info` VALUES (7, 4, '用户:查看会员', '/admin/list/2', NULL, '2020-01-30 14:17:43', NULL, 2);
INSERT INTO `auth_info` VALUES (8, 4, '用户:添加', '/admin', NULL, '2020-01-30 14:18:24', NULL, 2);
INSERT INTO `auth_info` VALUES (9, 4, '用户:查询单个', '/admin/*', NULL, '2020-01-30 14:19:10', NULL, 2);
INSERT INTO `auth_info` VALUES (10, 4, '用户:修改用户角色', '/admin/adminRole', NULL, '2020-01-30 14:20:05', NULL, 2);
INSERT INTO `auth_info` VALUES (11, 4, '用户:删除角色', '/admin/*/*', NULL, '2020-01-30 14:22:40', NULL, 2);

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
  `create_management_id` bigint(50) UNSIGNED NULL DEFAULT NULL COMMENT '创建人编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role_rel
-- ----------------------------
INSERT INTO `auth_role_rel` VALUES (1, 1, 1, '2019-12-18 15:21:22', '2020-02-04 14:18:13', 5);
INSERT INTO `auth_role_rel` VALUES (2, 1, 2, '2019-12-24 18:15:56', '2020-02-04 14:18:09', 5);
INSERT INTO `auth_role_rel` VALUES (3, 2, 2, '2019-12-24 18:16:29', '2020-02-04 14:18:08', 5);

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
-- Table structure for errand_info
-- ----------------------------
DROP TABLE IF EXISTS `errand_info`;
CREATE TABLE `errand_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '跑腿表主键',
  `publisher_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布者id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务内容',
  `requirement` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '要求',
  `money` decimal(10, 0) UNSIGNED NULL DEFAULT NULL COMMENT '价钱',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-删除 2-有效 3-无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of errand_info
-- ----------------------------
INSERT INTO `errand_info` VALUES (1, 5, '帮我测试一下', '测试看看要求', 100, '2020-02-11 20:24:25', '2020-02-15 12:16:48', 3);

-- ----------------------------
-- Table structure for errand_order
-- ----------------------------
DROP TABLE IF EXISTS `errand_order`;
CREATE TABLE `errand_order`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '跑腿订单表主键',
  `order_code` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `errand_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '跑腿信息表主键',
  `holder_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '接单者id',
  `publisher_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布者id',
  `pay_money` decimal(10, 0) UNSIGNED NULL DEFAULT NULL COMMENT '支付金额',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `finish_time` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单完成时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT 3 COMMENT '1-取消订单  2-删除 3-未完成 4-已完成 5-下单失败',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of errand_order
-- ----------------------------
INSERT INTO `errand_order` VALUES (1, '1228517271270326272', 1, 5, NULL, NULL, NULL, NULL, NULL, 5);
INSERT INTO `errand_order` VALUES (2, '1228519277410123776', 1, 5, NULL, NULL, NULL, NULL, NULL, 5);
INSERT INTO `errand_order` VALUES (3, '1228519513171951616', 1, 5, NULL, NULL, NULL, NULL, NULL, 5);
INSERT INTO `errand_order` VALUES (4, '1228519952865034240', 1, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `errand_order` VALUES (5, '1228529327893643264', 1, 5, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `errand_order` VALUES (6, '1228529887573180416', 1, 5, NULL, NULL, NULL, NULL, NULL, 5);
INSERT INTO `errand_order` VALUES (7, '1228530539405770752', 1, 5, NULL, NULL, NULL, NULL, NULL, 3);
INSERT INTO `errand_order` VALUES (8, '1228531165258842112', 1, 5, NULL, NULL, NULL, NULL, NULL, 3);
INSERT INTO `errand_order` VALUES (9, '1228532441048678400', 1, 5, NULL, NULL, NULL, NULL, NULL, 3);
INSERT INTO `errand_order` VALUES (10, '1228532893467279360', 1, 5, NULL, NULL, NULL, NULL, NULL, 3);
INSERT INTO `errand_order` VALUES (11, '1228533131267538944', 1, 5, NULL, NULL, NULL, NULL, NULL, 3);
INSERT INTO `errand_order` VALUES (12, '1228533591533682688', 1, 5, NULL, NULL, NULL, NULL, NULL, 3);

-- ----------------------------
-- Table structure for forum_hot_topic
-- ----------------------------
DROP TABLE IF EXISTS `forum_hot_topic`;
CREATE TABLE `forum_hot_topic`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章模块热搜表',
  `target_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '目标id',
  `score` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT 'redis中存储的score',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) NULL DEFAULT 2 COMMENT '2-有效  1-无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_hot_topic
-- ----------------------------
INSERT INTO `forum_hot_topic` VALUES (1, 1, 108, NULL, '2020-02-16 15:33:30', 2);
INSERT INTO `forum_hot_topic` VALUES (2, 2, 16, NULL, '2020-02-16 11:58:23', NULL);

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
-- Table structure for irh_template
-- ----------------------------
DROP TABLE IF EXISTS `irh_template`;
CREATE TABLE `irh_template`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '模板表',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模板名称',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '模板描述',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-文章模板  2-商品详情页模板',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板内容',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效  2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for management_info
-- ----------------------------
DROP TABLE IF EXISTS `management_info`;
CREATE TABLE `management_info`  (
  `id` bigint(20) NOT NULL COMMENT '管理人员表主键',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '姓名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:服务人员  20:校园组织 25:公益组织 30:校园社团 40:管理员',
  `desc` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:注销 20:锁定 30:审核中 40:审核通过',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of management_info
-- ----------------------------
INSERT INTO `management_info` VALUES (1, 'hmr', '$2a$10$QDGDZDkNan7AKACYqjMoxOrJoHKs4HfzxfFQStzyQtfuyRMMZUsu2', 40, '超级管理员', '2019-12-02 18:30:15', '2019-12-02 18:31:00', 2);
INSERT INTO `management_info` VALUES (2, 'ls', '$2a$10$QDGDZDkNan7AKACYqjMoxOrJoHKs4HfzxfFQStzyQtfuyRMMZUsu2', 30, '管理员', '2020-01-21 19:13:40', '2020-01-21 19:13:57', 2);
INSERT INTO `management_info` VALUES (3, 'zs', '$2a$10$QDGDZDkNan7AKACYqjMoxOrJoHKs4HfzxfFQStzyQtfuyRMMZUsu2', 30, '管理员', '2020-01-21 19:14:31', NULL, 2);

-- ----------------------------
-- Table structure for news_info
-- ----------------------------
DROP TABLE IF EXISTS `news_info`;
CREATE TABLE `news_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '消息表主键',
  `receiver_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '接收方id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标题',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '内容',
  `target_id` bigint(20) NULL DEFAULT NULL COMMENT '根据news_type指向不同表的id',
  `news_type` tinyint(1) NULL DEFAULT NULL COMMENT '消息类型 10-订单  20-商品留言  30-商品评价',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `state` tinyint(255) UNSIGNED NULL DEFAULT 20 COMMENT '10:删除  20:已读  30:未读 ',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `sender_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发送方id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news_info
-- ----------------------------
INSERT INTO `news_info` VALUES (1, NULL, '测试', '测试内容', NULL, NULL, '2020-01-18 11:23:02', NULL, NULL, 0);
INSERT INTO `news_info` VALUES (2, NULL, '测试', '测试内容', NULL, NULL, '2020-01-18 11:24:20', NULL, NULL, 0);

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clientId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expiresAt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  `lastModifiedAt` timestamp(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('app', NULL, 'app', 'app', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `oauth_client_details` VALUES ('irhWebApp', NULL, '$2a$10$js00/YC69RHrLISBfhh8SuTySOyhOoh.wKM/iFDEEKwqwegaKezjG', 'app', 'authorization_code,password,refresh_token,client_credentials', NULL, NULL, 43200, 43200, NULL, NULL);

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(48) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `order_remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '订单标题',
  `address` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '送货地址:将楼号、楼层、宿舍号以json格式存储',
  `trade_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 10 COMMENT '10:线上交易 20:线下交易 30:公益捐赠',
  `payment_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `finish_time` timestamp(0) NULL DEFAULT NULL COMMENT '交易完成时间,用户确定收货的时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:订单超时 20:取消订单 30:删除订单 40:等待支付 50:交易成功 ',
  `order_code` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号,必须保证唯一,且64位之内64个字符以内,只能包含字母、数字、下划线',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (1, NULL, '', NULL, NULL, 0.00, '', '', 10, NULL, '2020-01-17 16:59:22', NULL, NULL, NULL, '');

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
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1:无效  2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category_info
-- ----------------------------
INSERT INTO `product_category_info` VALUES (1, 0, '1', '1', '2019-12-22 11:27:17', '2019-12-22 14:35:40', 2);
INSERT INTO `product_category_info` VALUES (2, 0, '2', '2', '2019-12-22 11:27:29', '2019-12-22 14:35:41', 2);
INSERT INTO `product_category_info` VALUES (3, 1, '3', '3', '2019-12-22 14:26:46', '2019-12-22 14:35:42', 2);
INSERT INTO `product_category_info` VALUES (4, 3, '4', '4', '2019-12-22 14:27:04', '2019-12-22 14:35:44', 2);
INSERT INTO `product_category_info` VALUES (5, 2, '5', '5', '2019-12-22 14:27:24', '2019-12-22 14:35:46', 2);

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
-- Table structure for product_demand_info
-- ----------------------------
DROP TABLE IF EXISTS `product_demand_info`;
CREATE TABLE `product_demand_info`  (
  `id` bigint(20) NOT NULL COMMENT '会员需求表主键',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `consumer_id` bigint(20) NULL DEFAULT NULL COMMENT '发布人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后一次更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态 1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product_evaluate_info
-- ----------------------------
DROP TABLE IF EXISTS `product_evaluate_info`;
CREATE TABLE `product_evaluate_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品评价表id',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品名称',
  `buyer_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '买家编号',
  `saler_id` bigint(20) NULL DEFAULT NULL COMMENT '卖家编号',
  `order_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '订单id',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '内容',
  `product_quality_evaluate` tinyint(1) UNSIGNED NULL DEFAULT 10 COMMENT '对商品质量的评价,0~10个等级',
  `saler_service_evaluate` tinyint(1) UNSIGNED NULL DEFAULT 10 COMMENT '对卖家服务的评价,0~10个等级',
  `whole_evaluate` tinyint(1) UNSIGNED NULL DEFAULT 10 COMMENT '整体评价等级,0~10个等级',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(255) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效',
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
  `product_desc` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '商品描述',
  `product_details_page` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品详情页',
  `trade_type` tinyint(1) NULL DEFAULT 10 COMMENT '10-正常交易  20-公益捐赠',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-无效 2-有效',
  `consumer_id` bigint(20) NULL DEFAULT NULL COMMENT '出售商品的人的id',
  `buying_time` char(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '卖家买入该商品的时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '', '', '', NULL, NULL, 5, NULL, '', 10, NULL, '2020-01-21 10:45:54', NULL, NULL, 5, NULL);

-- ----------------------------
-- Table structure for product_message
-- ----------------------------
DROP TABLE IF EXISTS `product_message`;
CREATE TABLE `product_message`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品留言表主键',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `consumer_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户编号',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '回复消息的id，0表示是新的留言的时候 ',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for report_feedback_info
-- ----------------------------
DROP TABLE IF EXISTS `report_feedback_info`;
CREATE TABLE `report_feedback_info`  (
  `id` bigint(20) NOT NULL COMMENT '举报反馈表的主键',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-商品举报 2-留言举报 3-评价举报 4-帖子举报',
  `target_id` bigint(20) NULL DEFAULT NULL COMMENT ' 举报对象的id',
  `customer_id` bigint(20) NULL DEFAULT NULL COMMENT '举报人',
  `reason` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '举报理由',
  `result` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '处理结果 1-举报失败 2-处理中 3-警告 4-冻结账',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员审核反馈',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '举报时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report_feedback_info
-- ----------------------------
INSERT INTO `report_feedback_info` VALUES (0, 2, 20000004, 10000002, NULL, 0, NULL, '2020-01-16 12:29:32', NULL, 2);
INSERT INTO `report_feedback_info` VALUES (1, 1, 10000009, 90000001, NULL, 1, '未发现', '2020-01-16 12:26:02', '2020-01-16 12:30:47', 2);
INSERT INTO `report_feedback_info` VALUES (2, 1, 10000009, 90000002, NULL, 0, '', '2020-01-16 12:26:10', '2020-01-16 12:30:49', 2);
INSERT INTO `report_feedback_info` VALUES (3, 1, 10000009, 90000003, NULL, 0, NULL, '2020-01-16 12:27:24', '2020-01-16 12:30:52', 2);
INSERT INTO `report_feedback_info` VALUES (4, 1, 10000009, 90000004, NULL, 0, NULL, '2020-01-16 12:28:13', '2020-01-16 12:30:54', 2);
INSERT INTO `report_feedback_info` VALUES (5, 2, 20000004, 90000004, NULL, 0, NULL, '2020-01-16 12:28:57', '2020-01-16 12:30:56', 2);
INSERT INTO `report_feedback_info` VALUES (7, 2, 20000004, 90000008, NULL, 0, NULL, '2020-01-16 12:30:31', '2020-01-16 12:31:02', 2);
INSERT INTO `report_feedback_info` VALUES (8, 3, 30000005, 90000004, NULL, 2, '举报成功', '2020-01-16 12:31:17', '2020-01-16 12:32:04', 2);
INSERT INTO `report_feedback_info` VALUES (9, 3, 30000004, 90000012, NULL, 0, NULL, '2020-01-16 12:31:59', '2020-01-16 12:32:19', 2);
INSERT INTO `report_feedback_info` VALUES (10, 3, 30000005, 90000012, NULL, 0, '', '2020-01-16 12:32:25', '2020-01-16 12:32:49', 2);

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动递增的id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限描述',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '状态 1:无效  2:有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `create_management` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人姓名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_info
-- ----------------------------
INSERT INTO `role_info` VALUES (1, 'admin', '超级管理员', 2, '2019-12-02 18:31:58', '2019-12-24 18:15:07', '1');
INSERT INTO `role_info` VALUES (2, 'user1', '普通管理员', 2, '2019-12-02 18:33:15', '2019-12-24 19:30:37', '1');
INSERT INTO `role_info` VALUES (3, '3', '3', 1, '2019-12-18 16:16:48', '2019-12-18 16:21:32', NULL);

-- ----------------------------
-- Table structure for static_template
-- ----------------------------
DROP TABLE IF EXISTS `static_template`;
CREATE TABLE `static_template`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '模板表id',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板内容',
  `desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板说明',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态  1-无效  2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_forum_attribute
-- ----------------------------
DROP TABLE IF EXISTS `user_forum_attribute`;
CREATE TABLE `user_forum_attribute`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户论坛点赞表主键',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `target_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '点赞对象id',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-文章   2-评论',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动生成的id',
  `age` tinyint(1) UNSIGNED NULL DEFAULT 18 COMMENT '年龄',
  `email` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录用的邮箱',
  `password` char(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'md5加密的密码',
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
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:普通会员 20:服务人员  30:校园组织 35:公益组织 40:校园社团 50:管理员',
  `portrait` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 18, NULL, '$2a$10$dkGY5PlR8uERXm/l0FZ/GOEM7AwixXBmhxfs8fq65KHSVygD/cyJm', NULL, 'ww', NULL, NULL, '10000', NULL, NULL, NULL, NULL, NULL, '北京大学', '信息工程学院', '软件工程', NULL, '2020-02-06 15:51:27', NULL, NULL, NULL);
INSERT INTO `user_info` VALUES (2, 18, '', '$2a$10$dkGY5PlR8uERXm/l0FZ/GOEM7AwixXBmhxfs8fq65KHSVygD/cyJm', '', '里斯', '', 2, '12315', '', '', '', '', '', '北大青鸟', '食品工程学院', '食品安全', 10, '2020-02-06 15:52:24', '2019-11-26 11:23:34', NULL, NULL);
INSERT INTO `user_info` VALUES (3, 18, '', '$2a$10$dkGY5PlR8uERXm/l0FZ/GOEM7AwixXBmhxfs8fq65KHSVygD/cyJm', '', 'zhangsan', '', 2, '120', '', '', '', '', '', '黑马', '生物工程', '', 10, '2020-02-06 15:53:02', NULL, 50, NULL);
INSERT INTO `user_info` VALUES (4, 18, '2452035127@qq.com', '$2a$10$dkGY5PlR8uERXm/l0FZ/GOEM7AwixXBmhxfs8fq65KHSVygD/cyJm', NULL, '赵六', '2452035127', 1, '119', '22', '5', '512', NULL, NULL, '清华大学', '信息工程学院', '软件工程', 10, '2020-02-19 11:10:55', NULL, 40, NULL);
INSERT INTO `user_info` VALUES (5, 18, '1978773465@qq.com', '$2a$10$dkGY5PlR8uERXm/l0FZ/GOEM7AwixXBmhxfs8fq65KHSVygD/cyJm', '', 'hmr', '1978773465@qq.com', 2, '110', '23', '9', '922', '', '', '內蒙古科技大学', '信息工程学院', '软件工程', 40, '2020-02-06 15:52:52', '2019-12-20 17:17:05', 50, NULL);

-- ----------------------------
-- Table structure for user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `user_role_rel`;
CREATE TABLE `user_role_rel`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员角色表的主键',
  `staff_id` bigint(10) UNSIGNED NOT NULL COMMENT '工作人员表的id',
  `role_id` bigint(10) UNSIGNED NOT NULL COMMENT '角色表中的id',
  `create_ management _id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) NULL DEFAULT 2 COMMENT '状态 1:无效 2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role_rel
-- ----------------------------
INSERT INTO `user_role_rel` VALUES (1, 5, 1, 1, '2019-12-02 18:34:02', '2020-01-28 15:40:06', 2);
INSERT INTO `user_role_rel` VALUES (2, 5, 2, 1, '2019-12-02 18:34:20', '2020-01-28 15:40:09', 2);

SET FOREIGN_KEY_CHECKS = 1;
