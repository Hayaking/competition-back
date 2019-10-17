/*
 Navicat Premium Data Transfer

 Source Server         : haya
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : xkjs

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 17/10/2019 17:02:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for budget
-- ----------------------------
DROP TABLE IF EXISTS `budget`;
CREATE TABLE `budget`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '预算表',
  `enter` decimal(11, 0) NULL DEFAULT NULL COMMENT '报名费',
  `travel` decimal(11, 0) NULL DEFAULT NULL COMMENT '差旅费',
  `thing` decimal(11, 0) NULL DEFAULT NULL COMMENT '物料费',
  `reason` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  `other` decimal(11, 0) NULL DEFAULT NULL COMMENT '其它费用',
  `competition_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of budget
-- ----------------------------
INSERT INTO `budget` VALUES (2, 123, 123, 123, '123', 123, 65);

-- ----------------------------
-- Table structure for certificate
-- ----------------------------
DROP TABLE IF EXISTS `certificate`;
CREATE TABLE `certificate`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `certificate_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '证书编号',
  `price_id` int(11) NULL DEFAULT NULL,
  `pic_id` int(11) NULL DEFAULT NULL COMMENT '照片',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `price_id`(`price_id`) USING BTREE,
  CONSTRAINT `certificate_ibfk_1` FOREIGN KEY (`price_id`) REFERENCES `price` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '竞赛名称',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '比赛开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '比赛结束时间',
  `enter_start_time` datetime(0) NULL DEFAULT NULL COMMENT '报名开始时间',
  `enter_end_time` datetime(0) NULL DEFAULT NULL COMMENT '报名结束时间',
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立项状态',
  `enter_state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报名状态',
  `start_state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始状态',
  `group_num` int(11) NULL DEFAULT NULL COMMENT '预期参赛对数',
  `stu_num` int(11) NULL DEFAULT NULL COMMENT '预期参赛人数',
  `ex_res` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预期成果',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛地点',
  `org` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办单位',
  `co_org` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协办单位',
  `person_in_charge` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '负责人',
  `creator` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立项者',
  `process` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '比赛流程',
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '比赛简介',
  `max_level_id` int(255) NULL DEFAULT NULL COMMENT '比赛最高级别id',
  `min_level_id` int(11) NULL DEFAULT NULL COMMENT '比赛最小级别id',
  `teacher_group_id` int(11) NULL DEFAULT NULL COMMENT '工作组id',
  `join_type_id` int(11) NULL DEFAULT NULL COMMENT '参赛形式id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type_id`(`min_level_id`) USING BTREE,
  INDEX `teacher_group_id`(`teacher_group_id`) USING BTREE,
  INDEX `type_join_id`(`join_type_id`) USING BTREE,
  INDEX `highest_level`(`max_level_id`) USING BTREE,
  CONSTRAINT `competition_ibfk_1` FOREIGN KEY (`min_level_id`) REFERENCES `type_competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `competition_ibfk_3` FOREIGN KEY (`join_type_id`) REFERENCES `type_join` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `competition_ibfk_4` FOREIGN KEY (`max_level_id`) REFERENCES `type_competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of competition
-- ----------------------------
INSERT INTO `competition` VALUES (43, '蓝桥杯', '2019-10-01 00:00:00', '2019-11-30 00:00:00', '2019-10-01 00:00:00', '2019-10-31 00:00:00', '通过', '已开始', '已开始', 1, 1, '', '1', '1', '1', '1', 't2', '1', '123', 4, 1, 37, 1);
INSERT INTO `competition` VALUES (63, '123', '2019-10-02 00:00:00', '2019-11-17 00:00:00', '2019-10-12 00:00:00', '2019-11-10 00:00:00', '通过', '已开始', '已开始', 123, 123, '123', '123', '123', '123', '123', 't2', '123', '123', 1, 1, 37, 1);
INSERT INTO `competition` VALUES (65, '1', '2019-10-08 00:00:00', '2019-11-11 00:00:00', '2019-10-04 00:00:00', '2019-11-11 00:00:00', '拒绝', '已开始', '已开始', 123, 123, '', '1', '2', '3', '123', 't2', '123', '123', 1, 1, 37, 1);

-- ----------------------------
-- Table structure for cost
-- ----------------------------
DROP TABLE IF EXISTS `cost`;
CREATE TABLE `cost`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost_val` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '值',
  `cost_res` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '理由',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '类型',
  `join_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `join_id`(`join_id`) USING BTREE,
  INDEX `type_id`(`type_id`) USING BTREE,
  CONSTRAINT `cost_ibfk_1` FOREIGN KEY (`join_id`) REFERENCES `join` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `cost_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `type_cost` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for join
-- ----------------------------
DROP TABLE IF EXISTS `join`;
CREATE TABLE `join`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `works_id` int(11) NULL DEFAULT NULL COMMENT '参赛作品id',
  `competition_id` int(11) NULL DEFAULT NULL COMMENT '比赛id',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '参赛小组id',
  `teacher_id1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指导老师1id',
  `teacher_id2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '指导老师2id',
  `apply_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请状态1',
  `apply_state2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '申请状态1',
  `enter_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '报名状态',
  `join_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参赛状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `competition_id`(`competition_id`) USING BTREE,
  INDEX `works_id`(`works_id`) USING BTREE,
  CONSTRAINT `join_ibfk_3` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `join_ibfk_4` FOREIGN KEY (`works_id`) REFERENCES `works` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of join
-- ----------------------------
INSERT INTO `join` VALUES (19, 24, 43, NULL, '17', '0', '通过', '申请中', '通过', '未开始');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `meta_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `meta_id`(`meta_id`) USING BTREE,
  INDEX `path`(`path`) USING BTREE,
  CONSTRAINT `menu_ibfk_1` FOREIGN KEY (`meta_id`) REFERENCES `meta` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, '/student', 'student', 'Main', 3);
INSERT INTO `menu` VALUES (2, '/teacher', 'teacher', 'Main', 3);
INSERT INTO `menu` VALUES (3, '/admin', 'admin', 'Main', 3);
INSERT INTO `menu` VALUES (4, '/judges', 'judges', 'Main', 3);
INSERT INTO `menu` VALUES (5, '/common ', 'common ', 'Main', 3);
INSERT INTO `menu` VALUES (6, '/group', 'group', 'Main', 3);
INSERT INTO `menu` VALUES (7, '/lead', 'lead', 'Main', 3);
INSERT INTO `menu` VALUES (8, '/student/join', 'stu_join', 'stu_join', 7);
INSERT INTO `menu` VALUES (9, '/teacher/group', 'teacher_group', 'teacher_group', 1);
INSERT INTO `menu` VALUES (10, '/admin/teacher/group', 'admin_teacher_group', 'admin_teacher_group', 2);
INSERT INTO `menu` VALUES (11, '/admin/post', 'post', 'admin_post', 2);
INSERT INTO `menu` VALUES (12, '/admin/menu', 'admin_menu', 'admin_menu', 2);
INSERT INTO `menu` VALUES (13, '/admin/permission', 'admin_permission', 'admin_permission', 2);
INSERT INTO `menu` VALUES (14, '/admin/user', 'admin_user', 'admin_user', 2);
INSERT INTO `menu` VALUES (15, '/admin/competition/', 'admin_competition', 'admin_competition', 2);
INSERT INTO `menu` VALUES (16, '/admin/competition/result', 'admin_competition_result', 'admin_competition_result', 2);
INSERT INTO `menu` VALUES (17, '/common/message', 'common_message', 'common_message', 2);
INSERT INTO `menu` VALUES (18, '/common/edit', 'common_edit_self', 'common_edit_self', 2);
INSERT INTO `menu` VALUES (19, '/common/competition', 'common_competition', 'common_competition', 2);
INSERT INTO `menu` VALUES (20, '/group/invite', 'group_invite', 'group_invite', 1);
INSERT INTO `menu` VALUES (21, '/group/competition', 'group_competition', 'group_competition', 2);
INSERT INTO `menu` VALUES (22, '/group/competition/list', 'group_competition_list', 'group_competition_list', 1);
INSERT INTO `menu` VALUES (23, '/group/post', 'group_post', 'group_post', 1);
INSERT INTO `menu` VALUES (24, '/lead/review', 'review', 'lead_review', 2);
INSERT INTO `menu` VALUES (25, '/lead/all', 'all', '/lead/review', 2);
INSERT INTO `menu` VALUES (27, '/student/join/list', 'stu_join_list', 'stu_join_list', 4);
INSERT INTO `menu` VALUES (28, '/student/group', 'stu_group', 'stu_group', 2);
INSERT INTO `menu` VALUES (29, '/teacher/group/list', 'teacher_group_list', 'teacher_group_list', 1);
INSERT INTO `menu` VALUES (30, '/test', 'test', 'test', 2);
INSERT INTO `menu` VALUES (31, '/competition/enter/list', 'competition_enter_list', 'competition_enter_list', 1);
INSERT INTO `menu` VALUES (32, '/common/post/page', 'common-post-page', 'common-post-page', 2);

-- ----------------------------
-- Table structure for menu_children
-- ----------------------------
DROP TABLE IF EXISTS `menu_children`;
CREATE TABLE `menu_children`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_father` int(11) NULL DEFAULT NULL,
  `menu_child` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `menu_father`(`menu_father`) USING BTREE,
  INDEX `menu_child`(`menu_child`) USING BTREE,
  CONSTRAINT `menu_children_ibfk_1` FOREIGN KEY (`menu_father`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `menu_children_ibfk_2` FOREIGN KEY (`menu_child`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu_children
-- ----------------------------
INSERT INTO `menu_children` VALUES (1, 1, 8);
INSERT INTO `menu_children` VALUES (2, 2, 9);
INSERT INTO `menu_children` VALUES (3, 3, 10);
INSERT INTO `menu_children` VALUES (4, 3, 11);
INSERT INTO `menu_children` VALUES (5, 3, 12);
INSERT INTO `menu_children` VALUES (6, 3, 13);
INSERT INTO `menu_children` VALUES (7, 3, 14);
INSERT INTO `menu_children` VALUES (8, 3, 15);
INSERT INTO `menu_children` VALUES (9, 3, 16);
INSERT INTO `menu_children` VALUES (10, 5, 17);
INSERT INTO `menu_children` VALUES (11, 5, 18);
INSERT INTO `menu_children` VALUES (12, 5, 19);
INSERT INTO `menu_children` VALUES (13, 6, 20);
INSERT INTO `menu_children` VALUES (14, 6, 21);
INSERT INTO `menu_children` VALUES (15, 6, 22);
INSERT INTO `menu_children` VALUES (16, 6, 23);
INSERT INTO `menu_children` VALUES (21, 1, 27);
INSERT INTO `menu_children` VALUES (22, 1, 28);
INSERT INTO `menu_children` VALUES (23, 7, 24);
INSERT INTO `menu_children` VALUES (24, 2, 29);
INSERT INTO `menu_children` VALUES (25, 2, 30);
INSERT INTO `menu_children` VALUES (26, 2, 31);
INSERT INTO `menu_children` VALUES (27, 5, 32);

-- ----------------------------
-- Table structure for meta
-- ----------------------------
DROP TABLE IF EXISTS `meta`;
CREATE TABLE `meta`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hide_in_menu` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meta
-- ----------------------------
INSERT INTO `meta` VALUES (1, 'md-home', 'true', '首页');
INSERT INTO `meta` VALUES (2, 'md-home', 'false', '哈哈');
INSERT INTO `meta` VALUES (3, 'md-home', '0', '11');
INSERT INTO `meta` VALUES (4, 'md-home', '0', 'new');
INSERT INTO `meta` VALUES (7, 'md-home', '1', '学生参赛');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '学生');
INSERT INTO `permission` VALUES (2, '教师');
INSERT INTO `permission` VALUES (3, '管理员');
INSERT INTO `permission` VALUES (4, '评委');
INSERT INTO `permission` VALUES (5, '通用');
INSERT INTO `permission` VALUES (6, '工作组');
INSERT INTO `permission` VALUES (7, '指导教师');

-- ----------------------------
-- Table structure for permission_menu
-- ----------------------------
DROP TABLE IF EXISTS `permission_menu`;
CREATE TABLE `permission_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_id` int(11) NULL DEFAULT NULL,
  `menu_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  INDEX `permission_menu_ibfk_2`(`menu_id`) USING BTREE,
  CONSTRAINT `permission_menu_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `permission_menu_ibfk_2` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_menu
-- ----------------------------
INSERT INTO `permission_menu` VALUES (1, 1, 1);
INSERT INTO `permission_menu` VALUES (2, 2, 2);
INSERT INTO `permission_menu` VALUES (3, 3, 3);
INSERT INTO `permission_menu` VALUES (4, 4, 4);
INSERT INTO `permission_menu` VALUES (5, 5, 5);
INSERT INTO `permission_menu` VALUES (6, 6, 6);
INSERT INTO `permission_menu` VALUES (7, 7, 7);

-- ----------------------------
-- Table structure for pic
-- ----------------------------
DROP TABLE IF EXISTS `pic`;
CREATE TABLE `pic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pic
-- ----------------------------
INSERT INTO `pic` VALUES (1, 'static/images/TIM图片20191001113219.jpg', '2019-10-11 19:05:10');
INSERT INTO `pic` VALUES (2, 'static/images/TIM图片20191001113219.jpg', '2019-10-11 19:09:56');

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name_space` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '命名空间',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `body` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '内容',
  `creator_id` int(255) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for price
-- ----------------------------
DROP TABLE IF EXISTS `price`;
CREATE TABLE `price`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '审核状态',
  `price_time` datetime(0) NULL DEFAULT NULL COMMENT '获奖时间',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '获奖类型',
  `join_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `join_id`(`join_id`) USING BTREE,
  INDEX `type_id`(`type_id`) USING BTREE,
  CONSTRAINT `price_ibfk_2` FOREIGN KEY (`join_id`) REFERENCES `join` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `price_ibfk_3` FOREIGN KEY (`type_id`) REFERENCES `type_price` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for process
-- ----------------------------
DROP TABLE IF EXISTS `process`;
CREATE TABLE `process`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `persons` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '参与者',
  `competition_id` int(11) NULL DEFAULT NULL,
  `pic_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `process_ibfk_1`(`competition_id`) USING BTREE,
  CONSTRAINT `process_ibfk_1` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of process
-- ----------------------------
INSERT INTO `process` VALUES (13, '123', '2019-10-11 19:04:26', '123', 43, 0);
INSERT INTO `process` VALUES (14, '123', '2019-10-11 19:05:00', '123', 43, 1);
INSERT INTO `process` VALUES (15, '123', '2019-10-11 19:08:40', '123123333', 43, 2);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '学生');
INSERT INTO `role` VALUES (2, '教师');
INSERT INTO `role` VALUES (3, '管理员');
INSERT INTO `role` VALUES (4, '评委');
INSERT INTO `role` VALUES (5, '通用');
INSERT INTO `role` VALUES (6, '学生组长');
INSERT INTO `role` VALUES (7, '学生组员');
INSERT INTO `role` VALUES (8, '教师组长');
INSERT INTO `role` VALUES (9, '教师组员');
INSERT INTO `role` VALUES (10, '指导教师');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `permission_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `permission_id`(`permission_id`) USING BTREE,
  CONSTRAINT `role_permission_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_permission_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (1, 1, 1);
INSERT INTO `role_permission` VALUES (2, 2, 2);
INSERT INTO `role_permission` VALUES (3, 3, 3);
INSERT INTO `role_permission` VALUES (4, 4, 4);
INSERT INTO `role_permission` VALUES (5, 5, 5);
INSERT INTO `role_permission` VALUES (6, 8, 6);
INSERT INTO `role_permission` VALUES (7, 9, 6);
INSERT INTO `role_permission` VALUES (8, 10, 7);
INSERT INTO `role_permission` VALUES (89, 1, 5);
INSERT INTO `role_permission` VALUES (90, 2, 5);
INSERT INTO `role_permission` VALUES (91, 3, 5);
INSERT INTO `role_permission` VALUES (92, 4, 5);
INSERT INTO `role_permission` VALUES (93, 10, 2);
INSERT INTO `role_permission` VALUES (94, 10, 5);

-- ----------------------------
-- Table structure for role_stu
-- ----------------------------
DROP TABLE IF EXISTS `role_stu`;
CREATE TABLE `role_stu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  CONSTRAINT `role_stu_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_stu_ibfk_3` FOREIGN KEY (`stu_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_stu
-- ----------------------------
INSERT INTO `role_stu` VALUES (27, 15, 1);
INSERT INTO `role_stu` VALUES (28, 15, 6);
INSERT INTO `role_stu` VALUES (29, 15, 7);
INSERT INTO `role_stu` VALUES (30, 16, 1);
INSERT INTO `role_stu` VALUES (31, 16, 7);

-- ----------------------------
-- Table structure for role_teacher
-- ----------------------------
DROP TABLE IF EXISTS `role_teacher`;
CREATE TABLE `role_teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NULL DEFAULT NULL,
  `role_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `role_teacher_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_teacher_ibfk_3` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_teacher
-- ----------------------------
INSERT INTO `role_teacher` VALUES (1, 4, 3);
INSERT INTO `role_teacher` VALUES (18, 4, 2);
INSERT INTO `role_teacher` VALUES (19, 15, 2);
INSERT INTO `role_teacher` VALUES (20, 15, 8);
INSERT INTO `role_teacher` VALUES (21, 16, 2);
INSERT INTO `role_teacher` VALUES (22, 16, 9);
INSERT INTO `role_teacher` VALUES (23, 17, 10);

-- ----------------------------
-- Table structure for stu_group
-- ----------------------------
DROP TABLE IF EXISTS `stu_group`;
CREATE TABLE `stu_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组名',
  `creator` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_group
-- ----------------------------
INSERT INTO `stu_group` VALUES (36, '123', '15', '2019-10-06 14:14:17');

-- ----------------------------
-- Table structure for stu_in_group
-- ----------------------------
DROP TABLE IF EXISTS `stu_in_group`;
CREATE TABLE `stu_in_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stu_id` int(11) NULL DEFAULT NULL,
  `group_id` int(11) NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `group_id`(`group_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  CONSTRAINT `stu_in_group_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `stu_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `stu_in_group_ibfk_3` FOREIGN KEY (`stu_id`) REFERENCES `student` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_in_group
-- ----------------------------
INSERT INTO `stu_in_group` VALUES (42, 15, 36, '邀请成功');
INSERT INTO `stu_in_group` VALUES (43, 16, 36, '邀请中');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `stu_class` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '班级',
  `stu_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系',
  `stu_sex` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `stu_bank_card_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `pic_id` int(255) NULL DEFAULT NULL COMMENT '照片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (15, 's1', '0000', '组长', NULL, NULL, NULL, NULL, 0);
INSERT INTO `student` VALUES (16, 's2', '0000', '组员', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '帐号',
  `password` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `teacher_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `teacher_sex` varchar(3) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `teacher_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `teacher_master` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '擅长',
  `teacher_level` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职称',
  `teacher_bank_card_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `pic_id` int(255) NULL DEFAULT NULL COMMENT '照片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (4, 't1', '0000', '管理员', '男', '110', NULL, NULL, NULL, 0);
INSERT INTO `teacher` VALUES (15, 't2', '0000', '工作组长', '男', NULL, NULL, NULL, NULL, 0);
INSERT INTO `teacher` VALUES (16, 't3', '0000', '工作组员', NULL, NULL, NULL, NULL, NULL, 0);
INSERT INTO `teacher` VALUES (17, 't4', '0000', '指导老师', '男', NULL, NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for teacher_group
-- ----------------------------
DROP TABLE IF EXISTS `teacher_group`;
CREATE TABLE `teacher_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `creator_id` int(255) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `reason` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '申请原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_group
-- ----------------------------
INSERT INTO `teacher_group` VALUES (37, '菊爆大队j', '通过', 15, '2019-10-01 16:18:35', '为了部落');
INSERT INTO `teacher_group` VALUES (38, '小分队', '通过', 16, '2019-10-04 15:42:03', '222');

-- ----------------------------
-- Table structure for teacher_in_group
-- ----------------------------
DROP TABLE IF EXISTS `teacher_in_group`;
CREATE TABLE `teacher_in_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) NULL DEFAULT NULL,
  `group_id` int(11) NULL DEFAULT NULL,
  `state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `group_id`(`group_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `teacher_in_group_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `teacher_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `teacher_in_group_ibfk_3` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_in_group
-- ----------------------------
INSERT INTO `teacher_in_group` VALUES (37, 16, 37, '邀请中');
INSERT INTO `teacher_in_group` VALUES (38, 17, 37, '邀请中');
INSERT INTO `teacher_in_group` VALUES (39, 15, 37, '邀请成功');
INSERT INTO `teacher_in_group` VALUES (40, 4, 37, '邀请中');
INSERT INTO `teacher_in_group` VALUES (41, 15, 38, '邀请成功');

-- ----------------------------
-- Table structure for type_competition
-- ----------------------------
DROP TABLE IF EXISTS `type_competition`;
CREATE TABLE `type_competition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type_competition
-- ----------------------------
INSERT INTO `type_competition` VALUES (1, '院赛');
INSERT INTO `type_competition` VALUES (2, '校赛');
INSERT INTO `type_competition` VALUES (3, '省赛');
INSERT INTO `type_competition` VALUES (4, '国赛');

-- ----------------------------
-- Table structure for type_cost
-- ----------------------------
DROP TABLE IF EXISTS `type_cost`;
CREATE TABLE `type_cost`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for type_join
-- ----------------------------
DROP TABLE IF EXISTS `type_join`;
CREATE TABLE `type_join`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type_join
-- ----------------------------
INSERT INTO `type_join` VALUES (1, '个人赛');
INSERT INTO `type_join` VALUES (2, '小组赛');

-- ----------------------------
-- Table structure for type_price
-- ----------------------------
DROP TABLE IF EXISTS `type_price`;
CREATE TABLE `type_price`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of type_price
-- ----------------------------
INSERT INTO `type_price` VALUES (1, '一等奖');
INSERT INTO `type_price` VALUES (2, '二等奖');
INSERT INTO `type_price` VALUES (3, '三等奖');
INSERT INTO `type_price` VALUES (4, '优秀奖');

-- ----------------------------
-- Table structure for workload
-- ----------------------------
DROP TABLE IF EXISTS `workload`;
CREATE TABLE `workload`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `workload_val` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作量',
  `join_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `join_id`(`join_id`) USING BTREE,
  CONSTRAINT `workload_ibfk_1` FOREIGN KEY (`join_id`) REFERENCES `join` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for works
-- ----------------------------
DROP TABLE IF EXISTS `works`;
CREATE TABLE `works`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `works_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作品名',
  `stu_group_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stu_group_id`(`stu_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of works
-- ----------------------------
INSERT INTO `works` VALUES (24, '123', 36);

SET FOREIGN_KEY_CHECKS = 1;
