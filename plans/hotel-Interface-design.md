# 酒店管理技术接口设计文档

## 1. 文档说明

本文档基于 [`plans/酒店.md`](plans/酒店.md) 的业务规划内容，结合当前项目的若依前后端分离基础结构，输出酒店业务模块的技术接口设计方案。

默认技术前提如下：

- 前端：Vue2 + Element UI + 若依标准列表/表单/详情页模式
- 后端：Spring Boot + Spring Security + MyBatis
- 管理端控制器模块：[`yimamerchant-admin`](yimamerchant-admin)
- 业务领域模型、Service、Mapper、XML 所在模块：[`yimamerchant-system`](yimamerchant-system)
- 数据库脚本输出：[`sql/hotel.sql`](sql/hotel.sql)

本文档重点覆盖三部分内容：

1. 前端页面规划
2. 后端接口设计
3. 数据库表设计说明

---

## 2. 模块总览

### 2.1 菜单结构建议

```text
[酒店合作中心]
├─ 待签约酒店
├─ 合作酒店
├─ BD-酒店归属管理
└─ BD绩效看板（可选）

[酒店运营中心]
├─ 酒店信息
├─ 房型管理
└─ 库存价格管理

[交易与服务中心]
└─ 酒店订单

[财务结算中心]
└─ 账单管理

[系统与配置]
├─ 用户与角色管理（复用若依）
└─ 酒店业务参数配置
```

### 2.2 建议代码分层

#### 前端目录建议

- [`yimamerchant-ui/src/views/hotel/cooperate/pending/index.vue`](yimamerchant-ui/src/views/hotel/cooperate/pending/index.vue)
- [`yimamerchant-ui/src/views/hotel/cooperate/partner/index.vue`](yimamerchant-ui/src/views/hotel/cooperate/partner/index.vue)
- [`yimamerchant-ui/src/views/hotel/cooperate/partner/detail.vue`](yimamerchant-ui/src/views/hotel/cooperate/partner/detail.vue)
- [`yimamerchant-ui/src/views/hotel/cooperate/bind/index.vue`](yimamerchant-ui/src/views/hotel/cooperate/bind/index.vue)
- [`yimamerchant-ui/src/views/hotel/operate/info/index.vue`](yimamerchant-ui/src/views/hotel/operate/info/index.vue)
- [`yimamerchant-ui/src/views/hotel/operate/info/edit.vue`](yimamerchant-ui/src/views/hotel/operate/info/edit.vue)
- [`yimamerchant-ui/src/views/hotel/operate/roomType/index.vue`](yimamerchant-ui/src/views/hotel/operate/roomType/index.vue)
- [`yimamerchant-ui/src/views/hotel/operate/price/index.vue`](yimamerchant-ui/src/views/hotel/operate/price/index.vue)
- [`yimamerchant-ui/src/views/hotel/service/order/index.vue`](yimamerchant-ui/src/views/hotel/service/order/index.vue)
- [`yimamerchant-ui/src/views/hotel/finance/bill/index.vue`](yimamerchant-ui/src/views/hotel/finance/bill/index.vue)
- [`yimamerchant-ui/src/views/hotel/finance/bill/detail.vue`](yimamerchant-ui/src/views/hotel/finance/bill/detail.vue)
- [`yimamerchant-ui/src/views/hotel/config/index.vue`](yimamerchant-ui/src/views/hotel/config/index.vue)

#### 前端 API 目录建议

- [`yimamerchant-ui/src/api/hotel/cooperate/pending.js`](yimamerchant-ui/src/api/hotel/cooperate/pending.js)
- [`yimamerchant-ui/src/api/hotel/cooperate/partner.js`](yimamerchant-ui/src/api/hotel/cooperate/partner.js)
- [`yimamerchant-ui/src/api/hotel/cooperate/bind.js`](yimamerchant-ui/src/api/hotel/cooperate/bind.js)
- [`yimamerchant-ui/src/api/hotel/operate/info.js`](yimamerchant-ui/src/api/hotel/operate/info.js)
- [`yimamerchant-ui/src/api/hotel/operate/roomType.js`](yimamerchant-ui/src/api/hotel/operate/roomType.js)
- [`yimamerchant-ui/src/api/hotel/operate/price.js`](yimamerchant-ui/src/api/hotel/operate/price.js)
- [`yimamerchant-ui/src/api/hotel/service/order.js`](yimamerchant-ui/src/api/hotel/service/order.js)
- [`yimamerchant-ui/src/api/hotel/finance/bill.js`](yimamerchant-ui/src/api/hotel/finance/bill.js)
- [`yimamerchant-ui/src/api/hotel/config.js`](yimamerchant-ui/src/api/hotel/config.js)

#### 后端包结构建议

控制器建议放在 [`yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel)。

业务代码建议放在 [`yimamerchant-system/src/main/java/com/yimamerchant/system/hotel`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel)。

推荐分层：

- controller
- domain
- dto
- vo
- mapper
- service
- service.impl

---

## 3. 前端页面规划

## 3.1 酒店合作中心

### 3.1.1 待签约酒店

#### 页面信息

- 菜单名称：待签约酒店
- 前端路由：`/hotel/cooperate/pending`
- 页面组件：[`yimamerchant-ui/src/views/hotel/cooperate/pending/index.vue`](yimamerchant-ui/src/views/hotel/cooperate/pending/index.vue)

#### 页面组成

1. 查询区
   - 酒店名称
   - 申请状态
   - 申请时间范围
2. 工具栏
   - 新增申请
   - 导出
   - 批量删除
3. 列表区
   - 酒店名称
   - 申请状态
   - 申请人
   - 申请时间
   - 最后操作时间
4. 操作区
   - 查看
   - 审核通过
   - 审核驳回
   - 删除
5. 详情弹窗/抽屉
   - 基础信息
   - 资质附件
   - 地理位置
   - 审核日志

#### 页面交互说明

- 列表页采用若依标准 [`listQuery()`](yimamerchant-ui/src/views/hotel/cooperate/pending/index.vue:1) + [`resetQuery()`](yimamerchant-ui/src/views/hotel/cooperate/pending/index.vue:1) 模式
- 新增、审核、查看建议使用弹窗或抽屉，减少页面切换
- 审核通过后刷新列表并提示已生成合作酒店档案

### 3.1.2 合作酒店

#### 页面信息

- 菜单名称：合作酒店
- 前端路由：`/hotel/cooperate/partner`
- 列表页：[`yimamerchant-ui/src/views/hotel/cooperate/partner/index.vue`](yimamerchant-ui/src/views/hotel/cooperate/partner/index.vue)
- 详情页：[`yimamerchant-ui/src/views/hotel/cooperate/partner/detail.vue`](yimamerchant-ui/src/views/hotel/cooperate/partner/detail.vue)

#### 页面组成

1. 查询区
   - 酒店名称
   - 合作状态
   - 签约时间范围
   - 所属BD
2. 数据卡片
   - 总合作酒店数
   - 正常合作数
   - 本月新增数
3. 列表区
   - 酒店ID
   - 酒店名称
   - 合作状态
   - 签约时间
   - 所属BD
   - 本月订单数
   - 本月销售额
4. 详情页 Tab
   - 基础信息
   - 合同管理
   - 佣金设置
   - 账号管理

#### 页面交互说明

- 列表页进入详情页使用跳转模式，适合承载多 Tab 配置
- 合作状态操作应增加二次确认
- 账号重置密码、发送通知建议使用独立弹窗

### 3.1.3 BD-酒店归属管理

#### 页面信息

- 菜单名称：BD-酒店归属管理
- 前端路由：`/hotel/cooperate/bind`
- 页面组件：[`yimamerchant-ui/src/views/hotel/cooperate/bind/index.vue`](yimamerchant-ui/src/views/hotel/cooperate/bind/index.vue)

#### 页面组成

1. 双向筛选区
   - BD姓名/工号
   - 酒店名称
   - 绑定状态
2. 工具栏
   - 单个绑定
   - 批量绑定
   - 单个解绑
   - 批量解绑
   - 一键交接
   - 查看历史
3. 列表区
   - BD姓名
   - BD工号
   - 酒店名称
   - 绑定时间
   - 绑定状态
4. 弹窗
   - BD选择器
   - 酒店选择器
   - 批量交接弹窗
   - 历史记录弹窗

#### 页面交互说明

- BD选择器应支持远程搜索和在职状态过滤
- 酒店选择器支持多选和展示当前绑定信息
- 一键交接需强提醒，并显示影响酒店数量

---

## 3.2 酒店运营中心

### 3.2.1 酒店信息

#### 页面信息

- 菜单名称：酒店信息
- 前端路由：`/hotel/operate/info`
- 列表页：[`yimamerchant-ui/src/views/hotel/operate/info/index.vue`](yimamerchant-ui/src/views/hotel/operate/info/index.vue)
- 编辑页：[`yimamerchant-ui/src/views/hotel/operate/info/edit.vue`](yimamerchant-ui/src/views/hotel/operate/info/edit.vue)

#### 页面组成

1. 列表页
   - 查询条件：城市、状态、标签
   - 表格字段：LOGO、酒店名称、城市、状态、最后更新时间
   - 操作：编辑、上架/下架、预览
2. 编辑页 Tab
   - 基础信息
   - 图文详情
   - 政策设置
   - 房型预览

#### 页面交互说明

- 编辑页建议使用页面式编辑，不建议弹窗承载大量内容
- 图文详情支持多图上传、拖拽排序、分类管理
- 预览按钮可跳转模拟C端展示视图或弹窗预览

### 3.2.2 房型管理

#### 页面信息

- 菜单名称：房型管理
- 前端路由：`/hotel/operate/roomType`
- 页面组件：[`yimamerchant-ui/src/views/hotel/operate/roomType/index.vue`](yimamerchant-ui/src/views/hotel/operate/roomType/index.vue)

#### 页面组成

1. 页面头部
   - 酒店下拉选择器
2. 查询区
   - 房型名称
   - 状态
3. 列表区
   - 房型名称
   - 床型
   - 入住人数
   - 面积
   - 窗户
   - 状态
   - 创建时间
4. 操作区
   - 新增
   - 编辑
   - 删除
   - 启用/停售
   - 排序
5. 编辑弹窗
   - 基础信息
   - 设施属性
   - 图文管理
   - 销售设置

#### 页面交互说明

- 必须先选择酒店后再加载房型数据
- 排序推荐结合 [`el-table`](yimamerchant-ui/src/views/hotel/operate/roomType/index.vue:1) + 拖拽插件实现
- 图文上传可复用若依上传组件

### 3.2.3 库存价格管理

#### 页面信息

- 菜单名称：库存价格管理
- 前端路由：`/hotel/operate/price`
- 页面组件：[`yimamerchant-ui/src/views/hotel/operate/price/index.vue`](yimamerchant-ui/src/views/hotel/operate/price/index.vue)

#### 页面组成

1. 双层筛选
   - 酒店选择
   - 房型选择（支持多选）
2. 日历工具栏
   - 月份切换
   - 批量设置
   - 批量关房
   - 批量复制
   - 恢复默认
3. 日历表格区
   - 日期
   - 星期
   - 价格字段（根据模式动态变化）
   - 库存
   - 退款规则
   - 操作
4. 单日编辑弹窗
5. 批量设置弹窗
6. 历史记录弹窗

#### 页面交互说明

- 该页面为核心运营页，建议采用自定义表格日历混合布局
- 佣金模式不同，列头动态切换
- 批量日期选择建议支持勾选与连续范围选择两种模式

---

## 3.3 交易与服务中心

### 3.3.1 酒店订单

#### 页面信息

- 菜单名称：酒店订单
- 前端路由：`/hotel/service/order`
- 页面组件：[`yimamerchant-ui/src/views/hotel/service/order/index.vue`](yimamerchant-ui/src/views/hotel/service/order/index.vue)

#### 页面组成

1. 高级查询区
   - 订单号
   - 酒店/房型
   - 下单时间范围
   - 入住日期范围
   - 订单状态
   - 用户ID
   - 手机号
   - 所属BD
2. 工具栏
   - 批量确认
   - 导出订单
   - 发送通知
3. 列表区
   - 订单号
   - 酒店
   - 房型
   - 入住/离店日期
   - 订单金额
   - 用户
   - 下单时间
   - 状态
4. 详情抽屉 Tab
   - 订单基本信息
   - 用户与入住信息
   - 酒店与房型信息
   - 操作日志
5. 操作弹窗
   - 取消订单
   - 添加备注
   - 售后处理

#### 页面交互说明

- 订单详情建议使用右侧抽屉承载，提高处理效率
- 列表操作按钮按状态动态控制显示
- 订单状态变更后应局部刷新或回填当前行数据

---

## 3.4 财务结算中心

### 3.4.1 账单管理

#### 页面信息

- 菜单名称：账单管理
- 前端路由：`/hotel/finance/bill`
- 列表页：[`yimamerchant-ui/src/views/hotel/finance/bill/index.vue`](yimamerchant-ui/src/views/hotel/finance/bill/index.vue)
- 详情页：[`yimamerchant-ui/src/views/hotel/finance/bill/detail.vue`](yimamerchant-ui/src/views/hotel/finance/bill/detail.vue)

#### 页面组成

1. 查询区
   - 酒店
   - 状态
   - 结算周期
   - 生成时间
2. 汇总区
   - 当前筛选总金额
   - 当前筛选总订单数
3. 工具栏
   - 手动生成账单
   - 导出汇总
   - 导出明细
4. 列表区
   - 账单号
   - 酒店名称
   - 结算周期
   - 订单数量
   - 结算金额
   - 状态
   - 生成时间
5. 详情页
   - 账单摘要卡片
   - 订单明细表格
   - 对账记录
   - 付款记录
   - 状态操作区

#### 页面交互说明

- 手动生成账单应先弹出预览窗口，再确认生成
- 账单详情适合使用独立详情页承载复杂信息
- 有争议状态下应允许发起重新核算

---

## 3.5 系统与配置

### 3.5.1 酒店业务参数配置

#### 页面信息

- 菜单名称：酒店业务参数配置
- 前端路由：`/hotel/config`
- 页面组件：[`yimamerchant-ui/src/views/hotel/config/index.vue`](yimamerchant-ui/src/views/hotel/config/index.vue)

#### 页面组成

1. 酒店参数
2. 订单参数
3. 结算参数
4. 通知模板参数

#### 页面交互说明

- 建议采用表单配置页 + 保存按钮模式
- 配置项如需历史追溯，可增加配置变更日志

---

## 4. 后端接口设计

## 4.1 统一设计规范

### 4.1.1 控制器建议

控制器建议命名如下：

- [`HotelPendingController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)
- [`HotelPartnerController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPartnerController.java:1)
- [`HotelBindController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelBindController.java:1)
- [`HotelInfoController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelInfoController.java:1)
- [`HotelRoomTypeController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelRoomTypeController.java:1)
- [`HotelPriceController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPriceController.java:1)
- [`HotelOrderController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelOrderController.java:1)
- [`HotelBillController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelBillController.java:1)
- [`HotelConfigController`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelConfigController.java:1)

### 4.1.2 返回结构建议

沿用若依标准响应结构：

- 列表接口：[`TableDataInfo`](yimamerchant-common/src/main/java/com/yimamerchant/common/core/page/TableDataInfo.java:1)
- 单体操作：[`AjaxResult`](yimamerchant-common/src/main/java/com/yimamerchant/common/core/domain/AjaxResult.java:1)

### 4.1.3 通用字段建议

所有主业务表建议保留以下字段：

- `create_by`
- `create_time`
- `update_by`
- `update_time`
- `remark`
- `del_flag`

---

## 4.2 酒店合作中心接口

## 4.2.1 待签约酒店接口

### 1. 查询待签约酒店列表

- 方法：`GET`
- 路径：`/hotel/cooperate/pending/list`
- 控制器：[`HotelPendingController.list()`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)

#### 请求参数

| 参数 | 类型 | 说明 |
| --- | --- | --- |
| hotelName | String | 酒店名称 |
| applyStatus | String | 申请状态 |
| beginApplyTime | String | 申请开始时间 |
| endApplyTime | String | 申请结束时间 |

#### 返回字段

| 字段 | 说明 |
| --- | --- |
| id | 申请ID |
| hotelName | 酒店名称 |
| applyStatus | 申请状态 |
| applicantName | 申请人 |
| applicantPhone | 联系方式 |
| applyTime | 申请时间 |
| lastOperateTime | 最后操作时间 |

### 2. 获取申请详情

- 方法：`GET`
- 路径：`/hotel/cooperate/pending/{id}`
- 控制器：[`HotelPendingController.getInfo()`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)

### 3. 新增申请

- 方法：`POST`
- 路径：`/hotel/cooperate/pending`
- 控制器：[`HotelPendingController.add()`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)

#### Body 示例

```json
{
  "hotelName": "杭州西湖湖景酒店",
  "contactName": "王经理",
  "contactPhone": "13800138000",
  "provinceCode": "330000",
  "cityCode": "330100",
  "districtCode": "330106",
  "address": "西湖区灵隐路88号",
  "longitude": 120.12001,
  "latitude": 30.22001,
  "licenseFiles": "1,2,3",
  "applyRemark": "重点合作酒店"
}
```

### 4. 审核通过

- 方法：`PUT`
- 路径：`/hotel/cooperate/pending/approve`
- 控制器：[`HotelPendingController.approve()`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)

#### Body 参数

| 参数 | 类型 | 说明 |
| --- | --- | --- |
| id | Long | 申请ID |
| contractStartDate | String | 合同开始日期 |
| contractEndDate | String | 合同结束日期 |
| commissionMode | String | 佣金模式 |
| bdUserId | Long | 归属BD |
| auditRemark | String | 审核备注 |

### 5. 审核驳回

- 方法：`PUT`
- 路径：`/hotel/cooperate/pending/reject`
- 控制器：[`HotelPendingController.reject()`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)

### 6. 删除申请

- 方法：`DELETE`
- 路径：`/hotel/cooperate/pending/{ids}`
- 控制器：[`HotelPendingController.remove()`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)

### 7. 导出申请列表

- 方法：`POST`
- 路径：`/hotel/cooperate/pending/export`
- 控制器：[`HotelPendingController.export()`](yimamerchant-admin/src/main/java/com/yimamerchant/web/controller/hotel/HotelPendingController.java:1)

## 4.2.2 合作酒店接口

### 1. 查询合作酒店列表

- 方法：`GET`
- 路径：`/hotel/cooperate/partner/list`

### 2. 获取合作酒店详情

- 方法：`GET`
- 路径：`/hotel/cooperate/partner/{hotelId}`

### 3. 更新合作酒店信息

- 方法：`PUT`
- 路径：`/hotel/cooperate/partner`

### 4. 更新合作状态

- 方法：`PUT`
- 路径：`/hotel/cooperate/partner/status`

#### Body 参数

| 参数 | 类型 | 说明 |
| --- | --- | --- |
| hotelId | Long | 酒店ID |
| cooperateStatus | String | 合作状态 |
| operateReason | String | 处理原因 |

### 5. 获取合作酒店统计

- 方法：`GET`
- 路径：`/hotel/cooperate/partner/statistics`

### 6. 导出合作酒店

- 方法：`POST`
- 路径：`/hotel/cooperate/partner/export`

### 7. 重置酒店账号密码

- 方法：`PUT`
- 路径：`/hotel/cooperate/partner/resetPassword`

### 8. 获取酒店子账号列表

- 方法：`GET`
- 路径：`/hotel/cooperate/partner/account/list`

## 4.2.3 BD归属管理接口

### 1. 查询归属关系列表

- 方法：`GET`
- 路径：`/hotel/cooperate/bind/list`

### 2. 批量绑定

- 方法：`POST`
- 路径：`/hotel/cooperate/bind/batch`

#### Body 示例

```json
{
  "bdUserId": 100,
  "hotelIds": [1, 2, 3],
  "remark": "新签酒店批量绑定"
}
```

### 3. 批量解绑

- 方法：`DELETE`
- 路径：`/hotel/cooperate/bind/{ids}`

### 4. 批量转移

- 方法：`PUT`
- 路径：`/hotel/cooperate/bind/transfer`

#### Body 示例

```json
{
  "sourceBdUserId": 100,
  "targetBdUserId": 101,
  "hotelIds": [1, 2],
  "transferReason": "组织调整"
}
```

### 5. 查询绑定历史

- 方法：`GET`
- 路径：`/hotel/cooperate/bind/history`

### 6. 获取BD下拉选项

- 方法：`GET`
- 路径：`/hotel/cooperate/bind/bdOptions`

### 7. 获取酒店下拉选项

- 方法：`GET`
- 路径：`/hotel/cooperate/bind/hotelOptions`

---

## 4.3 酒店运营中心接口

## 4.3.1 酒店信息接口

### 1. 查询酒店列表

- 方法：`GET`
- 路径：`/hotel/operate/info/list`

### 2. 获取酒店详情

- 方法：`GET`
- 路径：`/hotel/operate/info/{hotelId}`

### 3. 新增酒店

- 方法：`POST`
- 路径：`/hotel/operate/info`

### 4. 更新酒店信息

- 方法：`PUT`
- 路径：`/hotel/operate/info`

### 5. 更新上下架状态

- 方法：`PUT`
- 路径：`/hotel/operate/info/status`

### 6. 获取酒店下拉选项

- 方法：`GET`
- 路径：`/hotel/operate/info/options`

### 7. 获取酒店变更日志

- 方法：`GET`
- 路径：`/hotel/operate/info/changeLog`

## 4.3.2 房型管理接口

### 1. 查询房型列表

- 方法：`GET`
- 路径：`/hotel/operate/roomType/list`

#### 请求参数

| 参数 | 类型 | 说明 |
| --- | --- | --- |
| hotelId | Long | 酒店ID，必填 |
| roomTypeName | String | 房型名称 |
| saleStatus | String | 状态 |

### 2. 获取房型详情

- 方法：`GET`
- 路径：`/hotel/operate/roomType/{id}`

### 3. 新增房型

- 方法：`POST`
- 路径：`/hotel/operate/roomType`

### 4. 更新房型

- 方法：`PUT`
- 路径：`/hotel/operate/roomType`

### 5. 删除房型

- 方法：`DELETE`
- 路径：`/hotel/operate/roomType/{ids}`

### 6. 更新房型状态

- 方法：`PUT`
- 路径：`/hotel/operate/roomType/status`

### 7. 更新房型排序

- 方法：`PUT`
- 路径：`/hotel/operate/roomType/sort`

### 8. 批量复制房型配置

- 方法：`POST`
- 路径：`/hotel/operate/roomType/copy`

## 4.3.3 库存价格接口

### 1. 获取价格日历

- 方法：`GET`
- 路径：`/hotel/operate/price/calendar`

#### 请求参数

| 参数 | 类型 | 说明 |
| --- | --- | --- |
| hotelId | Long | 酒店ID |
| roomTypeIds | String | 房型ID集合，逗号分隔 |
| month | String | 月份，如 `2026-03` |

### 2. 设置单日价格库存

- 方法：`POST`
- 路径：`/hotel/operate/price/daily`

### 3. 批量设置价格库存

- 方法：`POST`
- 路径：`/hotel/operate/price/batch`

### 4. 批量关房

- 方法：`POST`
- 路径：`/hotel/operate/price/close`

### 5. 批量复制价格库存

- 方法：`POST`
- 路径：`/hotel/operate/price/copy`

### 6. 恢复默认设置

- 方法：`POST`
- 路径：`/hotel/operate/price/restore`

### 7. 查询价格修改历史

- 方法：`GET`
- 路径：`/hotel/operate/price/history`

#### 单日/批量设置 Body 建议

```json
{
  "hotelId": 1,
  "roomTypeIds": [11, 12],
  "bizDates": ["2026-03-21", "2026-03-22"],
  "settlementPrice": 300.00,
  "salePrice": 368.00,
  "commissionRate": 0.12,
  "inventory": 20,
  "refundRule": "TIME_LIMIT",
  "specialTag": "HOLIDAY"
}
```

---

## 4.4 交易与服务中心接口

## 4.4.1 酒店订单接口

### 1. 查询订单列表

- 方法：`GET`
- 路径：`/hotel/service/order/list`

### 2. 获取订单详情

- 方法：`GET`
- 路径：`/hotel/service/order/{orderNo}`

### 3. 订单确认

- 方法：`PUT`
- 路径：`/hotel/service/order/confirm`

### 4. 取消订单

- 方法：`PUT`
- 路径：`/hotel/service/order/cancel`

### 5. 办理入住

- 方法：`PUT`
- 路径：`/hotel/service/order/checkin`

### 6. 办理离店

- 方法：`PUT`
- 路径：`/hotel/service/order/checkout`

### 7. 添加备注

- 方法：`POST`
- 路径：`/hotel/service/order/remark`

### 8. 导出订单

- 方法：`POST`
- 路径：`/hotel/service/order/export`

### 9. 查询操作日志

- 方法：`GET`
- 路径：`/hotel/service/order/logs`

### 10. 提交退款申请处理

- 方法：`POST`
- 路径：`/hotel/service/order/refund`

### 11. 提交纠纷处理

- 方法：`POST`
- 路径：`/hotel/service/order/dispute`

---

## 4.5 财务结算中心接口

## 4.5.1 账单管理接口

### 1. 查询账单列表

- 方法：`GET`
- 路径：`/hotel/finance/bill/list`

### 2. 生成账单

- 方法：`POST`
- 路径：`/hotel/finance/bill/generate`

#### Body 示例

```json
{
  "hotelId": 1,
  "statementStartDate": "2026-03-01",
  "statementEndDate": "2026-03-31",
  "generateMode": "MANUAL"
}
```

### 3. 获取账单详情

- 方法：`GET`
- 路径：`/hotel/finance/bill/{billNo}`

### 4. 获取账单订单明细

- 方法：`GET`
- 路径：`/hotel/finance/bill/orders`

### 5. 确认账单

- 方法：`PUT`
- 路径：`/hotel/finance/bill/confirm`

### 6. 提出异议

- 方法：`PUT`
- 路径：`/hotel/finance/bill/dispute`

### 7. 重新核算

- 方法：`PUT`
- 路径：`/hotel/finance/bill/recalculate`

### 8. 发起付款

- 方法：`POST`
- 路径：`/hotel/finance/bill/payment`

### 9. 导出账单汇总

- 方法：`GET`
- 路径：`/hotel/finance/bill/export`

### 10. 获取账单统计

- 方法：`GET`
- 路径：`/hotel/finance/bill/statistics`

---

## 4.6 系统配置接口

## 4.6.1 酒店业务参数接口

### 1. 获取业务参数

- 方法：`GET`
- 路径：`/hotel/config/get`

### 2. 保存业务参数

- 方法：`PUT`
- 路径：`/hotel/config/save`

### 3. 获取配置变更日志

- 方法：`GET`
- 路径：`/hotel/config/logs`

---

## 5. 数据库设计说明

## 5.1 建表范围

本次酒店业务 SQL 脚本输出到 [`sql/hotel.sql`](sql/hotel.sql)，建议覆盖以下核心表：

1. 酒店申请表
2. 酒店申请审核日志表
3. 合作酒店主表
4. 酒店合同表
5. 酒店账号表
6. BD酒店绑定表
7. BD酒店绑定历史表
8. 酒店资料表
9. 酒店图片表
10. 房型表
11. 房型图片表
12. 房价库存日历表
13. 房价库存修改日志表
14. 酒店订单主表
15. 酒店订单入住人表
16. 酒店订单日志表
17. 酒店账单主表
18. 酒店账单明细表
19. 酒店账单对账日志表
20. 酒店付款记录表
21. 酒店业务配置表
22. 酒店业务配置日志表

## 5.2 编码与命名建议

- 表名前缀统一建议：`hotel_`
- 主键字段统一使用 `BIGINT`
- 状态字段统一使用 `VARCHAR(32)` 或 `CHAR(1)`
- 金额字段统一使用 `DECIMAL(12,2)`
- 比例字段统一使用 `DECIMAL(8,4)`
- 日期字段按业务语义区分 `DATETIME` 与 `DATE`

## 5.3 核心表关系说明

### 1. 酒店合作关系链

- `hotel_pending_apply` → `hotel_partner` → `hotel_profile`

### 2. BD归属关系链

- `hotel_partner` ← `hotel_bd_bind` → `sys_user`
- 历史记录落表 `hotel_bd_bind_history`

### 3. 商品关系链

- `hotel_partner` → `hotel_room_type` → `hotel_room_type_image`
- `hotel_room_type` → `hotel_room_calendar`

### 4. 交易关系链

- `hotel_order` → `hotel_order_guest`
- `hotel_order` → `hotel_order_log`

### 5. 结算关系链

- `hotel_bill` → `hotel_bill_order`
- `hotel_bill` → `hotel_bill_check_log`
- `hotel_bill` → `hotel_bill_payment`

---

## 6. 后端实现建议

## 6.1 Domain 建议

建议在 [`yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain) 下建立领域对象，例如：

- [`HotelPendingApply`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelPendingApply.java:1)
- [`HotelPartner`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelPartner.java:1)
- [`HotelBdBind`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelBdBind.java:1)
- [`HotelProfile`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelProfile.java:1)
- [`HotelRoomType`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelRoomType.java:1)
- [`HotelRoomCalendar`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelRoomCalendar.java:1)
- [`HotelOrder`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelOrder.java:1)
- [`HotelBill`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/domain/HotelBill.java:1)

## 6.2 DTO/VO 建议

对于复杂查询和详情聚合，建议增加 DTO/VO：

- 待审核酒店审核 DTO
- 合作酒店详情 VO
- 日历价格批量设置 DTO
- 订单详情 VO
- 账单详情 VO

## 6.3 Mapper 与 XML 建议

Mapper 接口建议放在 [`yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/mapper`](yimamerchant-system/src/main/java/com/yimamerchant/system/hotel/mapper)。

XML 建议放在 [`yimamerchant-system/src/main/resources/mapper/hotel`](yimamerchant-system/src/main/resources/mapper/hotel)。

复杂场景建议使用 XML：

- 账单生成
- 订单复杂筛选
- 房价日历批量查询
- 合作酒店详情聚合

---

## 7. 权限与字典建议

## 7.1 菜单权限标识建议

| 模块 | 权限标识建议 |
| --- | --- |
| 待签约酒店 | `hotel:cooperate:pending:list` |
| 合作酒店 | `hotel:cooperate:partner:list` |
| BD归属管理 | `hotel:cooperate:bind:list` |
| 酒店信息 | `hotel:operate:info:list` |
| 房型管理 | `hotel:operate:roomType:list` |
| 库存价格 | `hotel:operate:price:list` |
| 酒店订单 | `hotel:service:order:list` |
| 账单管理 | `hotel:finance:bill:list` |
| 参数配置 | `hotel:config:edit` |

## 7.2 字典类型建议

建议补充以下字典：

- 酒店申请状态
- 合作状态
- 佣金模式
- 酒店上下架状态
- 房型销售状态
- 退款规则
- 特殊日期标记
- 订单状态
- 账单状态
- 支付状态
- 异议状态

---

## 8. SQL 产出说明

本次完整 SQL 已单独输出到 [`sql/hotel.sql`](sql/hotel.sql)。

SQL 脚本应包含：

- 建表语句
- 字段注释
- 索引语句
- 默认值设计
- 部分初始化字典/配置建议（如需要）

---

## 9. 迭代建议

### 9.1 第一阶段优先落地

- 合作中心
- 酒店信息
- 房型管理
- 库存价格管理
- 订单管理
- 账单管理基础能力

### 9.2 第二阶段增强

- 酒店端账单确认
- 批量通知
- 经营统计
- 配置变更日志
- 更多自动化处理任务

---

## 10. 总结

本文档将 [`plans/酒店.md`](plans/酒店.md) 的原始规划转化为面向若依前后端分离项目的技术接口设计方案，重点明确了：

- 前端页面如何规划
- 后端接口如何定义
- 数据库表如何拆分
- 模块代码如何组织

后续开发可基于本文档继续细化 Controller、Service、Mapper、Vue 页面和权限菜单配置。