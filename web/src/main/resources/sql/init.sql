
-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `area_id` bigint(11) NULL DEFAULT NULL COMMENT '关联区域',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登陆账号',
  `password` varchar(126) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆密码',
  `sex` int(11) NULL DEFAULT NULL COMMENT '性别 0：男 1：女',
  `truename` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_num` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户编号',
  `email` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'email地址',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址信息',
  `level_id` int(11) NULL DEFAULT NULL COMMENT '职务',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `state` int(2) NULL DEFAULT 1 COMMENT '用户状态，0，不允许登录，1允许登录，',
  `priority` int(11) NULL DEFAULT NULL COMMENT '用户优先级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for menu_right
-- ----------------------------
DROP TABLE IF EXISTS `menu_right`;
CREATE TABLE `menu_right`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父节点id',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '节点名称',
  `method` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '方法（get、post等）',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问地址',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标样式',
  `isvisible` int(1) NULL DEFAULT NULL COMMENT '是否有效 1-有效 -1无效（不可使用）',
  `grades` int(11) NULL DEFAULT NULL COMMENT '层级',
  `seq` int(11) NULL DEFAULT NULL COMMENT '排序号',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 288 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单节点表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `parent_id` bigint(11) NULL DEFAULT NULL COMMENT '父节点id',
  `role_index` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所有父级节点和当前节点',
  `category_id` bigint(11) NULL DEFAULT NULL COMMENT '问/答分类',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称描述',
  `role_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统的角色名称',
  `seq` int(11) NULL DEFAULT NULL COMMENT '角色排序',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '问答库表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `user_id` bigint(11) NOT NULL COMMENT '用户id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
DROP TABLE IF EXISTS `role_right`;
CREATE TABLE `role_right`  (
  `role_id` bigint(11) NOT NULL COMMENT '角色id',
  `menu_id` bigint(11) NOT NULL COMMENT '菜单id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `log_type` int(11) NULL DEFAULT NULL COMMENT '日志类型  根据系统模块来定义日志类型，采用一级菜单的ID，0为默认值',
  `operation` int(11) NULL DEFAULT NULL COMMENT '操作类型： 添加-1 删除-2 更新-3 查看-4 登录-5 登出-6 导入-7 导出-8 0为默认值',
  `log_user` bigint(11) NULL DEFAULT NULL COMMENT '操作人员ID',
  `log_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问IP',
  `log_method` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `log_params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '请求参数',
  `log_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '日志描述',
  `log_time` bigint(32) NULL DEFAULT NULL COMMENT '响应时间',
  `exception_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '异常码',
  `exception_detail` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '异常描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1249 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统日志表' ROW_FORMAT = Compact;

-- ----------------------------
-- 日期：2019-4-26 10:14:59
-- oauth2 授权码表
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`  (
  `code` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `create_time` datetime NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- 日期：2019-4-26 10:14:59
-- oauth2 客户端信息明细表
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
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
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- 日期：2019-4-26 10:14:59
-- oauth2 token信息表
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication_id` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `authentication` blob NULL,
  `refresh_token` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`authentication_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- 日期：2019-4-26 10:14:59
-- oauth2 refresh_token信息表
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token`  (
  `token_id` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `token` blob NULL,
  `authentication` blob NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


-------------------------初始化数据--------------------------

-- ----------------------------
-- Table structure for menu_right
-- ----------------------------
INSERT INTO `menu_right` VALUES (1, 0, '首页', '', '/home', '', 1, 1, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (7, 0, '系统管理', '', '/SystemManagement', '', 1, 1, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (8, 0, '运维管理', '', '/operationManagement', '', 1, 1, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (15, 7, '用户管理', '', '/SystemManagement/userManagement', '', 1, 2, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (16, 7, '角色管理', '', '/SystemManagement/roleManagement', '', 1, 2, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (135, 1, '获取当前登录用户详细信息', 'get', '/user/findNowUser', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (22, 8, '日志管理', '', '/operationManagement/logManagement', '', 1, 2, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (137, 1, '通过账号查询是否有该用户(返回数量)', 'get', '/user/findCountByName', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (138, 1, '通过编号查询是否有该编号(返回数量)', 'get', '/user/findCountByNum', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (139, 1, '获取当前登录用户所能操作角色集合', 'get', '/role/getRoles', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (140, 15, '添加用户', 'post', '/user/addAllMessages', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (141, 15, '删除用户', 'delete', '/user/deleteList', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (142, 1, '修改用户', 'put', '/user/updateAllMessages', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (144, 15, '导出用户列表', 'get', '/user/export', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (145, 16, '获取角色列表', 'get', '/role/list', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (146, 16, '获取角色对应的用户列表', 'get', '/role/UserList', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (153, 16, '权限获取', 'get', '/authmenu/findtree', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (154, 16, '删除角色及其权限', 'delete', '/role/deleteList', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (155, 16, '添加角色及其权限', 'post', '/role/addRoleAndMenu', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);
INSERT INTO `menu_right` VALUES (156, 16, '修改角色及其权限', 'put', '/role/updateRoleAndMenu', '', 1, 3, NULL, '2019-01-09 17:09:08', NULL);


-- ----------------------------
-- Table structure for user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '2', 'admin', '123456', '0', '李四', 'y0012', '5625@qq.com', '18382100123', '航利中心', '4', '2018-09-09 15:03:18', '2019-01-15 10:06:47', '1', '1');

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '1', '1', null, 'ROLE_超级管理员', '超级管理员', '超级管理员', null, '2018-11-21 16:58:11', '2019-01-10 13:19:59');
INSERT INTO `role` VALUES ('2', '1', '1,2', null, 'ROLE_系统管理员', '系统管理员', '系统管理员', null, '2018-11-21 16:58:07', '2019-01-21 17:22:34');

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
INSERT INTO `user_role` VALUES(1,1);

-- ----------------------------
-- Table structure for role_right
-- ----------------------------
DELETE FROM role_right WHERE role_id=1;

INSERT INTO role_right (SELECT 1, id FROM menu_right);

DELETE FROM role_right WHERE role_id=2;

INSERT INTO role_right (SELECT 2, id FROM menu_right);
