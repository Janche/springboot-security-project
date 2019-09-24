
CREATE TABLE `menu_right`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父节点id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `method` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '方法(GET/POST/DELETE/PUT)',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标样式',
  `grades` int(11) NULL DEFAULT NULL COMMENT '层级(1-一级菜单，2-二级菜单，3-接口)',
  `seq` int(11) NOT NULL DEFAULT 1 COMMENT '权限类型：0-菜单，1-查看，2-编辑',
  `status` int(1) NULL DEFAULT 1 COMMENT '0-禁用，1-启用(默认启用)',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 605 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Compact;

CREATE TABLE `role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称(页面显示)',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `seq` int(11) NULL DEFAULT NULL COMMENT '角色排序',
  `status` int(11) NULL DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_index`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = Compact;

CREATE TABLE `role_right`  (
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `menu_id` bigint(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
CREATE TABLE `sys_log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `log_type` int(11) NULL DEFAULT NULL COMMENT '所属系统：2-权限管理系统，3-交叉推广系统，4-广告管理系统，5-数据统计系统，6-游戏合集系统',
  `operation` int(11) NULL DEFAULT NULL COMMENT '操作类型： 添加-1 删除-2 更新-3 查看-4 登录-5 登出-6 导出-7 导入-8 0为默认值',
  `log_user` bigint(11) NULL DEFAULT NULL COMMENT '操作人员ID',
  `log_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问IP',
  `log_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `log_params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `log_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志描述',
  `log_time` bigint(32) NULL DEFAULT NULL COMMENT '响应时间',
  `exception_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常码',
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 386 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user
-- ----------------------------
CREATE TABLE `user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `actual_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` int(2) NULL DEFAULT NULL COMMENT '性别 0：男 1：女',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '职务ID',
  `post_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职务名称',
  `status` int(2) NULL DEFAULT 1 COMMENT '账号状态：0-禁用，1-启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `index_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 102 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for user_role
-- ----------------------------
CREATE TABLE `user_role`  (
  `user_id` bigint(11) NOT NULL COMMENT '用户id',
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`role_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

CREATE TABLE `oauth_client_details`  (
  `client_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
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
  `create_time` timestamp(0) NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

CREATE TABLE `oauth_access_token`  (
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  INDEX `token_id_index`(`token_id`) USING BTREE,
  INDEX `authentication_id_index`(`authentication_id`) USING BTREE,
  INDEX `user_name_index`(`user_name`) USING BTREE,
  INDEX `client_id_index`(`client_id`) USING BTREE,
  INDEX `refresh_token_index`(`refresh_token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


CREATE TABLE `oauth_refresh_token`  (
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `token_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL,
  INDEX `token_id_index`(`token_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


CREATE TABLE `oauth_code`  (
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  INDEX `code_index`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `oauth_client_details` VALUES ('sheep1', '', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', 'all', 'authorization_code, refresh_token', 'http://localhost:8086/login', NULL, 30, 60, NULL, 'true', '2019-07-18 10:00:19');
INSERT INTO `oauth_client_details` VALUES ('sheep2', '', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', 'all', 'authorization_code, refresh_token', 'http://localhost:8087/client2/login', NULL, 30, 60, NULL, 'true', '2019-07-18 10:26:29');

INSERT INTO `role` VALUES (1,  '超级管理员', '超级管理员', NULL, 1, '2018-11-21 16:58:11', '2019-08-19 16:38:07');
INSERT INTO `role` VALUES (2,  '系统管理员', '系统管理员', NULL, 1, '2018-11-21 16:58:07', '2019-01-21 17:22:34');
INSERT INTO `role` VALUES (3,  '普通用户', '普通用户', NULL, 1, '2019-07-11 09:52:07', '2019-08-19 16:34:03');


INSERT INTO `role_right` VALUES (3, 1);
INSERT INTO `role_right` VALUES (3, 2);
INSERT INTO `role_right` VALUES (3, 3);
INSERT INTO `role_right` VALUES (3, 20);
INSERT INTO `role_right` VALUES (3, 21);
INSERT INTO `role_right` VALUES (3, 201);
INSERT INTO `role_right` VALUES (3, 202);
INSERT INTO `role_right` VALUES (3, 203);
INSERT INTO `role_right` VALUES (3, 204);
INSERT INTO `role_right` VALUES (1, 1);
INSERT INTO `role_right` VALUES (1, 2);
INSERT INTO `role_right` VALUES (1, 3);
INSERT INTO `role_right` VALUES (1, 20);
INSERT INTO `role_right` VALUES (1, 21);
INSERT INTO `role_right` VALUES (1, 201);
INSERT INTO `role_right` VALUES (1, 202);
INSERT INTO `role_right` VALUES (1, 203);
INSERT INTO `role_right` VALUES (1, 204);
INSERT INTO `role_right` VALUES (1, 210);
INSERT INTO `role_right` VALUES (1, 211);
INSERT INTO `role_right` VALUES (1, 212);
INSERT INTO `role_right` VALUES (1, 213);


INSERT INTO `user` VALUES (1, 'admin', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', '李四', 0, '5625@qq.com', '18382100123', '航利中心', 4, NULL, 1, '2018-09-09 15:03:18', '2019-01-15 10:06:47');
INSERT INTO `user` VALUES (2, 'test', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', '张三', 1, NULL, NULL, NULL, 4, NULL, 1, NULL, NULL);
INSERT INTO `user` VALUES (3, 'libii', '$2a$10$4ZpXaJyS4/oLUCsIDz1kCO7T7g9LtVq8hD1E0PJQwVfvI48U.RF7.', '力比', 0, 'libii@libii.com', '13020310', NULL, 4, NULL, 1, '2019-07-15 17:44:50', NULL);


INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (2, 3);
INSERT INTO `user_role` VALUES (2, 2);
INSERT INTO `user_role` VALUES (3, 3);


INSERT INTO `menu_right` VALUES (1, 0, '首页', '', '/home', '', 1, 1, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (2, 1, '系统管理', '', '/SystemManagement', '', 1, 1, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (3, 1, '日志管理', '', '/operationManagement/logManagement', '', 1, 2, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (20, 2, '用户管理', '', '/SystemManagement/userManagement', '', 1, 2, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (21, 2, '角色管理', '', '/SystemManagement/roleManagement', '', 1, 2, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (201, 20, '添加用户', 'POST', '/user/add', '', 3, 3, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (202, 20, '删除用户', 'DELETE', '/user/delete/*', '', 3, 3, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (203, 20, '修改用户', 'PUT', '/user/update', '', 3, 3, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (204, 20, '用户列表', 'GET', '/user/list', NULL, 3, 3, 1, '2019-08-19 16:20:17', NULL);
INSERT INTO `menu_right` VALUES (210, 21, '角色列表', 'GET', '/role/list', '', 3, 3, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (211, 21, '删除角色', 'DELETE', '/role/delete/*', '', 3, 3, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (212, 21, '添加角色', 'GET', '/role/add', '', 3, 3, 1, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (213, 21, '修改角色', 'GET', '/role/update', '', 3, 3, 1, '2019-01-09 17:09:08', NULL);

