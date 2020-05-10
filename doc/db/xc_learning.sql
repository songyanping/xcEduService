/*
Navicat MySQL Data Transfer

Source Server         : 10.20.0.204
Source Server Version : 50722
Source Host           : 10.20.0.204:3306
Source Database       : xc_learning

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2020-05-10 17:43:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xc_learning_course
-- ----------------------------
DROP TABLE IF EXISTS `xc_learning_course`;
CREATE TABLE `xc_learning_course` (
  `id` varchar(32) NOT NULL,
  `course_id` varchar(32) NOT NULL COMMENT '课程id',
  `user_id` varchar(32) NOT NULL COMMENT '用户id',
  `charge` varchar(32) DEFAULT NULL COMMENT '收费规则',
  `price` float(8,2) DEFAULT NULL COMMENT '课程价格',
  `valid` varchar(32) DEFAULT NULL COMMENT '有效性',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` varchar(32) DEFAULT NULL COMMENT '选课状态',
  PRIMARY KEY (`id`),
  UNIQUE KEY `xc_learning_list_unique` (`course_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xc_learning_course
-- ----------------------------
INSERT INTO `xc_learning_course` VALUES ('4028a08771fdbbe90171fdc8d6020000', '4028e58161bcf7f40161bcf8b77c0000', '49', null, null, null, null, null, '501001');

-- ----------------------------
-- Table structure for xc_task_his
-- ----------------------------
DROP TABLE IF EXISTS `xc_task_his`;
CREATE TABLE `xc_task_his` (
  `id` varchar(32) NOT NULL COMMENT '任务id',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `delete_time` datetime DEFAULT NULL,
  `task_type` varchar(32) DEFAULT NULL COMMENT '任务类型',
  `mq_exchange` varchar(64) DEFAULT NULL COMMENT '交换机名称',
  `mq_routingkey` varchar(64) DEFAULT NULL COMMENT 'routingkey',
  `request_body` varchar(512) DEFAULT NULL COMMENT '任务请求的内容',
  `version` int(10) DEFAULT '0' COMMENT '乐观锁版本号',
  `status` varchar(32) DEFAULT NULL COMMENT '任务状态',
  `errormsg` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xc_task_his
-- ----------------------------
INSERT INTO `xc_task_his` VALUES ('4028858162959ce5016295b604ba0000', '2018-04-05 20:09:17', '2020-05-10 16:47:54', '2018-04-05 20:09:21', 'add_choosecourse', 'ex_learning_addchoosecourse', 'addchoosecourse', '{\"courseId\":\"4028e58161bcf7f40161bcf8b77c0000\",\"userId\":\"49\"}', null, '10201', null);
