/*该文件用来记录一些对数据库的小修改*/
ALTER TABLE `irh`.`management_info`
ADD COLUMN `password` varchar(255) NULL COMMENT '登陆密码';

ALTER TABLE `irh`.`role_info`
MODIFY COLUMN `state` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '状态 1:无效  2:有效' AFTER `role_desc`;

ALTER TABLE `irh`.`role_info`
CHANGE COLUMN `create_management _id` `create_management` varchar(50) NULL DEFAULT NULL COMMENT '创建人姓名' AFTER `update_time`;

ALTER TABLE `irh`.`auth_role_rel`
CHANGE COLUMN `create_ management _id` `create_ management` varchar(50) NULL DEFAULT NULL COMMENT '创建人' AFTER `update_time`;

/*2019.12.22*/
ALTER TABLE `irh`.`product_info`
ADD COLUMN `state` tinyint(1) UNSIGNED NULL COMMENT '1-无效 2-有效' AFTER `update_time`;
ALTER TABLE `irh`.`product_category_info`
MODIFY COLUMN `state` tinyint(1) UNSIGNED NULL DEFAULT 2 COMMENT '1:无效  2:有效' AFTER `update_time`;
ALTER TABLE `irh`.`product_info`
CHANGE COLUMN `parent_category_id` `category_id` bigint(20) NULL DEFAULT NULL COMMENT '分类id' AFTER `trade_type`;

/*12.23*/
ALTER TABLE `irh`.`order_info`
ADD COLUMN `order_code` char(64) NOT NULL COMMENT '订单编号,必须保证唯一,且64位之内64个字符以内,只能包含字母、数字、下划线' AFTER `state`;
ALTER TABLE `irh`.`order_info`
MODIFY COLUMN `order_remark` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '订单标题' AFTER `payment_money`;

/*12.28*/
ALTER TABLE `irh`.`product_info`
ADD COLUMN `consumer_id` bigint(20) NULL COMMENT '出售商品的人的id' AFTER `state`;
ALTER TABLE `irh`.`order_info`
MODIFY COLUMN `state` tinyint(1) UNSIGNED NULL DEFAULT NULL COMMENT '10:订单超时 20:取消订单 30:删除订单 40:等待支付 50:交易成功 ' AFTER `update_time`;


/*1.9*/
ALTER TABLE `irh`.`product_evaluate_info`
CHANGE COLUMN `buyer_ nickname` `saler_id` bigint(20) NULL DEFAULT '' COMMENT '卖家编号' AFTER `buyer_id`;
ALTER TABLE `irh`.`product_evaluate_info`
MODIFY COLUMN `state` tinyint(255) UNSIGNED NULL DEFAULT 2 COMMENT '1-无效 2-有效' AFTER `update_time`;
ALTER TABLE `irh`.`product_message`
MODIFY COLUMN `parent_id` bigint(20) UNSIGNED NULL DEFAULT NULL COMMENT '回复消息的id，0表示是新的留言的时候 ' AFTER `consumer_id`;