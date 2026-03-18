-- ----------------------------
-- 酒店商家端模块表结构 + 菜单权限字典初始化脚本
-- 适用项目：YimaMerchant 若依商家端
-- 执行说明：
-- 1. 首次初始化建议在测试库执行
-- 2. 若表已存在，可按需注释 drop 语句
-- 3. 菜单与字典脚本依赖若依基础表已存在
-- ----------------------------

-- ----------------------------
-- 1、酒店信息表
-- ----------------------------
drop table if exists hotel_info;
create table hotel_info (
  id               bigint(20)      not null auto_increment    comment '主键',
  merchant_id      bigint(20)      not null                   comment '商家编号',
  hotel_name       varchar(50)     not null                   comment '酒店名称',
  hotel_cover      varchar(255)    default null               comment '酒店封面图',
  hotel_images     text                                       comment '酒店轮播图(JSON数组)',
  phone            varchar(20)     not null                   comment '联系电话',
  province_code    varchar(20)     default null               comment '省编码',
  city_code        varchar(20)     default null               comment '市编码',
  district_code    varchar(20)     default null               comment '区编码',
  address          varchar(255)    not null                   comment '详细地址',
  longitude        decimal(10,6)   default null               comment '经度',
  latitude         decimal(10,6)   default null               comment '纬度',
  check_in_time    varchar(10)     not null                   comment '入住时间',
  check_out_time   varchar(10)     not null                   comment '离店时间',
  intro            text                                       comment '酒店简介',
  booking_notice   text                                       comment '预订须知',
  cancel_rule      text                                       comment '取消规则',
  invoice_desc     text                                       comment '开票说明',
  parking_desc     text                                       comment '停车说明',
  status           char(1)         default '1'                comment '酒店状态（0草稿 1启用 2停业）',
  del_flag         char(1)         default '0'                comment '删除标记（0存在 2删除）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (id),
  unique key uk_merchant_id (merchant_id),
  key idx_status (status)
) engine=innodb default charset=utf8mb4 comment = '酒店信息表';

-- ----------------------------
-- 2、酒店房型表
-- ----------------------------
drop table if exists hotel_room_type;
create table hotel_room_type (
  id               bigint(20)      not null auto_increment    comment '主键',
  hotel_id         bigint(20)      not null                   comment '酒店编号',
  merchant_id      bigint(20)      not null                   comment '商家编号',
  room_type_name   varchar(50)     not null                   comment '房型名称',
  room_type_code   varchar(50)     not null                   comment '房型编码',
  room_images      text                                       comment '房型图片(JSON数组)',
  bed_type         char(1)         not null                   comment '床型',
  people_limit     int(11)         default 1                  comment '可住人数',
  area             varchar(30)     default null               comment '面积说明',
  floor_desc       varchar(50)     default null               comment '楼层描述',
  window_type      char(1)         default null               comment '窗型',
  breakfast_count  int(11)         default 0                  comment '早餐数量',
  extra_bed_flag   char(1)         default 'N'                comment '是否可加床（Y是 N否）',
  description      text                                       comment '房型描述',
  base_price       decimal(10,2)   default null               comment '默认价格',
  market_price     decimal(10,2)   default null               comment '划线价',
  base_stock       int(11)         default 0                  comment '默认库存',
  sold_num         int(11)         default 0                  comment '已售数量',
  config_status    char(1)         default '1'                comment '配置状态（0停用 1启用）',
  sale_status      char(1)         default '0'                comment '上架状态（0下架 1上架）',
  bookable_flag    char(1)         default 'Y'                comment '是否可预订（Y是 N否）',
  sort_num         int(11)         default 0                  comment '排序号',
  del_flag         char(1)         default '0'                comment '删除标记（0存在 2删除）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (id),
  unique key uk_hotel_room_type_name (hotel_id, room_type_name),
  unique key uk_hotel_room_type_code (hotel_id, room_type_code),
  key idx_hotel_id (hotel_id),
  key idx_merchant_id (merchant_id),
  key idx_sale_status (sale_status),
  key idx_config_status (config_status)
) engine=innodb default charset=utf8mb4 comment = '酒店房型表';

-- ----------------------------
-- 3、酒店设施表
-- ----------------------------
drop table if exists hotel_facility;
create table hotel_facility (
  id               bigint(20)      not null auto_increment    comment '主键',
  facility_name    varchar(50)     not null                   comment '设施名称',
  facility_type    char(1)         not null                   comment '设施分类（1房间设施 2卫浴设施 3公共设施 4服务设施）',
  status           char(1)         default '1'                comment '状态（0停用 1启用）',
  sort_num         int(11)         default 0                  comment '排序号',
  remark           varchar(500)    default null               comment '备注',
  primary key (id),
  unique key uk_facility_name_type (facility_name, facility_type),
  key idx_facility_type (facility_type),
  key idx_status (status)
) engine=innodb default charset=utf8mb4 comment = '酒店设施表';

-- ----------------------------
-- 4、房型设施关联表
-- ----------------------------
drop table if exists hotel_room_type_facility_rel;
create table hotel_room_type_facility_rel (
  id               bigint(20)      not null auto_increment    comment '主键',
  hotel_id         bigint(20)      not null                   comment '酒店编号',
  room_type_id     bigint(20)      not null                   comment '房型编号',
  facility_id      bigint(20)      not null                   comment '设施编号',
  primary key (id),
  unique key uk_room_type_facility (room_type_id, facility_id),
  key idx_hotel_id (hotel_id),
  key idx_facility_id (facility_id)
) engine=innodb default charset=utf8mb4 comment = '房型设施关联表';

-- ----------------------------
-- 5、酒店退款单表
-- ----------------------------
drop table if exists hotel_refund_order;
create table hotel_refund_order (
  id                   bigint(20)      not null auto_increment    comment '主键',
  refund_no            varchar(50)     not null                   comment '退款单号',
  order_id             bigint(20)      not null                   comment '订单编号',
  order_no             varchar(50)     not null                   comment '订单号',
  hotel_id             bigint(20)      not null                   comment '酒店编号',
  merchant_id          bigint(20)      not null                   comment '商家编号',
  room_type_id         bigint(20)      not null                   comment '房型编号',
  guest_name           varchar(50)     default null               comment '入住人',
  guest_phone          varchar(20)     default null               comment '联系电话',
  check_in_date        date                                       comment '入住日期',
  check_out_date       date                                       comment '离店日期',
  order_amount         decimal(10,2)   default null               comment '订单金额',
  apply_refund_amount  decimal(10,2)   not null                   comment '申请退款金额',
  refund_reason        varchar(500)    default null               comment '退款原因',
  refund_status        char(1)         not null default '1'       comment '退款状态（1待商家处理 2商家已同意 3商家已拒绝）',
  audit_remark         varchar(500)    default null               comment '审核备注',
  audit_by             varchar(64)     default null               comment '审核人',
  audit_time           datetime                                   comment '审核时间',
  create_time          datetime                                   comment '创建时间',
  update_time          datetime                                   comment '更新时间',
  primary key (id),
  unique key uk_refund_no (refund_no),
  key idx_order_id (order_id),
  key idx_hotel_id (hotel_id),
  key idx_merchant_id (merchant_id),
  key idx_refund_status (refund_status),
  key idx_check_in_date (check_in_date)
) engine=innodb default charset=utf8mb4 comment = '酒店退款单表';

-- ----------------------------
-- 6、酒店房态库存价格表（预留）
-- ----------------------------
drop table if exists hotel_room_inventory;
create table hotel_room_inventory (
  id               bigint(20)      not null auto_increment    comment '主键',
  hotel_id         bigint(20)      not null                   comment '酒店编号',
  merchant_id      bigint(20)      not null                   comment '商家编号',
  room_type_id     bigint(20)      not null                   comment '房型编号',
  biz_date         date            not null                   comment '业务日期',
  stock_num        int(11)         default 0                  comment '库存数量',
  sold_num         int(11)         default 0                  comment '已售数量',
  available_num    int(11)         default 0                  comment '剩余可售',
  sale_price       decimal(10,2)   default null               comment '销售价',
  market_price     decimal(10,2)   default null               comment '划线价',
  sale_status      char(1)         default '1'                comment '可售状态（0不可售 1可售）',
  create_time      datetime                                   comment '创建时间',
  update_time      datetime                                   comment '更新时间',
  primary key (id),
  unique key uk_room_type_biz_date (room_type_id, biz_date),
  key idx_hotel_id (hotel_id),
  key idx_merchant_id (merchant_id),
  key idx_biz_date (biz_date)
) engine=innodb default charset=utf8mb4 comment = '房态库存价格表';

-- ----------------------------
-- 7、初始化设施数据
-- ----------------------------
delete from hotel_facility;
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (1, 'WiFi', '1', '1', 1, '基础设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (2, '空调', '1', '1', 2, '基础设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (3, '独立卫浴', '2', '1', 3, '卫浴设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (4, '热水', '2', '1', 4, '卫浴设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (5, '电视', '1', '1', 5, '房间设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (6, '吹风机', '2', '1', 6, '房间设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (7, '洗漱用品', '2', '1', 7, '房间设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (8, '免费停车', '3', '1', 8, '公共设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (9, '电梯', '3', '1', 9, '公共设施');
insert into hotel_facility (id, facility_name, facility_type, status, sort_num, remark) values (10, '早餐', '4', '1', 10, '服务设施');

-- ----------------------------
-- 8、初始化酒店业务字典类型
-- ----------------------------
delete from sys_dict_data where dict_type in (
  'hotel_status',
  'hotel_room_bed_type',
  'hotel_room_window_type',
  'hotel_facility_type',
  'hotel_room_config_status',
  'hotel_room_sale_status',
  'hotel_bookable_flag',
  'hotel_refund_status'
);

delete from sys_dict_type where dict_type in (
  'hotel_status',
  'hotel_room_bed_type',
  'hotel_room_window_type',
  'hotel_facility_type',
  'hotel_room_config_status',
  'hotel_room_sale_status',
  'hotel_bookable_flag',
  'hotel_refund_status'
);

insert into sys_dict_type values(200, '酒店状态', 'hotel_status', '0', 'admin', sysdate(), '', null, '酒店状态字典');
insert into sys_dict_type values(201, '房型床型', 'hotel_room_bed_type', '0', 'admin', sysdate(), '', null, '房型床型字典');
insert into sys_dict_type values(202, '房型窗型', 'hotel_room_window_type', '0', 'admin', sysdate(), '', null, '房型窗型字典');
insert into sys_dict_type values(203, '设施分类', 'hotel_facility_type', '0', 'admin', sysdate(), '', null, '设施分类字典');
insert into sys_dict_type values(204, '房型配置状态', 'hotel_room_config_status', '0', 'admin', sysdate(), '', null, '房型配置状态字典');
insert into sys_dict_type values(205, '房型上架状态', 'hotel_room_sale_status', '0', 'admin', sysdate(), '', null, '房型上架状态字典');
insert into sys_dict_type values(206, '是否可预订', 'hotel_bookable_flag', '0', 'admin', sysdate(), '', null, '是否可预订字典');
insert into sys_dict_type values(207, '酒店退款状态', 'hotel_refund_status', '0', 'admin', sysdate(), '', null, '酒店退款状态字典');

insert into sys_dict_data values(2000, 1,  '草稿',     '0', 'hotel_status',              '', 'default', 'Y', '0', 'admin', sysdate(), '', null, '酒店状态');
insert into sys_dict_data values(2001, 2,  '启用',     '1', 'hotel_status',              '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '酒店状态');
insert into sys_dict_data values(2002, 3,  '停业',     '2', 'hotel_status',              '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '酒店状态');

insert into sys_dict_data values(2010, 1,  '大床',     '1', 'hotel_room_bed_type',       '', 'default', 'N', '0', 'admin', sysdate(), '', null, '床型');
insert into sys_dict_data values(2011, 2,  '双床',     '2', 'hotel_room_bed_type',       '', 'success', 'N', '0', 'admin', sysdate(), '', null, '床型');
insert into sys_dict_data values(2012, 3,  '圆床',     '3', 'hotel_room_bed_type',       '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '床型');
insert into sys_dict_data values(2013, 4,  '榻榻米',   '4', 'hotel_room_bed_type',       '', 'info',    'N', '0', 'admin', sysdate(), '', null, '床型');

insert into sys_dict_data values(2020, 1,  '有窗',     '1', 'hotel_room_window_type',    '', 'success', 'N', '0', 'admin', sysdate(), '', null, '窗型');
insert into sys_dict_data values(2021, 2,  '无窗',     '2', 'hotel_room_window_type',    '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '窗型');
insert into sys_dict_data values(2022, 3,  '部分有窗', '3', 'hotel_room_window_type',    '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '窗型');

insert into sys_dict_data values(2030, 1,  '房间设施', '1', 'hotel_facility_type',       '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '设施分类');
insert into sys_dict_data values(2031, 2,  '卫浴设施', '2', 'hotel_facility_type',       '', 'success', 'N', '0', 'admin', sysdate(), '', null, '设施分类');
insert into sys_dict_data values(2032, 3,  '公共设施', '3', 'hotel_facility_type',       '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '设施分类');
insert into sys_dict_data values(2033, 4,  '服务设施', '4', 'hotel_facility_type',       '', 'info',    'N', '0', 'admin', sysdate(), '', null, '设施分类');

insert into sys_dict_data values(2040, 1,  '停用',     '0', 'hotel_room_config_status',  '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '房型配置状态');
insert into sys_dict_data values(2041, 2,  '启用',     '1', 'hotel_room_config_status',  '', 'success', 'N', '0', 'admin', sysdate(), '', null, '房型配置状态');

insert into sys_dict_data values(2050, 1,  '下架',     '0', 'hotel_room_sale_status',    '', 'info',    'N', '0', 'admin', sysdate(), '', null, '房型上架状态');
insert into sys_dict_data values(2051, 2,  '上架',     '1', 'hotel_room_sale_status',    '', 'success', 'N', '0', 'admin', sysdate(), '', null, '房型上架状态');

insert into sys_dict_data values(2060, 1,  '否',       'N', 'hotel_bookable_flag',       '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '是否可预订');
insert into sys_dict_data values(2061, 2,  '是',       'Y', 'hotel_bookable_flag',       '', 'success', 'N', '0', 'admin', sysdate(), '', null, '是否可预订');

insert into sys_dict_data values(2070, 1,  '待商家处理', '1', 'hotel_refund_status',     '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '退款状态');
insert into sys_dict_data values(2071, 2,  '商家已同意', '2', 'hotel_refund_status',     '', 'success', 'N', '0', 'admin', sysdate(), '', null, '退款状态');
insert into sys_dict_data values(2072, 3,  '商家已拒绝', '3', 'hotel_refund_status',     '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '退款状态');

-- ----------------------------
-- 9、初始化菜单与权限
-- 说明：parent_id=0 时会挂到顶级目录下，可按现网菜单结构自行调整
-- ----------------------------
delete from sys_menu where perms like 'hotel:%';
delete from sys_menu where path in ('hotel', 'hotelInfo', 'hotelRoomType', 'hotelInventory', 'hotelRefund');

insert into sys_menu values('2000', '酒店管理', '0', '6', 'hotel', null, '', 1, 0, 'M', '0', '0', '', 'guide', 'admin', sysdate(), '', null, '酒店管理目录');
insert into sys_menu values('2001', '酒店信息', '2000', '1', 'hotelInfo', 'hotel/info/index', '', 1, 0, 'C', '0', '0', 'hotel:info:list', 'build', 'admin', sysdate(), '', null, '酒店信息菜单');
insert into sys_menu values('2002', '房型管理', '2000', '2', 'hotelRoomType', 'hotel/roomType/index', '', 1, 0, 'C', '0', '0', 'hotel:roomType:list', 'tree', 'admin', sysdate(), '', null, '房型管理菜单');
insert into sys_menu values('2003', '库存价格管理', '2000', '3', 'hotelInventory', 'hotel/inventory/index', '', 1, 0, 'C', '0', '0', 'hotel:inventory:list', 'shopping', 'admin', sysdate(), '', null, '库存价格管理菜单');
insert into sys_menu values('2004', '酒店退款', '2000', '4', 'hotelRefund', 'hotel/refund/index', '', 1, 0, 'C', '0', '0', 'hotel:refund:list', 'money', 'admin', sysdate(), '', null, '酒店退款菜单');

insert into sys_menu values('2101', '酒店信息查询', '2001', '1', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:info:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2102', '酒店信息新增', '2001', '2', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:info:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2103', '酒店信息修改', '2001', '3', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:info:edit', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu values('2201', '房型查询', '2002', '1', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:roomType:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2202', '房型新增', '2002', '2', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:roomType:add', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2203', '房型修改', '2002', '3', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:roomType:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2204', '房型删除', '2002', '4', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:roomType:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2205', '房型上下架', '2002', '5', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:roomType:changeStatus', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu values('2301', '库存价格查询', '2003', '1', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:inventory:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2302', '库存价格修改', '2003', '2', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:inventory:edit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2303', '库存价格批量修改', '2003', '3', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:inventory:batchEdit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2304', '库存价格上下架', '2003', '4', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:inventory:changeStatus', '#', 'admin', sysdate(), '', null, '');

insert into sys_menu values('2401', '退款查询', '2004', '1', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:refund:query', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2402', '退款审核', '2004', '2', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:refund:audit', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2403', '退款详情', '2004', '3', '#', '', '', 1, 0, 'F', '0', '0', 'hotel:refund:detail', '#', 'admin', sysdate(), '', null, '');
