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

 Date: 14/12/2019 15:40:08
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
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of budget
-- ----------------------------
INSERT INTO `budget` VALUES (40, 100, 100, 100, '123', 100, 18);
INSERT INTO `budget` VALUES (41, 123, 123, 123, '123', 123, 19);
INSERT INTO `budget` VALUES (43, 123, 123, 123, '123', 123, 21);
INSERT INTO `budget` VALUES (44, 123, 123, 123, '123', 123, 22);
INSERT INTO `budget` VALUES (47, 123, 123, 123, '123', 123, 25);
INSERT INTO `budget` VALUES (48, 123, 23, 123, '123', 123, 27);
INSERT INTO `budget` VALUES (49, 12, 123, 123, '123', 123, 28);
INSERT INTO `budget` VALUES (50, 123, 123, 123, '12312', 123, 29);

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
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of certificate
-- ----------------------------
INSERT INTO `certificate` VALUES (2, '666', 3, 22);

-- ----------------------------
-- Table structure for competition
-- ----------------------------
DROP TABLE IF EXISTS `competition`;
CREATE TABLE `competition`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '竞赛名称',
  `state` int(10) NULL DEFAULT NULL COMMENT '审核状态',
  `ex_group_num` int(11) NULL DEFAULT NULL COMMENT '预期参赛对数',
  `ex_stu_num` int(11) NULL DEFAULT NULL COMMENT '预期参赛人数',
  `ex_res` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预期成果',
  `ex_condition` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛条件',
  `pre_price` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '预设奖项',
  `process` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '比赛流程',
  `intro` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '比赛简介',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `person_in_charge_id` int(40) NULL DEFAULT NULL COMMENT '负责人id',
  `creator_id` int(40) NULL DEFAULT NULL COMMENT '立项者id',
  `teacher_group_id` int(11) NULL DEFAULT NULL COMMENT '工作组id',
  `is_need_works` tinyint(1) NULL DEFAULT NULL COMMENT '是否需要参赛作品',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_group_id`(`teacher_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of competition
-- ----------------------------
INSERT INTO `competition` VALUES (98, '单人无作品', -1, 0, 0, '123', '123', '123', '123', '222222', '2019-11-21 18:53:46', 15, 15, 39, NULL);
INSERT INTO `competition` VALUES (99, '单人有作品', 1, NULL, 0, '123', '123', '123', '123', '123', '2019-11-21 18:54:56', 15, 15, 39, NULL);
INSERT INTO `competition` VALUES (101, '多人有作品', 1, NULL, 0, '123', '123', '123', '123', '123', '2019-11-21 19:01:38', 15, 15, 39, NULL);
INSERT INTO `competition` VALUES (102, '多人无作品', 1, NULL, 0, '123', '123', '123', '123', '123', '2019-11-21 19:01:50', 15, 15, 39, NULL);
INSERT INTO `competition` VALUES (105, '123', 0, 123, 123, '123', '123', '123', '123', '123', '2019-11-22 16:59:40', 15, 15, 39, NULL);
INSERT INTO `competition` VALUES (106, '蓝桥杯', 0, 12, 12, '123', '123', '123', '123', '123', '2019-12-04 17:20:34', 15, 15, 39, NULL);
INSERT INTO `competition` VALUES (107, '12312312', 1, 12, 12, '123', '123', '123', '123', '123', '2019-12-13 13:29:06', 15, 15, 39, NULL);

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  CONSTRAINT `join_ibfk_1` FOREIGN KEY (`works_id`) REFERENCES `works` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of join
-- ----------------------------
INSERT INTO `join` VALUES (41, 36, 107, 52, '17', '16', '通过', '申请中', NULL, NULL, 2, 15, '2019-12-13 13:32:26');
INSERT INTO `join` VALUES (46, 36, 107, NULL, '17', '16', '通过', '申请中', NULL, NULL, 2, 15, '2019-12-13 13:32:26');

-- ----------------------------
-- Table structure for join_in_progress
-- ----------------------------
DROP TABLE IF EXISTS `join_in_progress`;
CREATE TABLE `join_in_progress`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `progress_id` int(11) NULL DEFAULT NULL COMMENT '比赛阶段id',
  `join_id` int(11) NULL DEFAULT NULL COMMENT '参赛id',
  `price_id` int(11) NULL DEFAULT NULL COMMENT '获奖id',
  `review_state` tinyint(255) NULL DEFAULT NULL COMMENT '审核？',
  `enter_state` tinyint(255) NULL DEFAULT NULL COMMENT '报名？',
  `is_promotion` tinyint(255) NULL DEFAULT NULL COMMENT '晋级？',
  `is_price` tinyint(255) NULL DEFAULT NULL COMMENT '得奖？',
  `is_join` tinyint(255) NULL DEFAULT NULL COMMENT '参赛？',
  `is_editable` tinyint(255) NULL DEFAULT NULL COMMENT '可编辑？',
  `is_self_funded` tinyint(255) NULL DEFAULT NULL COMMENT '自费？',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `progress_id`(`progress_id`) USING BTREE,
  INDEX `join_id`(`join_id`) USING BTREE,
  CONSTRAINT `join_in_progress_ibfk_1` FOREIGN KEY (`progress_id`) REFERENCES `progress` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `join_in_progress_ibfk_2` FOREIGN KEY (`join_id`) REFERENCES `join` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of join_in_progress
-- ----------------------------
INSERT INTO `join_in_progress` VALUES (24, 21, 41, NULL, 0, 1, 1, 1, NULL, 1, NULL);
INSERT INTO `join_in_progress` VALUES (29, 29, 46, NULL, 0, 0, 0, 0, NULL, 1, 0);

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
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu2
-- ----------------------------
INSERT INTO `menu2` VALUES (1, '/student/join', 'stu_join', 'student/join', 7, 5, NULL);
INSERT INTO `menu2` VALUES (2, '/admin/teacher/group', 'admin_teacher_group', 'admin/admin_teacher_group', 2, 2, NULL);
INSERT INTO `menu2` VALUES (3, '/admin/post', 'post', 'admin/admin_post', 2, 2, NULL);
INSERT INTO `menu2` VALUES (4, '/admin/menu', 'admin_menu', 'admin/admin_menu', 2, 2, NULL);
INSERT INTO `menu2` VALUES (6, '/admin/user', 'admin_user', 'admin/admin_user', 2, 2, NULL);
INSERT INTO `menu2` VALUES (7, '/admin/competition/', 'admin_competition', 'admin/admin_competition', 2, 2, NULL);
INSERT INTO `menu2` VALUES (8, '/admin/competition/result', 'admin_competition_result', 'admin/admin_competition_result', 2, 2, NULL);
INSERT INTO `menu2` VALUES (9, '/group/post', 'group_post', 'group/group_post', 1, 1, NULL);
INSERT INTO `menu2` VALUES (10, '/lead/review', 'review', 'lead/lead_review', 2, 4, NULL);
INSERT INTO `menu2` VALUES (11, '/student/join/list', 'stu_join_list', 'student/join_list', 4, 5, NULL);
INSERT INTO `menu2` VALUES (12, '/student/group', 'stu_group', 'student/group', 2, 5, NULL);
INSERT INTO `menu2` VALUES (13, '/group', 'teacher_group_list', 'group/list', 2, 1, NULL);
INSERT INTO `menu2` VALUES (14, '/common/competition', 'common_competition_list', 'common/competition_list', 2, 6, NULL);
INSERT INTO `menu2` VALUES (15, '/common/edit', 'common_edit_self', 'common/common_edit_self', 2, 6, NULL);
INSERT INTO `menu2` VALUES (16, '/common/message', 'common_message', 'common/common_message', 2, 6, NULL);
INSERT INTO `menu2` VALUES (17, '/common/post/page', 'common-post-page', 'common/common-post-page', 1, 6, NULL);
INSERT INTO `menu2` VALUES (18, '/group/competition/enter', 'competition_enter_list', 'group/competition_enter_list', 1, 1, NULL);
INSERT INTO `menu2` VALUES (19, '/group/competition/list', 'group_competition_list', 'group/competition_list', 2, 1, NULL);
INSERT INTO `menu2` VALUES (20, '/group/competition', 'group_competition', 'group/competition', 1, 1, NULL);
INSERT INTO `menu2` VALUES (21, '/group/result/list', 'group_result_list', 'group/result_list', 1, 1, NULL);
INSERT INTO `menu2` VALUES (34, '/student/price', 'stu_price', 'student/price_list', 2, 5, NULL);
INSERT INTO `menu2` VALUES (35, '/lead/workload', 'work_load', 'lead/work_load', 2, 4, NULL);

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `code` int(255) NULL DEFAULT NULL,
  `to` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `from` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_read` tinyint(255) NULL DEFAULT NULL,
  `is_to_student` tinyint(255) NULL DEFAULT NULL,
  `is_from_student` tinyint(255) NULL DEFAULT NULL,
  `extra` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '附加信息',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 't4邀请你加入菊爆大队', 1, '17', '15', 1, 0, 0, '39', '2019-12-05 20:39:27');

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
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pic
-- ----------------------------
INSERT INTO `pic` VALUES (22, 'static/images/7c4315d1ly1fut553pcgyj20im0czjte.jpg', '2019-12-11 15:21:51');

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
  CONSTRAINT `price_ibfk_3` FOREIGN KEY (`type_id`) REFERENCES `type_price` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of price
-- ----------------------------
INSERT INTO `price` VALUES (3, '申请中', '2019-12-11 15:18:57', 1, 41, 24, 2);

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
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of process
-- ----------------------------
INSERT INTO `process` VALUES (17, '报名', '2019-12-01 16:49:00', NULL, NULL, NULL, 18);
INSERT INTO `process` VALUES (18, '结束', '2019-12-01 16:49:04', NULL, NULL, NULL, 18);
INSERT INTO `process` VALUES (19, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `process` VALUES (20, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for progress
-- ----------------------------
DROP TABLE IF EXISTS `progress`;
CREATE TABLE `progress`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '阶段名称',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '级别id',
  `competition_id` int(11) NULL DEFAULT NULL COMMENT '竞赛id',
  `start_state` int(10) NULL DEFAULT NULL COMMENT '开始状态',
  `enter_state` int(10) NULL DEFAULT NULL COMMENT '报名状态',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '比赛开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '比赛结束时间',
  `enter_start_time` datetime(0) NULL DEFAULT NULL COMMENT '报名开始时间',
  `enter_end_time` datetime(0) NULL DEFAULT NULL COMMENT '报名结束时间',
  `is_scan_start_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否自动扫描开始状态',
  `is_scan_enter_state` tinyint(1) NULL DEFAULT NULL COMMENT '是否自动扫描报名状态',
  `is_submit_result` tinyint(1) NULL DEFAULT NULL COMMENT '是否提交比赛结果\r\n',
  `is_review_result` tinyint(1) NULL DEFAULT NULL COMMENT '比赛结果审核是否通过',
  `is_single` tinyint(1) NULL DEFAULT NULL COMMENT '是否是单人比赛',
  `is_need_works` tinyint(1) NULL DEFAULT NULL COMMENT '是否需要作品',
  `org` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '主办方',
  `co_org` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '协办方',
  `place` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '比赛地点',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `progress_ibfk_2`(`type_id`) USING BTREE,
  INDEX `competition_id`(`competition_id`) USING BTREE,
  CONSTRAINT `progress_ibfk_2` FOREIGN KEY (`type_id`) REFERENCES `type_competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `progress_ibfk_3` FOREIGN KEY (`competition_id`) REFERENCES `competition` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of progress
-- ----------------------------
INSERT INTO `progress` VALUES (18, '初赛', 1, 98, 3, 3, '2019-12-01 00:00:00', '2019-12-08 00:00:00', '2019-11-01 00:00:00', '2019-11-30 00:00:00', 0, 0, 1, 0, 1, 0, '123', '2222222222222', '123');
INSERT INTO `progress` VALUES (19, '决赛', 1, 99, 2, 1, '2019-12-01 00:00:00', '2019-12-31 00:00:00', '2019-11-01 00:00:00', '2019-11-30 00:00:00', 0, 0, NULL, 0, 1, 1, '123', '123', '123');
INSERT INTO `progress` VALUES (21, '决赛', 1, 101, 2, 1, '2019-12-01 00:00:00', '2019-12-31 00:00:00', '2019-11-01 00:00:00', '2019-11-30 00:00:00', 1, 0, NULL, 0, 0, 1, '123', '123', '123');
INSERT INTO `progress` VALUES (22, '决赛', 1, 102, 2, 1, '2019-12-01 00:00:00', '2019-12-31 00:00:00', '2019-11-01 00:00:00', '2019-11-30 00:00:00', 1, 1, NULL, 0, 0, 0, '123', '123', '123');
INSERT INTO `progress` VALUES (25, '123', 1, 105, 2, 1, '2019-12-01 00:00:00', '2019-12-31 00:00:00', '2019-11-01 00:00:00', '2019-11-30 00:00:00', 1, 1, NULL, 0, 1, 0, '123', '123', '123');
INSERT INTO `progress` VALUES (27, '院内选拔', 1, 106, 1, 1, '2019-12-07 00:00:00', '2019-12-21 00:00:00', '2019-12-04 00:00:00', '2019-12-28 00:00:00', 1, 1, NULL, 0, 1, 0, '123', '123', '123');
INSERT INTO `progress` VALUES (28, '省赛', 3, 106, 0, 0, '2020-01-30 00:00:00', '2020-01-31 00:00:00', '2020-01-22 00:00:00', '2020-01-30 00:00:00', 1, 1, NULL, 0, 1, 0, '123', '123', '123');
INSERT INTO `progress` VALUES (29, '初赛', 1, 107, 1, 1, '2019-12-13 00:00:00', '2020-01-22 00:00:00', '2019-12-06 00:00:00', '2020-01-23 00:00:00', 1, 1, NULL, 0, 1, 1, '123', '', '');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES (1, 1, 1, NULL);
INSERT INTO `role_menu` VALUES (2, 1, 6, NULL);
INSERT INTO `role_menu` VALUES (3, 2, 6, 15);
INSERT INTO `role_menu` VALUES (5, 2, 2, 3);
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
INSERT INTO `role_menu` VALUES (59, 5, 5, 34);
INSERT INTO `role_menu` VALUES (61, 4, 4, 10);
INSERT INTO `role_menu` VALUES (62, 4, 4, 35);

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
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_stu
-- ----------------------------
INSERT INTO `role_stu` VALUES (42, 15, 5);
INSERT INTO `role_stu` VALUES (43, 16, 5);
INSERT INTO `role_stu` VALUES (44, 30, 5);
INSERT INTO `role_stu` VALUES (45, 31, 5);
INSERT INTO `role_stu` VALUES (46, 32, 5);
INSERT INTO `role_stu` VALUES (47, 34, 5);
INSERT INTO `role_stu` VALUES (48, 33, 5);
INSERT INTO `role_stu` VALUES (49, 35, 5);

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
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_teacher
-- ----------------------------
INSERT INTO `role_teacher` VALUES (1, 4, 2);
INSERT INTO `role_teacher` VALUES (24, 15, 1);
INSERT INTO `role_teacher` VALUES (25, 17, 4);
INSERT INTO `role_teacher` VALUES (26, 16, 1);
INSERT INTO `role_teacher` VALUES (27, 16, 4);
INSERT INTO `role_teacher` VALUES (28, 17, 1);
INSERT INTO `role_teacher` VALUES (29, 15, 4);

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
) ENGINE = InnoDB AUTO_INCREMENT = 52 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_group
-- ----------------------------
INSERT INTO `stu_group` VALUES (49, 'g1', 15, '2019-11-21 20:58:42');
INSERT INTO `stu_group` VALUES (50, 'g2', 15, '2019-11-21 21:00:47');
INSERT INTO `stu_group` VALUES (51, '组组组', 15, '2019-11-24 17:38:29');
INSERT INTO `stu_group` VALUES (52, '组组组', 15, '2019-11-24 17:38:29');

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
) ENGINE = InnoDB AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_in_group
-- ----------------------------
INSERT INTO `stu_in_group` VALUES (66, 15, 49, '邀请成功');
INSERT INTO `stu_in_group` VALUES (67, 16, 49, '邀请中');
INSERT INTO `stu_in_group` VALUES (68, 15, 50, '邀请成功');
INSERT INTO `stu_in_group` VALUES (69, 16, 50, '邀请中');
INSERT INTO `stu_in_group` VALUES (70, 15, 52, '邀请成功');
INSERT INTO `stu_in_group` VALUES (71, 15, 51, '邀请成功');
INSERT INTO `stu_in_group` VALUES (72, 16, 51, '邀请中');
INSERT INTO `stu_in_group` VALUES (73, 16, 52, '邀请中');

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
  `price_num` int(11) NULL DEFAULT NULL COMMENT '得奖数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (15, 's1', 'cb4b89b98dc11b379391f4b81189b425', 'haya', '1', NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485', 0);
INSERT INTO `student` VALUES (16, 's2', 'cb4b89b98dc11b379391f4b81189b425', 's2', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485', 0);
INSERT INTO `student` VALUES (30, 's3', 'cb4b89b98dc11b379391f4b81189b425', 's3', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485', 0);
INSERT INTO `student` VALUES (31, 's4', 'cb4b89b98dc11b379391f4b81189b425', 's4', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485', 0);
INSERT INTO `student` VALUES (32, 's5', 'cb4b89b98dc11b379391f4b81189b425', 's5', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485', 0);
INSERT INTO `student` VALUES (33, 's6', 'cb4b89b98dc11b379391f4b81189b425', 's6', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485', 0);
INSERT INTO `student` VALUES (34, 's7', 'cb4b89b98dc11b379391f4b81189b425', 's7', NULL, NULL, NULL, NULL, 0, '2019-10-18 17:39:06.485', 0);
INSERT INTO `student` VALUES (35, 'sss', '5eea4922fa277cab4fa894a0e75767f6', NULL, NULL, NULL, NULL, NULL, 0, '2019-12-13 11:32:54.848', 0);

-- ----------------------------
-- Table structure for student_group_log
-- ----------------------------
DROP TABLE IF EXISTS `student_group_log`;
CREATE TABLE `student_group_log`  (
  `id` int(11) NOT NULL,
  `action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `operator_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `group_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `group_id`(`group_id`) USING BTREE,
  CONSTRAINT `student_group_log_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `stu_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_group
-- ----------------------------
INSERT INTO `teacher_group` VALUES (39, '菊爆大队', '通过', 15, '2019-10-23 17:01:43', '为了部落');
INSERT INTO `teacher_group` VALUES (40, '小分队', '通过', 15, '2019-10-23 17:04:26', '全军出击');

-- ----------------------------
-- Table structure for teacher_group_log
-- ----------------------------
DROP TABLE IF EXISTS `teacher_group_log`;
CREATE TABLE `teacher_group_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `action` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `operator_id` int(11) NULL DEFAULT NULL,
  `group_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `group_id`(`group_id`) USING BTREE,
  CONSTRAINT `teacher_group_log_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `teacher_group` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_group_log
-- ----------------------------
INSERT INTO `teacher_group_log` VALUES (1, 't2,创建了蓝桥杯', '2019-12-04 17:20:34', 15, 39);
INSERT INTO `teacher_group_log` VALUES (2, 't2,邀请t4加入工作组', '2019-12-04 20:40:45', 15, 39);
INSERT INTO `teacher_group_log` VALUES (3, 't2,邀请t4加入工作组', '2019-12-04 20:43:46', 15, 39);
INSERT INTO `teacher_group_log` VALUES (4, 't2,邀请t4加入工作组', '2019-12-04 20:46:33', 15, 39);
INSERT INTO `teacher_group_log` VALUES (5, 't2,邀请t4加入工作组', '2019-12-04 20:46:33', 15, 39);
INSERT INTO `teacher_group_log` VALUES (6, 't2,邀请t4加入工作组', '2019-12-04 20:49:05', 15, 39);
INSERT INTO `teacher_group_log` VALUES (7, 't2,邀请t4加入工作组', '2019-12-04 20:52:47', 15, 39);
INSERT INTO `teacher_group_log` VALUES (8, 't2,邀请t4加入工作组', '2019-12-04 20:57:47', 15, 39);
INSERT INTO `teacher_group_log` VALUES (9, 't2,邀请t4加入工作组', '2019-12-04 21:02:46', 15, 39);
INSERT INTO `teacher_group_log` VALUES (10, 't2,邀请t4加入工作组', '2019-12-04 21:02:52', 15, 39);
INSERT INTO `teacher_group_log` VALUES (11, 't2,邀请t4加入工作组', '2019-12-04 21:03:20', 15, 39);
INSERT INTO `teacher_group_log` VALUES (12, 't2,邀请t4加入工作组', '2019-12-04 21:03:31', 15, 39);
INSERT INTO `teacher_group_log` VALUES (13, 't2,邀请t7加入工作组', '2019-12-04 21:03:52', 15, 39);
INSERT INTO `teacher_group_log` VALUES (14, 't2,邀请t4加入工作组', '2019-12-04 21:08:33', 15, 39);
INSERT INTO `teacher_group_log` VALUES (15, 't2,邀请t4加入工作组', '2019-12-04 21:08:33', 15, 39);
INSERT INTO `teacher_group_log` VALUES (16, 't2,邀请t4加入工作组', '2019-12-04 21:08:37', 15, 39);
INSERT INTO `teacher_group_log` VALUES (17, 't2,邀请t4加入工作组', '2019-12-04 21:08:42', 15, 39);
INSERT INTO `teacher_group_log` VALUES (18, 't2,邀请t4加入工作组', '2019-12-04 21:08:46', 15, 39);
INSERT INTO `teacher_group_log` VALUES (19, 't2,邀请t4加入工作组', '2019-12-04 21:08:52', 15, 39);
INSERT INTO `teacher_group_log` VALUES (20, 't2,邀请t4加入工作组', '2019-12-04 21:08:56', 15, 39);
INSERT INTO `teacher_group_log` VALUES (21, 't2,邀请t4加入工作组', '2019-12-04 21:14:57', 15, 39);
INSERT INTO `teacher_group_log` VALUES (22, 't2,邀请t4加入工作组', '2019-12-04 21:15:03', 15, 39);
INSERT INTO `teacher_group_log` VALUES (23, 't2,邀请t4加入工作组', '2019-12-04 21:15:19', 15, 39);
INSERT INTO `teacher_group_log` VALUES (24, 't2,邀请t4加入工作组', '2019-12-04 21:15:49', 15, 39);
INSERT INTO `teacher_group_log` VALUES (25, 't2,邀请t4加入工作组', '2019-12-04 21:16:08', 15, 39);
INSERT INTO `teacher_group_log` VALUES (26, 't2,邀请t5加入工作组', '2019-12-04 21:16:09', 15, 39);
INSERT INTO `teacher_group_log` VALUES (27, 't2,邀请t4加入工作组', '2019-12-04 21:16:13', 15, 39);
INSERT INTO `teacher_group_log` VALUES (28, 't2,邀请t4加入工作组', '2019-12-04 21:18:06', 15, 39);
INSERT INTO `teacher_group_log` VALUES (29, 't2,邀请t4加入工作组', '2019-12-04 21:18:46', 15, 39);
INSERT INTO `teacher_group_log` VALUES (30, 't2,邀请t4加入工作组', '2019-12-05 16:16:48', 15, 39);
INSERT INTO `teacher_group_log` VALUES (31, 't2,邀请t4加入工作组', '2019-12-05 16:17:37', 15, 39);
INSERT INTO `teacher_group_log` VALUES (32, 't2,邀请t4加入工作组', '2019-12-05 16:22:43', 15, 39);
INSERT INTO `teacher_group_log` VALUES (33, 't2,邀请t4加入工作组', '2019-12-05 16:24:00', 15, 39);
INSERT INTO `teacher_group_log` VALUES (34, 't2,创建了12312312', '2019-12-13 13:29:06', 15, 39);

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
) ENGINE = InnoDB AUTO_INCREMENT = 116 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_in_group
-- ----------------------------
INSERT INTO `teacher_in_group` VALUES (48, 15, 39, '邀请成功');
INSERT INTO `teacher_in_group` VALUES (49, 15, 40, '邀请成功');
INSERT INTO `teacher_in_group` VALUES (51, 16, 39, '邀请成功');
INSERT INTO `teacher_in_group` VALUES (116, 17, 39, '邀请成功');

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
  `val` double(255, 0) NULL DEFAULT NULL COMMENT '工作量',
  `join_id` int(11) NULL DEFAULT NULL,
  `teacher_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE,
  INDEX `join_id`(`join_id`) USING BTREE,
  CONSTRAINT `workload_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of works
-- ----------------------------
INSERT INTO `works` VALUES (31, '学科竞赛管理系统', 52, 15, '123123123123123123123123123');
INSERT INTO `works` VALUES (36, '123', 0, 15, '123');

SET FOREIGN_KEY_CHECKS = 1;
