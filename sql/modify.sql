/*该文件用来记录一些对数据库的小修改*/
ALTER TABLE `irh`.`management_info`
ADD COLUMN `password` varchar(255) NULL COMMENT '登陆密码';

ALTER TABLE `irh`.`role_info`
MODIFY COLUMN `state` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '状态 1:无效  2:有效' AFTER `role_desc`;

ALTER TABLE `irh`.`role_info`
CHANGE COLUMN `create_management _id` `create_management` varchar(50) NULL DEFAULT NULL COMMENT '创建人姓名' AFTER `update_time`;

ALTER TABLE `irh`.`auth_role_rel`
CHANGE COLUMN `create_ management _id` `create_ management` varchar(50) NULL DEFAULT NULL COMMENT '创建人' AFTER `update_time`;