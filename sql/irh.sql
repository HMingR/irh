/*
 Navicat MySQL Data Transfer

 Source Server         : win_MySQL
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : irh

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 22/04/2020 20:49:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_category_info
-- ----------------------------
DROP TABLE IF EXISTS `article_category_info`;
CREATE TABLE `article_category_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '帖子标签关系表',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态 1-无效 2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_category_info
-- ----------------------------

-- ----------------------------
-- Table structure for article_collection_rel
-- ----------------------------
DROP TABLE IF EXISTS `article_collection_rel`;
CREATE TABLE `article_collection_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户收藏表主键',
  `article_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '文章id',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-无效  2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_collection_rel
-- ----------------------------

-- ----------------------------
-- Table structure for article_forward_info
-- ----------------------------
DROP TABLE IF EXISTS `article_forward_info`;
CREATE TABLE `article_forward_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户转发表主键',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '转发人的id',
  `article_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '文章id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '评论',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效  2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_forward_info
-- ----------------------------

-- ----------------------------
-- Table structure for article_info
-- ----------------------------
DROP TABLE IF EXISTS `article_info`;
CREATE TABLE `article_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章/帖子表主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `tag_names` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章/帖子分类id集合',
  `collect_total` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '收藏总数',
  `up_total` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '点赞总数',
  `browser_times` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '浏览次数',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布者id',
  `review_total` int(255) NULL DEFAULT 0 COMMENT '留言总数',
  `main_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '封面图片',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效  2-有效   3-发布中  4-草稿箱',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `forward_times` bigint(20) NULL DEFAULT NULL COMMENT '转发总次数',
  `detail_page` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文章详情页url',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '文章分类id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_info
-- ----------------------------
INSERT INTO `article_info` VALUES (23, '测试', '1,2', 32, 34, 1122, 5, 0, 'group1/M00/00/01/rBgYGV6RdkOACa0DAABW5M6zRWk259.jpg', '', 2, '2020-04-11 16:41:00', '2020-04-14 15:15:06', 12, 'group1/M00/00/01/rBgYGV6Rdl-AUwSJAAAFMqfA8Jo04.html', NULL);
INSERT INTO `article_info` VALUES (24, '测试3', NULL, 0, 0, 2, 8, 12, NULL, '<p>斯大法官</p><p class=\"ql-align-center\"><img src=\"http://39.105.0.169:8080/group1/M00/00/01/rBgYGV6RgoqASQGSAACHVu5mYbk744.jpg\"></p><p class=\"ql-align-center\">啊手动阀手动阀</p>', 2, '2020-04-11 16:41:00', '2020-04-12 15:42:29', 22, NULL, NULL);
INSERT INTO `article_info` VALUES (25, '测试3', NULL, 0, 0, 1, 5, 23, '', '<p>啊手动阀</p><p class=\"ql-align-center\">山东分公司</p>', 1, '2020-04-11 16:48:28', '2020-04-12 15:42:30', 1, '', NULL);
INSERT INTO `article_info` VALUES (26, '测试4', NULL, 0, 0, 2, 8, 0, '', NULL, 1, '2020-04-11 17:03:24', '2020-04-12 15:42:31', 23, 'group1/M00/00/01/rBgYGV6Rh9yAHFHtAAAGzuEUmz432.html', NULL);
INSERT INTO `article_info` VALUES (27, '测试1000', NULL, 0, 0, 10, 5, 0, '', NULL, 1, '2020-04-11 17:06:44', '2020-04-12 15:42:32', 43, 'group1/M00/00/01/rBgYGV6RiKOAHZS6AAAG6UeUZ4M07.html', NULL);
INSERT INTO `article_info` VALUES (28, '101', NULL, 0, 0, 1, 9, 0, '', NULL, 2, '2020-04-11 17:38:11', '2020-04-12 15:42:34', 2, 'group1/M00/00/01/rBgYGV6RkAKAVlwPAAAJEsUuf8I85.html', NULL);
INSERT INTO `article_info` VALUES (29, '啊手动阀手动阀', NULL, 0, 0, 277, 5, 0, '', NULL, 2, '2020-04-11 17:43:50', '2020-04-22 12:06:17', 12, 'group1/M00/00/01/rBgYGV6RkVaAESV4AAAIYImhsWs70.html', NULL);

-- ----------------------------
-- Table structure for article_review_info
-- ----------------------------
DROP TABLE IF EXISTS `article_review_info`;
CREATE TABLE `article_review_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章评论表主键',
  `article_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '文章编号id',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `first_class_id` bigint(20) NULL DEFAULT -1 COMMENT '一级留言的id,也就是顶级parent_id,当parent_id为0时该值也为0',
  `parent_id` bigint(20) NULL DEFAULT -1 COMMENT '回复消息的id，0表示是新的留言的时候 ',
  `content` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '内容',
  `up_total` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '点赞总数',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_review_info
-- ----------------------------
INSERT INTO `article_review_info` VALUES (48, 29, 5, -1, -1, '123123132132', 12, 2, '2020-04-21 21:37:56', '2020-04-22 12:26:16');
INSERT INTO `article_review_info` VALUES (49, 29, 5, 48, -1, '1111111111', 0, 2, '2020-04-21 21:38:10', NULL);
INSERT INTO `article_review_info` VALUES (50, 29, 5, 48, -1, '111111122132324323434534545555555555555555555566666666666666666666666666666666666666', 0, 2, '2020-04-21 21:51:51', NULL);
INSERT INTO `article_review_info` VALUES (51, 29, 5, 48, -1, '1212121211231123321', 0, 2, '2020-04-21 21:54:23', NULL);
INSERT INTO `article_review_info` VALUES (52, 29, 5, 48, 50, '@master    护士的农夫i哦啊季后赛得分咖啡色的理解', 0, 2, '2020-04-21 22:04:58', NULL);
INSERT INTO `article_review_info` VALUES (53, 29, 5, -1, -1, '12312312313', 12, 2, '2020-04-21 22:18:16', '2020-04-22 12:26:18');
INSERT INTO `article_review_info` VALUES (54, 29, 5, -1, -1, '12312312313', 12, 2, '2020-04-21 22:18:30', '2020-04-22 12:26:18');
INSERT INTO `article_review_info` VALUES (55, 29, 5, -1, -1, '121212321123123123321', 4, 2, '2020-04-22 09:52:59', '2020-04-22 12:26:18');
INSERT INTO `article_review_info` VALUES (56, 29, 5, -1, -1, 'wqqwqwqwqwqweqweqewq', 4, 2, '2020-04-22 09:54:28', '2020-04-22 12:26:18');
INSERT INTO `article_review_info` VALUES (57, 29, 5, -1, -1, '11111111', 0, 2, '2020-04-22 09:54:47', NULL);
INSERT INTO `article_review_info` VALUES (58, 29, 5, -1, -1, 'azasdasdfasfdasdf', 0, 2, '2020-04-22 09:55:21', NULL);
INSERT INTO `article_review_info` VALUES (59, 29, 5, -1, -1, '122132132323443543433434r435345345fdffgfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff', 0, 2, '2020-04-22 10:01:42', NULL);
INSERT INTO `article_review_info` VALUES (60, 29, 5, -1, -1, 'dddddddddddddddddxccccccccccccccccc', 0, 2, '2020-04-22 10:01:49', NULL);
INSERT INTO `article_review_info` VALUES (61, 29, 5, -1, -1, 'ccccccccccccccccccccccccccccccccccc', 0, 2, '2020-04-22 10:01:54', NULL);
INSERT INTO `article_review_info` VALUES (62, 29, 5, -1, -1, 'ccc       vvvvvvvvvvvvvvvcxx                                            ', 0, 2, '2020-04-22 10:02:00', NULL);
INSERT INTO `article_review_info` VALUES (63, 29, 5, -1, -1, 'ccc       vvvvvvvvvvvvvvvcxx                                            ', 0, 2, '2020-04-22 10:02:00', NULL);
INSERT INTO `article_review_info` VALUES (64, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:26', NULL);
INSERT INTO `article_review_info` VALUES (65, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:31', NULL);
INSERT INTO `article_review_info` VALUES (66, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:32', NULL);
INSERT INTO `article_review_info` VALUES (67, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:32', NULL);
INSERT INTO `article_review_info` VALUES (68, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:32', NULL);
INSERT INTO `article_review_info` VALUES (69, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:32', NULL);
INSERT INTO `article_review_info` VALUES (70, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:33', NULL);
INSERT INTO `article_review_info` VALUES (71, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:33', NULL);
INSERT INTO `article_review_info` VALUES (72, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:33', NULL);
INSERT INTO `article_review_info` VALUES (73, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:33', NULL);
INSERT INTO `article_review_info` VALUES (74, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:33', NULL);
INSERT INTO `article_review_info` VALUES (75, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:34', NULL);
INSERT INTO `article_review_info` VALUES (76, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:34', NULL);
INSERT INTO `article_review_info` VALUES (77, 29, 5, 48, -1, '测试', 0, 2, '2020-04-22 10:05:34', NULL);
INSERT INTO `article_review_info` VALUES (78, 29, 5, -1, -1, '噢噢噢噢噢噢噢噢噢噢噢噢噢噢噢噢哦哦哦', 0, 2, '2020-04-22 10:06:19', NULL);
INSERT INTO `article_review_info` VALUES (79, 29, 5, -1, -1, '噢噢噢噢噢噢噢噢噢噢噢噢噢噢噢噢哦哦哦', 0, 2, '2020-04-22 10:06:23', NULL);
INSERT INTO `article_review_info` VALUES (80, 29, 5, 48, -1, '11111踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩从踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩踩从', 0, 2, '2020-04-22 10:30:42', NULL);
INSERT INTO `article_review_info` VALUES (81, 29, 5, 48, -1, '12121212121231213213213', 0, 2, '2020-04-22 10:31:53', NULL);
INSERT INTO `article_review_info` VALUES (82, 29, 5, 48, 81, '11111111111', 0, 2, '2020-04-22 10:32:28', NULL);
INSERT INTO `article_review_info` VALUES (83, 29, 5, 48, 81, '是灯光灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌使得否                    士大夫水水水水水水水水水水水水水水水水', 0, 2, '2020-04-22 10:32:43', NULL);
INSERT INTO `article_review_info` VALUES (84, 29, 5, 48, -1, '123333333333333333333333333333333333333333333333333333333333333333333333333', 0, 2, '2020-04-22 10:40:36', NULL);
INSERT INTO `article_review_info` VALUES (85, 29, 5, 48, -1, '呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪苟富贵灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌灌', 0, 2, '2020-04-22 10:40:56', NULL);
INSERT INTO `article_review_info` VALUES (86, 29, 5, 48, -1, '有有有有有有有有有有有有有有有有有有有呱呱呱呱呱呱呱呱呱呱呱呱呱呱呱古古怪怪', 0, 2, '2020-04-22 10:41:25', NULL);
INSERT INTO `article_review_info` VALUES (87, 29, 5, 48, -1, '21111111111111111111111111111111111111111111111113333333333333333333333333333333333333333333333333333', 0, 2, '2020-04-22 10:43:42', NULL);
INSERT INTO `article_review_info` VALUES (88, 29, 5, 48, -1, '122113333333333333333333333333333333333333333333333333333333333', 0, 2, '2020-04-22 10:45:38', NULL);
INSERT INTO `article_review_info` VALUES (89, 29, 5, 48, -1, '122222222222222222222222222222', 0, 2, '2020-04-22 10:46:01', NULL);
INSERT INTO `article_review_info` VALUES (90, 29, 5, 48, -1, '1222222222222222222222', 0, 2, '2020-04-22 10:46:37', NULL);
INSERT INTO `article_review_info` VALUES (91, 29, 5, -1, -1, '11111', 0, 2, '2020-04-22 11:00:28', NULL);
INSERT INTO `article_review_info` VALUES (92, 29, 5, -1, -1, '111111', 0, 2, '2020-04-22 11:04:38', NULL);
INSERT INTO `article_review_info` VALUES (93, 29, 5, -1, -1, '112112211221', 0, 2, '2020-04-22 11:18:57', NULL);
INSERT INTO `article_review_info` VALUES (94, 29, 5, -1, -1, '112112211221', 0, 2, '2020-04-22 11:19:04', NULL);
INSERT INTO `article_review_info` VALUES (95, 29, 5, -1, -1, '12122123の ', 0, 2, '2020-04-22 11:19:15', NULL);
INSERT INTO `article_review_info` VALUES (96, 29, 5, -1, -1, '', 0, 2, '2020-04-22 11:20:42', NULL);
INSERT INTO `article_review_info` VALUES (97, 29, 5, -1, -1, '1231312', 0, 2, '2020-04-22 11:20:46', NULL);
INSERT INTO `article_review_info` VALUES (98, 29, 5, -1, -1, '1111', 0, 2, '2020-04-22 11:24:43', NULL);
INSERT INTO `article_review_info` VALUES (99, 29, 5, -1, -1, '12121212121', 0, 2, '2020-04-22 11:24:53', NULL);
INSERT INTO `article_review_info` VALUES (100, 29, 5, -1, -1, '1111111111', 0, 2, '2020-04-22 11:35:58', NULL);
INSERT INTO `article_review_info` VALUES (101, 29, 5, -1, -1, '1111221212121121221', 0, 2, '2020-04-22 11:36:31', NULL);
INSERT INTO `article_review_info` VALUES (102, 29, 5, -1, -1, '123123123123123123123333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333', 0, 2, '2020-04-22 11:38:03', NULL);
INSERT INTO `article_review_info` VALUES (103, 29, 5, -1, -1, '12333333333333333333333333333333333333333333333333', 0, 2, '2020-04-22 11:38:28', NULL);
INSERT INTO `article_review_info` VALUES (104, 29, 5, -1, -1, '1234444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444444', 0, 2, '2020-04-22 11:38:34', NULL);
INSERT INTO `article_review_info` VALUES (105, 29, 5, -1, -1, '12222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222', 0, 2, '2020-04-22 11:40:14', NULL);
INSERT INTO `article_review_info` VALUES (106, 29, 5, -1, -1, '1243士大夫方法方法烦烦烦烦烦烦烦烦烦烦烦烦水电站反反复复烦烦烦烦烦烦烦烦烦烦烦烦使得否反对水水水水水水水水水水水水水水水水', 0, 2, '2020-04-22 11:42:25', NULL);
INSERT INTO `article_review_info` VALUES (107, 29, 5, -1, -1, '122222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222', 0, 2, '2020-04-22 11:44:47', NULL);
INSERT INTO `article_review_info` VALUES (108, 29, 5, 48, 90, '122222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222撒大苏打实打实大苏打实打实大苏打实打实士大夫方法方法烦烦烦烦烦烦烦烦烦烦烦烦', 0, 2, '2020-04-22 11:45:06', NULL);
INSERT INTO `article_review_info` VALUES (109, 29, 5, -1, -1, '11212333333333333333333333333333333333阿三顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶啊手动阀打发打发打发打发打发打发打发的啊手动阀打发打发打发打发打发打发打发的', 0, 2, '2020-04-22 11:53:43', NULL);
INSERT INTO `article_review_info` VALUES (110, 29, 5, -1, -1, '12222222222222222222222222222223333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333', 0, 2, '2020-04-22 11:54:55', NULL);
INSERT INTO `article_review_info` VALUES (111, 29, 5, -1, -1, '1111', 0, 2, '2020-04-22 11:58:26', NULL);
INSERT INTO `article_review_info` VALUES (112, 29, 5, -1, -1, '11111', 0, 2, '2020-04-22 11:59:17', NULL);
INSERT INTO `article_review_info` VALUES (113, 29, 5, -1, -1, '1122121', 0, 2, '2020-04-22 12:00:39', NULL);
INSERT INTO `article_review_info` VALUES (114, 29, 5, -1, -1, '123111111111133333333333', 0, 2, '2020-04-22 12:01:14', NULL);
INSERT INTO `article_review_info` VALUES (115, 29, 5, -1, -1, '12222222', 0, 2, '2020-04-22 12:02:08', NULL);

-- ----------------------------
-- Table structure for article_tag_info
-- ----------------------------
DROP TABLE IF EXISTS `article_tag_info`;
CREATE TABLE `article_tag_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章分类表主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类名称',
  `category_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户兴趣标的id',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效  2-有效',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_tag_info
-- ----------------------------
INSERT INTO `article_tag_info` VALUES (1, 'Java', 17, 2, '2020-04-11 10:11:29', NULL);
INSERT INTO `article_tag_info` VALUES (2, 'Java Script', 17, 2, '2020-04-11 10:11:44', NULL);
INSERT INTO `article_tag_info` VALUES (3, 'MySql', 17, 2, '2020-04-11 10:11:54', NULL);

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
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '状态 1:删除 2:无效 2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_info
-- ----------------------------

-- ----------------------------
-- Table structure for auth_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_rel`;
CREATE TABLE `auth_role_rel`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '角色权限表主键',
  `role_id` bigint(10) UNSIGNED NOT NULL COMMENT '角色表中的id',
  `auth_id` bigint(10) UNSIGNED NOT NULL COMMENT '权限表中的id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `create_management_id` bigint(50) UNSIGNED NULL DEFAULT NULL COMMENT '创建人编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_role_rel
-- ----------------------------

-- ----------------------------
-- Table structure for consumer_interest_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `consumer_interest_tag_rel`;
CREATE TABLE `consumer_interest_tag_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `consumer_id` bigint(20) NULL DEFAULT NULL COMMENT '用户表的主键id',
  `tag_id` bigint(20) NULL DEFAULT NULL COMMENT '标签表的主键id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `score` tinyint(1) UNSIGNED NULL DEFAULT 5 COMMENT '评分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of consumer_interest_tag_rel
-- ----------------------------
INSERT INTO `consumer_interest_tag_rel` VALUES (14, 5, 17, '2020-04-11 09:33:59', NULL, 5);
INSERT INTO `consumer_interest_tag_rel` VALUES (15, 5, 18, '2020-04-11 09:34:07', NULL, 5);

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
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-删除 2-有效 3-已被接单',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of errand_info
-- ----------------------------

-- ----------------------------
-- Table structure for errand_order_info
-- ----------------------------
DROP TABLE IF EXISTS `errand_order_info`;
CREATE TABLE `errand_order_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '跑腿订单表主键',
  `order_code` char(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `errand_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '跑腿信息表主键',
  `holder_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '接单者id',
  `publisher_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发布者id',
  `pay_money` decimal(10, 0) UNSIGNED NULL DEFAULT NULL COMMENT '支付金额',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
  `finish_time` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单完成时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT 3 COMMENT '1-取消订单  2-删除 3-未完成 4-已完成 5-下单失败',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of errand_order_info
-- ----------------------------

-- ----------------------------
-- Table structure for forum_hot_topic_info
-- ----------------------------
DROP TABLE IF EXISTS `forum_hot_topic_info`;
CREATE TABLE `forum_hot_topic_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章模块热搜表',
  `target_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '目标id',
  `score` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT 'redis中存储的score',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '2-有效  1-无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_hot_topic_info
-- ----------------------------
INSERT INTO `forum_hot_topic_info` VALUES (10, 23, 1131, NULL, '2020-04-14 15:24:23', NULL);
INSERT INTO `forum_hot_topic_info` VALUES (11, 24, 14, NULL, '2020-04-11 17:57:20', NULL);
INSERT INTO `forum_hot_topic_info` VALUES (12, 25, 19, NULL, '2020-04-11 17:57:20', NULL);
INSERT INTO `forum_hot_topic_info` VALUES (13, 26, 41, NULL, '2020-04-11 17:57:20', NULL);
INSERT INTO `forum_hot_topic_info` VALUES (14, 27, 20, NULL, '2020-04-11 17:57:20', NULL);
INSERT INTO `forum_hot_topic_info` VALUES (15, 29, 396, NULL, '2020-04-22 12:26:17', NULL);

-- ----------------------------
-- Table structure for interest_tag_info
-- ----------------------------
DROP TABLE IF EXISTS `interest_tag_info`;
CREATE TABLE `interest_tag_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '兴趣标签表主键',
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '标签名称',
  `manager_id` bigint(20) NULL DEFAULT NULL COMMENT '创建者的id,对应于管理员表中的主键id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '状态:1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of interest_tag_info
-- ----------------------------
INSERT INTO `interest_tag_info` VALUES (17, 'IT', 5, '2020-04-11 09:32:15', NULL, 2);
INSERT INTO `interest_tag_info` VALUES (18, '娱乐', 5, '2020-04-11 09:32:28', NULL, 2);
INSERT INTO `interest_tag_info` VALUES (19, '游戏', 5, '2020-04-11 09:32:37', NULL, 2);

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
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效  2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of irh_template
-- ----------------------------

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
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `state` tinyint(255) UNSIGNED NULL DEFAULT 20 COMMENT '10:删除  20:已读  30:未读 ',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `sender_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '发送方id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of news_info
-- ----------------------------

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
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals`  (
  `userId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `clientId` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `expiresAt` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `lastModifiedAt` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

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
INSERT INTO `oauth_client_details` VALUES ('irhWebApp', NULL, '$2a$10$IliTyZPWTbcMd3TI0pdLD.IrW843ZnfMV0tlgcSbbd4N7nqXjGMaW', 'app', 'authorization_code,password,refresh_token,client_credentials', NULL, NULL, 43200, 43200, NULL, NULL);

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
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

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
-- Records of oauth_refresh_token
-- ----------------------------

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '订单表主键',
  `saler_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '会员表的id',
  `saler_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '会员表的nickname字段',
  `buyer_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '会员表的id',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `payment_money` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '支付金额',
  `order_remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '订单标题',
  `address` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '送货地址:将楼号、楼层、宿舍号以json格式存储',
  `trade_type` tinyint(1) UNSIGNED NOT NULL DEFAULT 10 COMMENT '10:正常交易  20:公益捐赠',
  `payment_time` timestamp(0) NULL DEFAULT NULL COMMENT '支付时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '订单创建时间',
  `finish_time` timestamp(0) NULL DEFAULT NULL COMMENT '交易完成时间,用户确定收货的时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 40 COMMENT '10:订单超时 20:取消订单 30:买家删除订单 35:卖家删除订单  40:等待支付 50:交易成功\r\n60:捐款金额已分配 ',
  `order_code` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单编号,必须保证唯一,且64位之内64个字符以内,只能包含字母、数字、下划线',
  `order_version` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '订单版本，默认为1，当修改订单的时候会将版本号加1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------
INSERT INTO `order_info` VALUES (7, 8, '里斯', 5, 1, 123.00, '', '23号楼923寝室', 10, '2020-04-08 15:00:05', '2020-04-08 15:00:05', '2020-04-13 15:00:05', '2020-04-14 11:38:52', 40, '1234123412111', 1);
INSERT INTO `order_info` VALUES (8, 5, 'master', 8, 2, 700.00, '', '23号楼922寝室', 10, NULL, '2020-04-14 11:32:28', NULL, '2020-04-14 11:38:44', 40, '23562334512', 1);
INSERT INTO `order_info` VALUES (9, 5, 'master', 8, 3, 1000.00, '', '23号楼922寝室', 20, '2020-04-16 15:00:05', '2020-04-16 10:28:12', '2020-04-16 10:28:12', '2020-04-19 14:23:31', 50, '1234123411', 1);
INSERT INTO `order_info` VALUES (10, 5, 'master', 8, 4, 7000.00, '', '23号楼922寝室', 20, NULL, '2020-04-16 10:38:14', NULL, '2020-04-16 10:38:31', 50, '223546757243', 1);
INSERT INTO `order_info` VALUES (11, 5, 'master', 8, 4, 0.00, '', '', 10, NULL, '2020-04-19 12:15:39', NULL, '2020-04-19 12:15:42', 50, '', 1);

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
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_payment_info
-- ----------------------------

-- ----------------------------
-- Table structure for product_category_info
-- ----------------------------
DROP TABLE IF EXISTS `product_category_info`;
CREATE TABLE `product_category_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品分类信息表的主键',
  `parent_id` bigint(10) UNSIGNED NULL DEFAULT 0 COMMENT '当值为0的时候标识根节点',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分类名称',
  `desc` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分类的描述',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1:无效  2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category_info
-- ----------------------------
INSERT INTO `product_category_info` VALUES (1, 0, '家电', '家电', '2020-04-18 16:15:14', NULL, 2);
INSERT INTO `product_category_info` VALUES (2, 0, '服饰', '服饰', '2020-04-18 16:15:44', NULL, 2);
INSERT INTO `product_category_info` VALUES (3, 0, '化妆品', '化妆品', '2020-04-18 16:15:57', NULL, 2);
INSERT INTO `product_category_info` VALUES (4, 1, '联想', '联想', '2020-04-18 16:16:37', NULL, 2);
INSERT INTO `product_category_info` VALUES (5, 1, '海尔', '海尔', '2020-04-18 16:16:57', NULL, 2);
INSERT INTO `product_category_info` VALUES (6, 1, '雷神', '雷神', '2020-04-18 16:17:36', '2020-04-18 16:17:40', 2);
INSERT INTO `product_category_info` VALUES (7, 2, 'JACK_JONES', 'JACK_JONES', '2020-04-18 16:18:12', NULL, 2);
INSERT INTO `product_category_info` VALUES (8, 2, '真维斯', '真维斯', '2020-04-18 16:18:24', NULL, 2);
INSERT INTO `product_category_info` VALUES (9, 2, '花花公子', '花花公子', '2020-04-18 16:18:33', NULL, 2);
INSERT INTO `product_category_info` VALUES (10, 2, '劲霸', '劲霸', '2020-04-18 16:19:55', NULL, 2);
INSERT INTO `product_category_info` VALUES (11, 3, '香奈儿', '香奈儿', '2020-04-18 16:20:49', NULL, 2);

-- ----------------------------
-- Table structure for product_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `product_category_rel`;
CREATE TABLE `product_category_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品分类表主键',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `category_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '分类id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_category_rel
-- ----------------------------

-- ----------------------------
-- Table structure for product_demand_info
-- ----------------------------
DROP TABLE IF EXISTS `product_demand_info`;
CREATE TABLE `product_demand_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '会员需求表主键',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标题',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '内容',
  `consumer_id` bigint(20) NULL DEFAULT NULL COMMENT '发布人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '状态 1-无效 2-有效',
  `main_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '封面图片url',
  `other_pics` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '其他附图的地址，多个用逗号分隔',
  `tag_names` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标签名称',
  `browser_times` bigint(20) UNSIGNED NULL DEFAULT 0 COMMENT '浏览次数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_demand_info
-- ----------------------------
INSERT INTO `product_demand_info` VALUES (2, '谁有Java相关的书', 1, '急需一本软件工程专业Java课程相关的书', 5, '2020-04-13 14:10:47', '2020-04-13 15:09:46', 2, 'group1/M00/00/01/rBgYGV6Qaf6Ad3zKAACHVu5mYbk685.jpg', NULL, 'java|书籍', 12);
INSERT INTO `product_demand_info` VALUES (3, '自行车', 2, '马上毕业了，那位学长有自行车', 5, '2020-04-13 14:11:02', '2020-04-13 15:09:58', 1, '', NULL, '自行车', 343);
INSERT INTO `product_demand_info` VALUES (4, '87678', 2, '撒打发', 5, '2020-04-21 15:47:32', NULL, 2, 'group1/M00/00/01/rBgYGV6eo6aAEg6uAAA4C-O0X9g914.jpg', 'group1/M00/00/01/rBgYGV6eo6mAIQanAAA4C-O0X9g672.jpg,group1/M00/00/01/rBgYGV6eo6yADPoRAABd06ZVPBU039.jpg', '32', 0);

-- ----------------------------
-- Table structure for product_donation_apply_info
-- ----------------------------
DROP TABLE IF EXISTS `product_donation_apply_info`;
CREATE TABLE `product_donation_apply_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '爱心捐款申请表',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `apply_user_id` bigint(20) NULL DEFAULT NULL COMMENT '申请人',
  `alipay_num` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收款支付宝账号',
  `apply_amount` decimal(10, 4) NULL DEFAULT NULL COMMENT '申请金额',
  `payment_amount` decimal(10, 4) NULL DEFAULT NULL COMMENT '实际发放金额',
  `reason` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '申请理由',
  `witness_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '证明人姓名',
  `witness_phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '证明人电话号码',
  `reason_pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '申请凭证图片url',
  `feedback` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '反馈',
  `feedback_pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '反馈图片url',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-删除  2-审核中  3-失败  4-审核通过 5-已转账',
  `approve_user_id` bigint(20) NULL DEFAULT NULL COMMENT '审核人id',
  `approve_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审核人姓名',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '审核备注',
  `grant_user_id` bigint(20) NULL DEFAULT NULL COMMENT '发放金额的人的id',
  `grant_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发放金额的人的名字',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `apply_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '申请编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_donation_apply_info
-- ----------------------------
INSERT INTO `product_donation_apply_info` VALUES (1, '內蒙古科技大学活动', 5, '1000', 1100.0000, 1000.0000, '人数较多', '', '', 'group1/M00/00/01/rBgYGV6Qaf6Ad3zKAACHVu5mYbk685.jpg', '感谢大家的支持', 'group1/M00/00/01/rBgYGV6Qaf6Ad3zKAACHVu5mYbk685.jpg', 5, 5, 'hmr', '可以', 5, '5', '2020-04-16 10:42:59', '2020-04-19 15:40:46', NULL);
INSERT INTO `product_donation_apply_info` VALUES (2, '青年大学习活动', 8, '1000', 800.0000, NULL, '发放流量资金', '', '', '', '', '', 2, NULL, NULL, '', NULL, NULL, '2020-04-18 18:14:30', '2020-04-18 18:15:01', NULL);
INSERT INTO `product_donation_apply_info` VALUES (3, '就是像申请', 5, '123412234', 1000.0000, NULL, '物料看看', '', '', 'group1/M00/00/01/rBgYGV6Qaf6Ad3zKAACHVu5mYbk685.jpg', '', '', 2, NULL, NULL, '', NULL, NULL, '2020-04-19 14:50:22', '2020-04-19 14:55:41', NULL);

-- ----------------------------
-- Table structure for product_donation_order_rel
-- ----------------------------
DROP TABLE IF EXISTS `product_donation_order_rel`;
CREATE TABLE `product_donation_order_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '爱心捐赠申请表和商品订单关系表',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单表的主键id',
  `donation_apply_id` bigint(20) NULL DEFAULT NULL COMMENT '申请表id',
  `operation_user_id` bigint(20) NULL DEFAULT NULL COMMENT '操作人',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_donation_order_rel
-- ----------------------------
INSERT INTO `product_donation_order_rel` VALUES (1, 9, 1, 5, '2020-04-19 14:23:42', NULL);

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
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(255) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_evaluate_info
-- ----------------------------

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表的主键',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品标题',
  `main_pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品主图url',
  `unit` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '计量单位',
  `original_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品原价',
  `sale_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '售卖价格',
  `old_degree` tinyint(1) NULL DEFAULT 5 COMMENT '商品的新旧程度,1~10数值越大越新',
  `product_desc` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `product_details_page` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '商品详情页',
  `trade_type` tinyint(1) NULL DEFAULT 10 COMMENT '10-正常交易  20-公益捐赠',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类id',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效 3-锁定',
  `consumer_id` bigint(20) NULL DEFAULT NULL COMMENT '出售商品的人的id',
  `browser_times` bigint(20) NULL DEFAULT NULL COMMENT '浏览次数',
  `tag_names` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES (1, '山地自行车', 'group1/M00/00/01/rBgYGV6RdkOACa0DAABW5M6zRWk259.jpg', '辆', 500.00, 200.00, 9, '本人已经大四，出售一辆全新的山地自行车，有没有想要的', '', 10, 1, '2020-04-13 15:09:31', '2020-04-19 16:56:40', 2, 5, 2, '自行车|九九新');
INSERT INTO `product_info` VALUES (2, '出一台联想笔记本', 'group1/M00/00/01/rBgYGV6RdkOACa0DAABW5M6zRWk259.jpg', '台', 1000.00, 700.00, 8, '用了两年的联想笔记本,打算卖出去,所有的钱都捐给慈善机构', '', 10, 2, '2020-04-14 11:31:04', '2020-04-19 16:56:42', 2, 4, 12, '电脑|联想');
INSERT INTO `product_info` VALUES (3, 'Iphone8', '', '台', 2000.00, 1700.00, 6, '本人用了两年的手机', '', 20, 2, '2020-04-16 10:27:20', '2020-04-16 10:37:51', 2, 5, 234, '手机|苹果');
INSERT INTO `product_info` VALUES (4, 'Mac Book', '', '台', 8000.00, 7600.00, 9, '用了三个月', '', 20, 2, '2020-04-16 10:37:46', NULL, 2, 5, 111111, '电脑|苹果');
INSERT INTO `product_info` VALUES (5, '2435', '', '2', NULL, 124.00, 5, '23452', '', 10, 1, '2020-04-19 15:25:49', NULL, 2, 5, NULL, NULL);

-- ----------------------------
-- Table structure for product_message_info
-- ----------------------------
DROP TABLE IF EXISTS `product_message_info`;
CREATE TABLE `product_message_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品留言表主键',
  `product_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '商品id',
  `consumer_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户编号',
  `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '回复消息的id，0表示是新的留言(一级留言)的时候 ',
  `first_class_id` bigint(20) NULL DEFAULT NULL COMMENT '一级留言id，0标识一级留言',
  `content` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product_message_info
-- ----------------------------

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
  `result` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '处理结果 1-举报失败 2-处理中 3-警告 4-冻结账',
  `process_id` bigint(20) NULL DEFAULT NULL COMMENT '处理人id',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '管理员审核反馈',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '举报时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of report_feedback_info
-- ----------------------------

-- ----------------------------
-- Table structure for role_info
-- ----------------------------
DROP TABLE IF EXISTS `role_info`;
CREATE TABLE `role_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动递增的id',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限名称',
  `role_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '权限描述',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '状态 1:无效  2:有效',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `create_management` bigint(50) UNSIGNED NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_info
-- ----------------------------
INSERT INTO `role_info` VALUES (1, 'admin', '管理员', 2, '2020-04-11 09:25:27', NULL, 5);
INSERT INTO `role_info` VALUES (2, 'super_admin', '超级管理员', 2, '2020-04-11 09:25:45', '2020-04-11 09:25:51', 5);
INSERT INTO `role_info` VALUES (3, 'association', '社团', 2, '2020-04-11 09:27:17', NULL, 5);
INSERT INTO `role_info` VALUES (8, 'organization', '组织', 2, '2020-04-11 09:27:40', NULL, 5);

-- ----------------------------
-- Table structure for static_template
-- ----------------------------
DROP TABLE IF EXISTS `static_template`;
CREATE TABLE `static_template`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '模板表id',
  `context` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '模板内容',
  `desc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模板说明',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '状态  1-无效  2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of static_template
-- ----------------------------

-- ----------------------------
-- Table structure for user_authen_record_info
-- ----------------------------
DROP TABLE IF EXISTS `user_authen_record_info`;
CREATE TABLE `user_authen_record_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '认证记录表主键',
  `picUri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '上传的图片地址',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '认证人',
  `input_card_no` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '输入的一卡通账号',
  `input_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '输入的名字',
  `type` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1-一卡通认证   2-身份证认证失败之后需要人工认证',
  `state` tinyint(1) UNSIGNED NULL DEFAULT 1 COMMENT '1-认证中  2-认证成功  3-认证失败',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_authen_record_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_forum_attribute_info
-- ----------------------------
DROP TABLE IF EXISTS `user_forum_attribute_info`;
CREATE TABLE `user_forum_attribute_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户论坛点赞表主键',
  `user_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '用户id',
  `target_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '点赞对象id',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-文章   2-评论',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '1-无效 2-有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_forum_attribute_info
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自动生成的id',
  `age` tinyint(1) UNSIGNED NULL DEFAULT 18 COMMENT '年龄',
  `email` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '登录用的邮箱',
  `password` char(65) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'md5加密的密码',
  `sign` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个性签名',
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
  `state` tinyint(1) UNSIGNED NULL DEFAULT 25 COMMENT '10:注销 20:锁定 25:未实名 30:审核中  40:审核通过 50:认证失败',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '注册时间',
  `type` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:普通会员 20:服务人员  30:校园组织  40:校园社团 50:管理员',
  `portrait` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (5, 18, '1978773465@qq.com', '$2a$10$DUDz1OKs5jmIDvUIrVwbAuP.586WMg/cVJYqZC7Nmk9USe5ebPc9a', NULL, '', 'master', '1978773465', 2, '110', '23', '9', '922', '', '', '內蒙古科技大学', '信息工程学院', '软件工程', 40, '2020-04-21 09:11:16', '2020-04-10 20:42:22', 50, 'group1/M00/00/01/rBgYGV6Qaf6Ad3zKAACHVu5mYbk685.jpg');
INSERT INTO `user_info` VALUES (8, 18, '1234567890@qq.com', '$2a$10$DUDz1OKs5jmIDvUIrVwbAuP.586WMg/cVJYqZC7Nmk9USe5ebPc9a', NULL, '', '里斯', '12345678789', 2, '119', '', '', '', '', '', '清华大学', '文学院', '', 25, '2020-04-21 19:18:49', '2020-04-11 09:29:06', 10, 'group1/M00/00/01/rBgYGV6Qaf6Ad3zKAACHVu5mYbk685.jpg');
INSERT INTO `user_info` VALUES (9, 18, '1111111111@qq.com', '$2a$10$DUDz1OKs5jmIDvUIrVwbAuP.586WMg/cVJYqZC7Nmk9USe5ebPc9a', NULL, '', '张三', '', 2, '', '', '', '', '', '', '北京大学', '理学院', '生物工程', 25, '2020-04-21 19:18:51', '2020-04-11 09:30:51', 20, 'group1/M00/00/01/rBgYGV6Qaf6Ad3zKAACHVu5mYbk685.jpg');

-- ----------------------------
-- Table structure for user_role_rel
-- ----------------------------
DROP TABLE IF EXISTS `user_role_rel`;
CREATE TABLE `user_role_rel`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '管理员角色表的主键',
  `staff_id` bigint(10) UNSIGNED NOT NULL COMMENT '工作人员表的id',
  `role_id` bigint(10) UNSIGNED NOT NULL COMMENT '角色表中的id',
  `create_ management _id` bigint(20) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最后一次修改时间',
  `state` tinyint(1) NULL DEFAULT 2 COMMENT '状态 1:无效 2:有效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role_rel
-- ----------------------------
INSERT INTO `user_role_rel` VALUES (3, 5, 2, 5, '2020-04-11 09:28:15', NULL, 2);

SET FOREIGN_KEY_CHECKS = 1;
