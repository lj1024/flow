--创建序列
DROP TABLE IF EXISTS sequence; 
CREATE TABLE sequence ( 
     name VARCHAR(50) NOT NULL, 
     current_value INT NOT NULL, 
     increment INT NOT NULL DEFAULT 1, 
     PRIMARY KEY (name) 
) ENGINE=InnoDB; 

DROP FUNCTION IF EXISTS currval; 
DELIMITER $ 
CREATE FUNCTION currval (seq_name VARCHAR(50)) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     DECLARE value INTEGER; 
     SET value = 0; 
     SELECT current_value INTO value 
          FROM sequence
          WHERE name = seq_name; 
     RETURN value; 
END
$ 
DELIMITER ; 

DROP FUNCTION IF EXISTS nextval; 
DELIMITER $ 
CREATE FUNCTION nextval (seq_name VARCHAR(50)) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     UPDATE sequence
          SET current_value = current_value + increment 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END
$ 
DELIMITER ;

DROP FUNCTION IF EXISTS setval; 
DELIMITER $ 
CREATE FUNCTION setval (seq_name VARCHAR(50), value INTEGER) 
     RETURNS INTEGER
     LANGUAGE SQL 
     DETERMINISTIC 
     CONTAINS SQL 
     SQL SECURITY DEFINER 
     COMMENT ''
BEGIN
     UPDATE sequence
          SET current_value = value 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END
$ 
DELIMITER ;

INSERT INTO `flow`.`sequence` (`name`, `current_value`, `increment`) VALUES ('idseq', '24', '1');
INSERT INTO `flow`.`sequence` (`name`, `current_value`, `increment`) VALUES ('sortseq', '100', '1');


-- ----------------------------
-- Table structure for `resources`
-- ----------------------------
DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources` (
  `id` int(11) NOT NULL ,
  `name` varchar(255) default NULL COMMENT '资源名称',
  `resUrl` varchar(255) default NULL COMMENT '资源url',
  `type` int(11) default NULL COMMENT '资源类型   1:菜单    2：按钮',
  `parentId` int(11) default NULL COMMENT '父资源',
  `sort` int(11) default NULL COMMENT '排序',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources
-- ----------------------------
INSERT INTO `resources` VALUES ('1', '系统设置', '/system', '0', '0', '1');
INSERT INTO `resources` VALUES ('2', '用户管理', '/usersPage', '1', '1', '2');
INSERT INTO `resources` VALUES ('3', '角色管理', '/rolesPage', '1', '1', '3');
INSERT INTO `resources` VALUES ('4', '资源管理', '/resourcesPage', '1', '1', '4');
INSERT INTO `resources` VALUES ('5', '添加用户', '/users/add', '2', '2', '5');
INSERT INTO `resources` VALUES ('6', '删除用户', '/users/delete', '2', '2', '6');
INSERT INTO `resources` VALUES ('7', '添加角色', '/roles/add', '2', '3', '7');
INSERT INTO `resources` VALUES ('8', '删除角色', '/roles/delete', '2', '3', '8');
INSERT INTO `resources` VALUES ('9', '添加资源', '/resources/add', '2', '4', '9');
INSERT INTO `resources` VALUES ('10', '删除资源', '/resources/delete', '2', '4', '10');
INSERT INTO `resources` VALUES ('11', '分配角色', '/users/saveUserRoles', '2', '2', '11');
INSERT INTO `resources` VALUES ('13', '分配权限', '/roles/saveRoleResources', '2', '3', '12');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL ,
  `roleDesc` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员');
INSERT INTO `role` VALUES ('2', '普通用户');
INSERT INTO `role` VALUES ('3', '超级管理员');

-- ----------------------------
-- Table structure for `role_resources`
-- ----------------------------
DROP TABLE IF EXISTS `role_resources`;
CREATE TABLE `role_resources` (
  `roleId` int(11) NOT NULL,
  `resourcesId` int(11) NOT NULL,
  PRIMARY KEY  (`roleId`,`resourcesId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_resources
-- ----------------------------
INSERT INTO `role_resources` VALUES ('1', '2');
INSERT INTO `role_resources` VALUES ('1', '3');
INSERT INTO `role_resources` VALUES ('1', '4');
INSERT INTO `role_resources` VALUES ('1', '5');
INSERT INTO `role_resources` VALUES ('1', '6');
INSERT INTO `role_resources` VALUES ('1', '7');
INSERT INTO `role_resources` VALUES ('1', '8');
INSERT INTO `role_resources` VALUES ('1', '9');
INSERT INTO `role_resources` VALUES ('1', '10');
INSERT INTO `role_resources` VALUES ('1', '11');
INSERT INTO `role_resources` VALUES ('1', '13');
INSERT INTO `role_resources` VALUES ('2', '2');
INSERT INTO `role_resources` VALUES ('2', '3');
INSERT INTO `role_resources` VALUES ('2', '4');
INSERT INTO `role_resources` VALUES ('2', '9');
INSERT INTO `role_resources` VALUES ('3', '2');
INSERT INTO `role_resources` VALUES ('3', '3');
INSERT INTO `role_resources` VALUES ('3', '4');
INSERT INTO `role_resources` VALUES ('3', '5');
INSERT INTO `role_resources` VALUES ('3', '7');
INSERT INTO `role_resources` VALUES ('3', '8');
INSERT INTO `role_resources` VALUES ('3', '9');
INSERT INTO `role_resources` VALUES ('3', '10');
INSERT INTO `role_resources` VALUES ('9', '9');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(20) DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `real_name` varchar(128) DEFAULT NULL COMMENT '真实姓名',
  `phone` int(11) DEFAULT NULL COMMENT '电话',
  `sex` varchar(12) DEFAULT NULL COMMENT '性别',
  `photo` varchar(256) DEFAULT NULL COMMENT '照片',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `is_leave` varchar(5) DEFAULT NULL COMMENT '是否离职',
  `status` int(1) DEFAULT '1' COMMENT '1:有效，0:禁止登录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` (`id`, `username`, `email`, `password`, `real_name`, `phone`, `sex`, `photo`, `create_time`, `last_login_time`, `is_leave`, `status`) VALUES ('1', 'admin', '', '3ef7164d1f6167cb9f2658c07d3c2f0a', NULL, NULL, 'Male', NULL, '2018-01-30 15:27:09', '2018-01-30 15:27:16', 'No', '1');
INSERT INTO `user` (`id`, `username`, `email`, `password`, `real_name`, `phone`, `sex`, `photo`, `create_time`, `last_login_time`, `is_leave`, `status`) VALUES ('2', 'user1', '', '90e66e36e3135a91d298177d4389851e', '李四', NULL, 'Female', NULL, '2018-01-30 15:27:13', '2018-01-30 15:27:18', 'No', '1');
INSERT INTO `user` (`id`, `username`, `email`, `password`, `real_name`, `phone`, `sex`, `photo`, `create_time`, `last_login_time`, `is_leave`, `status`) VALUES ('21', 'luwja1', '123@qq.com', '4898a0d62716cb3fee56b805fd79a3b7', '李四', '123', 'Female', NULL, '2018-02-01 10:04:18', NULL, 'No', '1');
INSERT INTO `user` (`id`, `username`, `email`, `password`, `real_name`, `phone`, `sex`, `photo`, `create_time`, `last_login_time`, `is_leave`, `status`) VALUES ('23', 'lisi', '', 'ea458c73cbf60c8b38e04b63b65925ec', '李四', '1', 'Male', NULL, '2018-02-01 15:28:21', NULL, 'No', '1');



-- ----------------------------
-- Table structure for `user_role`
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `userId` int(11) default NULL,
  `roleId` int(11) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('23', '2');
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('2', '2');

-- ----------------------------
-- Table structure for `员工表`
-- ----------------------------
CREATE TABLE `employee` (
  `id` int(11) NOT NULL COMMENT 'id',
  `em_name` varchar(100) NOT NULL COMMENT '姓名',
  `sex` varchar(10) DEFAULT NULL COMMENT '性别',
  `identity_card_no` varchar(50) DEFAULT NULL COMMENT '身份证号',
  `phone` int(11) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `address` varchar(500) DEFAULT NULL COMMENT '地址',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `social_security` varchar(10) DEFAULT NULL COMMENT '社保情况',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `is_internal` varchar(10) DEFAULT NULL COMMENT '是否为内部员工',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `is_valid` varchar(20) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `证书表`
-- ----------------------------
CREATE TABLE `certificate` (
  `id` int(11) NOT NULL COMMENT 'id',
  `certificate_no` varchar(50) NOT NULL COMMENT '证书编号',
  `license_date` datetime DEFAULT NULL COMMENT '发证日期',
  `expire_date` datetime DEFAULT NULL COMMENT '有效期',
  `review_date` datetime DEFAULT NULL COMMENT '有效期',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `trade_id` int(11) DEFAULT NULL COMMENT '行业id',
  `trade_name` varchar(100) DEFAULT NULL COMMENT '行业名字',
  `domain_id` int(11) DEFAULT NULL COMMENT '专业',
  `domain_name` varchar(100) DEFAULT NULL COMMENT '行子名字',
  `level_id` int(11) DEFAULT NULL COMMENT '等级id',
  `level_name` varchar(100) DEFAULT NULL COMMENT '等级名称',
  `employee_id` int(11) NOT NULL COMMENT '员工id',
  `is_valid` varchar(10) NOT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`),
  KEY `certificate_no_index` (`certificate_no`) USING HASH,
  KEY `expire_date_index` (`expire_date`) USING BTREE,
  KEY `review_date_index` (`review_date`) USING BTREE,
  KEY `license_date_index` (`license_date`) USING BTREE,
  KEY `domain_id_level_id_employee_id_index` (`domain_id`,`level_id`,`employee_id`) USING BTREE,
  KEY `trade_id_index` (`trade_id`) USING BTREE,
  KEY `domain_id_index` (`domain_id`) USING BTREE,
  KEY `level_id_index` (`level_id`) USING BTREE,
  KEY `trade_id_domain_id_index` (`trade_id`,`domain_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `证书字典表`
-- ----------------------------
CREATE TABLE `cert_dictionary` (
  `id` int(11) NOT NULL,
  `dic_name` varchar(255) DEFAULT NULL COMMENT '名称',
  `type` varchar(100) DEFAULT NULL COMMENT '资源类型   trade:行业   domain: 专业 level：级别',
  `parent_id` int(11) DEFAULT NULL COMMENT '父id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `is_valid` varchar(10) DEFAULT NULL COMMENT '是否有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('100', '证书', 'Cert', '0', '110', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('101', '安考证', 'Trade', '100', '111', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('102', '市政类建安考', 'Domain', '101', '112', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('103', 'A证', 'Level', '102', '113', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('104', 'B证', 'Level', '102', '114', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('105', 'C证', 'Level', '102', '115', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('110', 'D', 'Level', '102', '118', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('111', '工程师', 'Domain', '101', '119', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('113', 'E1', 'Level', '102', '120', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('114', 'E', 'Level', '102', '121', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('115', 'F', 'Level', '102', '122', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('116', 'G', 'Level', '102', '123', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('117', 'H', 'Level', '102', '124', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('118', '助理', 'Level', '111', '125', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('119', 'test', 'Trade', '100', '126', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('120', 'G', 'Level', '102', '127', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('121', 'G', 'Level', '102', '128', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('122', 'D证', 'Level', '102', '129', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('123', 'D证', 'Level', '102', '130', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('124', '中级', 'Level', '111', '131', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('125', '高级1', 'Level', '111', '132', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('126', 'G', 'Level', '102', '133', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('127', 'H', 'Level', '102', '134', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('128', 'H1', 'Level', '102', '135', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('129', '高级2', 'Level', '111', '136', 'No');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('130', 'test2', 'Trade', '100', '137', 'Yes');
INSERT INTO `cert_dictionary` (`id`, `dic_name`, `type`, `parent_id`, `sort`, `is_valid`) VALUES ('131', 'H1', 'Level', '102', '138', 'Yes');


