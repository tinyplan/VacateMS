/*
 Navicat Premium Data Transfer

 Source Server         : MYSQL56
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : localhost:3306
 Source Schema         : vacate

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 01/02/2020 19:52:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `course_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `teacher_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `week` int(2) NOT NULL,
  PRIMARY KEY (`id`, `course_id`) USING BTREE,
  INDEX `fk_course_id`(`teacher_id`) USING BTREE,
  CONSTRAINT `fk_course_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, 'c0', 'javaEE', 't001', 1);
INSERT INTO `course` VALUES (2, 'c1', '数据结构', 't002', 1);
INSERT INTO `course` VALUES (3, 'c2', '图形图像处理', 't002', 2);
INSERT INTO `course` VALUES (4, 'c3', '微机接口', 't003', 1);
INSERT INTO `course` VALUES (5, 'c4', 'C++', 't001', 3);
INSERT INTO `course` VALUES (6, 'c5', '英语', 't001', 3);
INSERT INTO `course` VALUES (7, 'c6', '计算机接口技术', 't004', 4);
INSERT INTO `course` VALUES (8, 'c7', 'Android高级应用开发', 't003', 5);
INSERT INTO `course` VALUES (9, 'c8', '网络系统配置', 't002', 5);

-- ----------------------------
-- Table structure for edu
-- ----------------------------
DROP TABLE IF EXISTS `edu`;
CREATE TABLE `edu`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `edu_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`, `edu_id`) USING BTREE,
  INDEX `edu_id`(`edu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of edu
-- ----------------------------
INSERT INTO `edu` VALUES (1, 'edu01', 'Mike', '444');

-- ----------------------------
-- Table structure for edu_vacate
-- ----------------------------
DROP TABLE IF EXISTS `edu_vacate`;
CREATE TABLE `edu_vacate`  (
  `edu_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vacate_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(1) NOT NULL DEFAULT 0,
  INDEX `fk_part_edu_id`(`edu_id`) USING BTREE,
  INDEX `fk_part_edu_v`(`vacate_id`) USING BTREE,
  CONSTRAINT `fk_part_edu_id` FOREIGN KEY (`edu_id`) REFERENCES `edu` (`edu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_part_edu_v` FOREIGN KEY (`vacate_id`) REFERENCES `vacate` (`vacate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of edu_vacate
-- ----------------------------
INSERT INTO `edu_vacate` VALUES ('edu01', '54649153-e767-4b1c-8d8d-0c7b34452b64', 1);
INSERT INTO `edu_vacate` VALUES ('edu01', '978532c7-24a1-4433-a655-d44466cce035', 1);
INSERT INTO `edu_vacate` VALUES ('edu01', '96e9b1fc-b579-4c07-874e-0aae59e1fc60', 1);
INSERT INTO `edu_vacate` VALUES ('edu01', '8b25ea85-3bd6-4cf7-8e53-488470d9919f', 1);

-- ----------------------------
-- Table structure for grade
-- ----------------------------
DROP TABLE IF EXISTS `grade`;
CREATE TABLE `grade`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `grade_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `tutor_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`, `grade_id`) USING BTREE,
  INDEX `grade_id`(`grade_id`) USING BTREE,
  INDEX `fk_tutor_id`(`tutor_id`) USING BTREE,
  CONSTRAINT `fk_tutor_id` FOREIGN KEY (`tutor_id`) REFERENCES `tutor` (`tutor_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of grade
-- ----------------------------
INSERT INTO `grade` VALUES (1, 'grade01', 'tutor01');
INSERT INTO `grade` VALUES (2, 'grade02', 'tutor01');
INSERT INTO `grade` VALUES (3, 'grade03', 'tutor02');

-- ----------------------------
-- Table structure for stu_vacate
-- ----------------------------
DROP TABLE IF EXISTS `stu_vacate`;
CREATE TABLE `stu_vacate`  (
  `stu_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vacate_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`vacate_id`) USING BTREE,
  INDEX `fk_part_stu_id`(`stu_id`) USING BTREE,
  CONSTRAINT `fk_part_stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_part_stu_v` FOREIGN KEY (`vacate_id`) REFERENCES `vacate` (`vacate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of stu_vacate
-- ----------------------------
INSERT INTO `stu_vacate` VALUES ('stu01', '30eff0a3-bbb2-46bd-8ceb-f7d7f4e1f658', -1);
INSERT INTO `stu_vacate` VALUES ('stu02', '4b7deff0-817c-444f-8607-b465f25bd48d', -1);
INSERT INTO `stu_vacate` VALUES ('stu01', '54649153-e767-4b1c-8d8d-0c7b34452b64', 1);
INSERT INTO `stu_vacate` VALUES ('stu01', '6a575238-86da-46ff-b5b5-45de2cb81bf7', 0);
INSERT INTO `stu_vacate` VALUES ('stu01', '8b25ea85-3bd6-4cf7-8e53-488470d9919f', 1);
INSERT INTO `stu_vacate` VALUES ('stu01', '93d50113-20b7-44c7-a6f5-c1f399bff00b', 0);
INSERT INTO `stu_vacate` VALUES ('stu02', '96e9b1fc-b579-4c07-874e-0aae59e1fc60', 1);
INSERT INTO `stu_vacate` VALUES ('stu01', '978532c7-24a1-4433-a655-d44466cce035', 1);
INSERT INTO `stu_vacate` VALUES ('stu01', 'a05e4c6f-c9df-4ee7-96e9-20920d17ec0e', 0);
INSERT INTO `stu_vacate` VALUES ('stu01', 'f797bf0e-9e70-491e-ab61-0834b4e12f0d', 0);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `grade_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `course_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`, `stu_id`) USING BTREE,
  INDEX `fk_grade_id`(`grade_id`) USING BTREE,
  INDEX `stu_id`(`stu_id`) USING BTREE,
  CONSTRAINT `fk_grade_id` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`grade_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'stu01', 'tom', 'grade01', '123', 'c0,c2,c5,c8');
INSERT INTO `student` VALUES (2, 'stu02', '李四', 'grade02', '123', 'c0,c1,c3,c4,c7');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `teacher_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`, `teacher_id`) USING BTREE,
  INDEX `teacher_id`(`teacher_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 't001', '教师1', '123');
INSERT INTO `teacher` VALUES (2, 't002', '教师2', '123');
INSERT INTO `teacher` VALUES (3, 't003', '教师3', '456');
INSERT INTO `teacher` VALUES (4, 't004', '教师4', '123');
INSERT INTO `teacher` VALUES (5, 't005', '教师5', '123');

-- ----------------------------
-- Table structure for teacher_vacate
-- ----------------------------
DROP TABLE IF EXISTS `teacher_vacate`;
CREATE TABLE `teacher_vacate`  (
  `teacher_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vacate_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(1) NOT NULL,
  INDEX `fk_part_teacher_id`(`teacher_id`) USING BTREE,
  INDEX `fk_part_teacher_v`(`vacate_id`) USING BTREE,
  CONSTRAINT `fk_part_teacher_id` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`teacher_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_part_teacher_v` FOREIGN KEY (`vacate_id`) REFERENCES `vacate` (`vacate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of teacher_vacate
-- ----------------------------
INSERT INTO `teacher_vacate` VALUES ('t001', '54649153-e767-4b1c-8d8d-0c7b34452b64', 1);
INSERT INTO `teacher_vacate` VALUES ('t001', '978532c7-24a1-4433-a655-d44466cce035', 1);
INSERT INTO `teacher_vacate` VALUES ('t001', '96e9b1fc-b579-4c07-874e-0aae59e1fc60', 1);
INSERT INTO `teacher_vacate` VALUES ('t001', '8b25ea85-3bd6-4cf7-8e53-488470d9919f', 0);
INSERT INTO `teacher_vacate` VALUES ('t002', '8b25ea85-3bd6-4cf7-8e53-488470d9919f', 0);

-- ----------------------------
-- Table structure for tutor
-- ----------------------------
DROP TABLE IF EXISTS `tutor`;
CREATE TABLE `tutor`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `tutor_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `pwd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `edu_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`, `tutor_id`) USING BTREE,
  INDEX `tutor_id`(`tutor_id`) USING BTREE,
  INDEX `fk_edu_id`(`edu_id`) USING BTREE,
  CONSTRAINT `fk_edu_id` FOREIGN KEY (`edu_id`) REFERENCES `edu` (`edu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tutor
-- ----------------------------
INSERT INTO `tutor` VALUES (1, 'tutor01', 'tom', '123456', 'edu01');
INSERT INTO `tutor` VALUES (2, 'tutor02', '张三', '123', 'edu01');
INSERT INTO `tutor` VALUES (7, '2003', '李四', '666666', 'edu01');
INSERT INTO `tutor` VALUES (8, '2004', '王五', '666666', 'edu01');

-- ----------------------------
-- Table structure for tutor_vacate
-- ----------------------------
DROP TABLE IF EXISTS `tutor_vacate`;
CREATE TABLE `tutor_vacate`  (
  `tutor_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `vacate_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `status` int(1) NOT NULL DEFAULT 0,
  INDEX `fk_part_tutor_id`(`tutor_id`) USING BTREE,
  INDEX `fk_part_tutor_v`(`vacate_id`) USING BTREE,
  CONSTRAINT `fk_part_tutor_id` FOREIGN KEY (`tutor_id`) REFERENCES `tutor` (`tutor_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `fk_part_tutor_v` FOREIGN KEY (`vacate_id`) REFERENCES `vacate` (`vacate_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tutor_vacate
-- ----------------------------
INSERT INTO `tutor_vacate` VALUES ('tutor01', '978532c7-24a1-4433-a655-d44466cce035', 1);
INSERT INTO `tutor_vacate` VALUES ('tutor01', '54649153-e767-4b1c-8d8d-0c7b34452b64', 1);
INSERT INTO `tutor_vacate` VALUES ('tutor01', '30eff0a3-bbb2-46bd-8ceb-f7d7f4e1f658', -1);
INSERT INTO `tutor_vacate` VALUES ('tutor01', '96e9b1fc-b579-4c07-874e-0aae59e1fc60', 1);
INSERT INTO `tutor_vacate` VALUES ('tutor01', '4b7deff0-817c-444f-8607-b465f25bd48d', -1);
INSERT INTO `tutor_vacate` VALUES ('tutor01', '8b25ea85-3bd6-4cf7-8e53-488470d9919f', 1);
INSERT INTO `tutor_vacate` VALUES ('tutor01', '6a575238-86da-46ff-b5b5-45de2cb81bf7', 0);
INSERT INTO `tutor_vacate` VALUES ('tutor01', 'a05e4c6f-c9df-4ee7-96e9-20920d17ec0e', 0);
INSERT INTO `tutor_vacate` VALUES ('tutor01', 'f797bf0e-9e70-491e-ab61-0834b4e12f0d', 0);
INSERT INTO `tutor_vacate` VALUES ('tutor01', '93d50113-20b7-44c7-a6f5-c1f399bff00b', 0);

-- ----------------------------
-- Table structure for vacate
-- ----------------------------
DROP TABLE IF EXISTS `vacate`;
CREATE TABLE `vacate`  (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `vacate_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `stu_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `term` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `submit_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `start_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `end_time` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `period` int(3) NOT NULL,
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `course_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `step` int(3) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`, `vacate_id`) USING BTREE,
  INDEX `fk_stu_id`(`stu_id`) USING BTREE,
  INDEX `vacate_id`(`vacate_id`) USING BTREE,
  CONSTRAINT `fk_stu_id` FOREIGN KEY (`stu_id`) REFERENCES `student` (`stu_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of vacate
-- ----------------------------
INSERT INTO `vacate` VALUES (79, '978532c7-24a1-4433-a655-d44466cce035', 'stu01', '2019-2020-1', '1576326124000', '1575388800000', '1576080000000', 9, '病假', '', 'c5', 3);
INSERT INTO `vacate` VALUES (80, '54649153-e767-4b1c-8d8d-0c7b34452b64', 'stu01', '2019-2020-1', '1576326133000', '1575388800000', '1576080000000', 9, '学院学生辅助工作', '', 'c5', 3);
INSERT INTO `vacate` VALUES (81, '30eff0a3-bbb2-46bd-8ceb-f7d7f4e1f658', 'stu01', '2019-2020-1', '1576326938000', '1575302400000', '1576080000000', 10, '学院学生辅助工作', '', 'c5', 1);
INSERT INTO `vacate` VALUES (82, '96e9b1fc-b579-4c07-874e-0aae59e1fc60', 'stu02', '2019-2020-1', '1576391723000', '1575907200000', '1576080000000', 3, '学院学生辅助工作', '', 'c4', 3);
INSERT INTO `vacate` VALUES (83, '4b7deff0-817c-444f-8607-b465f25bd48d', 'stu02', '2019-2020-1', '1576391901000', '1575302400000', '1575993600000', 9, '其它', '0444', 'c4', 1);
INSERT INTO `vacate` VALUES (84, 'f797bf0e-9e70-491e-ab61-0834b4e12f0d', 'stu01', '2019-2020-1', '1576395154000', '1575907200000', '1576080000000', 3, '事假', '', 'c5,c2', 1);
INSERT INTO `vacate` VALUES (85, '8b25ea85-3bd6-4cf7-8e53-488470d9919f', 'stu01', '2019-2020-1', '1576482114000', '1576425600000', '1576684800000', 4, '其它', '家中有事', 'c5,c2', 3);
INSERT INTO `vacate` VALUES (86, '6a575238-86da-46ff-b5b5-45de2cb81bf7', 'stu01', '2019-2020-1', '1576485334000', '1576425600000', '1576684800000', 4, '学院学生辅助工作', '', 'c5,c8,c2', 1);
INSERT INTO `vacate` VALUES (87, 'a05e4c6f-c9df-4ee7-96e9-20920d17ec0e', 'stu01', '2019-2020-1', '1576491981000', '1576425600000', '1576684800000', 4, '事假', '', 'c5,c2', 1);
INSERT INTO `vacate` VALUES (88, '93d50113-20b7-44c7-a6f5-c1f399bff00b', 'stu01', '2019-2020-2', '1579162309000', '1579104000000', '1579190400000', 2, '事假', '', 'c8', 1);

SET FOREIGN_KEY_CHECKS = 1;
