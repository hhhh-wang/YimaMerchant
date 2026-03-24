-- 酒店业务模块建表脚本
-- 说明：适用于若依前后端分离项目酒店业务场景
-- 字符集：utf8mb4
-- 引擎：InnoDB

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 1. 待签约酒店申请表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_pending_apply`;
CREATE TABLE `hotel_pending_apply`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `apply_no` varchar(64) NOT NULL COMMENT '申请单号',
  `hotel_name` varchar(128) NOT NULL COMMENT '酒店名称',
  `contact_name` varchar(64) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `province_code` varchar(32) DEFAULT NULL COMMENT '省编码',
  `province_name` varchar(64) DEFAULT NULL COMMENT '省名称',
  `city_code` varchar(32) DEFAULT NULL COMMENT '市编码',
  `city_name` varchar(64) DEFAULT NULL COMMENT '市名称',
  `district_code` varchar(32) DEFAULT NULL COMMENT '区编码',
  `district_name` varchar(64) DEFAULT NULL COMMENT '区名称',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `longitude` decimal(12, 6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(12, 6) DEFAULT NULL COMMENT '纬度',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '酒店封面图',
  `banner_images` text COMMENT '酒店轮播图，多个逗号分隔',
  `checkin_time` varchar(16) DEFAULT NULL COMMENT '入住时间',
  `checkout_time` varchar(16) DEFAULT NULL COMMENT '离店时间',
  `hotel_desc` text COMMENT '酒店简介',
  `booking_notice` text COMMENT '预订须知',
  `cancel_policy` text COMMENT '取消规则说明',
  `invoice_notice` text COMMENT '开票说明',
  `parking_notice` text COMMENT '停车说明',
  `business_status` varchar(32) NOT NULL DEFAULT 'OPEN' COMMENT '酒店经营状态：OPEN/SUSPENDED/PREPARING',
  `business_license_files` varchar(500) DEFAULT NULL COMMENT '营业执照附件ID集合',
  `special_license_files` varchar(500) DEFAULT NULL COMMENT '特种行业许可证附件ID集合',
  `health_license_files` varchar(500) DEFAULT NULL COMMENT '卫生许可证附件ID集合',
  `apply_remark` varchar(500) DEFAULT NULL COMMENT '申请备注',
  `apply_status` varchar(32) NOT NULL DEFAULT 'PENDING' COMMENT '申请状态：PENDING/APPROVED/REJECTED',
  `applicant_id` bigint DEFAULT NULL COMMENT '申请人ID',
  `applicant_name` varchar(64) DEFAULT NULL COMMENT '申请人姓名',
  `apply_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `audit_user_id` bigint DEFAULT NULL COMMENT '审核人ID',
  `audit_user_name` varchar(64) DEFAULT NULL COMMENT '审核人姓名',
  `reject_reason` varchar(500) DEFAULT NULL COMMENT '驳回原因',
  `partner_hotel_id` bigint DEFAULT NULL COMMENT '转合作酒店ID',
  `last_operate_time` datetime DEFAULT NULL COMMENT '最后操作时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0存在 2删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_apply_no` (`apply_no`),
  KEY `idx_hotel_name` (`hotel_name`),
  KEY `idx_apply_status` (`apply_status`),
  KEY `idx_apply_time` (`apply_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='待签约酒店申请表';

-- ----------------------------
-- 2. 酒店申请审核日志表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_pending_audit_log`;
CREATE TABLE `hotel_pending_audit_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `apply_id` bigint NOT NULL COMMENT '申请ID',
  `operate_type` varchar(32) NOT NULL COMMENT '操作类型：SUBMIT/APPROVE/REJECT/UPDATE/DELETE',
  `operate_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operate_user_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `operate_remark` varchar(500) DEFAULT NULL COMMENT '操作备注',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_apply_id` (`apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店申请审核日志表';

-- ----------------------------
-- 3. 合作酒店主表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_partner`;
CREATE TABLE `hotel_partner`  (
  `hotel_id` bigint NOT NULL AUTO_INCREMENT COMMENT '酒店ID',
  `hotel_code` varchar(64) NOT NULL COMMENT '酒店编码',
  `hotel_name` varchar(128) NOT NULL COMMENT '酒店名称',
  `hotel_name_en` varchar(128) DEFAULT NULL COMMENT '酒店英文名',
  `brand_name` varchar(64) DEFAULT NULL COMMENT '品牌',
  `star_level` varchar(32) DEFAULT NULL COMMENT '星级',
  `contact_name` varchar(64) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(32) DEFAULT NULL COMMENT '联系电话',
  `contact_email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `province_code` varchar(32) DEFAULT NULL COMMENT '省编码',
  `province_name` varchar(64) DEFAULT NULL COMMENT '省名称',
  `city_code` varchar(32) DEFAULT NULL COMMENT '市编码',
  `city_name` varchar(64) DEFAULT NULL COMMENT '市名称',
  `district_code` varchar(32) DEFAULT NULL COMMENT '区编码',
  `district_name` varchar(64) DEFAULT NULL COMMENT '区名称',
  `address` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `longitude` decimal(12, 6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(12, 6) DEFAULT NULL COMMENT '纬度',
  `cooperate_status` varchar(32) NOT NULL DEFAULT 'NORMAL' COMMENT '合作状态：NORMAL/PAUSED/TERMINATED',
  `source_apply_id` bigint DEFAULT NULL COMMENT '来源申请ID',
  `sign_date` datetime DEFAULT NULL COMMENT '签约时间',
  `contract_start_date` date DEFAULT NULL COMMENT '合同开始日期',
  `contract_end_date` date DEFAULT NULL COMMENT '合同结束日期',
  `commission_mode` varchar(32) NOT NULL DEFAULT 'BASE_PRICE' COMMENT '佣金模式：BASE_PRICE/SELL_PRICE',
  `commission_rate` decimal(8, 4) DEFAULT NULL COMMENT '佣金比例',
  `markup_rate` decimal(8, 4) DEFAULT NULL COMMENT '加价率',
  `bd_user_id` bigint DEFAULT NULL COMMENT '当前归属BD',
  `bd_user_name` varchar(64) DEFAULT NULL COMMENT '当前归属BD姓名',
  `account_status` varchar(32) NOT NULL DEFAULT 'ENABLED' COMMENT '酒店账号状态：ENABLED/DISABLED',
  `status_reason` varchar(500) DEFAULT NULL COMMENT '状态变更原因',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`hotel_id`),
  UNIQUE KEY `uk_hotel_code` (`hotel_code`),
  KEY `idx_hotel_name` (`hotel_name`),
  KEY `idx_cooperate_status` (`cooperate_status`),
  KEY `idx_bd_user_id` (`bd_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='合作酒店主表';

-- ----------------------------
-- 4. 酒店合同表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_contract`;
CREATE TABLE `hotel_contract`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `contract_no` varchar(64) NOT NULL COMMENT '合同编号',
  `contract_name` varchar(128) DEFAULT NULL COMMENT '合同名称',
  `contract_file_ids` varchar(500) DEFAULT NULL COMMENT '合同附件',
  `contract_start_date` date DEFAULT NULL COMMENT '合同开始日期',
  `contract_end_date` date DEFAULT NULL COMMENT '合同结束日期',
  `commission_mode` varchar(32) NOT NULL COMMENT '佣金模式',
  `commission_rate` decimal(8, 4) DEFAULT NULL COMMENT '佣金比例',
  `markup_rate` decimal(8, 4) DEFAULT NULL COMMENT '加价率',
  `contract_status` varchar(32) NOT NULL DEFAULT 'EFFECTIVE' COMMENT '合同状态：EFFECTIVE/EXPIRED/TERMINATED',
  `sign_user_id` bigint DEFAULT NULL COMMENT '签约操作人ID',
  `sign_user_name` varchar(64) DEFAULT NULL COMMENT '签约操作人姓名',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_contract_no` (`contract_no`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店合同表';

-- ----------------------------
-- 5. 酒店账号表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_account`;
CREATE TABLE `hotel_account`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `sys_user_id` bigint DEFAULT NULL COMMENT '关联系统用户ID',
  `account_type` varchar(32) NOT NULL DEFAULT 'MAIN' COMMENT '账号类型：MAIN/SUB',
  `account_name` varchar(64) NOT NULL COMMENT '账号名称',
  `nick_name` varchar(64) DEFAULT NULL COMMENT '昵称',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `role_names` varchar(255) DEFAULT NULL COMMENT '角色名集合',
  `account_status` varchar(32) NOT NULL DEFAULT 'ENABLED' COMMENT '账号状态',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_sys_user_id` (`sys_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店账号表';

-- ----------------------------
-- 6. BD酒店绑定表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_bd_bind`;
CREATE TABLE `hotel_bd_bind`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `bd_user_id` bigint NOT NULL COMMENT 'BD用户ID',
  `bd_user_name` varchar(64) DEFAULT NULL COMMENT 'BD姓名',
  `bind_status` varchar(32) NOT NULL DEFAULT 'BOUND' COMMENT '绑定状态：BOUND/UNBOUND',
  `bind_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '绑定时间',
  `unbind_time` datetime DEFAULT NULL COMMENT '解绑时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_bd_user_id` (`bd_user_id`),
  KEY `idx_bind_status` (`bind_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BD酒店绑定表';

-- ----------------------------
-- 7. BD酒店绑定历史表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_bd_bind_history`;
CREATE TABLE `hotel_bd_bind_history`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `old_bd_user_id` bigint DEFAULT NULL COMMENT '原BD用户ID',
  `old_bd_user_name` varchar(64) DEFAULT NULL COMMENT '原BD姓名',
  `new_bd_user_id` bigint DEFAULT NULL COMMENT '新BD用户ID',
  `new_bd_user_name` varchar(64) DEFAULT NULL COMMENT '新BD姓名',
  `operate_type` varchar(32) NOT NULL COMMENT '操作类型：BIND/UNBIND/TRANSFER',
  `operate_reason` varchar(500) DEFAULT NULL COMMENT '操作原因',
  `operate_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operate_user_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_old_bd_user_id` (`old_bd_user_id`),
  KEY `idx_new_bd_user_id` (`new_bd_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BD酒店绑定历史表';

-- ----------------------------
-- 8. 酒店资料表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_profile`;
CREATE TABLE `hotel_profile`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `hotel_logo` varchar(255) DEFAULT NULL COMMENT '酒店LOGO',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图',
  `hotel_desc` text COMMENT '酒店描述',
  `booking_notice` text COMMENT '预订须知',
  `invoice_notice` text COMMENT '开票说明',
  `parking_notice` text COMMENT '停车说明',
  `business_status` varchar(32) NOT NULL DEFAULT 'OPEN' COMMENT '酒店经营状态：OPEN/SUSPENDED/PREPARING',
  `service_tags` varchar(500) DEFAULT NULL COMMENT '设施服务标签',
  `traffic_info` varchar(500) DEFAULT NULL COMMENT '交通指引',
  `customer_service_time` varchar(64) DEFAULT NULL COMMENT '客服时间',
  `checkin_time` varchar(16) DEFAULT NULL COMMENT '入住时间',
  `checkout_time` varchar(16) DEFAULT NULL COMMENT '离店时间',
  `child_policy` varchar(500) DEFAULT NULL COMMENT '儿童政策',
  `pet_policy` varchar(500) DEFAULT NULL COMMENT '宠物政策',
  `cancel_policy` varchar(500) DEFAULT NULL COMMENT '取消政策',
  `sale_status` varchar(32) NOT NULL DEFAULT 'OFF_SHELF' COMMENT '上下架状态：ON_SHELF/OFF_SHELF',
  `sync_source` varchar(32) DEFAULT NULL COMMENT '同步来源',
  `sync_time` datetime DEFAULT NULL COMMENT '最近同步时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店资料表';

-- ----------------------------
-- 9. 酒店图片表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_profile_image`;
CREATE TABLE `hotel_profile_image`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `image_type` varchar(32) NOT NULL COMMENT '图片类型：BANNER/COVER/APPEARANCE/ROOM/FACILITY/OTHER',
  `image_url` varchar(255) NOT NULL COMMENT '图片地址',
  `image_name` varchar(128) DEFAULT NULL COMMENT '图片名称',
  `sort_num` int NOT NULL DEFAULT 0 COMMENT '排序号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店图片表';

-- ----------------------------
-- 10. 房型表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_room_type`;
CREATE TABLE `hotel_room_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `room_type_code` varchar(64) NOT NULL COMMENT '房型编码',
  `room_type_name` varchar(128) NOT NULL COMMENT '房型名称',
  `room_type_name_en` varchar(128) DEFAULT NULL COMMENT '房型英文名',
  `adult_count` int NOT NULL DEFAULT 1 COMMENT '可住成人数',
  `child_count` int NOT NULL DEFAULT 0 COMMENT '可住儿童数',
  `bed_type` varchar(64) DEFAULT NULL COMMENT '床型',
  `bed_desc` varchar(255) DEFAULT NULL COMMENT '床型描述',
  `area_size` decimal(8, 2) DEFAULT NULL COMMENT '面积(㎡)',
  `window_status` varchar(32) DEFAULT NULL COMMENT '窗户情况',
  `bathroom_type` varchar(32) DEFAULT NULL COMMENT '卫浴类型',
  `is_smoking` char(1) NOT NULL DEFAULT '0' COMMENT '是否可吸烟（0否 1是）',
  `is_breakfast` char(1) NOT NULL DEFAULT '0' COMMENT '是否含早（0否 1是）',
  `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图',
  `room_desc` text COMMENT '房型描述',
  `default_refund_rule` varchar(32) DEFAULT NULL COMMENT '默认退款规则',
  `min_booking_rooms` int DEFAULT NULL COMMENT '最少预订间数',
  `max_booking_rooms` int DEFAULT NULL COMMENT '最多预订间数',
  `min_booking_nights` int DEFAULT NULL COMMENT '最少预订晚数',
  `max_booking_nights` int DEFAULT NULL COMMENT '最多预订晚数',
  `sale_status` varchar(32) NOT NULL DEFAULT 'ON_SALE' COMMENT '销售状态：ON_SALE/OFF_SALE',
  `sort_num` int NOT NULL DEFAULT 0 COMMENT '排序号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_room_type_code` (`room_type_code`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_sale_status` (`sale_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房型表';

-- ----------------------------
-- 11. 房型图片表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_room_type_image`;
CREATE TABLE `hotel_room_type_image`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `room_type_id` bigint NOT NULL COMMENT '房型ID',
  `image_url` varchar(255) NOT NULL COMMENT '图片地址',
  `image_name` varchar(128) DEFAULT NULL COMMENT '图片名称',
  `sort_num` int NOT NULL DEFAULT 0 COMMENT '排序号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_room_type_id` (`room_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房型图片表';

-- ----------------------------
-- 12. 房价库存日历表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_room_calendar`;
CREATE TABLE `hotel_room_calendar`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `room_type_id` bigint NOT NULL COMMENT '房型ID',
  `biz_date` date NOT NULL COMMENT '业务日期',
  `week_no` tinyint DEFAULT NULL COMMENT '星期',
  `settlement_price` decimal(12, 2) DEFAULT NULL COMMENT '结算底价',
  `sale_price` decimal(12, 2) DEFAULT NULL COMMENT '平台卖价/酒店卖价',
  `commission_rate` decimal(8, 4) DEFAULT NULL COMMENT '佣金比例',
  `commission_amount` decimal(12, 2) DEFAULT NULL COMMENT '佣金金额',
  `inventory` int NOT NULL DEFAULT 0 COMMENT '库存',
  `room_status` varchar(32) NOT NULL DEFAULT 'OPEN' COMMENT '房态：OPEN/CLOSED/TIGHT',
  `refund_rule` varchar(32) DEFAULT NULL COMMENT '退款规则',
  `special_tag` varchar(32) DEFAULT NULL COMMENT '特殊日期标记',
  `is_default_data` char(1) NOT NULL DEFAULT '0' COMMENT '是否默认数据（0否 1是）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_room_date` (`room_type_id`, `biz_date`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_biz_date` (`biz_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房价库存日历表';

-- ----------------------------
-- 13. 房价库存修改日志表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_room_calendar_log`;
CREATE TABLE `hotel_room_calendar_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `calendar_id` bigint DEFAULT NULL COMMENT '日历ID',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `room_type_id` bigint NOT NULL COMMENT '房型ID',
  `biz_date` date NOT NULL COMMENT '业务日期',
  `operate_type` varchar(32) NOT NULL COMMENT '操作类型：DAILY/BATCH/CLOSE/COPY/RESTORE',
  `before_json` text COMMENT '变更前JSON',
  `after_json` text COMMENT '变更后JSON',
  `operate_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operate_user_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_room_type_id` (`room_type_id`),
  KEY `idx_biz_date` (`biz_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房价库存修改日志表';

-- ----------------------------
-- 14. 酒店订单主表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_order`;
CREATE TABLE `hotel_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `hotel_name` varchar(128) NOT NULL COMMENT '酒店名称',
  `room_type_id` bigint NOT NULL COMMENT '房型ID',
  `room_type_name` varchar(128) DEFAULT NULL COMMENT '房型名称',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(64) DEFAULT NULL COMMENT '用户名',
  `user_mobile` varchar(32) DEFAULT NULL COMMENT '用户手机号',
  `bd_user_id` bigint DEFAULT NULL COMMENT '所属BD',
  `checkin_date` date NOT NULL COMMENT '入住日期',
  `checkout_date` date NOT NULL COMMENT '离店日期',
  `night_count` int NOT NULL DEFAULT 1 COMMENT '入住晚数',
  `room_count` int NOT NULL DEFAULT 1 COMMENT '预订间数',
  `order_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '订单金额',
  `tax_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '税费',
  `discount_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '优惠金额',
  `pay_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '实付金额',
  `refund_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '已退款金额',
  `order_status` varchar(32) NOT NULL DEFAULT 'PENDING_CONFIRM' COMMENT '订单状态',
  `refund_status` varchar(32) DEFAULT NULL COMMENT '退款状态',
  `refund_reason` varchar(500) DEFAULT NULL COMMENT '退款原因',
  `special_request` varchar(500) DEFAULT NULL COMMENT '特殊要求',
  `inner_remark` varchar(500) DEFAULT NULL COMMENT '内部备注',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `checkin_time` datetime DEFAULT NULL COMMENT '入住时间',
  `checkout_time` datetime DEFAULT NULL COMMENT '离店时间',
  `cancel_time` datetime DEFAULT NULL COMMENT '取消时间',
  `cancel_reason` varchar(500) DEFAULT NULL COMMENT '取消原因',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_room_type_id` (`room_type_id`),
  KEY `idx_order_status` (`order_status`),
  KEY `idx_checkin_date` (`checkin_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店订单主表';

-- ----------------------------
-- 15. 酒店订单入住人表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_order_guest`;
CREATE TABLE `hotel_order_guest`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `guest_name` varchar(64) NOT NULL COMMENT '入住人姓名',
  `guest_mobile` varchar(32) DEFAULT NULL COMMENT '入住人手机号',
  `certificate_type` varchar(32) DEFAULT NULL COMMENT '证件类型',
  `certificate_no` varchar(64) DEFAULT NULL COMMENT '证件号',
  `is_main_guest` char(1) NOT NULL DEFAULT '0' COMMENT '是否主入住人（0否 1是）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店订单入住人表';

-- ----------------------------
-- 16. 酒店订单日志表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_order_log`;
CREATE TABLE `hotel_order_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `operate_type` varchar(32) NOT NULL COMMENT '操作类型：CREATE/CONFIRM/CANCEL/CHECKIN/CHECKOUT/REFUND/REMARK/DISPUTE',
  `before_status` varchar(32) DEFAULT NULL COMMENT '变更前状态',
  `after_status` varchar(32) DEFAULT NULL COMMENT '变更后状态',
  `operate_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operate_user_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `operate_remark` varchar(500) DEFAULT NULL COMMENT '操作备注',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店订单日志表';

-- ----------------------------
-- 17. 酒店账单主表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_bill`;
CREATE TABLE `hotel_bill`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_no` varchar(64) NOT NULL COMMENT '账单号',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `hotel_name` varchar(128) NOT NULL COMMENT '酒店名称',
  `statement_start_date` date NOT NULL COMMENT '结算开始日期',
  `statement_end_date` date NOT NULL COMMENT '结算结束日期',
  `order_count` int NOT NULL DEFAULT 0 COMMENT '订单数量',
  `room_night_count` int NOT NULL DEFAULT 0 COMMENT '间夜数',
  `total_room_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '总房费',
  `total_commission_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '总佣金',
  `total_settlement_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '应结算金额',
  `bill_status` varchar(32) NOT NULL DEFAULT 'PENDING_CONFIRM' COMMENT '账单状态',
  `dispute_status` varchar(32) DEFAULT NULL COMMENT '异议状态',
  `confirm_time` datetime DEFAULT NULL COMMENT '确认时间',
  `confirm_user_id` bigint DEFAULT NULL COMMENT '确认人ID',
  `confirm_user_name` varchar(64) DEFAULT NULL COMMENT '确认人姓名',
  `payment_status` varchar(32) NOT NULL DEFAULT 'UNPAID' COMMENT '付款状态',
  `generate_mode` varchar(32) DEFAULT NULL COMMENT '生成方式：AUTO/MANUAL',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bill_no` (`bill_no`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_bill_status` (`bill_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店账单主表';

-- ----------------------------
-- 18. 酒店账单明细表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_bill_order`;
CREATE TABLE `hotel_bill_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `bill_no` varchar(64) NOT NULL COMMENT '账单号',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `order_no` varchar(64) NOT NULL COMMENT '订单号',
  `hotel_id` bigint NOT NULL COMMENT '酒店ID',
  `room_type_id` bigint DEFAULT NULL COMMENT '房型ID',
  `room_type_name` varchar(128) DEFAULT NULL COMMENT '房型名称',
  `checkin_date` date DEFAULT NULL COMMENT '入住日期',
  `checkout_date` date DEFAULT NULL COMMENT '离店日期',
  `night_count` int DEFAULT NULL COMMENT '入住晚数',
  `order_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '订单金额',
  `commission_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '佣金金额',
  `settlement_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '结算金额',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_order_no` (`order_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店账单明细表';

-- ----------------------------
-- 19. 酒店账单对账日志表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_bill_check_log`;
CREATE TABLE `hotel_bill_check_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `bill_no` varchar(64) NOT NULL COMMENT '账单号',
  `operate_type` varchar(32) NOT NULL COMMENT '操作类型：GENERATE/CONFIRM/DISPUTE/RECALCULATE',
  `operate_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operate_user_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `operate_remark` varchar(500) DEFAULT NULL COMMENT '操作备注',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_bill_no` (`bill_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店账单对账日志表';

-- ----------------------------
-- 20. 酒店付款记录表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_bill_payment`;
CREATE TABLE `hotel_bill_payment`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `bill_id` bigint NOT NULL COMMENT '账单ID',
  `bill_no` varchar(64) NOT NULL COMMENT '账单号',
  `payment_no` varchar(64) NOT NULL COMMENT '付款单号',
  `payment_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '付款金额',
  `payment_status` varchar(32) NOT NULL DEFAULT 'APPLYING' COMMENT '付款状态：APPLYING/APPROVED/PAID/FAILED',
  `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
  `voucher_file_ids` varchar(500) DEFAULT NULL COMMENT '付款凭证附件',
  `invoice_title` varchar(128) DEFAULT NULL COMMENT '发票抬头',
  `invoice_tax_no` varchar(64) DEFAULT NULL COMMENT '税号',
  `bank_account_name` varchar(128) DEFAULT NULL COMMENT '收款账户名',
  `bank_name` varchar(128) DEFAULT NULL COMMENT '开户行',
  `bank_account_no` varchar(64) DEFAULT NULL COMMENT '收款账号',
  `apply_user_id` bigint DEFAULT NULL COMMENT '申请人ID',
  `apply_user_name` varchar(64) DEFAULT NULL COMMENT '申请人姓名',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_payment_no` (`payment_no`),
  KEY `idx_bill_id` (`bill_id`),
  KEY `idx_bill_no` (`bill_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店付款记录表';

-- ----------------------------
-- 21. 酒店业务配置表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_business_config`;
CREATE TABLE `hotel_business_config`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_key` varchar(64) NOT NULL COMMENT '配置键',
  `config_name` varchar(128) NOT NULL COMMENT '配置名称',
  `config_group` varchar(64) NOT NULL COMMENT '配置分组',
  `config_value` text COMMENT '配置值',
  `config_type` varchar(32) NOT NULL DEFAULT 'TEXT' COMMENT '配置类型：TEXT/JSON/NUMBER/BOOLEAN',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店业务配置表';

-- ----------------------------
-- 22. 酒店业务配置日志表
-- ----------------------------
DROP TABLE IF EXISTS `hotel_business_config_log`;
CREATE TABLE `hotel_business_config_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `config_id` bigint NOT NULL COMMENT '配置ID',
  `config_key` varchar(64) NOT NULL COMMENT '配置键',
  `before_value` text COMMENT '变更前值',
  `after_value` text COMMENT '变更后值',
  `operate_user_id` bigint DEFAULT NULL COMMENT '操作人ID',
  `operate_user_name` varchar(64) DEFAULT NULL COMMENT '操作人姓名',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新人',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `idx_config_id` (`config_id`),
  KEY `idx_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店业务配置日志表';

-- ----------------------------
-- 初始化配置数据
-- ----------------------------
INSERT INTO `hotel_business_config` (`config_key`, `config_name`, `config_group`, `config_value`, `config_type`, `status`, `remark`, `create_by`, `create_time`, `update_by`, `update_time`, `del_flag`) VALUES
('hotel.defaultCommissionMode', '默认佣金模式', 'HOTEL', 'BASE_PRICE', 'TEXT', '0', '默认佣金模式', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.defaultCommissionRate', '默认佣金比例', 'HOTEL', '0.1200', 'NUMBER', '0', '默认佣金比例', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.defaultRefundRule', '默认退款规则', 'HOTEL', 'TIME_LIMIT', 'TEXT', '0', '默认退款规则', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.bookingMinDays', '最少预订天数', 'HOTEL', '1', 'NUMBER', '0', '最少预订天数', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.bookingMaxDays', '最大预订天数', 'HOTEL', '30', 'NUMBER', '0', '最大预订天数', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.orderAutoConfirmMinutes', '订单自动确认分钟数', 'ORDER', '30', 'NUMBER', '0', '订单自动确认分钟数', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.orderAutoCancelMinutes', '订单自动取消分钟数', 'ORDER', '15', 'NUMBER', '0', '订单自动取消分钟数', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.orderAutoFinishDays', '订单自动完成天数', 'ORDER', '3', 'NUMBER', '0', '订单自动完成天数', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.billCycleType', '账单结算周期', 'FINANCE', 'MONTHLY', 'TEXT', '0', '账单结算周期', 'admin', NOW(), 'admin', NOW(), '0'),
('hotel.billGenerateDay', '账单生成日', 'FINANCE', '1', 'NUMBER', '0', '账单生成日', 'admin', NOW(), 'admin', NOW(), '0');

-- ----------------------------
-- 若依菜单初始化（酒店业务）
-- 说明：菜单ID从 2000 开始，避免与基础菜单冲突
-- 一级目录：酒店管理
-- 二级菜单：合作中心、运营中心、交易服务、财务结算、业务配置
-- 三级按钮：按若依标准权限点初始化
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('2000', '酒店管理', '0', '5', 'hotel', '', '', '', 1, 0, 'M', '0', '0', '', 'hotel', 'admin', sysdate(), '', NULL, '酒店管理目录');

INSERT INTO `sys_menu` VALUES ('2001', '酒店合作中心', '2000', '1', 'cooperate', '', '', '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', sysdate(), '', NULL, '酒店合作中心目录');
INSERT INTO `sys_menu` VALUES ('2002', '酒店运营中心', '2000', '2', 'operate', '', '', '', 1, 0, 'M', '0', '0', '', 'build', 'admin', sysdate(), '', NULL, '酒店运营中心目录');
INSERT INTO `sys_menu` VALUES ('2003', '交易与服务中心', '2000', '3', 'service', '', '', '', 1, 0, 'M', '0', '0', '', 'form', 'admin', sysdate(), '', NULL, '交易与服务中心目录');
INSERT INTO `sys_menu` VALUES ('2004', '财务结算中心', '2000', '4', 'finance', '', '', '', 1, 0, 'M', '0', '0', '', 'money', 'admin', sysdate(), '', NULL, '财务结算中心目录');
INSERT INTO `sys_menu` VALUES ('2005', '酒店业务参数', '2000', '5', 'config', 'hotel/config/index', '', '', 1, 0, 'C', '0', '0', 'hotel:config:list', 'edit', 'admin', sysdate(), '', NULL, '酒店业务参数菜单');

INSERT INTO `sys_menu` VALUES ('2101', '待签约酒店', '2001', '1', 'pending', 'hotel/cooperate/pending/index', '', '', 1, 0, 'C', '0', '0', 'hotel:cooperate:pending:list', 'clipboard', 'admin', sysdate(), '', NULL, '待签约酒店菜单');
INSERT INTO `sys_menu` VALUES ('2102', '合作酒店', '2001', '2', 'partner', 'hotel/cooperate/partner/index', '', '', 1, 0, 'C', '0', '0', 'hotel:cooperate:partner:list', 'tree-table', 'admin', sysdate(), '', NULL, '合作酒店菜单');
INSERT INTO `sys_menu` VALUES ('2103', 'BD-酒店归属管理', '2001', '3', 'bind', 'hotel/cooperate/bind/index', '', '', 1, 0, 'C', '0', '0', 'hotel:cooperate:bind:list', 'people', 'admin', sysdate(), '', NULL, 'BD酒店归属管理菜单');
INSERT INTO `sys_menu` VALUES ('2104', 'BD绩效看板', '2001', '4', 'bdBoard', 'hotel/cooperate/bdBoard/index', '', '', 0, 0, 'C', '0', '0', 'hotel:cooperate:board:list', 'chart', 'admin', sysdate(), '', NULL, 'BD绩效看板菜单');

INSERT INTO `sys_menu` VALUES ('2201', '酒店信息', '2002', '1', 'info', 'hotel/operate/info/index', '', '', 1, 0, 'C', '0', '0', 'hotel:operate:info:list', 'example', 'admin', sysdate(), '', NULL, '酒店信息菜单');
INSERT INTO `sys_menu` VALUES ('2202', '房型管理', '2002', '2', 'roomType', 'hotel/operate/roomType/index', '', '', 1, 0, 'C', '0', '0', 'hotel:operate:roomType:list', 'nested', 'admin', sysdate(), '', NULL, '房型管理菜单');
INSERT INTO `sys_menu` VALUES ('2203', '库存价格管理', '2002', '3', 'price', 'hotel/operate/price/index', '', '', 1, 0, 'C', '0', '0', 'hotel:operate:price:list', 'date', 'admin', sysdate(), '', NULL, '库存价格管理菜单');

INSERT INTO `sys_menu` VALUES ('2301', '酒店订单', '2003', '1', 'order', 'hotel/service/order/index', '', '', 1, 0, 'C', '0', '0', 'hotel:service:order:list', 'order', 'admin', sysdate(), '', NULL, '酒店订单菜单');

INSERT INTO `sys_menu` VALUES ('2401', '账单管理', '2004', '1', 'bill', 'hotel/finance/bill/index', '', '', 1, 0, 'C', '0', '0', 'hotel:finance:bill:list', 'skill', 'admin', sysdate(), '', NULL, '账单管理菜单');

INSERT INTO `sys_menu` VALUES ('2501', '参数查询', '2005', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:config:query', '#', 'admin', sysdate(), '', NULL, '酒店业务参数查询');
INSERT INTO `sys_menu` VALUES ('2502', '参数保存', '2005', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:config:edit', '#', 'admin', sysdate(), '', NULL, '酒店业务参数保存');
INSERT INTO `sys_menu` VALUES ('2503', '配置日志', '2005', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:config:log', '#', 'admin', sysdate(), '', NULL, '酒店业务参数日志');

INSERT INTO `sys_menu` VALUES ('2601', '待签约查询', '2101', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:pending:query', '#', 'admin', sysdate(), '', NULL, '待签约酒店查询');
INSERT INTO `sys_menu` VALUES ('2602', '待签约新增', '2101', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:pending:add', '#', 'admin', sysdate(), '', NULL, '待签约酒店新增');
INSERT INTO `sys_menu` VALUES ('2603', '待签约审核', '2101', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:pending:audit', '#', 'admin', sysdate(), '', NULL, '待签约酒店审核');
INSERT INTO `sys_menu` VALUES ('2604', '待签约删除', '2101', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:pending:remove', '#', 'admin', sysdate(), '', NULL, '待签约酒店删除');
INSERT INTO `sys_menu` VALUES ('2605', '待签约导出', '2101', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:pending:export', '#', 'admin', sysdate(), '', NULL, '待签约酒店导出');
INSERT INTO `sys_menu` VALUES ('2606', '待签约修改', '2101', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:pending:edit', '#', 'admin', sysdate(), '', NULL, '待签约酒店修改');

INSERT INTO `sys_menu` VALUES ('2611', '合作酒店查询', '2102', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:partner:query', '#', 'admin', sysdate(), '', NULL, '合作酒店查询');
INSERT INTO `sys_menu` VALUES ('2612', '合作酒店修改', '2102', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:partner:edit', '#', 'admin', sysdate(), '', NULL, '合作酒店修改');
INSERT INTO `sys_menu` VALUES ('2613', '合作状态修改', '2102', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:partner:status', '#', 'admin', sysdate(), '', NULL, '合作状态修改');
INSERT INTO `sys_menu` VALUES ('2614', '合作酒店导出', '2102', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:partner:export', '#', 'admin', sysdate(), '', NULL, '合作酒店导出');
INSERT INTO `sys_menu` VALUES ('2615', '账号密码重置', '2102', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:partner:resetPwd', '#', 'admin', sysdate(), '', NULL, '酒店账号密码重置');

INSERT INTO `sys_menu` VALUES ('2621', '归属查询', '2103', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:bind:query', '#', 'admin', sysdate(), '', NULL, '归属关系查询');
INSERT INTO `sys_menu` VALUES ('2622', '归属绑定', '2103', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:bind:add', '#', 'admin', sysdate(), '', NULL, '归属绑定');
INSERT INTO `sys_menu` VALUES ('2623', '归属解绑', '2103', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:bind:remove', '#', 'admin', sysdate(), '', NULL, '归属解绑');
INSERT INTO `sys_menu` VALUES ('2624', '归属转移', '2103', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:bind:transfer', '#', 'admin', sysdate(), '', NULL, '归属转移');
INSERT INTO `sys_menu` VALUES ('2625', '归属历史', '2103', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:cooperate:bind:history', '#', 'admin', sysdate(), '', NULL, '归属历史查询');

INSERT INTO `sys_menu` VALUES ('2631', '酒店信息查询', '2201', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:info:query', '#', 'admin', sysdate(), '', NULL, '酒店信息查询');
INSERT INTO `sys_menu` VALUES ('2632', '酒店信息新增', '2201', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:info:add', '#', 'admin', sysdate(), '', NULL, '酒店信息新增');
INSERT INTO `sys_menu` VALUES ('2633', '酒店信息修改', '2201', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:info:edit', '#', 'admin', sysdate(), '', NULL, '酒店信息修改');
INSERT INTO `sys_menu` VALUES ('2634', '酒店信息上架', '2201', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:info:putOn', '#', 'admin', sysdate(), '', NULL, '酒店信息上架');
INSERT INTO `sys_menu` VALUES ('2635', '酒店信息下架', '2201', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:info:putOff', '#', 'admin', sysdate(), '', NULL, '酒店信息下架');

INSERT INTO `sys_menu` VALUES ('2641', '房型查询', '2202', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:roomType:query', '#', 'admin', sysdate(), '', NULL, '房型查询');
INSERT INTO `sys_menu` VALUES ('2642', '房型新增', '2202', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:roomType:add', '#', 'admin', sysdate(), '', NULL, '房型新增');
INSERT INTO `sys_menu` VALUES ('2643', '房型修改', '2202', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:roomType:edit', '#', 'admin', sysdate(), '', NULL, '房型修改');
INSERT INTO `sys_menu` VALUES ('2644', '房型删除', '2202', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:roomType:remove', '#', 'admin', sysdate(), '', NULL, '房型删除');
INSERT INTO `sys_menu` VALUES ('2645', '房型状态修改', '2202', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:roomType:status', '#', 'admin', sysdate(), '', NULL, '房型状态修改');
INSERT INTO `sys_menu` VALUES ('2646', '房型排序', '2202', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:roomType:sort', '#', 'admin', sysdate(), '', NULL, '房型排序');
INSERT INTO `sys_menu` VALUES ('2647', '房型复制', '2202', '7', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:roomType:copy', '#', 'admin', sysdate(), '', NULL, '房型复制');

INSERT INTO `sys_menu` VALUES ('2651', '价格日历查询', '2203', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:price:query', '#', 'admin', sysdate(), '', NULL, '价格日历查询');
INSERT INTO `sys_menu` VALUES ('2652', '单日设置', '2203', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:price:daily', '#', 'admin', sysdate(), '', NULL, '单日价格库存设置');
INSERT INTO `sys_menu` VALUES ('2653', '批量设置', '2203', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:price:batch', '#', 'admin', sysdate(), '', NULL, '批量价格库存设置');
INSERT INTO `sys_menu` VALUES ('2654', '批量关房', '2203', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:price:close', '#', 'admin', sysdate(), '', NULL, '批量关房');
INSERT INTO `sys_menu` VALUES ('2655', '批量复制', '2203', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:price:copy', '#', 'admin', sysdate(), '', NULL, '批量复制价格库存');
INSERT INTO `sys_menu` VALUES ('2656', '恢复默认', '2203', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:price:restore', '#', 'admin', sysdate(), '', NULL, '恢复默认价格库存');
INSERT INTO `sys_menu` VALUES ('2657', '价格日志查询', '2203', '7', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:operate:price:history', '#', 'admin', sysdate(), '', NULL, '价格日志查询');

INSERT INTO `sys_menu` VALUES ('2661', '订单查询', '2301', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:query', '#', 'admin', sysdate(), '', NULL, '订单查询');
INSERT INTO `sys_menu` VALUES ('2662', '订单确认', '2301', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:confirm', '#', 'admin', sysdate(), '', NULL, '订单确认');
INSERT INTO `sys_menu` VALUES ('2663', '订单取消', '2301', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:cancel', '#', 'admin', sysdate(), '', NULL, '订单取消');
INSERT INTO `sys_menu` VALUES ('2664', '办理入住', '2301', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:checkin', '#', 'admin', sysdate(), '', NULL, '办理入住');
INSERT INTO `sys_menu` VALUES ('2665', '办理离店', '2301', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:checkout', '#', 'admin', sysdate(), '', NULL, '办理离店');
INSERT INTO `sys_menu` VALUES ('2666', '订单备注', '2301', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:remark', '#', 'admin', sysdate(), '', NULL, '订单备注');
INSERT INTO `sys_menu` VALUES ('2667', '订单导出', '2301', '7', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:export', '#', 'admin', sysdate(), '', NULL, '订单导出');
INSERT INTO `sys_menu` VALUES ('2668', '退款处理', '2301', '8', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:refund', '#', 'admin', sysdate(), '', NULL, '退款处理');
INSERT INTO `sys_menu` VALUES ('2669', '纠纷处理', '2301', '9', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:service:order:dispute', '#', 'admin', sysdate(), '', NULL, '纠纷处理');

INSERT INTO `sys_menu` VALUES ('2671', '账单查询', '2401', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:finance:bill:query', '#', 'admin', sysdate(), '', NULL, '账单查询');
INSERT INTO `sys_menu` VALUES ('2672', '账单生成', '2401', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:finance:bill:generate', '#', 'admin', sysdate(), '', NULL, '账单生成');
INSERT INTO `sys_menu` VALUES ('2673', '账单确认', '2401', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:finance:bill:confirm', '#', 'admin', sysdate(), '', NULL, '账单确认');
INSERT INTO `sys_menu` VALUES ('2674', '账单异议', '2401', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:finance:bill:dispute', '#', 'admin', sysdate(), '', NULL, '账单异议');
INSERT INTO `sys_menu` VALUES ('2675', '重新核算', '2401', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:finance:bill:recalculate', '#', 'admin', sysdate(), '', NULL, '重新核算');
INSERT INTO `sys_menu` VALUES ('2676', '发起付款', '2401', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:finance:bill:payment', '#', 'admin', sysdate(), '', NULL, '发起付款');
INSERT INTO `sys_menu` VALUES ('2677', '账单导出', '2401', '7', '#', '', '', '', 1, 0, 'F', '0', '0', 'hotel:finance:bill:export', '#', 'admin', sysdate(), '', NULL, '账单导出');


