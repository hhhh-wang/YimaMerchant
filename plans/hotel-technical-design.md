# 酒店商家端技术设计文档

## 1. 文档说明

本文档基于 [`plans/hotel-management-plan.md`](plans/hotel-management-plan.md) 的业务方案，进一步细化为可执行的技术设计文档，面向当前若依前后端分离项目落地，覆盖以下内容：

- 前端页面与交互设计
- 后端接口设计
- 数据库表设计与建表建议
- 若依权限、字典、菜单与模块落地建议
- 第一阶段实现边界与第二阶段预留说明

当前项目结构已经具备典型若依前后端分离基础：

- 前端目录：[`yimamerchant-ui/src`](yimamerchant-ui/src)
- 后端管理端：[`yimamerchant-admin`](yimamerchant-admin)
- 后端业务模块：[`yimamerchant-system/src/main/java/com/yimamerchant/system`](yimamerchant-system/src/main/java/com/yimamerchant/system)
- SQL目录：[`sql`](sql)

本文档默认技术前提如下：

- 前端采用 Vue2 + Element UI + 若依标准页面模式
- 后端采用 Spring Boot + Spring Security + MyBatis
- 业务数据落在 [`yimamerchant-system`](yimamerchant-system) 模块
- 控制器落在 [`yimamerchant-admin`](yimamerchant-admin) 管理端模块

---

## 2. 本期技术实现范围

### 2.1 本期实现目标

一期目标为把酒店商家端的最小经营闭环跑通，包含：

- 酒店信息维护
- 房型档案维护
- 房型设施绑定
- 房型默认价格与默认库存管理
- 房型上下架
- 酒店退款列表与审核处理

### 2.2 本期不做

本期暂不实现以下能力：

- 多店管理
- 日历房态管理
- 平台复核退款
- 自动打款退款
- 渠道分销同步
- 财务对账中心

### 2.3 技术实现原则

- 单店模型，但所有核心表保留 `merchant_id` 与 `hotel_id`
- 列表能力优先采用若依标准分页接口
- 操作能力优先采用单表单、弹窗、详情页组合
- 状态字段按业务语义分离，不做混合复用
- 第一阶段以默认库存、默认价格驱动经营管理
- 预留按日房态库存表，不强制一期启用

---

## 3. 若依落地模块设计

## 3.1 后端分层建议

建议新增酒店业务包，统一归入 [`yimamerchant-system/src/main/java/com/yimamerchant/system`](yimamerchant-system/src/main/java/com/yimamerchant/system) 下：

```text
com.yimamerchant.system.domain.hotel
com.yimamerchant.system.mapper.hotel
com.yimamerchant.system.service.hotel
com.yimamerchant.system.service.hotel.impl
```

管理端控制器建议放在：

```text
com.yimamerchant.web.controller.hotel
```

### 3.1.1 推荐类清单

#### Domain

- `HotelInfo`
- `HotelRoomType`
- `HotelFacility`
- `HotelRoomTypeFacilityRel`
- `HotelInventory`
- `HotelRefundOrder`

#### Query / DTO / VO

- `HotelRoomTypeQuery`
- `HotelInventoryQuery`
- `HotelRefundQuery`
- `HotelRoomTypeForm`
- `HotelInfoForm`
- `HotelRefundAuditForm`
- `HotelRoomTypeVO`
- `HotelRefundDetailVO`

#### Service

- `IHotelInfoService`
- `IHotelRoomTypeService`
- `IHotelFacilityService`
- `IHotelInventoryService`
- `IHotelRefundService`

#### Controller

- `HotelInfoController`
- `HotelRoomTypeController`
- `HotelFacilityController`
- `HotelInventoryController`
- `HotelRefundController`

---

## 3.2 前端目录建议

建议在 [`yimamerchant-ui/src/views`](yimamerchant-ui/src/views) 下新建业务目录：

```text
yimamerchant-ui/src/views/hotel/
├─ info/
│  └─ index.vue
├─ roomType/
│  └─ index.vue
├─ inventory/
│  └─ index.vue
└─ refund/
   └─ index.vue
```

接口定义建议放在 [`yimamerchant-ui/src/api`](yimamerchant-ui/src/api) 下：

```text
yimamerchant-ui/src/api/hotel/
├─ info.js
├─ roomType.js
├─ facility.js
├─ inventory.js
└─ refund.js
```

---

## 4. 前端页面技术设计

## 4.1 酒店信息页

页面文件建议：[`yimamerchant-ui/src/views/hotel/info/index.vue`](yimamerchant-ui/src/views/hotel/info/index.vue)

### 4.1.1 页面定位

单表单页面，用于商家维护当前酒店基础资料。

### 4.1.2 页面布局建议

- 顶部：页面标题 + 保存按钮
- 主体：基础信息表单
- 图片区域：酒店封面图上传、轮播图上传
- 文本区域：酒店简介、预订须知、取消规则、开票说明、停车说明

### 4.1.3 表单字段

| 字段 | 组件建议 | 是否必填 | 说明 |
|---|---|---|---|
| hotelName | `el-input` | 是 | 酒店名称 |
| hotelCover | 上传组件 | 是 | 封面图 |
| hotelImages | 多图上传 | 否 | 轮播图 |
| phone | `el-input` | 是 | 联系电话 |
| region | 省市区联动 | 是 | 行政区划 |
| address | `el-input` | 是 | 详细地址 |
| longitude | `el-input-number` | 否 | 经度 |
| latitude | `el-input-number` | 否 | 纬度 |
| checkInTime | 时间选择 | 是 | 入住时间 |
| checkOutTime | 时间选择 | 是 | 离店时间 |
| intro | `el-input type=textarea` | 否 | 酒店简介 |
| bookingNotice | `el-input type=textarea` | 否 | 预订须知 |
| cancelRule | `el-input type=textarea` | 否 | 取消规则 |
| invoiceDesc | `el-input type=textarea` | 否 | 开票说明 |
| parkingDesc | `el-input type=textarea` | 否 | 停车说明 |
| status | `el-radio-group` | 是 | 酒店状态 |

### 4.1.4 交互规则

- 页面初始化调用查询接口获取当前商家酒店信息
- 若不存在数据，页面以新增模式展示
- 若存在数据，页面以编辑模式展示
- 点击保存时先执行前端校验，再提交后台
- 封面图建议限制一张，轮播图建议限制最多 9 张

### 4.1.5 前端校验规则

- 酒店名称不能为空，长度建议不超过 50
- 联系电话不能为空，格式进行手机号或座机校验
- 省市区不能为空
- 详细地址不能为空，长度建议不超过 200
- 入住时间、离店时间不能为空

---

## 4.2 房型管理页

页面文件建议：[`yimamerchant-ui/src/views/hotel/roomType/index.vue`](yimamerchant-ui/src/views/hotel/roomType/index.vue)

### 4.2.1 页面定位

列表页 + 新增编辑弹窗，用于维护房型基础档案与设施绑定。

### 4.2.2 页面结构

- 查询区域
- 房型列表表格
- 新增编辑弹窗
- 详情弹窗 可选
- 设施勾选区域 可置于弹窗中 tabs 内

### 4.2.3 查询条件

| 字段 | 组件建议 | 说明 |
|---|---|---|
| roomTypeName | `el-input` | 房型名称 |
| configStatus | `el-select` | 配置状态 |
| saleStatus | `el-select` | 上架状态 |

### 4.2.4 列表字段

- 房型名称
- 房型编码
- 房型图片首图
- 床型
- 可住人数
- 默认价格
- 默认库存
- 配置状态
- 上架状态
- 更新时间
- 操作列

### 4.2.5 操作按钮

- 新增
- 编辑
- 详情
- 启用 / 停用
- 删除
- 上架 / 下架

### 4.2.6 新增编辑表单字段

| 字段 | 组件建议 | 是否必填 | 说明 |
|---|---|---|---|
| roomTypeName | `el-input` | 是 | 房型名称 |
| roomTypeCode | `el-input` | 是 | 房型编码 |
| roomImages | 多图上传 | 是 | 房型图片 |
| bedType | `el-select` | 是 | 床型字典 |
| peopleLimit | `el-input-number` | 是 | 可住人数 |
| area | `el-input` | 否 | 面积 |
| floorDesc | `el-input` | 否 | 楼层描述 |
| windowType | `el-select` | 否 | 窗型字典 |
| breakfastCount | `el-input-number` | 否 | 早餐数 |
| extraBedFlag | `el-radio-group` | 否 | 是否可加床 |
| description | `el-input type=textarea` | 否 | 房型描述 |
| basePrice | `el-input-number` | 否 | 默认价格 |
| baseStock | `el-input-number` | 否 | 默认库存 |
| configStatus | `el-radio-group` | 是 | 启用停用 |
| bookableFlag | `el-radio-group` | 是 | 是否可预订 |
| sortNum | `el-input-number` | 否 | 排序 |
| remark | `el-input type=textarea` | 否 | 备注 |
| facilityIds | 复选框组 | 否 | 设施绑定 |

### 4.2.7 交互规则

- 新增与编辑共用弹窗
- 编辑时需带出已绑定设施列表
- 删除前需弹出确认框
- 对已有关联经营数据或订单的房型，删除按钮置灰或后端返回不可删除提示
- 上架前先调用后端完整性校验，也可以由更新接口统一校验

---

## 4.3 库存价格管理页

页面文件建议：[`yimamerchant-ui/src/views/hotel/inventory/index.vue`](yimamerchant-ui/src/views/hotel/inventory/index.vue)

### 4.3.1 页面定位

一期先做房型经营列表页，以默认价格和默认库存为核心。

### 4.3.2 页面结构

- 查询区域
- 批量操作区
- 列表表格
- 单行快速编辑弹窗 可选

### 4.3.3 查询条件

- 房型名称
- 上架状态
- 是否可预订

### 4.3.4 列表字段

- 房型名称
- 销售价
- 划线价
- 默认库存
- 已售数量 先预留，可默认显示 0 或从订单统计
- 剩余可售 先由默认库存减已售计算
- 是否可预订
- 上架状态
- 更新时间

### 4.3.5 页面操作

- 批量设置价格
- 批量设置库存
- 单行修改价格
- 单行修改库存
- 上架
- 下架
- 开启 / 关闭预订

### 4.3.6 交互规则

- 支持多选房型后批量调整价格库存
- 批量提交前需校验正数与金额精度
- 上架前进行完整性校验
- 下架无需完整性校验

---

## 4.4 酒店退款页

页面文件建议：[`yimamerchant-ui/src/views/hotel/refund/index.vue`](yimamerchant-ui/src/views/hotel/refund/index.vue)

### 4.4.1 页面定位

列表页 + 详情弹窗，用于商家处理退款申请。

### 4.4.2 查询条件

- 订单编号
- 退款单号
- 房型名称
- 退款状态
- 申请时间范围
- 入住日期范围

### 4.4.3 列表字段

- 退款单号
- 订单编号
- 入住人
- 联系电话
- 房型名称
- 入住日期
- 离店日期
- 订单金额
- 申请退款金额
- 退款状态
- 申请时间
- 处理时间
- 操作

### 4.4.4 详情弹窗字段

- 订单编号
- 退款单号
- 房型名称
- 入住日期
- 离店日期
- 支付金额
- 申请退款金额
- 退款原因
- 取消规则说明
- 审核备注

### 4.4.5 操作按钮

- 查看详情
- 同意退款
- 拒绝退款

### 4.4.6 交互规则

- 仅待商家处理状态显示同意和拒绝按钮
- 拒绝退款时审核备注必填
- 同意退款时可选填写审核备注
- 审核成功后刷新列表并关闭详情弹窗

---

## 5. 前端 API 文件设计

建议按若依风格封装请求。

## 5.1 酒店信息接口文件

文件：[`yimamerchant-ui/src/api/hotel/info.js`](yimamerchant-ui/src/api/hotel/info.js)

建议方法：

- `getHotelInfo()`
- `saveHotelInfo(data)`
- `updateHotelStatus(data)` 可选

## 5.2 房型接口文件

文件：[`yimamerchant-ui/src/api/hotel/roomType.js`](yimamerchant-ui/src/api/hotel/roomType.js)

建议方法：

- `listRoomType(query)`
- `getRoomType(id)`
- `addRoomType(data)`
- `updateRoomType(data)`
- `removeRoomType(id)`
- `changeRoomTypeConfigStatus(id, configStatus)`
- `changeRoomTypeSaleStatus(id, saleStatus)`

## 5.3 设施接口文件

文件：[`yimamerchant-ui/src/api/hotel/facility.js`](yimamerchant-ui/src/api/hotel/facility.js)

建议方法：

- `listFacility(query)`
- `listRoomTypeFacilities(roomTypeId)`
- `saveRoomTypeFacilities(data)`

## 5.4 库存价格接口文件

文件：[`yimamerchant-ui/src/api/hotel/inventory.js`](yimamerchant-ui/src/api/hotel/inventory.js)

建议方法：

- `listInventory(query)`
- `updateBasePrice(data)`
- `updateBaseStock(data)`
- `batchUpdateInventory(data)`
- `changeBookableFlag(data)`
- `changeSaleStatus(data)`

## 5.5 退款接口文件

文件：[`yimamerchant-ui/src/api/hotel/refund.js`](yimamerchant-ui/src/api/hotel/refund.js)

建议方法：

- `listRefund(query)`
- `getRefundDetail(id)`
- `approveRefund(data)`
- `rejectRefund(data)`

---

## 6. 后端接口设计

统一前缀建议为：

- `/hotel/info`
- `/hotel/roomType`
- `/hotel/facility`
- `/hotel/inventory`
- `/hotel/refund`

返回格式遵循若依标准：

- 列表：`TableDataInfo`
- 明细 / 操作：`AjaxResult`

---

## 6.1 酒店信息接口

控制器建议：`HotelInfoController`

### 6.1.1 查询当前商家酒店信息

- 请求方式：`GET`
- 请求路径：`/hotel/info/getInfo`
- 权限：`hotel:info:query`

响应示例：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "id": 1,
    "merchantId": 1001,
    "hotelName": "云栖酒店",
    "hotelCover": "...",
    "hotelImages": "...",
    "phone": "13800000000",
    "provinceCode": "110000",
    "cityCode": "110100",
    "districtCode": "110101",
    "address": "某街道88号",
    "checkInTime": "14:00",
    "checkOutTime": "12:00",
    "intro": "...",
    "bookingNotice": "...",
    "cancelRule": "...",
    "invoiceDesc": "...",
    "parkingDesc": "...",
    "status": "1"
  }
}
```

### 6.1.2 保存酒店信息

- 请求方式：`POST`
- 请求路径：`/hotel/info/save`
- 权限：`hotel:info:edit`

请求体字段：

- `hotelName`
- `hotelCover`
- `hotelImages`
- `phone`
- `provinceCode`
- `cityCode`
- `districtCode`
- `address`
- `longitude`
- `latitude`
- `checkInTime`
- `checkOutTime`
- `intro`
- `bookingNotice`
- `cancelRule`
- `invoiceDesc`
- `parkingDesc`
- `status`

### 6.1.3 实现建议

- 当前商家有酒店则执行更新
- 当前商家无酒店则执行新增
- 不暴露商家侧按 ID 任意查询能力

---

## 6.2 房型管理接口

控制器建议：`HotelRoomTypeController`

### 6.2.1 房型分页列表

- 请求方式：`GET`
- 路径：`/hotel/roomType/list`
- 权限：`hotel:roomType:list`

请求参数：

- `roomTypeName`
- `configStatus`
- `saleStatus`
- `pageNum`
- `pageSize`

### 6.2.2 查询房型详情

- 请求方式：`GET`
- 路径：`/hotel/roomType/{id}`
- 权限：`hotel:roomType:detail`

响应需包含：

- 房型基础信息
- 已绑定设施 ID 集合

### 6.2.3 新增房型

- 请求方式：`POST`
- 路径：`/hotel/roomType`
- 权限：`hotel:roomType:add`

### 6.2.4 修改房型

- 请求方式：`PUT`
- 路径：`/hotel/roomType`
- 权限：`hotel:roomType:edit`

### 6.2.5 删除房型

- 请求方式：`DELETE`
- 路径：`/hotel/roomType/{id}`
- 权限：`hotel:roomType:remove`

### 6.2.6 修改房型配置状态

- 请求方式：`PUT`
- 路径：`/hotel/roomType/changeConfigStatus`
- 权限：`hotel:roomType:enable`

请求体：

```json
{
  "id": 1,
  "configStatus": "1"
}
```

### 6.2.7 房型上下架

- 请求方式：`PUT`
- 路径：`/hotel/roomType/changeSaleStatus`
- 权限：`hotel:roomType:putOn` 或 `hotel:roomType:putOff`

请求体：

```json
{
  "id": 1,
  "saleStatus": "1"
}
```

### 6.2.8 后端校验建议

- 酒店不存在时不允许新增房型
- 房型名称同一酒店下唯一
- 房型编码同一酒店下唯一
- 上架前校验图片、价格、库存、配置状态
- 已产生订单或经营数据的房型禁止物理删除

---

## 6.3 设施接口

控制器建议：`HotelFacilityController`

### 6.3.1 设施列表

- 请求方式：`GET`
- 路径：`/hotel/facility/list`
- 权限：`hotel:facility:list`

请求参数：

- `facilityType`
- `status`

### 6.3.2 查询房型设施

- 请求方式：`GET`
- 路径：`/hotel/facility/roomType/{roomTypeId}`
- 权限：`hotel:facility:list`

响应建议：

- 设施清单
- 当前房型已选设施 ID 列表

### 6.3.3 保存房型设施

- 请求方式：`POST`
- 路径：`/hotel/facility/saveRoomTypeFacilities`
- 权限：`hotel:facility:bind`

请求体示例：

```json
{
  "roomTypeId": 1,
  "facilityIds": [1, 2, 5, 8]
}
```

### 6.3.4 实现建议

- 采用先删后插方式维护房型设施关联
- 保存前校验房型是否属于当前商家酒店

---

## 6.4 库存价格管理接口

控制器建议：`HotelInventoryController`

一期实际上主要操作 [`hotel_room_type`](sql/hotel_room_type.sql) 中的默认价格、默认库存、预订开关与上架状态；
二期再正式启用 [`hotel_room_inventory`](sql/hotel_room_inventory.sql) 进行按日房态管理。

### 6.4.1 经营列表

- 请求方式：`GET`
- 路径：`/hotel/inventory/list`
- 权限：`hotel:inventory:list`

请求参数：

- `roomTypeName`
- `saleStatus`
- `bookableFlag`
- `pageNum`
- `pageSize`

### 6.4.2 修改默认价格

- 请求方式：`PUT`
- 路径：`/hotel/inventory/updateBasePrice`
- 权限：`hotel:inventory:edit`

请求体示例：

```json
{
  "id": 1,
  "basePrice": 299.00
}
```

### 6.4.3 修改默认库存

- 请求方式：`PUT`
- 路径：`/hotel/inventory/updateBaseStock`
- 权限：`hotel:inventory:edit`

请求体示例：

```json
{
  "id": 1,
  "baseStock": 20
}
```

### 6.4.4 批量修改价格库存

- 请求方式：`PUT`
- 路径：`/hotel/inventory/batchUpdate`
- 权限：`hotel:inventory:batchEdit`

请求体示例：

```json
{
  "ids": [1, 2, 3],
  "basePrice": 299.00,
  "baseStock": 15
}
```

### 6.4.5 修改是否可预订

- 请求方式：`PUT`
- 路径：`/hotel/inventory/changeBookableFlag`
- 权限：`hotel:inventory:edit`

### 6.4.6 修改上架状态

- 请求方式：`PUT`
- 路径：`/hotel/inventory/changeSaleStatus`
- 权限：`hotel:roomType:putOn` / `hotel:roomType:putOff`

### 6.4.7 业务规则

- 默认价格必须大于 0
- 默认库存必须大于等于 0
- 上架时必须满足配置完整条件
- 下架时直接更新状态
- 批量更新时需校验所有房型归属当前商家

---

## 6.5 酒店退款接口

控制器建议：`HotelRefundController`

### 6.5.1 退款分页列表

- 请求方式：`GET`
- 路径：`/hotel/refund/list`
- 权限：`hotel:refund:list`

请求参数：

- `orderNo`
- `refundNo`
- `roomTypeName`
- `refundStatus`
- `beginApplyTime`
- `endApplyTime`
- `beginCheckInDate`
- `endCheckInDate`

### 6.5.2 退款详情

- 请求方式：`GET`
- 路径：`/hotel/refund/{id}`
- 权限：`hotel:refund:detail`

### 6.5.3 同意退款

- 请求方式：`PUT`
- 路径：`/hotel/refund/approve`
- 权限：`hotel:refund:approve`

请求体示例：

```json
{
  "id": 1,
  "auditRemark": "符合取消规则，同意退款"
}
```

### 6.5.4 拒绝退款

- 请求方式：`PUT`
- 路径：`/hotel/refund/reject`
- 权限：`hotel:refund:reject`

请求体示例：

```json
{
  "id": 1,
  "auditRemark": "超过免费取消时间，不予退款"
}
```

### 6.5.5 后端处理规则

- 仅待商家处理状态允许审核
- 同意退款：状态更新为商家已同意
- 拒绝退款：状态更新为商家已拒绝
- 同意或拒绝都必须记录审核人、审核时间、备注
- 拒绝时备注必填

---

## 7. 数据库设计

## 7.1 命名规范

建议使用以下命名风格：

- 表名前缀统一使用 `hotel_`
- 主键统一 `id`
- 删除标记统一 `del_flag`
- 创建更新字段沿用若依标准字段
- 状态字段统一使用 `char(1)` 或 `varchar(10)`，与字典保持一致

---

## 7.2 酒店表

建议表名：`hotel_info`

### 7.2.1 建表建议

```sql
CREATE TABLE `hotel_info` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `merchant_id` bigint NOT NULL COMMENT '商家编号',
  `hotel_name` varchar(50) NOT NULL COMMENT '酒店名称',
  `hotel_cover` varchar(255) DEFAULT NULL COMMENT '封面图',
  `hotel_images` text COMMENT '轮播图集合，逗号分隔或JSON数组',
  `phone` varchar(20) NOT NULL COMMENT '联系电话',
  `province_code` varchar(20) DEFAULT NULL COMMENT '省编码',
  `city_code` varchar(20) DEFAULT NULL COMMENT '市编码',
  `district_code` varchar(20) DEFAULT NULL COMMENT '区编码',
  `address` varchar(255) NOT NULL COMMENT '详细地址',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  `check_in_time` varchar(10) NOT NULL COMMENT '入住时间',
  `check_out_time` varchar(10) NOT NULL COMMENT '离店时间',
  `intro` text COMMENT '酒店简介',
  `booking_notice` text COMMENT '预订须知',
  `cancel_rule` text COMMENT '取消规则',
  `invoice_desc` text COMMENT '开票说明',
  `parking_desc` text COMMENT '停车说明',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '酒店状态',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店信息表';
```

---

## 7.3 房型表

建议表名：`hotel_room_type`

### 7.3.1 建表建议

```sql
CREATE TABLE `hotel_room_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hotel_id` bigint NOT NULL COMMENT '酒店编号',
  `merchant_id` bigint NOT NULL COMMENT '商家编号',
  `room_type_name` varchar(50) NOT NULL COMMENT '房型名称',
  `room_type_code` varchar(50) NOT NULL COMMENT '房型编码',
  `room_images` text COMMENT '房型图片集合',
  `bed_type` char(1) NOT NULL COMMENT '床型',
  `people_limit` int NOT NULL DEFAULT 1 COMMENT '可住人数',
  `area` varchar(30) DEFAULT NULL COMMENT '面积说明',
  `floor_desc` varchar(50) DEFAULT NULL COMMENT '楼层描述',
  `window_type` char(1) DEFAULT NULL COMMENT '窗型',
  `breakfast_count` int DEFAULT 0 COMMENT '早餐数量',
  `extra_bed_flag` char(1) DEFAULT 'N' COMMENT '是否可加床',
  `description` text COMMENT '房型描述',
  `base_price` decimal(10,2) DEFAULT NULL COMMENT '默认价格',
  `base_stock` int DEFAULT 0 COMMENT '默认库存',
  `config_status` char(1) NOT NULL DEFAULT '1' COMMENT '配置状态',
  `sale_status` char(1) NOT NULL DEFAULT '0' COMMENT '上架状态',
  `bookable_flag` char(1) NOT NULL DEFAULT 'Y' COMMENT '是否可预订',
  `sort_num` int DEFAULT 0 COMMENT '排序号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_hotel_room_type_name` (`hotel_id`, `room_type_name`),
  UNIQUE KEY `uk_hotel_room_type_code` (`hotel_id`, `room_type_code`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店房型表';
```

---

## 7.4 设施表

建议表名：`hotel_facility`

### 7.4.1 建表建议

```sql
CREATE TABLE `hotel_facility` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `facility_name` varchar(50) NOT NULL COMMENT '设施名称',
  `facility_type` char(1) NOT NULL COMMENT '设施分类',
  `status` char(1) NOT NULL DEFAULT '1' COMMENT '状态',
  `sort_num` int DEFAULT 0 COMMENT '排序号',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店设施表';
```

---

## 7.5 房型设施关联表

建议表名：`hotel_room_type_facility_rel`

### 7.5.1 建表建议

```sql
CREATE TABLE `hotel_room_type_facility_rel` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hotel_id` bigint NOT NULL COMMENT '酒店编号',
  `room_type_id` bigint NOT NULL COMMENT '房型编号',
  `facility_id` bigint NOT NULL COMMENT '设施编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_room_type_facility` (`room_type_id`, `facility_id`),
  KEY `idx_hotel_id` (`hotel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房型设施关联表';
```

---

## 7.6 房态库存价格表 预留

建议表名：`hotel_room_inventory`

### 7.6.1 建表建议

```sql
CREATE TABLE `hotel_room_inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `hotel_id` bigint NOT NULL COMMENT '酒店编号',
  `merchant_id` bigint NOT NULL COMMENT '商家编号',
  `room_type_id` bigint NOT NULL COMMENT '房型编号',
  `biz_date` date NOT NULL COMMENT '业务日期',
  `stock_num` int NOT NULL DEFAULT 0 COMMENT '库存数量',
  `sold_num` int NOT NULL DEFAULT 0 COMMENT '已售数量',
  `available_num` int NOT NULL DEFAULT 0 COMMENT '剩余可售',
  `sale_price` decimal(10,2) DEFAULT NULL COMMENT '销售价',
  `market_price` decimal(10,2) DEFAULT NULL COMMENT '划线价',
  `sale_status` char(1) NOT NULL DEFAULT '1' COMMENT '可售状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_room_type_biz_date` (`room_type_id`, `biz_date`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='房态库存价格表';
```

说明：

- 一期建表可选
- 二期启用时直接接入日历房态能力

---

## 7.7 退款单表

建议表名：`hotel_refund_order`

### 7.7.1 建表建议

```sql
CREATE TABLE `hotel_refund_order` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `refund_no` varchar(50) NOT NULL COMMENT '退款单号',
  `order_id` bigint NOT NULL COMMENT '订单编号',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `hotel_id` bigint NOT NULL COMMENT '酒店编号',
  `merchant_id` bigint NOT NULL COMMENT '商家编号',
  `room_type_id` bigint NOT NULL COMMENT '房型编号',
  `guest_name` varchar(50) DEFAULT NULL COMMENT '入住人',
  `guest_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `check_in_date` date DEFAULT NULL COMMENT '入住日期',
  `check_out_date` date DEFAULT NULL COMMENT '离店日期',
  `order_amount` decimal(10,2) DEFAULT NULL COMMENT '订单金额',
  `apply_refund_amount` decimal(10,2) NOT NULL COMMENT '申请退款金额',
  `refund_reason` varchar(500) DEFAULT NULL COMMENT '退款原因',
  `refund_status` char(1) NOT NULL COMMENT '退款状态',
  `audit_remark` varchar(500) DEFAULT NULL COMMENT '审核备注',
  `audit_by` varchar(64) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_refund_no` (`refund_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_hotel_id` (`hotel_id`),
  KEY `idx_merchant_id` (`merchant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='酒店退款单表';
```

---

## 8. 字典设计

建议在若依字典中新增以下类型：

| 字典类型 | 字典名称 | 建议值 |
|---|---|---|
| `hotel_status` | 酒店状态 | 0草稿 1启用 2停业 |
| `hotel_room_config_status` | 房型配置状态 | 0停用 1启用 |
| `hotel_room_sale_status` | 房型上架状态 | 0下架 1上架 |
| `hotel_bookable_flag` | 是否可预订 | Y是 N否 |
| `hotel_bed_type` | 床型 | 1大床 2双床 3圆床 4榻榻米 9其他 |
| `hotel_window_type` | 窗型 | 1有窗 2无窗 3部分有窗 |
| `hotel_facility_type` | 设施分类 | 1房间设施 2卫浴设施 3公共设施 4服务设施 |
| `hotel_refund_status` | 退款状态 | 1待商家处理 2商家已同意 3商家已拒绝 4退款处理中 5退款成功 6退款失败 |

---

## 9. 权限与菜单配置设计

## 9.1 菜单结构建议

最终在若依菜单中建议配置：

```text
酒店管理
├─ 酒店信息
├─ 房型管理
├─ 库存价格管理
└─ 酒店退款
```

## 9.2 权限标识建议

### 酒店信息

- `hotel:info:query`
- `hotel:info:edit`

### 房型管理

- `hotel:roomType:list`
- `hotel:roomType:add`
- `hotel:roomType:edit`
- `hotel:roomType:remove`
- `hotel:roomType:detail`
- `hotel:roomType:enable`
- `hotel:roomType:putOn`
- `hotel:roomType:putOff`

### 设施配置

- `hotel:facility:list`
- `hotel:facility:bind`

### 库存价格管理

- `hotel:inventory:list`
- `hotel:inventory:edit`
- `hotel:inventory:batchEdit`

### 酒店退款

- `hotel:refund:list`
- `hotel:refund:detail`
- `hotel:refund:approve`
- `hotel:refund:reject`

---

## 10. 核心后端实现规则

## 10.1 商家数据隔离

商家侧所有查询和操作必须基于当前登录用户的商家身份进行数据隔离。

建议做法：

- 从登录态中获取当前用户 ID
- 通过商家绑定关系解析 `merchant_id`
- 查询房型、酒店、退款时统一带入 `merchant_id`
- 控制器层不信任前端传入的商家编号

## 10.2 房型上架校验

建议封装独立方法，例如：`validateRoomTypeCanPutOn()`。

校验项：

- 酒店存在且启用
- 房型配置状态为启用
- 房型图片不为空
- 默认价格大于 0
- 默认库存大于 0

## 10.3 删除规则

房型删除建议遵循：

- 无订单、无经营数据时允许逻辑删除
- 有订单或库存经营记录时只允许停用，不允许删除

## 10.4 退款审核幂等控制

退款审核操作建议加状态前置校验：

- 当前状态必须为待商家处理
- 审核完成后再次提交应提示状态已变化

---

## 11. 开发顺序建议

## 11.1 第一步

先落数据库与字典：

- 建酒店表
- 建房型表
- 建设施表
- 建房型设施关联表
- 建退款表
- 初始化字典数据

## 11.2 第二步

落后端基础能力：

- 酒店信息接口
- 房型管理接口
- 设施绑定接口
- 库存价格管理接口
- 退款列表与审核接口

## 11.3 第三步

落前端页面：

- 酒店信息页
- 房型管理页
- 库存价格管理页
- 酒店退款页

## 11.4 第四步

补菜单、权限、日志：

- 新增菜单数据
- 绑定角色权限
- 接入操作日志
- 联调验证权限点

---

## 12. 第一阶段可直接落地的文件建议

如果下一步直接开始编码，建议优先创建以下文件：

### 后端

- `yimamerchant-system/src/main/java/com/yimamerchant/system/domain/hotel/HotelInfo.java`
- `yimamerchant-system/src/main/java/com/yimamerchant/system/domain/hotel/HotelRoomType.java`
- `yimamerchant-system/src/main/java/com/yimamerchant/system/domain/hotel/HotelFacility.java`
- `yimamerchant-system/src/main/java/com/yimamerchant/system/domain/hotel/HotelRefundOrder.java`
- `yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelInfoController.java`
- `yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelRoomTypeController.java`
- `yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelInventoryController.java`
- `yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelRefundController.java`

### Mapper XML

- `yimamerchant-system/src/main/resources/mapper/hotel/HotelInfoMapper.xml`
- `yimamerchant-system/src/main/resources/mapper/hotel/HotelRoomTypeMapper.xml`
- `yimamerchant-system/src/main/resources/mapper/hotel/HotelFacilityMapper.xml`
- `yimamerchant-system/src/main/resources/mapper/hotel/HotelRefundOrderMapper.xml`

### 前端

- `yimamerchant-ui/src/api/hotel/info.js`
- `yimamerchant-ui/src/api/hotel/roomType.js`
- `yimamerchant-ui/src/api/hotel/inventory.js`
- `yimamerchant-ui/src/api/hotel/refund.js`
- `yimamerchant-ui/src/views/hotel/info/index.vue`
- `yimamerchant-ui/src/views/hotel/roomType/index.vue`
- `yimamerchant-ui/src/views/hotel/inventory/index.vue`
- `yimamerchant-ui/src/views/hotel/refund/index.vue`

### SQL

- `sql/hotel_module.sql`

---

## 13. 结论

该技术设计文档已经把 [`plans/hotel-management-plan.md`](plans/hotel-management-plan.md) 中的业务方案转换为前后端与数据库可执行方案，适合作为后续正式开发的技术基线。

如按当前节奏继续推进，下一步最合理的是：

- 先输出 [`sql/hotel_module.sql`](sql/hotel_module.sql) 的建表与初始化字典脚本
- 再落后端 domain、mapper、service、controller 骨架
- 最后补前端 API 与页面骨架
