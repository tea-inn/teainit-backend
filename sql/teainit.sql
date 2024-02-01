
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for login_log
-- ----------------------------
DROP TABLE IF EXISTS `login_log`;
CREATE TABLE `login_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问编号',
  `loginAccount` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录的账号',
  `loginState` tinyint NOT NULL COMMENT '操作状态（0-成功 1-失败）',
  `loginInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录信息',
  `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地址',
  `loginAddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地点',
  `browser` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '浏览器',
  `operSystem` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作系统',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除 1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 638 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of login_log
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单 id',
  `menuName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `parentId` bigint NULL DEFAULT 0 COMMENT '父级 id',
  `sortNum` int NOT NULL COMMENT '菜单排序号（数字越小越靠前）',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限',
  `type` tinyint NOT NULL COMMENT '类型（0-目录，1-菜单，2-按钮）',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（查寝时间）',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除 1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 925 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '系统管理', 0, 1, '-', 0, '2023-05-04 08:09:26', '2024-01-27 22:16:04', 0);
INSERT INTO `menu` VALUES (100, '用户管理', 1, 1, 'system:user:view', 1, '2023-05-04 08:11:33', '2024-01-29 00:22:43', 0);
INSERT INTO `menu` VALUES (101, '角色管理', 1, 2, 'system:role:view', 1, '2023-05-04 08:12:11', '2024-01-28 15:39:02', 0);
INSERT INTO `menu` VALUES (102, '日志管理', 1, 4, '-', 0, '2023-05-04 08:12:22', '2024-01-28 15:46:16', 0);
INSERT INTO `menu` VALUES (104, '菜单管理', 1, 3, 'system:menu:view', 1, '2023-05-04 08:13:23', '2024-01-28 15:08:47', 0);
INSERT INTO `menu` VALUES (800, '操作日志', 102, 1, 'system:oper:view', 1, '2023-05-04 08:19:30', '2024-01-28 15:10:28', 0);
INSERT INTO `menu` VALUES (801, '登录日志', 102, 2, 'system:login:view', 1, '2023-05-04 08:19:44', '2024-01-28 15:10:33', 0);
INSERT INTO `menu` VALUES (901, '新增用户', 100, 1, 'system:user:add', 2, '2024-01-28 10:08:30', '2024-01-28 15:04:13', 1);
INSERT INTO `menu` VALUES (903, '用户查询', 100, 1, 'system:user:list', 2, '2024-01-28 14:39:37', '2024-01-28 14:40:53', 0);
INSERT INTO `menu` VALUES (904, '用户新增', 100, 2, 'system:user:add', 2, '2024-01-28 15:04:46', '2024-01-28 15:04:54', 0);
INSERT INTO `menu` VALUES (905, '用户修改', 100, 3, 'system:user:edit', 2, '2024-01-28 15:05:26', '2024-01-28 15:05:26', 0);
INSERT INTO `menu` VALUES (906, '用户删除', 100, 4, 'system:user:remove', 2, '2024-01-28 15:06:28', '2024-01-28 15:06:28', 0);
INSERT INTO `menu` VALUES (907, '角色查询', 101, 1, 'system:role:list', 2, '2024-01-28 15:12:44', '2024-01-28 15:12:44', 0);
INSERT INTO `menu` VALUES (908, '角色新增', 101, 2, 'system:role:add', 2, '2024-01-28 15:21:55', '2024-01-28 15:21:55', 0);
INSERT INTO `menu` VALUES (909, '角色修改', 101, 3, 'system:role:edit', 2, '2024-01-28 15:22:43', '2024-01-28 15:22:43', 0);
INSERT INTO `menu` VALUES (910, '角色删除', 101, 4, 'system:role:remove', 2, '2024-01-28 15:23:06', '2024-01-28 15:23:06', 0);
INSERT INTO `menu` VALUES (911, '操作查询', 800, 1, 'system:oper:list', 2, '2024-01-28 15:26:22', '2024-01-28 19:49:05', 0);
INSERT INTO `menu` VALUES (912, '登录查询', 801, 1, 'system:login:list', 2, '2024-01-28 15:26:48', '2024-01-28 19:49:12', 0);
INSERT INTO `menu` VALUES (913, '菜单查看', 104, 1, 'system:menu:list', 2, '2024-01-28 15:27:20', '2024-01-28 15:27:20', 0);
INSERT INTO `menu` VALUES (914, '菜单新增', 104, 2, 'system:menu:add', 2, '2024-01-28 15:27:40', '2024-01-28 15:27:40', 0);
INSERT INTO `menu` VALUES (915, '菜单修改', 104, 3, 'system:menu:edit', 2, '2024-01-28 15:28:04', '2024-01-28 15:33:25', 0);
INSERT INTO `menu` VALUES (916, '菜单删除', 104, 4, 'system:menu:remove', 2, '2024-01-28 15:28:22', '2024-01-28 15:33:31', 0);

-- ----------------------------
-- Table structure for oper_log
-- ----------------------------
DROP TABLE IF EXISTS `oper_log`;
CREATE TABLE `oper_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键 id',
  `userId` bigint NOT NULL COMMENT '操作人',
  `userAccount` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作账号',
  `operModule` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作模块',
  `operType` tinyint NOT NULL COMMENT '操作类型（0-其他，1-新增，2-修改，3-删除，4-导入，5-导出）',
  `operState` tinyint NOT NULL COMMENT '操作状态（0-成功 1-失败）',
  `ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作地址',
  `operAddress` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作地点',
  `operMethod` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作方法',
  `operUrl` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '操作路径',
  `requestParam` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求参数',
  `responseInfo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '返回信息',
  `errorInfo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误信息',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除 1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 392 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oper_log
-- ----------------------------

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色 id',
  `roleName` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间（查寝时间）',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除（0-未删除 1-删除）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '超级管理员', '2023-05-27 16:45:21', '2024-01-29 19:43:53', 0);

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `roleId` bigint NULL DEFAULT NULL COMMENT '角色 id',
  `menuId` bigint NULL DEFAULT NULL COMMENT '菜单 id （拥有权限）'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 903);
INSERT INTO `role_menu` VALUES (1, 904);
INSERT INTO `role_menu` VALUES (1, 905);
INSERT INTO `role_menu` VALUES (1, 906);
INSERT INTO `role_menu` VALUES (1, 907);
INSERT INTO `role_menu` VALUES (1, 908);
INSERT INTO `role_menu` VALUES (1, 909);
INSERT INTO `role_menu` VALUES (1, 910);
INSERT INTO `role_menu` VALUES (1, 913);
INSERT INTO `role_menu` VALUES (1, 914);
INSERT INTO `role_menu` VALUES (1, 915);
INSERT INTO `role_menu` VALUES (1, 916);
INSERT INTO `role_menu` VALUES (1, 911);
INSERT INTO `role_menu` VALUES (1, 912);
INSERT INTO `role_menu` VALUES (1, 800);
INSERT INTO `role_menu` VALUES (1, 801);
INSERT INTO `role_menu` VALUES (1, 102);
INSERT INTO `role_menu` VALUES (1, 100);
INSERT INTO `role_menu` VALUES (1, 101);
INSERT INTO `role_menu` VALUES (1, 104);
INSERT INTO `role_menu` VALUES (1, 1);
INSERT INTO `role_menu` VALUES (1, 919);
INSERT INTO `role_menu` VALUES (1, 920);
INSERT INTO `role_menu` VALUES (1, 923);
INSERT INTO `role_menu` VALUES (1, 921);
INSERT INTO `role_menu` VALUES (1, 922);
INSERT INTO `role_menu` VALUES (1, 924);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAccount` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `userAvatar` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `gender` tinyint NULL DEFAULT 2 COMMENT '性别（0-男 1-女 2-未知）',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `errorCount` int NOT NULL DEFAULT 0 COMMENT '登录错误次数',
  `lastLoginTime` datetime NULL DEFAULT NULL COMMENT '最后登录时间',
  `createTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `isDelete` tinyint NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_userAccount`(`userAccount` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '管理员', 'admin_teainn', 'http://teainn.top:10002/static/teainn-4fe86bd8.png', 0, '136cab82bfdab0e552715d9e79281371', 0, NULL, '2023-02-06 11:48:58', '2024-02-01 10:20:04', 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `userId` bigint NULL DEFAULT NULL COMMENT '用户 id',
  `roleId` bigint NULL DEFAULT NULL COMMENT '角色 id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色中间表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1);

SET FOREIGN_KEY_CHECKS = 1;
