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

 Date: 12/11/2019 17:05:55
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
  `progress_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `progress_id`(`progress_id`) USING BTREE,
  CONSTRAINT `budget_ibfk_1` FOREIGN KEY (`progress_id`) REFERENCES `progress` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of budget
-- ----------------------------
INSERT INTO `budget` VALUES (29, 123, 123, 123, '123', 23, 7);

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
-- Records of certificate
-- ----------------------------
INSERT INTO `certificate` VALUES (1, '0', 2, 21);

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '竞赛名称',
  `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '立项状态',
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
  `teacher_group_id` int(11) NULL DEFAULT NULL COMMENT '工作组id',
  `join_type_id` int(11) NULL DEFAULT NULL COMMENT '参赛形式id',
  `is_have_works` tinyint(1) NULL DEFAULT NULL COMMENT '是否需要参赛作品',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_group_id`(`teacher_group_id`) USING BTREE,
  INDEX `join_type_id`(`join_type_id`) USING BTREE,
  CONSTRAINT `competition_ibfk_1` FOREIGN KEY (`join_type_id`) REFERENCES `type_join` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of competition
-- ----------------------------
INSERT INTO `competition` VALUES (84, '单人不需要作品', '通过', 123, 123, '123', '123', '123', '123', '123', 't2', '123', '123', 39, 2, 0);

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
  `join_type_id` int(11) NULL DEFAULT NULL COMMENT '参赛形式id',
  `creator_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `competition_id`(`competition_id`) USING BTREE,
  INDEX `works_id`(`works_id`) USING BTREE,
  CONSTRAINT `join_ibfk_4` FOREIGN KEY (`works_id`) REFERENCES `works` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of join
-- ----------------------------
INSERT INTO `join` VALUES (24, NULL, 84, 39, '17', '16', '申请中', '申请中', '申请中', '未开始', 1, 15, '2019-11-11 16:44:50');

-- ----------------------------
-- Table structure for join_in_progress
-- ----------------------------
DROP TABLE IF EXISTS `join_in_progress`;
CREATE TABLE `join_in_progress`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `progress_id` int(11) NULL DEFAULT NULL COMMENT '比赛阶段id',
  `join_id` int(11) NULL DEFAULT NULL COMMENT '参赛id',
  `price_id` int(11) NULL DEFAULT NULL,
  `promotion_state` tinyint(255) NULL DEFAULT NULL COMMENT '晋级状态',
  `price_state` tinyint(255) NULL DEFAULT NULL COMMENT '得奖状态',
  `review_state` tinyint(255) NULL DEFAULT NULL COMMENT '审核状态',
  `enter_state` tinyint(255) NULL DEFAULT NULL COMMENT '报名状态',
  `join_state` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `progress_id`(`progress_id`) USING BTREE,
  INDEX `join_id`(`join_id`) USING BTREE,
  CONSTRAINT `join_in_progress_ibfk_1` FOREIGN KEY (`progress_id`) REFERENCES `progress` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `join_in_progress_ibfk_2` FOREIGN KEY (`join_id`) REFERENCES `join` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of join_in_progress
-- ----------------------------
INSERT INTO `join_in_progress` VALUES (6, 7, 24, NULL, 1, 1, 0, 1, NULL);

-- ----------------------------
-- Table structure for menu1
-- ----------------------------
DROP TABLE IF EXISTS `menu1`;
CREATE TABLE `menu1`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `meta_id` int(11) NULL DEFAULT NULL,
  `des` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `meta_id`(`meta_id`) USING BTREE,
  CONSTRAINT `menu1_ibfk_1` FOREIGN KEY (`meta_id`) REFERENCES `meta` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu1
-- ----------------------------
INSERT INTO `menu1` VALUES (1, '/group', 'group', 'Main', 3, '工作组');
INSERT INTO `menu1` VALUES (2, '/admin', 'admin', 'Main', 3, '管理');
INSERT INTO `menu1` VALUES (3, '/judges', 'judges', 'Main', 3, '评委');
INSERT INTO `menu1` VALUES (4, '/lead', 'lead', 'Main', 3, '指导');
INSERT INTO `menu1` VALUES (5, '/student', 'student', 'Main', 3, '学生');
INSERT INTO `menu1` VALUES (6, '/common', 'common', 'Main', 3, '通用');

-- ----------------------------
-- Table structure for menu2
-- ----------------------------
DROP TABLE IF EXISTS `menu2`;
CREATE TABLE `menu2`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `meta_id` int(11) NULL DEFAULT NULL,
  `father_id` int(11) NULL DEFAULT NULL,
  `des` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `father_id`(`father_id`) USING BTREE,
  INDEX `meta_id`(`meta_id`) USING BTREE,
  CONSTRAINT `menu2_ibfk_2` FOREIGN KEY (`meta_id`) REFERENCES `meta` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu2
-- ----------------------------
INSERT INTO `menu2` VALUES (1, '/student/join', 'stu_join', 'student/stu_join', 7, 5, NULL);
INSERT INTO `menu2` VALUES (2, '/admin/teacher/group', 'admin_teacher_group', 'admin/admin_teacher_group', 2, 2, NULL);
INSERT INTO `menu2` VALUES (3, '/admin/post', 'post', 'admin/admin_post', 2, 2, NULL);
INSERT INTO `menu2` VALUES (4, '/admin/menu', 'admin_menu', 'admin/admin_menu', 2, 2, NULL);
INSERT INTO `menu2` VALUES (6, '/admin/user', 'admin_user', 'admin/admin_user', 2, 2, NULL);
INSERT INTO `menu2` VALUES (7, '/admin/competition/', 'admin_competition', 'admin/admin_competition', 2, 2, NULL);
INSERT INTO `menu2` VALUES (8, '/admin/competition/result', 'admin_competition_result', 'admin/admin_competition_result', 2, 2, NULL);
INSERT INTO `menu2` VALUES (9, '/group/post', 'group_post', 'group/group_post', 1, 1, NULL);
INSERT INTO `menu2` VALUES (10, '/lead/review', 'review', 'lead/lead_review', 2, 4, NULL);
INSERT INTO `menu2` VALUES (11, '/student/join/list', 'stu_join_list', 'student/stu_join_list', 4, 5, NULL);
INSERT INTO `menu2` VALUES (12, '/student/group', 'stu_group', 'student/stu_group', 2, 5, NULL);
INSERT INTO `menu2` VALUES (13, '/group', 'teacher_group_list', 'group/list', 2, 1, NULL);
INSERT INTO `menu2` VALUES (14, '/common/competition', 'common_competition', 'common/common_competition', 2, 6, NULL);
INSERT INTO `menu2` VALUES (15, '/common/edit', 'common_edit_self', 'common/common_edit_self', 2, 6, NULL);
INSERT INTO `menu2` VALUES (16, '/common/message', 'common_message', 'common/common_message', 2, 6, NULL);
INSERT INTO `menu2` VALUES (17, '/common/post/page', 'common-post-page', 'common/common-post-page', 1, 6, NULL);
INSERT INTO `menu2` VALUES (18, '/group/competition/enter', 'competition_enter_list', 'group/competition_enter_list', 1, 1, NULL);
INSERT INTO `menu2` VALUES (19, '/group/competition/list', 'group_competition_list', 'group/competition_list', 2, 1, NULL);
INSERT INTO `menu2` VALUES (20, '/group/competition', 'group_competition', 'group/competition', 1, 1, NULL);
INSERT INTO `menu2` VALUES (21, '/group/result/list', 'group_result_list', 'group/result_list', 1, 1, NULL);

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
-- Table structure for pic
-- ----------------------------
DROP TABLE IF EXISTS `pic`;
CREATE TABLE `pic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pic
-- ----------------------------
INSERT INTO `pic` VALUES (1, 'static/images/TIM图片20191001113219.jpg', '2019-10-11 19:05:10');
INSERT INTO `pic` VALUES (2, 'static/images/TIM图片20191001113219.jpg', '2019-10-11 19:09:56');
INSERT INTO `pic` VALUES (3, 'static/images/TIM图片20191001113219.jpg', '2019-10-28 17:38:56');
INSERT INTO `pic` VALUES (4, 'static/images/77_avatar.jpg', '2019-11-05 17:24:30');
INSERT INTO `pic` VALUES (5, 'static/images/77_avatar.jpg', '2019-11-12 15:49:18');
INSERT INTO `pic` VALUES (6, 'static/images/bf235716gy1g87b436cjpj20hy0cp758.jpg', '2019-11-12 15:51:00');
INSERT INTO `pic` VALUES (7, 'static/images/bf235716gy1g87b436cjpj20hy0cp758.jpg', '2019-11-12 15:53:17');
INSERT INTO `pic` VALUES (8, 'static/images/v2-e056a7d438b716571413c597ba675961_hd.jpg', '2019-11-12 15:56:31');
INSERT INTO `pic` VALUES (9, 'static/images/006lyySrly1fosydp0gmdj3097069t93.jpg', '2019-11-12 15:57:18');
INSERT INTO `pic` VALUES (10, 'static/images/860d12fely1fuv9rpfrshj20ne0j1q4v.jpg', '2019-11-12 15:59:06');
INSERT INTO `pic` VALUES (11, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:01:40');
INSERT INTO `pic` VALUES (12, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:03:06');
INSERT INTO `pic` VALUES (13, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:05:27');
INSERT INTO `pic` VALUES (14, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:06:45');
INSERT INTO `pic` VALUES (15, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:10:03');
INSERT INTO `pic` VALUES (16, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:14:54');
INSERT INTO `pic` VALUES (17, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:15:42');
INSERT INTO `pic` VALUES (18, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:17:04');
INSERT INTO `pic` VALUES (19, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:18:08');
INSERT INTO `pic` VALUES (20, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:21:36');
INSERT INTO `pic` VALUES (21, 'static/images/005Suejwly1fns3xpwh9vj30xc0p0dpr.jpg', '2019-11-12 16:25:07');

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
  `join_in_progress_id` int(11) NULL DEFAULT NULL,
  `state` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `join_id`(`join_id`) USING BTREE,
  INDEX `type_id`(`type_id`) USING BTREE,
  INDEX `join_in_process_id`(`join_in_progress_id`) USING BTREE,
  CONSTRAINT `price_ibfk_3` FOREIGN KEY (`type_id`) REFERENCES `type_price` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `price_ibfk_4` FOREIGN KEY (`join_in_progress_id`) REFERENCES `join_in_progress` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of price
-- ----------------------------
INSERT INTO `price` VALUES (2, '申请中', '2019-11-12 16:24:57', 1, 0, 6, 2);

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
  `progress_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `process_ibfk_1`(`competition_id`) USING BTREE,
  INDEX `progress_id`(`progress_id`) USING BTREE,
  CONSTRAINT `process_ibfk_1` FOREIGN KEY (`progress_id`) REFERENCES `progress` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for progress
-- ----------------------------
DROP TABLE IF EXISTS `progress`;
CREATE TABLE `progress`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `competition_id` int(11) NULL DEFAULT NULL,
  `type_id` int(11) NULL DEFAULT NULL,
  `start_state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enter_state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `start_time` datetime(0) NULL DEFAULT NULL,
  `end_time` datetime(0) NULL DEFAULT NULL,
  `enter_start_time` datetime(0) NULL DEFAULT NULL,
  `enter_end_time` datetime(0) NULL DEFAULT NULL,
  `is_scan_start_state` tinyint(1) NULL DEFAULT NULL,
  `is_scan_enter_state` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `progress_ibfk_2`(`type_id`) USING BTREE,
  INDEX `competition_id`(`competition_id`) USING BTREE,
  CONSTRAINT `progress_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `type_competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `progress_ibfk_3` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of progress
-- ----------------------------
INSERT INTO `progress` VALUES (7, 84, 1, '未开始', '已开始', '2019-11-13 00:00:00', '2019-12-31 00:00:00', '2019-11-01 00:00:00', '2019-11-30 00:00:00', 1, 1);

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
INSERT INTO `role` VALUES (1, '老师');
INSERT INTO `role` VALUES (2, '管理员');
INSERT INTO `role` VALUES (3, '评委');
INSERT INTO `role` VALUES (4, '指导老师');
INSERT INTO `role` VALUES (5, '学生');
INSERT INTO `role` VALUES (6, '游客');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL,
  `menu1_id` int(11) NULL DEFAULT NULL,
  `menu2_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `role_id`(`role_id`) USING BTREE,
  INDEX `menu_id`(`menu1_id`) USING BTREE,
  INDEX `menu2_id`(`menu2_id`) USING BTREE,
  CONSTRAINT `role_menu_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_menu_ibfk_2` FOREIGN KEY (`menu1_id`) REFERENCES `menu1` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `role_menu_ibfk_3` FOREIGN KEY (`menu2_id`) REFERENCES `menu2` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1, NULL);
INSERT INTO `role_menu` VALUES (2, 1, 6, NULL);
INSERT INTO `role_menu` VALUES (3, 2, 6, 15);
INSERT INTO `role_menu` VALUES (5, 2, 2, 3);
INSERT INTO `role_menu` VALUES (6, 2, 2, 4);
INSERT INTO `role_menu` VALUES (8, 2, 2, 6);
INSERT INTO `role_menu` VALUES (9, 2, 2, 7);
INSERT INTO `role_menu` VALUES (10, 2, 2, 8);
INSERT INTO `role_menu` VALUES (15, 2, 6, 16);
INSERT INTO `role_menu` VALUES (16, 1, 1, 9);
INSERT INTO `role_menu` VALUES (17, 1, 1, 13);
INSERT INTO `role_menu` VALUES (18, 1, 6, 14);
INSERT INTO `role_menu` VALUES (19, 1, 6, 15);
INSERT INTO `role_menu` VALUES (20, 1, 6, 16);
INSERT INTO `role_menu` VALUES (21, 1, 6, 17);
INSERT INTO `role_menu` VALUES (22, 4, 4, 10);
INSERT INTO `role_menu` VALUES (23, 5, 5, 1);
INSERT INTO `role_menu` VALUES (24, 5, 5, 11);
INSERT INTO `role_menu` VALUES (25, 5, 5, 12);
INSERT INTO `role_menu` VALUES (26, 6, 6, 14);
INSERT INTO `role_menu` VALUES (27, 6, 6, 15);
INSERT INTO `role_menu` VALUES (28, 6, 6, 16);
INSERT INTO `role_menu` VALUES (29, 6, 6, 17);
INSERT INTO `role_menu` VALUES (30, 2, 6, 14);
INSERT INTO `role_menu` VALUES (31, 2, 6, 17);
INSERT INTO `role_menu` VALUES (46, 1, 1, 18);
INSERT INTO `role_menu` VALUES (47, 1, 1, 19);
INSERT INTO `role_menu` VALUES (48, 1, 1, 20);
INSERT INTO `role_menu` VALUES (49, 5, 6, 14);
INSERT INTO `role_menu` VALUES (50, 5, 6, 15);
INSERT INTO `role_menu` VALUES (51, 5, 6, 16);
INSERT INTO `role_menu` VALUES (52, 5, 6, 17);
INSERT INTO `role_menu` VALUES (54, 2, 2, 2);
INSERT INTO `role_menu` VALUES (55, 1, 1, 21);

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
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_stu
-- ----------------------------
INSERT INTO `role_stu` VALUES (32, 15, 5);
INSERT INTO `role_stu` VALUES (33, 16, 5);

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
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_teacher
-- ----------------------------
INSERT INTO `role_teacher` VALUES (1, 4, 2);
INSERT INTO `role_teacher` VALUES (24, 15, 1);
INSERT INTO `role_teacher` VALUES (25, 17, 4);
INSERT INTO `role_teacher` VALUES (26, 16, 1);
INSERT INTO `role_teacher` VALUES (27, 16, 4);

-- ----------------------------
-- Table structure for stu_group
-- ----------------------------
DROP TABLE IF EXISTS `stu_group`;
CREATE TABLE `stu_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '组名',
  `creator_id` int(255) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_group
-- ----------------------------
INSERT INTO `stu_group` VALUES (39, '', 15, '2019-11-11 16:44:49');

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
) ENGINE = InnoDB AUTO_INCREMENT = 48 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_in_group
-- ----------------------------
INSERT INTO `stu_in_group` VALUES (47, 15, 39, '邀请成功');

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
  `sign_time` datetime(3) NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (15, 's1', 'cb4b89b98dc11b379391f4b81189b425', 's1', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');
INSERT INTO `student` VALUES (16, 's2', 'cb4b89b98dc11b379391f4b81189b425', 's2', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');
INSERT INTO `student` VALUES (30, 's3', 'cb4b89b98dc11b379391f4b81189b425', 's3', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');
INSERT INTO `student` VALUES (31, 's4', 'cb4b89b98dc11b379391f4b81189b425', 's4', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');
INSERT INTO `student` VALUES (32, 's5', 'cb4b89b98dc11b379391f4b81189b425', 's5', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');
INSERT INTO `student` VALUES (33, 's6', 'cb4b89b98dc11b379391f4b81189b425', 's6', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');
INSERT INTO `student` VALUES (34, 's7', 'cb4b89b98dc11b379391f4b81189b425', 's7', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');

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
  `sign_time` datetime(3) NULL DEFAULT NULL COMMENT '注册时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (4, 't1', 'cb4b89b98dc11b379391f4b81189b425', '管理员', '男', '110', NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485');
INSERT INTO `teacher` VALUES (15, 't2', '4b525b79f3b0a2bafbe9ab860d38bbf8', 't2', '男', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.528');
INSERT INTO `teacher` VALUES (16, 't3', 'f4eebaf80cb7e40a991029e828878e37', 't3', NULL, NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.536');
INSERT INTO `teacher` VALUES (17, 't4', 'abf9ddc8c738ecd7db2fda272f2f3d3c', 't4', '男', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.544');
INSERT INTO `teacher` VALUES (22, 't5', 'c42340dcf758c62102352970ce591e08', 't5', NULL, NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:36.542');
INSERT INTO `teacher` VALUES (23, 't6', 'b953b9f58bfa2917ec2052d36fbe7277', 't6', NULL, NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:36.586');
INSERT INTO `teacher` VALUES (24, 't7', '13e578b2936665342a9658de6b1147c4', 't7', NULL, NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:36.594');
INSERT INTO `teacher` VALUES (25, 't8', '417219374a4d5947274bdca4739a28a2', 't8', NULL, NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:36.604');

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
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_group
-- ----------------------------
INSERT INTO `teacher_group` VALUES (39, '菊爆大队', '通过', 15, '2019-10-23 17:01:43', '为了部落');
INSERT INTO `teacher_group` VALUES (40, '小分队', '通过', 15, '2019-10-23 17:04:26', '全军出击');

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
) ENGINE = InnoDB AUTO_INCREMENT = 78 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_in_group
-- ----------------------------
INSERT INTO `teacher_in_group` VALUES (48, 15, 39, '邀请成功');
INSERT INTO `teacher_in_group` VALUES (49, 15, 40, '邀请成功');
INSERT INTO `teacher_in_group` VALUES (51, 16, 39, '邀请成功');
INSERT INTO `teacher_in_group` VALUES (70, 25, 39, '邀请中');
INSERT INTO `teacher_in_group` VALUES (75, 24, 39, '邀请中');
INSERT INTO `teacher_in_group` VALUES (77, 23, 39, '邀请中');

-- ----------------------------
-- Table structure for type_competition
-- ----------------------------
DROP TABLE IF EXISTS `type_competition`;
CREATE TABLE `type_competition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
INSERT INTO `type_join` VALUES (1, '小组赛');
INSERT INTO `type_join` VALUES (2, '个人赛');

-- ----------------------------
-- Table structure for type_price
-- ----------------------------
DROP TABLE IF EXISTS `type_price`;
CREATE TABLE `type_price`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `creator_id` int(11) NULL DEFAULT NULL,
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stu_group_id`(`stu_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of works
-- ----------------------------
INSERT INTO `works` VALUES (27, '', 0, 15, '');

SET FOREIGN_KEY_CHECKS = 1;
