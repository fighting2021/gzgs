CREATE DATABASE IF NOT EXISTS ims;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
`user_id` bigint(20) NOT NULL COMMENT '用户编号',
`user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
`pwd` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
`email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
`sex` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
`create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`user_statu` int(5) NULL DEFAULT NULL COMMENT '是否可用 0--可用  1--不可用',
`last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '上传登录时间',
PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- 角色表
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
`role_id` bigint(20) NOT NULL COMMENT '编号',
`name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
`code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色编码',
`remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
`create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
`role_statu` int(5) NULL DEFAULT NULL COMMENT '角色状态 0--可用  1--不可用',
PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- 菜单表
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
`menu_id` bigint(20) NOT NULL COMMENT '菜单id',
`parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父级id, 根菜单为0',
`menu_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单名',
`srt` int(4) NULL DEFAULT 0 COMMENT '排序',
`path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '路由地址',
`component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组件路径',
`menu_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单类型（M目录 C菜单 F请求路径）',
`visible` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
`status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
`icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- 用户角色关系表
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
`user_role_id` bigint(20) NOT NULL COMMENT '编号',
`user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户编号',
`role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色编号',
PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = Dynamic;

-- 角色菜单表
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
`role_menu_id` bigint(20) NOT NULL COMMENT '角色菜单id',
`role_id` bigint(20) NOT NULL COMMENT '角色id',
`menu_id` bigint(20) NOT NULL COMMENT '菜单id',
PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = Dynamic;

-- 插入记录
INSERT INTO `sys_user` VALUES (1, 'user', '$2a$10$sPbHeIKrKIobRnvv4wpzNuwszyAFGth8OliQCWKvJNVo31bF0YRCe', NULL, NULL, '2022-05-11 13:38:41', 0, NULL);
INSERT INTO `sys_user` VALUES (2, 'user2', '$2a$10$sPbHeIKrKIobRnvv4wpzNuwszyAFGth8OliQCWKvJNVo31bF0YRCe', NULL, NULL, '2022-08-29 11:51:21', 0, NULL);
INSERT INTO `sys_role` VALUES (1, '管理员', 'admin', NULL, '2022-08-23 16:25:14', 0);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'user', NULL, '2022-08-23 16:25:49', 0);
INSERT INTO `sys_menu` VALUES (1, 0, '查询操作', 0, '/sys/query', NULL, 'F', '0', '0', NULL);
INSERT INTO `sys_menu` VALUES (2, 0, '删除操作', 0, '/sys/delete', NULL, 'F', '0', '0', NULL);
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2, 2);
INSERT INTO `sys_role_menu` VALUES (1, 1, 1);
INSERT INTO `sys_role_menu` VALUES (2, 1, 2);
INSERT INTO `sys_role_menu` VALUES (3, 2, 1);

SET FOREIGN_KEY_CHECKS = 1;
