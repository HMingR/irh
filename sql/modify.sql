/*该文件用来记录一些对数据库的小修改*/
ALTER TABLE `irh`.`management_info`
ADD COLUMN `password` varchar(255) NULL COMMENT '登陆密码';

ALTER TABLE `irh`.`role_info`
MODIFY COLUMN `state` tinyint(1) UNSIGNED NULL DEFAULT 0 COMMENT '状态 1:无效  2:有效' AFTER `role_desc`;