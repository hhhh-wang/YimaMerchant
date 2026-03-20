-- ----------------------------
-- 酒店商家端业务表结构
-- 若执行前请确认库中不存在历史同名表/或允许重建（本脚本会 drop）
-- ----------------------------

-- 1) 酒店业务主表
drop table if exists hotel_bd_change_record;
drop table if exists hotel_refund_order;
drop table if exists hotel_order;
drop table if exists hotel_room_inventory;
drop table if exists hotel_room_type_facility_rel;
drop table if exists hotel_room_type;
drop table if exists hotel_facility;
drop table if exists hotel_info;

-- ----------------------------
-- 1、酒店信息表
-- ----------------------------
create table hotel_info (
  id                  bigint(20)      not null auto_increment comment '酒店主键',
  merchant_id         bigint(20)      not null comment '商家编号',
  hotel_name          varchar(50)     not null comment '酒店名称',
  hotel_cover         varchar(255)    default '' comment '封面图',
  hotel_images        text             comment '轮播图集合(JSON字符串)',
  phone               varchar(20)     not null default '' comment '联系电话',
  province_code      varchar(20)     default '' comment '省编码',
  city_code          varchar(20)     default '' comment '市编码',
  district_code      varchar(20)     default '' comment '区编码',
  address            varchar(255)    default '' comment '详细地址',
  longitude          decimal(10,6)   null comment '经度',
  latitude           decimal(10,6)   null comment '纬度',
  check_in_time      varchar(10)     not null default '' comment '入住时间(HH:mm)',
  check_out_time     varchar(10)     not null default '' comment '离店时间(HH:mm)',
  intro              text             comment '酒店简介',
  booking_notice     text             comment '预订须知',
  invoice_desc       text             comment '开票说明',
  parking_desc       text             comment '停车说明',

  commission_mode     char(1)         not null default '' comment '佣金模式',
  commission_value    decimal(10,2)  not null default 0.00 comment '佣金值',

  bd_user_id          bigint(20)      not null comment '当前 BD 编号',
  bd_name             varchar(50)     not null default '' comment '当前 BD 名称快照',
  bd_phone            varchar(20)     not null default '' comment '当前 BD 电话',

  refund_rule_type    char(1)         not null comment '退款规则类型',
  refund_rule_desc    varchar(500)   default '' comment '退款规则说明',
  refund_deadline_desc varchar(255)   default '' comment '限时退款条件说明',

  status              char(1)         not null default '0' comment '酒店状态',
  del_flag            char(1)         not null default '0' comment '删除标记（0存在 2删除）',

  create_by           varchar(64)     default '' comment '创建者',
  create_time         datetime         null comment '创建时间',
  update_by           varchar(64)     default '' comment '更新者',
  update_time         datetime         null comment '更新时间',

  primary key (id),
  unique key uk_merchant_id (merchant_id),
  key idx_bd_user_id (bd_user_id),
  key idx_status (status)
) engine=innodb comment = '酒店信息表';

-- ----------------------------
-- 2、房型表
-- ----------------------------
create table hotel_room_type (
  id                  bigint(20)      not null auto_increment comment '房型主键',
  hotel_id            bigint(20)      not null comment '酒店编号',
  merchant_id        bigint(20)      not null comment '商家编号（冗余）',
  room_type_name     varchar(50)     not null comment '房型名称',
  room_type_code     varchar(50)     not null comment '房型编码',
  room_images        text             comment '房型图片集合(JSON字符串)',
  bed_type            char(1)         not null default '9' comment '床型',
  people_limit       int(11)         not null default 0 comment '可住人数',
  area                varchar(50)    default '' comment '面积说明',
  floor_desc          varchar(100)   default '' comment '楼层描述',
  window_type        char(1)         not null default '1' comment '窗型',
  breakfast_count    int(11)         not null default 0 comment '早餐数量',
  extra_bed_flag     char(1)         not null default 'N' comment '是否可加床',
  description        text             comment '房型描述',

  base_price         decimal(10,2)  not null default 0.00 comment '默认价格',
  market_price       decimal(10,2)  not null default 0.00 comment '划线价',
  base_stock         int(11)         not null default 0 comment '默认库存',

  config_status      char(1)         not null default '0' comment '配置状态',
  sale_status        char(1)         not null default '0' comment '上架状态',
  bookable_flag      char(1)         not null default 'N' comment '是否可预订',
  sort_num           int(11)         not null default 0 comment '排序号',
  remark              varchar(500)   default null comment '备注',

  del_flag            char(1)         not null default '0' comment '删除标记（0存在 2删除）',
  create_by           varchar(64)     default '' comment '创建者',
  create_time         datetime         null comment '创建时间',
  update_by           varchar(64)     default '' comment '更新者',
  update_time         datetime         null comment '更新时间',

  primary key (id),
  unique key uk_hotel_room_type_name (hotel_id, room_type_name),
  unique key uk_hotel_room_type_code (hotel_id, room_type_code),
  key idx_merchant_id (merchant_id),
  key idx_hotel_id (hotel_id),
  key idx_sale_status (sale_status)
) engine=innodb comment = '房型表';

-- ----------------------------
-- 3、设施表
-- ----------------------------
create table hotel_facility (
  id                  bigint(20)      not null auto_increment comment '设施主键',
  facility_name       varchar(100)    not null comment '设施名称',
  facility_type       char(1)         not null comment '设施分类',
  status              char(1)         not null default '0' comment '状态（0正常 1停用）',
  sort_num           int(11)         not null default 0 comment '排序号',
  remark              varchar(500)   default null comment '备注',

  create_by           varchar(64)     default '' comment '创建者',
  create_time         datetime         null comment '创建时间',
  update_by           varchar(64)     default '' comment '更新者',
  update_time         datetime         null comment '更新时间',

  primary key (id),
  key idx_facility_type (facility_type),
  key idx_status (status)
) engine=innodb comment = '设施字典表';

-- ----------------------------
-- 4、房型-设施关联表
-- ----------------------------
create table hotel_room_type_facility_rel (
  id                 bigint(20)      not null auto_increment comment '关联主键',
  hotel_id           bigint(20)      not null comment '酒店编号（冗余）',
  room_type_id       bigint(20)      not null comment '房型编号',
  facility_id        bigint(20)      not null comment '设施编号',

  primary key (id),
  unique key uk_room_type_facility (room_type_id, facility_id),
  key idx_hotel_id (hotel_id),
  key idx_room_type_id (room_type_id),
  key idx_facility_id (facility_id)
) engine=innodb comment = '房型设施绑定关联表';

-- ----------------------------
-- 5、房态库存价格表（预留二期）
-- ----------------------------
create table hotel_room_inventory (
  id                 bigint(20)      not null auto_increment comment '房态库存主键',
  hotel_id           bigint(20)      not null comment '酒店编号',
  merchant_id        bigint(20)      not null comment '商家编号（冗余）',
  room_type_id       bigint(20)      not null comment '房型编号',
  biz_date           date             not null comment '业务日期',

  stock_num          int(11)         not null default 0 comment '库存数量',
  sold_num           int(11)         not null default 0 comment '已售数量',
  available_num     int(11)         not null default 0 comment '剩余可售',

  sale_price         decimal(10,2)  not null default 0.00 comment '销售价',
  market_price       decimal(10,2)  not null default 0.00 comment '划线价',
  sale_status        char(1)         not null default '0' comment '可售状态',

  create_by          varchar(64)     default '' comment '创建者',
  create_time        datetime         null comment '创建时间',
  update_by          varchar(64)     default '' comment '更新者',
  update_time        datetime         null comment '更新时间',

  primary key (id),
  unique key uk_hotel_room_inventory (hotel_id, room_type_id, biz_date),
  key idx_merchant_id (merchant_id),
  key idx_hotel_id (hotel_id),
  key idx_room_type_id (room_type_id),
  key idx_biz_date (biz_date)
) engine=innodb comment = '房态库存价格表';

-- ----------------------------
-- 6、酒店订单表
-- ----------------------------
create table hotel_order (
  id                 bigint(20)      not null auto_increment comment '订单主键',
  order_no           varchar(50)     not null comment '订单号',
  hotel_id           bigint(20)      not null comment '酒店编号',
  merchant_id        bigint(20)      not null comment '商家编号（冗余）',
  room_type_id       bigint(20)      not null comment '房型编号',

  contact_name       varchar(50)     not null default '' comment '入住人或联系人',
  contact_phone      varchar(20)     not null default '' comment '联系电话',
  check_in_date      date            not null comment '入住日期',
  check_out_date     date            not null comment '离店日期',

  order_amount       decimal(10,2)  not null default 0.00 comment '订单金额',
  pay_amount         decimal(10,2)  not null default 0.00 comment '实付金额',
  refunded_amount    decimal(10,2)  not null default 0.00 comment '已退款金额',
  refundable_amount  decimal(10,2)  not null default 0.00 comment '剩余可退款金额',

  order_status       char(1)         not null default '1' comment '订单状态',
  after_sale_status  char(1)         not null default '0' comment '售后状态',

  pay_time           datetime         null comment '支付时间',

  create_by          varchar(64)     default '' comment '创建者',
  create_time        datetime         null comment '创建时间',
  update_by          varchar(64)     default '' comment '更新者',
  update_time        datetime         null comment '更新时间',

  primary key (id),
  unique key uk_order_no (order_no),
  key idx_merchant_id (merchant_id),
  key idx_hotel_id (hotel_id),
  key idx_room_type_id (room_type_id),
  key idx_order_status (order_status),
  key idx_pay_time (pay_time)
) engine=innodb comment = '酒店订单表';

-- ----------------------------
-- 7、酒店退款单表
-- ----------------------------
create table hotel_refund_order (
  id                     bigint(20)      not null auto_increment comment '退款单主键',
  refund_no              varchar(50)     not null comment '退款单号',
  order_id               bigint(20)      not null comment '订单编号',
  order_no               varchar(50)     not null comment '订单号',

  hotel_id               bigint(20)      not null comment '酒店编号',
  merchant_id            bigint(20)      not null comment '商家编号（冗余）',
  room_type_id           bigint(20)      not null comment '房型编号',

  apply_refund_amount    decimal(10,2)  not null default 0.00 comment '申请退款金额',
  approved_refund_amount decimal(10,2)  not null default 0.00 comment '审核通过退款金额',

  refund_reason          varchar(500)   default '' comment '退款原因',
  refund_status          char(1)         not null default '1' comment '退款状态',

  audit_remark           varchar(500)   default '' comment '审核备注',
  audit_by               varchar(64)     default '' comment '审核人',
  audit_time             datetime         null comment '审核时间',

  create_by              varchar(64)     default '' comment '创建者',
  create_time            datetime         null comment '创建时间',
  update_by              varchar(64)     default '' comment '更新者',
  update_time            datetime         null comment '更新时间',

  primary key (id),
  unique key uk_refund_no (refund_no),
  key idx_order_id (order_id),
  key idx_refund_status (refund_status),
  key idx_hotel_id (hotel_id),
  key idx_merchant_id (merchant_id),
  key idx_create_time (create_time)
) engine=innodb comment = '酒店退款单表';

-- ----------------------------
-- 8、酒店所属 BD 变更记录表
-- ----------------------------
create table hotel_bd_change_record (
  id                 bigint(20)      not null auto_increment comment '变更记录主键',
  hotel_id           bigint(20)      not null comment '酒店编号',
  merchant_id        bigint(20)      not null comment '商家编号（冗余）',

  old_bd_user_id     bigint(20)      not null comment '原 BD 编号',
  old_bd_name        varchar(50)     not null default '' comment '原 BD 名称',
  old_bd_phone       varchar(20)     not null default '' comment '原 BD 电话',

  new_bd_user_id     bigint(20)      not null comment '新 BD 编号',
  new_bd_name        varchar(50)     not null default '' comment '新 BD 名称',
  new_bd_phone       varchar(20)     not null default '' comment '新 BD 电话',

  change_reason      varchar(500)   default '' comment '更换原因',
  effective_time     datetime         null comment '生效时间',

  operate_by         varchar(64)     default '' comment '操作人',
  operate_time       datetime         null comment '操作时间',

  create_time        datetime         null comment '创建时间',

  primary key (id),
  key idx_hotel_id (hotel_id),
  key idx_old_bd_user_id (old_bd_user_id),
  key idx_new_bd_user_id (new_bd_user_id),
  key idx_operate_time (operate_time)
) engine=innodb comment = '酒店 BD 归属变更记录表';

-- ----------------------------
-- 9、酒店模块字典初始化（若依字典表）
-- ----------------------------

-- 9.1 酒店状态
delete from sys_dict_data where dict_type = 'hotel_status';
delete from sys_dict_type where dict_type = 'hotel_status';
insert into sys_dict_type values(11, '酒店状态', 'hotel_status', '0', 'admin', sysdate(), '', null, '酒店状态列表');
insert into sys_dict_data values(30, 1, '草稿', '0', 'hotel_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '草稿');
insert into sys_dict_data values(31, 2, '启用', '1', 'hotel_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '启用');
insert into sys_dict_data values(32, 3, '停业', '2', 'hotel_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '停业');
insert into sys_dict_data values(33, 4, '待审核', '3', 'hotel_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '待审核');

-- 9.2 佣金模式
delete from sys_dict_data where dict_type = 'hotel_commission_mode';
delete from sys_dict_type where dict_type = 'hotel_commission_mode';
insert into sys_dict_type values(12, '佣金模式', 'hotel_commission_mode', '0', 'admin', sysdate(), '', null, '佣金模式列表');
insert into sys_dict_data values(34, 1, '底价模式', '1', 'hotel_commission_mode', '', '', 'N', '0', 'admin', sysdate(), '', null, '底价模式');
insert into sys_dict_data values(35, 2, '卖价模式', '2', 'hotel_commission_mode', '', '', 'N', '0', 'admin', sysdate(), '', null, '卖价模式');

-- 9.3 退款规则类型
delete from sys_dict_data where dict_type = 'hotel_refund_rule_type';
delete from sys_dict_type where dict_type = 'hotel_refund_rule_type';
insert into sys_dict_type values(13, '退款规则类型', 'hotel_refund_rule_type', '0', 'admin', sysdate(), '', null, '退款规则类型列表');
insert into sys_dict_data values(36, 1, '限时退', '1', 'hotel_refund_rule_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '限时退');
insert into sys_dict_data values(37, 2, '不可退', '2', 'hotel_refund_rule_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '不可退');
insert into sys_dict_data values(38, 3, '任意退', '3', 'hotel_refund_rule_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '任意退');

-- 9.4 房型配置状态
delete from sys_dict_data where dict_type = 'hotel_room_config_status';
delete from sys_dict_type where dict_type = 'hotel_room_config_status';
insert into sys_dict_type values(14, '房型配置状态', 'hotel_room_config_status', '0', 'admin', sysdate(), '', null, '房型配置状态列表');
insert into sys_dict_data values(39, 1, '未启用', '0', 'hotel_room_config_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '未启用');
insert into sys_dict_data values(40, 2, '已启用', '1', 'hotel_room_config_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '已启用');

-- 9.5 房型上架状态
delete from sys_dict_data where dict_type = 'hotel_room_sale_status';
delete from sys_dict_type where dict_type = 'hotel_room_sale_status';
insert into sys_dict_type values(15, '房型上架状态', 'hotel_room_sale_status', '0', 'admin', sysdate(), '', null, '房型上架状态列表');
insert into sys_dict_data values(41, 1, '已下架', '0', 'hotel_room_sale_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '已下架');
insert into sys_dict_data values(42, 2, '已上架', '1', 'hotel_room_sale_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '已上架');

-- 9.6 是否可预订
delete from sys_dict_data where dict_type = 'hotel_bookable_flag';
delete from sys_dict_type where dict_type = 'hotel_bookable_flag';
insert into sys_dict_type values(16, '是否可预订', 'hotel_bookable_flag', '0', 'admin', sysdate(), '', null, '是否可预订列表');
insert into sys_dict_data values(43, 1, '是', 'Y', 'hotel_bookable_flag', '', '', 'N', '0', 'admin', sysdate(), '', null, '是');
insert into sys_dict_data values(44, 2, '否', 'N', 'hotel_bookable_flag', '', '', 'N', '0', 'admin', sysdate(), '', null, '否');

-- 9.7 床型
delete from sys_dict_data where dict_type = 'hotel_bed_type';
delete from sys_dict_type where dict_type = 'hotel_bed_type';
insert into sys_dict_type values(17, '床型', 'hotel_bed_type', '0', 'admin', sysdate(), '', null, '床型列表');
insert into sys_dict_data values(45, 1, '大床', '1', 'hotel_bed_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '大床');
insert into sys_dict_data values(46, 2, '双床', '2', 'hotel_bed_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '双床');
insert into sys_dict_data values(47, 3, '圆床', '3', 'hotel_bed_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '圆床');
insert into sys_dict_data values(48, 4, '榻榻米', '4', 'hotel_bed_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '榻榻米');
insert into sys_dict_data values(49, 5, '其他', '9', 'hotel_bed_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '其他');

-- 9.8 窗型
delete from sys_dict_data where dict_type = 'hotel_window_type';
delete from sys_dict_type where dict_type = 'hotel_window_type';
insert into sys_dict_type values(18, '窗型', 'hotel_window_type', '0', 'admin', sysdate(), '', null, '窗型列表');
insert into sys_dict_data values(50, 1, '有窗', '1', 'hotel_window_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '有窗');
insert into sys_dict_data values(51, 2, '无窗', '2', 'hotel_window_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '无窗');
insert into sys_dict_data values(52, 3, '部分有窗', '3', 'hotel_window_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '部分有窗');

-- 9.9 设施分类
delete from sys_dict_data where dict_type = 'hotel_facility_type';
delete from sys_dict_type where dict_type = 'hotel_facility_type';
insert into sys_dict_type values(19, '设施分类', 'hotel_facility_type', '0', 'admin', sysdate(), '', null, '设施分类列表');
insert into sys_dict_data values(53, 1, '房间设施', '1', 'hotel_facility_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '房间设施');
insert into sys_dict_data values(54, 2, '卫浴设施', '2', 'hotel_facility_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '卫浴设施');
insert into sys_dict_data values(55, 3, '公共设施', '3', 'hotel_facility_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '公共设施');
insert into sys_dict_data values(56, 4, '服务设施', '4', 'hotel_facility_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '服务设施');

-- 9.10 订单状态
delete from sys_dict_data where dict_type = 'hotel_order_status';
delete from sys_dict_type where dict_type = 'hotel_order_status';
insert into sys_dict_type values(20, '订单状态', 'hotel_order_status', '0', 'admin', sysdate(), '', null, '订单状态列表');
insert into sys_dict_data values(57, 1, '待支付', '1', 'hotel_order_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '待支付');
insert into sys_dict_data values(58, 2, '已支付待入住', '2', 'hotel_order_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '已支付待入住');
insert into sys_dict_data values(59, 3, '入住中', '3', 'hotel_order_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '入住中');
insert into sys_dict_data values(60, 4, '已完成', '4', 'hotel_order_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '已完成');
insert into sys_dict_data values(61, 5, '已取消', '5', 'hotel_order_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '已取消');

-- 9.11 售后状态
delete from sys_dict_data where dict_type = 'hotel_after_sale_status';
delete from sys_dict_type where dict_type = 'hotel_after_sale_status';
insert into sys_dict_type values(21, '售后状态', 'hotel_after_sale_status', '0', 'admin', sysdate(), '', null, '售后状态列表');
insert into sys_dict_data values(62, 1, '无售后', '0', 'hotel_after_sale_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '无售后');
insert into sys_dict_data values(63, 2, '待退款处理', '1', 'hotel_after_sale_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '待退款处理');
insert into sys_dict_data values(64, 3, '部分退款中', '2', 'hotel_after_sale_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '部分退款中');
insert into sys_dict_data values(65, 4, '已退款', '3', 'hotel_after_sale_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '已退款');
insert into sys_dict_data values(66, 5, '退款驳回', '4', 'hotel_after_sale_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '退款驳回');

-- 9.12 退款状态
delete from sys_dict_data where dict_type = 'hotel_refund_status';
delete from sys_dict_type where dict_type = 'hotel_refund_status';
insert into sys_dict_type values(22, '退款状态', 'hotel_refund_status', '0', 'admin', sysdate(), '', null, '退款状态列表');
insert into sys_dict_data values(67, 1, '待商家处理', '1', 'hotel_refund_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '待商家处理');
insert into sys_dict_data values(68, 2, '商家已同意', '2', 'hotel_refund_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '商家已同意');
insert into sys_dict_data values(69, 3, '商家已拒绝', '3', 'hotel_refund_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '商家已拒绝');
insert into sys_dict_data values(70, 4, '退款处理中预留', '4', 'hotel_refund_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '退款处理中预留');
insert into sys_dict_data values(71, 5, '退款成功预留', '5', 'hotel_refund_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '退款成功预留');
insert into sys_dict_data values(72, 6, '退款失败预留', '6', 'hotel_refund_status', '', '', 'N', '0', 'admin', sysdate(), '', null, '退款失败预留');





