# 酒店商家端后端接口文档

## 1. 文档说明

本文档基于 [`plans/hotel-technical-design.md`](plans/hotel-technical-design.md) 与 [`sql/hotel.sql`](sql/hotel.sql) 整理酒店商家端一期后端接口设计，输出面向前后端联调、接口开发、测试用例编写的接口文档。

适用范围：

- 酒店信息维护
- 房型管理
- 房型设施绑定
- 默认库存与默认价格管理
- 房型上下架与可预订状态控制
- 酒店退款列表与审核处理

技术约定：

- 后端框架：Spring Boot + Spring Security + MyBatis
- 返回格式遵循若依标准 [`AjaxResult`](yimamerchant-common/src/main/java/com/yimamerchant/common/core/domain/AjaxResult.java) 与 [`TableDataInfo`](yimamerchant-common/src/main/java/com/yimamerchant/common/core/page/TableDataInfo.java)
- 接口默认通过当前登录商家上下文识别 `merchantId`
- 一期采用单店模型，但核心数据保留 `merchant_id`、`hotel_id`
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

- 除设施基础数据接口外，其余接口均基于当前登录商家隔离数据
- 酒店信息接口默认仅维护当前商家唯一酒店，对应 [`hotel_info`](sql/hotel.sql)
- 房型接口操作前需校验房型属于当前商家与当前酒店
- 上架前需校验房型基础资料完整、价格库存合法、状态允许上架
- 删除房型时若存在订单、退款单、库存明细等关联经营数据，应禁止删除
- 退款审核仅允许在“待商家处理”状态执行一次性审核

## 2.4 状态字典约定

状态取值建议与 [`sql/hotel.sql`](sql/hotel.sql) 初始化字典保持一致：

- `hotel_status`：`0` 草稿、`1` 启用、`2` 停业
- `hotel_room_config_status`：`0` 停用、`1` 启用
- `hotel_room_sale_status`：`0` 下架、`1` 上架
- `hotel_bookable_flag`：`N` 否、`Y` 是
- `hotel_refund_status`：`1` 待商家处理、`2` 商家已同意、`3` 商家已拒绝
- `hotel_room_bed_type`：`1` 大床、`2` 双床、`3` 圆床、`4` 榻榻米
- `hotel_room_window_type`：`1` 有窗、`2` 无窗、`3` 部分有窗
- `hotel_facility_type`：`1` 房间设施、`2` 卫浴设施、`3` 公共设施、`4` 服务设施

---

## 3. 模块总览

| 模块 | 前缀 | 控制器建议 | 说明 |
|---|---|---|---|
| 酒店信息 | `/hotel/info` | `HotelInfoController` | 当前商家酒店资料维护 |
| 房型管理 | `/hotel/roomType` | `HotelRoomTypeController` | 房型增删改查、上下架 |
| 设施管理 | `/hotel/facility` | `HotelFacilityController` | 设施列表与房型设施绑定 |
| 库存价格 | `/hotel/inventory` | `HotelInventoryController` | 默认库存、默认价格、批量修改 |
| 退款管理 | `/hotel/refund` | `HotelRefundController` | 退款列表、详情、审核 |

---

## 4. 酒店信息接口

接口前缀：`/hotel/info`

对应核心表：[`hotel_info`](sql/hotel.sql)

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
| checkInTime | string | 入住时间，格式 `HH:mm` |
| checkOutTime | string | 离店时间，格式 `HH:mm` |
| intro | string | 酒店简介 |
| bookingNotice | string | 预订须知 |
| cancelRule | string | 取消规则 |
| invoiceDesc | string | 开票说明 |
| parkingDesc | string | 停车说明 |
| status | string | 酒店状态 |
| remark | string | 备注 |
| createTime | string | 创建时间 |
| updateTime | string | 更新时间 |

#### 响应示例

```json
{
  "code": 200,
  "msg": "查询成功",
  "data": {
    "id": 1,
    "merchantId": 10001,
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
    "cancelRule": "入住前一天18:00前可免费取消",
    "invoiceDesc": "支持开具住宿发票",
    "parkingDesc": "提供免费停车位",
    "status": "1",
    "remark": ""
  }
}
```

### 4.2 新增或保存酒店信息

- 请求方式：`POST`
- 请求路径：`/hotel/info/save`
- 权限标识：首次新增建议 `hotel:info:add`，已存在编辑建议 `hotel:info:edit`

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
  "cancelRule": "入住前一天18:00前可免费取消",
  "invoiceDesc": "支持开具住宿发票",
  "parkingDesc": "提供免费停车位",
  "status": "1",
  "remark": ""
}
```

#### 请求字段说明

| 字段 | 必填 | 类型 | 说明 |
|---|---|---|---|
| hotelName | 是 | string | 酒店名称，最多 50 字 |
| hotelCover | 是 | string | 酒店封面图 |
| hotelImages | 否 | array<string> | 轮播图列表，建议最多 9 张 |
| phone | 是 | string | 联系电话 |
| provinceCode | 是 | string | 省编码 |
| cityCode | 是 | string | 市编码 |
| districtCode | 是 | string | 区编码 |
| address | 是 | string | 详细地址，最多 255 字 |
| longitude | 否 | number | 经度 |
| latitude | 否 | number | 纬度 |
| checkInTime | 是 | string | 入住时间 |
| checkOutTime | 是 | string | 离店时间 |
| intro | 否 | string | 酒店简介 |
| bookingNotice | 否 | string | 预订须知 |
| cancelRule | 否 | string | 取消规则 |
| invoiceDesc | 否 | string | 开票说明 |
| parkingDesc | 否 | string | 停车说明 |
| status | 是 | string | 酒店状态 |
| remark | 否 | string | 备注 |

#### 校验规则

- 当前商家仅允许存在一条酒店信息
- `hotelName`、`hotelCover`、`phone`、`provinceCode`、`cityCode`、`districtCode`、`address`、`checkInTime`、`checkOutTime` 不能为空
- `phone` 需通过手机号或座机格式校验
- 经纬度若传值需满足数值格式要求
- `status` 必须在 `hotel_status` 字典范围内

#### 响应示例

```json
{
  "code": 200,
  "msg": "保存成功"
}
```

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

#### 说明

用于快速变更酒店状态，例如启用、停业。

---

## 5. 房型管理接口

接口前缀：`/hotel/roomType`

对应核心表：[`hotel_room_type`](sql/hotel.sql)、[`hotel_room_type_facility_rel`](sql/hotel.sql)

### 5.1 分页查询房型列表

- 请求方式：`GET`
- 请求路径：`/hotel/roomType/list`
- 权限标识：`hotel:roomType:query`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| roomTypeName | 否 | string | 房型名称 |
| configStatus | 否 | string | 配置状态 |
| saleStatus | 否 | string | 上架状态 |

#### 列表行字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | long | 房型主键 |
| hotelId | long | 酒店编号 |
| merchantId | long | 商家编号 |
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
| availableNum | int | 剩余可售，建议服务端计算 |
| configStatus | string | 配置状态 |
| saleStatus | string | 上架状态 |
| bookableFlag | string | 是否可预订 |
| sortNum | int | 排序号 |
| updateTime | string | 更新时间 |

#### 响应示例

```json
{
  "code": 200,
  "msg": "查询成功",
  "rows": [
    {
      "id": 101,
      "hotelId": 1,
      "merchantId": 10001,
      "roomTypeName": "豪华大床房",
      "roomTypeCode": "DLX-DB-01",
      "roomImages": [
        "https://cdn.test.com/room/101-1.jpg"
      ],
      "firstImage": "https://cdn.test.com/room/101-1.jpg",
      "bedType": "1",
      "bedTypeLabel": "大床",
      "peopleLimit": 2,
      "basePrice": 299.00,
      "marketPrice": 399.00,
      "baseStock": 20,
      "soldNum": 3,
      "availableNum": 17,
      "configStatus": "1",
      "saleStatus": "1",
      "bookableFlag": "Y",
      "sortNum": 1,
      "updateTime": "2026-03-18 21:00:00"
    }
  ],
  "total": 1
}
```

### 5.2 查询房型详情

- 请求方式：`GET`
- 请求路径：`/hotel/roomType/{id}`
- 权限标识：`hotel:roomType:query`

#### 响应字段补充

除基础字段外，建议额外返回：

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
- 同一酒店下 `room_type_name` 不可重复，对应 [`uk_hotel_room_type_name`](sql/hotel.sql)
- 同一酒店下 `room_type_code` 不可重复，对应 [`uk_hotel_room_type_code`](sql/hotel.sql)
- `peopleLimit`、`breakfastCount`、`baseStock`、`sortNum` 不得小于 0
- `basePrice`、`marketPrice` 若传入则需大于等于 0，且最多两位小数
- `saleStatus` 初始建议默认为 `0`
- `facilityIds` 若传入，需校验设施存在且状态启用

#### 响应示例

```json
{
  "code": 200,
  "msg": "新增成功"
}
```

### 5.4 修改房型

- 请求方式：`PUT`
- 请求路径：`/hotel/roomType`
- 权限标识：`hotel:roomType:edit`

#### 请求说明

请求体与新增接口一致，但需携带 `id`。

```json
{
  "id": 101,
  "roomTypeName": "豪华大床房",
  "roomTypeCode": "DLX-DB-01",
  "roomImages": [
    "https://cdn.test.com/room/101-1.jpg"
  ],
  "bedType": "1",
  "peopleLimit": 2,
  "basePrice": 329.00,
  "marketPrice": 429.00,
  "baseStock": 18,
  "configStatus": "1",
  "saleStatus": "1",
  "bookableFlag": "Y",
  "facilityIds": [1, 2, 3]
}
```

### 5.5 删除房型

- 请求方式：`DELETE`
- 请求路径：`/hotel/roomType/{id}`
- 权限标识：`hotel:roomType:remove`

#### 删除规则

- 仅允许删除当前商家名下房型
- 若已有关联订单、退款单、房态库存明细或其他经营数据，禁止删除
- 删除建议采用逻辑删除，更新 `del_flag=2`
- 删除时同步清理房型设施关联表数据

### 5.6 修改房型配置状态

- 请求方式：`PUT`
- 请求路径：`/hotel/roomType/changeConfigStatus`
- 权限标识：`hotel:roomType:edit`

#### 请求体

```json
{
  "id": 101,
  "configStatus": "0"
}
```

#### 业务规则

- 停用后可禁止继续上架与预订
- 若房型已上架，停用时建议自动下架或拒绝停用，二选一并在实现中固定

### 5.7 修改房型上架状态

- 请求方式：`PUT`
- 请求路径：`/hotel/roomType/changeSaleStatus`
- 权限标识：`hotel:roomType:changeStatus`

#### 请求体

```json
{
  "id": 101,
  "saleStatus": "1"
}
```

#### 上架校验建议

- 房型必须属于当前商家
- 房型 `configStatus=1`
- `bookableFlag=Y`
- `basePrice` 不为空且大于 0
- `baseStock` 不小于 0
- 房型图片、床型、可住人数等核心信息完整

### 5.8 房型完整性校验接口（建议新增）

- 请求方式：`GET`
- 请求路径：`/hotel/roomType/checkComplete/{id}`
- 权限标识：`hotel:roomType:query`

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

对应核心表：[`hotel_facility`](sql/hotel.sql)、[`hotel_room_type_facility_rel`](sql/hotel.sql)

### 6.1 查询设施列表

- 请求方式：`GET`
- 请求路径：`/hotel/facility/list`
- 权限标识：建议复用 `hotel:roomType:query`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| facilityType | 否 | string | 设施分类 |
| status | 否 | string | 状态，默认查启用 |

#### 响应示例

```json
{
  "code": 200,
  "msg": "查询成功",
  "data": [
    {
      "id": 1,
      "facilityName": "WiFi",
      "facilityType": "1",
      "facilityTypeLabel": "房间设施",
      "status": "1",
      "sortNum": 1
    }
  ]
}
```

### 6.2 查询房型已绑定设施

- 请求方式：`GET`
- 请求路径：`/hotel/facility/roomType/{roomTypeId}`
- 权限标识：`hotel:roomType:query`

#### 响应示例

```json
{
  "code": 200,
  "msg": "查询成功",
  "data": [1, 2, 3, 4]
}
```

### 6.3 保存房型设施绑定

- 请求方式：`POST`
- 请求路径：`/hotel/facility/saveRoomTypeFacilities`
- 权限标识：`hotel:roomType:edit`

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

一期核心使用 [`hotel_room_type`](sql/hotel.sql) 中 `base_price`、`market_price`、`base_stock`、`sold_num` 字段实现默认经营管理；[`hotel_room_inventory`](sql/hotel.sql) 作为二期按日房态预留表。

### 7.1 分页查询库存价格列表

- 请求方式：`GET`
- 请求路径：`/hotel/inventory/list`
- 权限标识：`hotel:inventory:query`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| roomTypeName | 否 | string | 房型名称 |
| saleStatus | 否 | string | 上架状态 |
| bookableFlag | 否 | string | 是否可预订 |

#### 响应行字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | long | 房型编号 |
| roomTypeName | string | 房型名称 |
| salePrice | number | 销售价，映射 `basePrice` |
| marketPrice | number | 划线价 |
| baseStock | int | 默认库存 |
| soldNum | int | 已售数量 |
| availableNum | int | 剩余可售 |
| saleStatus | string | 上架状态 |
| bookableFlag | string | 是否可预订 |
| updateTime | string | 更新时间 |

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

#### 校验规则

- `basePrice` 必须大于等于 0
- `marketPrice` 若传值必须大于等于 0
- 金额最多两位小数

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

#### 校验规则

- `baseStock` 不得小于 0
- 若系统已接入订单，需校验新库存不小于已售数量，或在服务端自动修正为 `availableNum = max(baseStock - soldNum, 0)`

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

#### 说明

支持部分字段批量更新：

- 仅更新价格
- 仅更新库存
- 同时更新价格与库存

### 7.5 修改可预订状态

- 请求方式：`PUT`
- 请求路径：`/hotel/inventory/changeBookableFlag`
- 权限标识：`hotel:inventory:changeStatus`

#### 请求体

```json
{
  "roomTypeId": 101,
  "bookableFlag": "N"
}
```

### 7.6 修改上下架状态

- 请求方式：`PUT`
- 请求路径：`/hotel/inventory/changeSaleStatus`
- 权限标识：`hotel:inventory:changeStatus`

#### 请求体

```json
{
  "roomTypeId": 101,
  "saleStatus": "1"
}
```

#### 业务说明

该接口与 [`/hotel/roomType/changeSaleStatus`](plans/hotel-Interface-design.md) 存在能力重叠，建议二选一：

- 方案 A：房型管理维护上下架，库存价格页仅调用房型状态接口
- 方案 B：库存页保留独立接口，但内部复用同一服务方法

为了避免状态维护分散，推荐采用方案 A。

---

## 8. 退款管理接口

接口前缀：`/hotel/refund`

对应核心表：[`hotel_refund_order`](sql/hotel.sql)

### 8.1 分页查询退款列表

- 请求方式：`GET`
- 请求路径：`/hotel/refund/list`
- 权限标识：`hotel:refund:query`

#### 查询参数

| 参数 | 必填 | 类型 | 说明 |
|---|---|---|---|
| pageNum | 否 | int | 页码 |
| pageSize | 否 | int | 每页条数 |
| orderNo | 否 | string | 订单号 |
| refundNo | 否 | string | 退款单号 |
| roomTypeName | 否 | string | 房型名称 |
| refundStatus | 否 | string | 退款状态 |
| beginApplyTime | 否 | string | 申请开始时间 |
| endApplyTime | 否 | string | 申请结束时间 |
| beginCheckInDate | 否 | string | 入住开始日期 |
| endCheckInDate | 否 | string | 入住结束日期 |

#### 列表行字段

| 字段 | 类型 | 说明 |
|---|---|---|
| id | long | 退款单主键 |
| refundNo | string | 退款单号 |
| orderId | long | 订单编号 |
| orderNo | string | 订单号 |
| roomTypeId | long | 房型编号 |
| roomTypeName | string | 房型名称 |
| guestName | string | 入住人 |
| guestPhone | string | 联系电话 |
| checkInDate | string | 入住日期 |
| checkOutDate | string | 离店日期 |
| orderAmount | number | 订单金额 |
| applyRefundAmount | number | 申请退款金额 |
| refundStatus | string | 退款状态 |
| refundStatusLabel | string | 退款状态名称 |
| createTime | string | 申请时间 |
| auditTime | string | 处理时间 |

### 8.2 查询退款详情

- 请求方式：`GET`
- 请求路径：`/hotel/refund/{id}`
- 权限标识：`hotel:refund:detail`

#### 响应字段补充

| 字段 | 类型 | 说明 |
|---|---|---|
| refundReason | string | 退款原因 |
| auditRemark | string | 审核备注 |
| auditBy | string | 审核人 |
| cancelRule | string | 取消规则说明，可从酒店或订单快照补充 |

#### 响应示例

```json
{
  "code": 200,
  "msg": "查询成功",
  "data": {
    "id": 501,
    "refundNo": "RF202603180001",
    "orderId": 90001,
    "orderNo": "HT202603180001",
    "roomTypeId": 101,
    "roomTypeName": "豪华大床房",
    "guestName": "张三",
    "guestPhone": "13800138000",
    "checkInDate": "2026-03-20",
    "checkOutDate": "2026-03-21",
    "orderAmount": 299.00,
    "applyRefundAmount": 299.00,
    "refundReason": "行程变更",
    "refundStatus": "1",
    "auditRemark": "",
    "cancelRule": "入住前一天18:00前可免费取消",
    "createTime": "2026-03-18 10:00:00"
  }
}
```

### 8.3 同意退款

- 请求方式：`POST`
- 请求路径：`/hotel/refund/approve`
- 权限标识：`hotel:refund:audit`

#### 请求体

```json
{
  "id": 501,
  "auditRemark": "符合取消规则，同意退款"
}
```

#### 业务规则

- 仅允许处理 `refundStatus=1` 的记录
- 审核成功后更新状态为 `2`
- 记录 `auditBy`、`auditTime`
- 一期先完成商家审核状态流转，不做自动打款

### 8.4 拒绝退款

- 请求方式：`POST`
- 请求路径：`/hotel/refund/reject`
- 权限标识：`hotel:refund:audit`

#### 请求体

```json
{
  "id": 501,
  "auditRemark": "已超过可免费取消时间，拒绝退款"
}
```

#### 业务规则

- 仅允许处理 `refundStatus=1` 的记录
- `auditRemark` 必填
- 审核成功后更新状态为 `3`
- 记录 `auditBy`、`auditTime`

---

## 9. 推荐 DTO / VO 设计

建议在 [`yimamerchant-system/src/main/java/com/yimamerchant/system`](yimamerchant-system/src/main/java/com/yimamerchant/system) 下补充如下对象：

### 9.1 酒店信息

- `HotelInfoForm`
- `HotelInfoVO`

### 9.2 房型管理

- `HotelRoomTypeQuery`
- `HotelRoomTypeForm`
- `HotelRoomTypeVO`
- `HotelRoomTypeDetailVO`
- `HotelRoomTypeStatusForm`

### 9.3 设施绑定

- `HotelFacilityQuery`
- `HotelRoomTypeFacilityForm`

### 9.4 库存价格

- `HotelInventoryQuery`
- `HotelInventoryBatchForm`
- `HotelInventoryPriceForm`
- `HotelInventoryStockForm`
- `HotelInventoryStatusForm`
- `HotelInventoryVO`

### 9.5 退款管理

- `HotelRefundQuery`
- `HotelRefundAuditForm`
- `HotelRefundVO`
- `HotelRefundDetailVO`

---

## 10. 错误码与提示语建议

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
| 拒绝退款未填备注 | 拒绝退款时审核备注不能为空 |
| 删除受限 | 当前房型已有关联经营数据，不能删除 |

---

## 11. 联调建议

- 前端房型管理页和库存价格页尽量复用相同状态变更服务，避免接口职责重叠
- `roomImages`、`hotelImages` 建议接口层统一使用数组，持久化时在服务层转换为 JSON 字符串
- 字典数据可在页面初始化时通过若依字典接口统一获取
- 退款详情中的 `cancelRule` 建议优先取订单下单时快照，避免酒店后续修改规则影响历史单据展示
- 删除、上下架、审核等关键动作均建议记录操作日志，便于商家端审计

---

## 12. 一期与二期边界说明

### 12.1 一期落地

- 当前商家唯一酒店信息维护
- 房型基础档案管理
- 房型设施绑定
- 默认价格、默认库存维护
- 房型上下架与可预订控制
- 退款审核流转

### 12.2 二期预留

- 基于 [`hotel_room_inventory`](sql/hotel.sql) 的按日房态库存与价格管理
- 多店铺管理
- 与订单中心联动扣减库存
- 退款自动打款与平台复核
- 渠道分销与价格日历同步
