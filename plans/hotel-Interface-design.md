# 酒店商家端后端接口设计文档

## 1. 文档说明

本文档基于 [`plans/hotel-management-plan.md`](plans/hotel-management-plan.md) 的最新业务方案重构，输出面向前后端联调、接口开发、测试用例编写的一期接口设计。

本次接口设计重点与管理方案保持一致：

- 退款能力从独立菜单调整为酒店订单内的售后能力
- 酒店配置补充佣金模式、专属 BD、BD 电话、退款规则
- 新增更换所属 BD 接口，服务于平台交接场景
- 保持单店商家模型，但预留多店、按日房态、支付联动退款扩展空间

适用范围：

- 酒店信息与经营配置维护
- 房型管理
- 房型设施绑定
- 默认库存与默认价格管理
- 酒店订单查询与退款处理
- 更换所属 BD

技术约定：

- 后端框架：Spring Boot + Spring Security + MyBatis
- 返回格式遵循若依标准 `AjaxResult` 与 `TableDataInfo`
- 商家侧接口默认通过当前登录上下文识别 `merchantId` 与 `hotelId`
- 平台侧更换所属 BD 接口通过平台角色权限控制
- 时间格式统一为 `yyyy-MM-dd HH:mm:ss`
- 日期格式统一为 `yyyy-MM-dd`
- 金额单位统一为元，保留两位小数

---

## 2. 通用规范

## 2.1 请求头

所有管理端接口均需携带登录令牌：

```http
Authorization: Bearer {token}
Content-Type: application/json
```

## 2.2 统一返回结构

### 2.2.1 普通对象返回

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

### 2.2.2 列表分页返回

```json
{
  "code": 200,
  "msg": "查询成功",
  "rows": [],
  "total": 0
}
```

### 2.2.3 失败返回

```json
{
  "code": 500,
  "msg": "业务处理失败"
}
```

## 2.3 通用业务约束

- 商家侧接口默认按当前登录商家隔离数据
- 平台侧接口默认按平台角色权限控制，不信任前端传入的权限身份
- 酒店信息接口默认仅维护当前商家唯一酒店
- 房型接口操作前需校验房型属于当前商家与当前酒店
- 上架前需校验房型基础资料完整、价格库存合法、状态允许上架
- 订单退款处理仅允许在待处理状态执行审核
- 实际退款金额不得大于可退款金额
- 更换所属 BD 仅建议平台角色调用

## 2.4 状态字典约定

建议状态字典与 [`plans/hotel-management-plan.md`](plans/hotel-management-plan.md) 保持一致：

- `hotel_status`：`0` 草稿、`1` 启用、`2` 停业、`3` 待审核
- `hotel_commission_mode`：`1` 底价模式、`2` 卖价模式
- `hotel_refund_rule_type`：`1` 限时退、`2` 不可退、`3` 任意退
- `hotel_room_config_status`：`0` 未启用、`1` 已启用
- `hotel_room_sale_status`：`0` 已下架、`1` 已上架
- `hotel_bookable_flag`：`N` 否、`Y` 是
- `hotel_order_status`：`1` 待支付、`2` 已支付待入住、`3` 入住中、`4` 已完成、`5` 已取消
- `hotel_after_sale_status`：`0` 无售后、`1` 待退款处理、`2` 部分退款中、`3` 已退款、`4` 退款驳回
- `hotel_refund_status`：`1` 待商家处理、`2` 商家已同意、`3` 商家已拒绝、`4` 退款处理中、`5` 退款成功、`6` 退款失败
- `hotel_bed_type`：`1` 大床、`2` 双床、`3` 圆床、`4` 榻榻米、`9` 其他
- `hotel_window_type`：`1` 有窗、`2` 无窗、`3` 部分有窗
- `hotel_facility_type`：`1` 房间设施、`2` 卫浴设施、`3` 公共设施、`4` 服务设施

---

## 3. 模块总览

| 模块 | 前缀 | 控制器建议 | 说明 |
|---|---|---|---|
| 酒店信息与经营配置 | `/hotel/info` | `HotelInfoController` | 当前商家酒店资料、佣金、BD、退款规则维护 |
| 房型管理 | `/hotel/roomType` | `HotelRoomTypeController` | 房型增删改查、上下架、完整性校验 |
| 设施管理 | `/hotel/facility` | `HotelFacilityController` | 设施列表与房型设施绑定 |
| 库存价格管理 | `/hotel/inventory` | `HotelInventoryController` | 默认库存、默认价格、批量修改 |
| 酒店订单 | `/hotel/order` | `HotelOrderController` | 订单列表、详情、退款记录、退款处理 |
| 更换所属 BD | `/hotel/bdChange` | `HotelBdChangeController` | 平台侧酒店归属调整与记录查询 |

---

## 4. 酒店信息与经营配置接口

接口前缀：`/hotel/info`

对应核心表建议：`hotel_info`

### 4.1 查询当前商家酒店信息

- 请求方式：`GET`
- 请求路径：`/hotel/info/getInfo`
- 权限标识：`hotel:info:query`

#### 请求参数

无。

#### 响应字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | long | 酒店主键 |
| merchantId | long | 商家编号 |
| hotelName | string | 酒店名称 |
| hotelCover | string | 酒店封面图 |
| hotelImages | array<string> | 酒店轮播图 |
| phone | string | 联系电话 |
| provinceCode | string | 省编码 |
| cityCode | string | 市编码 |
| districtCode | string | 区编码 |
| address | string | 详细地址 |
| longitude | number | 经度 |
| latitude | number | 纬度 |
| checkInTime | string | 入住时间 |
| checkOutTime | string | 离店时间 |
| intro | string | 酒店简介 |
| bookingNotice | string | 预订须知 |
| invoiceDesc | string | 开票说明 |
| parkingDesc | string | 停车说明 |
| status | string | 酒店状态 |
| commissionMode | string | 佣金模式 |
| commissionValue | number | 佣金值 |
| bdUserId | long | 专属 BD 编号 |
| bdName | string | 专属 BD 名称 |
| bdPhone | string | BD 电话 |
| refundRuleType | string | 退款规则类型 |
| refundRuleDesc | string | 退款规则说明 |
| refundDeadlineDesc | string | 限时退款条件说明 |
| createTime | string | 创建时间 |
| updateTime | string | 更新时间 |

### 4.2 保存酒店信息与经营配置

- 请求方式：`POST`
- 请求路径：`/hotel/info/save`
- 权限标识：`hotel:info:edit`

#### 请求体

```json
{
  "hotelName": "逸马轻居酒店",
  "hotelCover": "https://cdn.test.com/hotel/cover.jpg",
  "hotelImages": [
    "https://cdn.test.com/hotel/1.jpg",
    "https://cdn.test.com/hotel/2.jpg"
  ],
  "phone": "13800138000",
  "provinceCode": "310000",
  "cityCode": "310100",
  "districtCode": "310101",
  "address": "南京东路100号",
  "longitude": 121.473701,
  "latitude": 31.230416,
  "checkInTime": "14:00",
  "checkOutTime": "12:00",
  "intro": "靠近地铁站，出行方便",
  "bookingNotice": "入住请携带身份证件",
  "invoiceDesc": "支持开具住宿发票",
  "parkingDesc": "提供免费停车位",
  "status": "1",
  "commissionMode": "1",
  "commissionValue": 30.00,
  "bdUserId": 9001,
  "bdName": "张BD",
  "bdPhone": "13900001111",
  "refundRuleType": "1",
  "refundRuleDesc": "入住前一天18点前可退",
  "refundDeadlineDesc": "入住前一天18:00前可全额退"
}
```

#### 请求字段说明

| 字段 | 必填 | 类型 | 说明 |
|---|---|---|---|
| hotelName | 是 | string | 酒店名称 |
| hotelCover | 是 | string | 酒店封面图 |
| hotelImages | 否 | array<string> | 轮播图列表 |
| phone | 是 | string | 联系电话 |
| provinceCode | 是 | string | 省编码 |
| cityCode | 是 | string | 市编码 |
| districtCode | 是 | string | 区编码 |
| address | 是 | string | 详细地址 |
| longitude | 否 | number | 经度 |
| latitude | 否 | number | 纬度 |
| checkInTime | 是 | string | 入住时间 |
| checkOutTime | 是 | string | 离店时间 |
| intro | 否 | string | 酒店简介 |
| bookingNotice | 否 | string | 预订须知 |
| invoiceDesc | 否 | string | 开票说明 |
| parkingDesc | 否 | string | 停车说明 |
| status | 是 | string | 酒店状态 |
| commissionMode | 是 | string | 佣金模式 |
| commissionValue | 是 | number | 佣金值 |
| bdUserId | 是 | long | 专属 BD 编号 |
| bdName | 否 | string | BD 名称快照，后端可自动回填 |
| bdPhone | 是 | string | BD 电话 |
| refundRuleType | 是 | string | 退款规则类型 |
| refundRuleDesc | 否 | string | 退款规则说明 |
| refundDeadlineDesc | 条件必填 | string | 限时退时必填 |

#### 校验规则

- 当前商家仅允许存在一条酒店信息
- `hotelName`、`phone`、`address`、`checkInTime`、`checkOutTime` 为必填
- `commissionMode`、`commissionValue`、`bdUserId`、`bdPhone`、`refundRuleType` 为必填
- `commissionValue` 必须大于等于 0，最多两位小数
- 当 `refundRuleType=1` 时，`refundDeadlineDesc` 必填
- `status`、`commissionMode`、`refundRuleType` 必须在对应字典范围内

### 4.3 修改酒店状态

- 请求方式：`PUT`
- 请求路径：`/hotel/info/changeStatus`
- 权限标识：`hotel:info:edit`

#### 请求体

```json
{
  "id": 1,
  "status": "2"
}
```

### 4.4 查询酒店经营配置摘要

- 请求方式：`GET`
- 请求路径：`/hotel/info/configSummary`
- 权限标识：`hotel:info:query`

#### 说明

用于前端在酒店配置页顶部展示当前佣金模式、退款规则、BD 信息摘要。

---

## 5. 房型管理接口

接口前缀：`/hotel/roomType`

对应核心表建议：`hotel_room_type`、`hotel_room_type_facility_rel`

### 5.1 分页查询房型列表

- 请求方式：`GET`
- 请求路径：`/hotel/roomType/list`
- 权限标识：`hotel:roomType:list`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| roomTypeName | 否 | string | 房型名称 |
| configStatus | 否 | string | 配置状态 |
| saleStatus | 否 | string | 上架状态 |
| bookableFlag | 否 | string | 是否可预订 |

#### 列表行字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | long | 房型主键 |
| roomTypeName | string | 房型名称 |
| roomTypeCode | string | 房型编码 |
| roomImages | array<string> | 房型图片 |
| firstImage | string | 首图 |
| bedType | string | 床型 |
| bedTypeLabel | string | 床型名称 |
| peopleLimit | int | 可住人数 |
| basePrice | number | 默认价格 |
| marketPrice | number | 划线价 |
| baseStock | int | 默认库存 |
| soldNum | int | 已售数量 |
| availableNum | int | 剩余可售 |
| configStatus | string | 配置状态 |
| saleStatus | string | 上架状态 |
| bookableFlag | string | 是否可预订 |
| sortNum | int | 排序号 |
| updateTime | string | 更新时间 |

### 5.2 查询房型详情

- 请求方式：`GET`
- 请求路径：`/hotel/roomType/{id}`
- 权限标识：`hotel:roomType:detail`

#### 响应字段补充

| 字段 | 类型 | 说明 |
|---|---|---|
| facilityIds | array<long> | 已绑定设施编号 |
| description | string | 房型描述 |
| floorDesc | string | 楼层描述 |
| area | string | 面积说明 |
| windowType | string | 窗型 |
| breakfastCount | int | 早餐数 |
| extraBedFlag | string | 是否可加床 |
| remark | string | 备注 |

### 5.3 新增房型

- 请求方式：`POST`
- 请求路径：`/hotel/roomType`
- 权限标识：`hotel:roomType:add`

#### 请求体

```json
{
  "roomTypeName": "豪华大床房",
  "roomTypeCode": "DLX-DB-01",
  "roomImages": [
    "https://cdn.test.com/room/101-1.jpg",
    "https://cdn.test.com/room/101-2.jpg"
  ],
  "bedType": "1",
  "peopleLimit": 2,
  "area": "30㎡",
  "floorDesc": "10-15层",
  "windowType": "1",
  "breakfastCount": 2,
  "extraBedFlag": "N",
  "description": "含独立卫浴与景观窗",
  "basePrice": 299.00,
  "marketPrice": 399.00,
  "baseStock": 20,
  "configStatus": "1",
  "saleStatus": "0",
  "bookableFlag": "Y",
  "sortNum": 1,
  "remark": "主推房型",
  "facilityIds": [1, 2, 3, 4]
}
```

#### 核心校验规则

- 当前商家必须已存在酒店信息
- `roomTypeName`、`roomTypeCode`、`roomImages`、`bedType`、`peopleLimit`、`configStatus`、`bookableFlag` 必填
- 同一酒店下房型名称不可重复
- 同一酒店下房型编码不可重复
- `peopleLimit`、`breakfastCount`、`baseStock`、`sortNum` 不得小于 0
- `basePrice`、`marketPrice` 若传入则需大于等于 0，且最多两位小数
- `facilityIds` 若传入，需校验设施存在且状态启用

### 5.4 修改房型

- 请求方式：`PUT`
- 请求路径：`/hotel/roomType`
- 权限标识：`hotel:roomType:edit`

#### 请求说明

请求体与新增接口一致，但需携带 `id`。

### 5.5 删除房型

- 请求方式：`DELETE`
- 请求路径：`/hotel/roomType/{id}`
- 权限标识：`hotel:roomType:remove`

#### 删除规则

- 仅允许删除当前商家名下房型
- 若已有关联订单、退款单、库存明细或其他经营数据，禁止删除
- 删除建议采用逻辑删除
- 删除时同步清理房型设施关联表数据

### 5.6 修改房型配置状态

- 请求方式：`PUT`
- 请求路径：`/hotel/roomType/changeConfigStatus`
- 权限标识：`hotel:roomType:enable`

#### 请求体

```json
{
  "id": 101,
  "configStatus": "0"
}
```

### 5.7 修改房型上架状态

- 请求方式：`PUT`
- 请求路径：`/hotel/roomType/changeSaleStatus`
- 权限标识：`hotel:roomType:putOn` 或 `hotel:roomType:putOff`

#### 请求体

```json
{
  "id": 101,
  "saleStatus": "1"
}
```

### 5.8 房型完整性校验接口

- 请求方式：`GET`
- 请求路径：`/hotel/roomType/checkComplete/{id}`
- 权限标识：`hotel:roomType:detail`

#### 响应示例

```json
{
  "code": 200,
  "msg": "校验完成",
  "data": {
    "pass": false,
    "message": "默认价格不能为空",
    "missingFields": [
      "basePrice"
    ]
  }
}
```

---

## 6. 设施接口

接口前缀：`/hotel/facility`

对应核心表建议：`hotel_facility`、`hotel_room_type_facility_rel`

### 6.1 查询设施列表

- 请求方式：`GET`
- 请求路径：`/hotel/facility/list`
- 权限标识：`hotel:facility:list`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| facilityType | 否 | string | 设施分类 |
| status | 否 | string | 状态，默认查启用 |

### 6.2 查询房型已绑定设施

- 请求方式：`GET`
- 请求路径：`/hotel/facility/roomType/{roomTypeId}`
- 权限标识：`hotel:facility:list`

### 6.3 保存房型设施绑定

- 请求方式：`POST`
- 请求路径：`/hotel/facility/saveRoomTypeFacilities`
- 权限标识：`hotel:facility:bind`

#### 请求体

```json
{
  "roomTypeId": 101,
  "facilityIds": [1, 2, 3, 4]
}
```

#### 业务规则

- 先删除该房型已有绑定，再批量插入新绑定
- 需校验 `roomTypeId` 属于当前商家
- 需校验设施编号合法

---

## 7. 库存价格管理接口

接口前缀：`/hotel/inventory`

一期核心仍使用 `hotel_room_type` 中 `base_price`、`base_stock` 实现默认经营管理，若需要展示划线价可补充 `market_price` 字段，`hotel_room_inventory` 作为二期按日房态预留表。

### 7.1 分页查询库存价格列表

- 请求方式：`GET`
- 请求路径：`/hotel/inventory/list`
- 权限标识：`hotel:inventory:list`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| roomTypeName | 否 | string | 房型名称 |
| saleStatus | 否 | string | 上架状态 |
| bookableFlag | 否 | string | 是否可预订 |

### 7.2 修改单个房型默认价格

- 请求方式：`PUT`
- 请求路径：`/hotel/inventory/updateBasePrice`
- 权限标识：`hotel:inventory:edit`

#### 请求体

```json
{
  "roomTypeId": 101,
  "basePrice": 299.00,
  "marketPrice": 399.00
}
```

### 7.3 修改单个房型默认库存

- 请求方式：`PUT`
- 请求路径：`/hotel/inventory/updateBaseStock`
- 权限标识：`hotel:inventory:edit`

#### 请求体

```json
{
  "roomTypeId": 101,
  "baseStock": 20
}
```

### 7.4 批量修改价格库存

- 请求方式：`PUT`
- 请求路径：`/hotel/inventory/batchUpdate`
- 权限标识：`hotel:inventory:batchEdit`

#### 请求体

```json
{
  "roomTypeIds": [101, 102, 103],
  "basePrice": 319.00,
  "marketPrice": 419.00,
  "baseStock": 30
}
```

### 7.5 修改可预订状态

- 请求方式：`PUT`
- 请求路径：`/hotel/inventory/changeBookableFlag`
- 权限标识：`hotel:inventory:edit`

#### 请求体

```json
{
  "roomTypeId": 101,
  "bookableFlag": "N"
}
```

### 7.6 业务规则

- `basePrice` 必须大于等于 0
- `marketPrice` 若传值必须大于等于 0
- `baseStock` 不得小于 0
- 若系统已接入订单，更新库存时需校验新库存与已售数量关系
- 上下架状态建议统一调用房型接口，避免重复维护

---

## 8. 酒店订单接口

接口前缀：`/hotel/order`

对应核心表建议：`hotel_order`、`hotel_refund_order`

### 8.1 分页查询订单列表

- 请求方式：`GET`
- 请求路径：`/hotel/order/list`
- 权限标识：`hotel:order:list`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| orderNo | 否 | string | 订单编号 |
| orderStatus | 否 | string | 订单状态 |
| afterSaleStatus | 否 | string | 售后状态 |
| contactPhone | 否 | string | 联系电话 |
| roomTypeName | 否 | string | 房型名称 |
| beginOrderTime | 否 | string | 下单开始时间 |
| endOrderTime | 否 | string | 下单结束时间 |
| beginCheckInDate | 否 | string | 入住开始日期 |
| endCheckInDate | 否 | string | 入住结束日期 |

#### 列表行字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | long | 订单主键 |
| orderNo | string | 订单号 |
| roomTypeId | long | 房型编号 |
| roomTypeName | string | 房型名称 |
| contactName | string | 入住人或联系人 |
| contactPhone | string | 联系电话 |
| checkInDate | string | 入住日期 |
| checkOutDate | string | 离店日期 |
| orderAmount | number | 订单金额 |
| payAmount | number | 实付金额 |
| refundedAmount | number | 已退款金额 |
| refundableAmount | number | 可退款金额 |
| orderStatus | string | 订单状态 |
| afterSaleStatus | string | 售后状态 |
| createTime | string | 下单时间 |
| payTime | string | 支付时间 |

### 8.2 查询订单详情

- 请求方式：`GET`
- 请求路径：`/hotel/order/{id}`
- 权限标识：`hotel:order:detail`

#### 响应字段补充

| 字段 | 类型 | 说明 |
|---|---|---|
| hotelRefundRuleType | string | 当前酒店退款规则类型 |
| hotelRefundRuleDesc | string | 当前酒店退款规则说明 |
| refundRecords | array<object> | 退款记录列表 |

### 8.3 查询订单退款记录

- 请求方式：`GET`
- 请求路径：`/hotel/order/refundRecords/{orderId}`
- 权限标识：`hotel:order:refundView`

#### 响应字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | long | 退款单主键 |
| refundNo | string | 退款单号 |
| applyRefundAmount | number | 申请退款金额 |
| approvedRefundAmount | number | 审核通过退款金额 |
| refundReason | string | 退款原因 |
| refundStatus | string | 退款状态 |
| auditRemark | string | 审核备注 |
| auditBy | string | 审核人 |
| auditTime | string | 审核时间 |
| createTime | string | 申请时间 |

### 8.4 查询退款详情

- 请求方式：`GET`
- 请求路径：`/hotel/order/refund/{refundId}`
- 权限标识：`hotel:order:refundView`

### 8.5 同意退款

- 请求方式：`POST`
- 请求路径：`/hotel/order/refund/approve`
- 权限标识：`hotel:order:refundApprove`

#### 请求体

```json
{
  "refundId": 501,
  "approvedRefundAmount": 199.00,
  "auditRemark": "结合限时退规则，同意部分退款"
}
```

#### 业务规则

- 仅允许处理 `refundStatus=1` 的记录
- `approvedRefundAmount` 必须大于 0
- `approvedRefundAmount` 不得大于申请退款金额
- `approvedRefundAmount` 不得大于订单剩余可退款金额
- 审核成功后更新退款状态为 `2`
- 同步更新订单已退款金额、可退款金额、售后状态
- 记录 `auditBy`、`auditTime`

### 8.6 拒绝退款

- 请求方式：`POST`
- 请求路径：`/hotel/order/refund/reject`
- 权限标识：`hotel:order:refundReject`

#### 请求体

```json
{
  "refundId": 501,
  "auditRemark": "已超过可退款时限，拒绝退款"
}
```

#### 业务规则

- 仅允许处理 `refundStatus=1` 的记录
- `auditRemark` 必填
- 审核成功后更新退款状态为 `3`
- 同步更新订单售后状态为退款驳回或维持既有规则
- 记录 `auditBy`、`auditTime`

---

## 9. 更换所属 BD 接口

接口前缀：`/hotel/bdChange`

对应核心表建议：`hotel_bd_change_record`、`hotel_info`

### 9.1 分页查询酒店归属列表

- 请求方式：`GET`
- 请求路径：`/hotel/bdChange/list`
- 权限标识：`hotel:bdChange:list`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| hotelName | 否 | string | 酒店名称 |
| currentBdName | 否 | string | 当前 BD 名称 |
| merchantId | 否 | long | 商家编号 |

#### 列表行字段

| 字段 | 类型 | 说明 |
|---|---|---|
| hotelId | long | 酒店编号 |
| merchantId | long | 商家编号 |
| hotelName | string | 酒店名称 |
| currentBdUserId | long | 当前 BD 编号 |
| currentBdName | string | 当前 BD 名称 |
| currentBdPhone | string | 当前 BD 电话 |
| updateTime | string | 最近更新时间 |

### 9.2 查询可选 BD 列表

- 请求方式：`GET`
- 请求路径：`/hotel/bdChange/bdOptions`
- 权限标识：`hotel:bdChange:list`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| keyword | 否 | string | 姓名或手机号模糊查询 |

### 9.3 单个更换所属 BD

- 请求方式：`POST`
- 请求路径：`/hotel/bdChange/change`
- 权限标识：`hotel:bdChange:update`

#### 请求体

```json
{
  "hotelId": 1,
  "newBdUserId": 9101,
  "newBdPhone": "13999990001",
  "changeReason": "原BD离职，执行交接",
  "effectiveTime": "2026-03-20 12:00:00"
}
```

#### 业务规则

- 必须校验酒店存在
- 必须校验新 BD 存在且可用
- 成功后同步更新酒店表中的 `bdUserId`、`bdName`、`bdPhone`
- 必须写入 BD 变更记录

### 9.4 批量更换所属 BD

- 请求方式：`POST`
- 请求路径：`/hotel/bdChange/batchChange`
- 权限标识：`hotel:bdChange:batchUpdate`

#### 请求体

```json
{
  "hotelIds": [1, 2, 3],
  "newBdUserId": 9101,
  "newBdPhone": "13999990001",
  "changeReason": "区域交接批量调整",
  "effectiveTime": "2026-03-20 12:00:00"
}
```

### 9.5 查询 BD 变更记录

- 请求方式：`GET`
- 请求路径：`/hotel/bdChange/record/list`
- 权限标识：`hotel:bdChange:record`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| hotelName | 否 | string | 酒店名称 |
| oldBdName | 否 | string | 原 BD 名称 |
| newBdName | 否 | string | 新 BD 名称 |
| beginOperateTime | 否 | string | 操作开始时间 |
| endOperateTime | 否 | string | 操作结束时间 |

---

## 10. 推荐 DTO / VO 设计

建议在业务模块中补充如下对象：

### 10.1 酒店信息与经营配置

- `HotelInfoForm`
- `HotelInfoVO`
- `HotelConfigSummaryVO`

### 10.2 房型管理

- `HotelRoomTypeQuery`
- `HotelRoomTypeForm`
- `HotelRoomTypeVO`
- `HotelRoomTypeDetailVO`
- `HotelRoomTypeStatusForm`
- `HotelRoomTypeCheckVO`

### 10.3 设施绑定

- `HotelFacilityQuery`
- `HotelRoomTypeFacilityForm`

### 10.4 库存价格

- `HotelInventoryQuery`
- `HotelInventoryBatchForm`
- `HotelInventoryPriceForm`
- `HotelInventoryStockForm`
- `HotelInventoryBookableForm`
- `HotelInventoryVO`

### 10.5 酒店订单与退款

- `HotelOrderQuery`
- `HotelOrderVO`
- `HotelOrderDetailVO`
- `HotelRefundRecordVO`
- `HotelRefundApproveForm`
- `HotelRefundRejectForm`

### 10.6 更换所属 BD

- `HotelBdChangeQuery`
- `HotelBdChangeForm`
- `HotelBdBatchChangeForm`
- `HotelBdOptionVO`
- `HotelBdChangeRecordVO`

---

## 11. 错误码与提示语建议

| 场景 | 建议提示 |
|---|---|
| 当前商家未创建酒店 | 当前商家尚未维护酒店信息，请先完善酒店资料 |
| 房型名称重复 | 同一酒店下房型名称不能重复 |
| 房型编码重复 | 同一酒店下房型编码不能重复 |
| 房型不存在 | 房型不存在或无权操作 |
| 房型不可上架 | 房型基础信息不完整，无法上架 |
| 库存非法 | 默认库存不能小于 0 |
| 金额非法 | 金额格式不正确或小于 0 |
| 退款状态非法 | 当前退款单状态已变更，请刷新后重试 |
| 可退款金额不足 | 当前订单剩余可退款金额不足 |
| 拒绝退款未填备注 | 拒绝退款时审核备注不能为空 |
| 删除受限 | 当前房型已有关联经营数据，不能删除 |
| BD 不存在 | 目标 BD 不存在或不可用 |
| BD 更换失败 | 酒店所属 BD 更新失败，请稍后重试 |

---

## 12. 联调建议

- `roomImages`、`hotelImages` 建议接口层统一使用数组，持久化时在服务层转换为 JSON 字符串
- 字典数据可在页面初始化时通过若依字典接口统一获取
- 退款详情中的酒店退款规则建议优先展示订单申请时快照，当前酒店规则作为辅助参考
- 上下架能力建议统一由房型管理模块对外暴露，库存页复用相同服务能力
- 更换所属 BD 完成后，酒店信息页需重新拉取摘要信息，避免前端本地缓存不一致
- 同意退款、拒绝退款、更换所属 BD 等关键动作建议接入操作日志

---

## 13. 一期与二期边界说明

### 13.1 一期落地

- 当前商家唯一酒店信息与经营配置维护
- 房型基础档案管理
- 房型设施绑定
- 默认价格、默认库存维护
- 房型上下架与可预订控制
- 酒店订单列表与详情
- 订单内退款处理
- 更换所属 BD 与变更记录

### 13.2 二期预留

- 基于 `hotel_room_inventory` 的按日房态库存与价格管理
- 多店铺管理
- 与订单中心联动扣减库存
- 退款自动打款与平台复核
- 基于佣金模式的自动结算
- BD 交接审批流与历史轨迹增强
