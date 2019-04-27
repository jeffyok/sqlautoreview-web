/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50715
Source Host           : localhost:3306
Source Database       : db_sqlautoreview

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2019-04-27 17:01:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_project
-- ----------------------------
DROP TABLE IF EXISTS `t_project`;
CREATE TABLE `t_project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `project_name` varchar(100) NOT NULL COMMENT '项目名称',
  `project_ch_name` varchar(200) DEFAULT NULL COMMENT '项目中文名',
  `project_desc` varchar(200) DEFAULT NULL COMMENT '项目描述',
  `db_ip` varchar(50) NOT NULL COMMENT '数据库ip',
  `db_port` int(11) NOT NULL COMMENT '数据库端口',
  `db_name` varchar(20) NOT NULL COMMENT '数据库',
  `db_user` varchar(50) NOT NULL COMMENT '数据库用户名',
  `db_password` varchar(20) NOT NULL COMMENT '数据库密码',
  `mapper_root_path` varchar(2000) DEFAULT NULL COMMENT 'mapper文件根路径',
  `review_time` datetime DEFAULT NULL COMMENT 'review时间',
  `review_flag` char(1) DEFAULT NULL COMMENT '是否review',
  `score` decimal(11,2) DEFAULT NULL COMMENT '得分',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目';

-- ----------------------------
-- Table structure for t_review_result
-- ----------------------------
DROP TABLE IF EXISTS `t_review_result`;
CREATE TABLE `t_review_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  `tablename` varchar(100) NOT NULL COMMENT '表名',
  `real_tablename` varchar(100) NOT NULL COMMENT '真实表名',
  `exist_indexes` varchar(4000) DEFAULT NULL COMMENT '已存在索引',
  `new_indexes` varchar(4000) DEFAULT NULL COMMENT '新索引',
  `merge_result` varchar(4000) DEFAULT NULL COMMENT '合并结果',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_t_review_result_project_id` (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='review结果表';

-- ----------------------------
-- Table structure for t_sqlmapper_file
-- ----------------------------
DROP TABLE IF EXISTS `t_sqlmapper_file`;
CREATE TABLE `t_sqlmapper_file` (
  `mapper_file_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文件ID',
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  `mapper_file_path` varchar(500) NOT NULL COMMENT '文件路径,全路径',
  `file_name` varchar(200) NOT NULL COMMENT '文件名',
  `scan_time` datetime DEFAULT NULL COMMENT '扫描时间',
  `operate_user` varchar(50) DEFAULT NULL COMMENT '操作人',
  `update_user` varchar(50) DEFAULT NULL COMMENT '修改人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`mapper_file_id`),
  KEY `idx_t_sqlmapper_file_project_id` (`project_id`),
  KEY `idx_t_sqlmapper_file_file_name` (`file_name`,`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sqlmaper文件配置';

-- ----------------------------
-- Table structure for t_sqlreview
-- ----------------------------
DROP TABLE IF EXISTS `t_sqlreview`;
CREATE TABLE `t_sqlreview` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  `mapper_file_id` int(11) NOT NULL COMMENT '文件ID',
  `java_class_id` varchar(200) NOT NULL COMMENT 'mapper方法名',
  `sql_xml` varchar(8000) DEFAULT NULL COMMENT 'mapper sql',
  `sql_comment` varchar(200) DEFAULT NULL COMMENT 'mapper备注',
  `real_sql` varchar(8000) NOT NULL COMMENT '真实sql',
  `real_sql_hash` varchar(32) DEFAULT NULL COMMENT '真实sql hash',
  `table_name` varchar(100) DEFAULT NULL COMMENT '表名',
  `status` int(11) DEFAULT NULL COMMENT '状态 0 未review 1已review  2review出错',
  `auto_review_err` varchar(200) DEFAULT NULL COMMENT '自动检查错误信息',
  `auto_review_tip` varchar(200) DEFAULT NULL COMMENT '自动检查提示',
  `auto_review_time` datetime DEFAULT NULL COMMENT '自动检查时间',
  `sql_auto_index` varchar(500) DEFAULT NULL COMMENT 'sql自动索引',
  `dba_review_time` datetime DEFAULT NULL COMMENT 'dba检查时间',
  `sql_dba_index` varchar(500) DEFAULT NULL COMMENT 'dba索引',
  `index_flag` char(1) DEFAULT '0' COMMENT '索引标记,1已标记 0未标记',
  `sql_type` varchar(20) DEFAULT NULL COMMENT 'sql类型，insert，select，update，delete',
  `dba_advice` varchar(200) DEFAULT NULL COMMENT 'dba建议',
  `dba_review_status` char(1) DEFAULT '0' COMMENT 'dba 检查状态 0未审核 1通过 2不通过',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  KEY `idx_t_sqlreview_status` (`status`,`project_id`),
  KEY `idx_t_sqlreview_project_id` (`project_id`,`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sqlreview表';
