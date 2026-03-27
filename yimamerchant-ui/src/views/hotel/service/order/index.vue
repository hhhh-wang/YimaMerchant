<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" label-width="68px">
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          style="width: 220px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          placeholder="请选择状态"
          clearable
          style="width: 180px"
        >
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="手机号" prop="userMobile">
        <el-input
          v-model="queryParams.userMobile"
          placeholder="请输入下单手机号"
          clearable
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="loading" :data="list" border>
      <el-table-column label="订单号" prop="orderNo" min-width="180" />
      <el-table-column label="酒店" prop="hotelName" min-width="160" show-overflow-tooltip />
      <el-table-column label="房型" prop="roomTypeName" min-width="140" show-overflow-tooltip />
      <el-table-column label="入住信息" min-width="180">
        <template slot-scope="scope">
          <div>{{ formatDate(scope.row.checkinDate) }} 至 {{ formatDate(scope.row.checkoutDate) }}</div>
          <div class="sub-text">{{ scope.row.nightCount || 0 }}晚 / {{ scope.row.roomCount || 0 }}间</div>
        </template>
      </el-table-column>
      <el-table-column label="联系人" min-width="140">
        <template slot-scope="scope">
          <div>{{ scope.row.userName || '-' }}</div>
          <div class="sub-text">{{ scope.row.userMobile || '-' }}</div>
        </template>
      </el-table-column>
      <el-table-column label="金额" prop="payAmount" width="110">
        <template slot-scope="scope">
          <span class="amount-text">￥{{ formatAmount(scope.row.payAmount || scope.row.orderAmount) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="110">
        <template slot-scope="scope">
          <el-tag size="small" :type="statusType(scope.row.orderStatus)">
            {{ statusLabel(scope.row.orderStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="下单时间" min-width="160">
        <template slot-scope="scope">
          {{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="260" fixed="right">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="showDetail(scope.row)">详情</el-button>
          <el-button
            v-for="action in resolveActions(scope.row)"
            :key="action.key"
            type="text"
            size="mini"
            @click="handleAction(scope.row, action.key)"
          >
            {{ action.label }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-drawer :visible.sync="drawer" title="订单详情" size="46%">
      <div v-if="drawer" class="detail-panel">
        <el-descriptions :column="2" border size="small">
          <el-descriptions-item label="订单号">{{ detail.orderNo || '-' }}</el-descriptions-item>
          <el-descriptions-item label="订单状态">
            <el-tag size="small" :type="statusType(detail.orderStatus)">
              {{ statusLabel(detail.orderStatus) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="酒店">{{ detail.hotelName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="房型">{{ detail.roomTypeName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="下单用户">{{ detail.userName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{ detail.userMobile || '-' }}</el-descriptions-item>
          <el-descriptions-item label="入住日期">{{ formatDate(detail.checkinDate) }}</el-descriptions-item>
          <el-descriptions-item label="离店日期">{{ formatDate(detail.checkoutDate) }}</el-descriptions-item>
          <el-descriptions-item label="间夜">{{ detail.nightCount || 0 }}晚 / {{ detail.roomCount || 0 }}间</el-descriptions-item>
          <el-descriptions-item label="订单金额">￥{{ formatAmount(detail.payAmount || detail.orderAmount) }}</el-descriptions-item>
          <el-descriptions-item label="特殊要求" :span="2">{{ detail.specialRequest || '-' }}</el-descriptions-item>
          <el-descriptions-item label="内部备注" :span="2">{{ detail.innerRemark || '-' }}</el-descriptions-item>
        </el-descriptions>

        <div class="section-block">
          <div class="section-title">入住人</div>
          <el-table :data="detail.guestList || []" size="mini" border>
            <el-table-column label="姓名" prop="guestName" min-width="120" />
            <el-table-column label="手机号" prop="guestMobile" min-width="140" />
            <el-table-column label="主入住人" min-width="90">
              <template slot-scope="scope">
                {{ scope.row.isMainGuest === '1' || scope.row.isMainGuest === true ? '是' : '否' }}
              </template>
            </el-table-column>
          </el-table>
        </div>

        <div class="section-block">
          <div class="section-title">操作日志</div>
          <el-table :data="detail.logList || []" size="mini" border>
            <el-table-column label="时间" min-width="160">
              <template slot-scope="scope">
                {{ parseTime(scope.row.operateTime, '{y}-{m}-{d} {h}:{i}:{s}') }}
              </template>
            </el-table-column>
            <el-table-column label="操作类型" prop="operateType" min-width="110" />
            <el-table-column label="状态变更" min-width="150">
              <template slot-scope="scope">
                {{ statusLabel(scope.row.beforeStatus) }} → {{ statusLabel(scope.row.afterStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="操作人" prop="operateUserName" min-width="110" />
            <el-table-column label="备注" prop="operateRemark" min-width="180" show-overflow-tooltip />
          </el-table>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import {
  cancelHotelOrder,
  checkinHotelOrder,
  checkoutHotelOrder,
  confirmHotelOrder,
  getHotelOrder,
  listHotelOrder,
} from '@/api/hotel/service/order'
import { parseTime as parseTimeUtil } from '@/utils/ruoyi'

export default {
  name: 'HotelOrder',
  data() {
    return {
      loading: false,
      total: 0,
      list: [],
      drawer: false,
      detail: {},
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: undefined,
        orderStatus: undefined,
        userMobile: undefined,
      },
      statusOptions: [
        { label: '待支付', value: 'PENDING_PAY' },
        { label: '待确认', value: 'PENDING_CONFIRM' },
        { label: '已确认', value: 'CONFIRMED' },
        { label: '已入住', value: 'CHECKED_IN' },
        { label: '已完成', value: 'COMPLETED' },
        { label: '已取消', value: 'CANCELLED' },
      ],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    parseTime(value, pattern) {
      return parseTimeUtil(value, pattern)
    },
    getList() {
      this.loading = true
      listHotelOrder(this.queryParams).then(res => {
        this.list = res.rows || []
        this.total = res.total || 0
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.queryParams.pageNum = 1
      this.queryParams.pageSize = 10
      this.getList()
    },
    showDetail(row) {
      getHotelOrder(row.orderNo).then(res => {
        this.detail = res.data || {}
        this.drawer = true
      })
    },
    resolveActions(row) {
      switch (row.orderStatus) {
        case 'PENDING_CONFIRM':
          return [
            { key: 'confirm', label: '确认订单' },
            { key: 'cancel', label: '取消订单' },
          ]
        case 'CONFIRMED':
          return [
            { key: 'checkin', label: '办理入住' },
            { key: 'cancel', label: '取消订单' },
          ]
        case 'CHECKED_IN':
          return [
            { key: 'checkout', label: '办理离店' },
          ]
        default:
          return []
      }
    },
    handleAction(row, action) {
      if (action === 'confirm') {
        this.confirmOrder(row)
        return
      }
      if (action === 'cancel') {
        this.cancelOrder(row)
        return
      }
      if (action === 'checkin') {
        this.checkinOrder(row)
        return
      }
      if (action === 'checkout') {
        this.checkoutOrder(row)
      }
    },
    confirmOrder(row) {
      this.$modal.confirm(`确认订单 ${row.orderNo} 已通过酒店审核？`).then(() => {
        return confirmHotelOrder({ orderNo: row.orderNo })
      }).then(() => {
        this.$modal.msgSuccess('确认成功')
        this.refreshListAndDetail(row)
      }).catch(() => {})
    },
    cancelOrder(row) {
      this.$prompt('请输入取消原因', '取消订单', {
        confirmButtonText: '确定',
        cancelButtonText: '返回',
        inputValue: '商家取消',
        inputValidator: value => !!value,
        inputErrorMessage: '请输入取消原因',
      }).then(({ value }) => {
        return cancelHotelOrder({ orderNo: row.orderNo, cancelReason: value, operateRemark: value })
      }).then(() => {
        this.$modal.msgSuccess('取消成功')
        this.refreshListAndDetail(row)
      }).catch(() => {})
    },
    checkinOrder(row) {
      this.$modal.confirm(`确认订单 ${row.orderNo} 已办理入住？`).then(() => {
        return checkinHotelOrder({ orderNo: row.orderNo, operateRemark: '商家办理入住' })
      }).then(() => {
        this.$modal.msgSuccess('已标记入住')
        this.refreshListAndDetail(row)
      }).catch(() => {})
    },
    checkoutOrder(row) {
      this.$modal.confirm(`确认订单 ${row.orderNo} 已办理离店？`).then(() => {
        return checkoutHotelOrder({ orderNo: row.orderNo, operateRemark: '商家办理离店' })
      }).then(() => {
        this.$modal.msgSuccess('已标记离店')
        this.refreshListAndDetail(row)
      }).catch(() => {})
    },
    refreshListAndDetail(row) {
      this.getList()
      if (this.drawer && this.detail.orderNo === row.orderNo) {
        this.showDetail(row)
      }
    },
    statusLabel(status) {
      const matched = this.statusOptions.find(item => item.value === status)
      return matched ? matched.label : (status || '-')
    },
    statusType(status) {
      switch (status) {
        case 'PENDING_PAY':
          return 'warning'
        case 'PENDING_CONFIRM':
          return ''
        case 'CONFIRMED':
          return 'success'
        case 'CHECKED_IN':
          return 'primary'
        case 'COMPLETED':
          return 'info'
        case 'CANCELLED':
          return 'danger'
        default:
          return 'info'
      }
    },
    formatDate(value) {
      return value ? parseTimeUtil(value, '{y}-{m}-{d}') : '-'
    },
    formatAmount(value) {
      const amount = Number(value || 0)
      return amount.toFixed(2)
    },
  },
}
</script>

<style scoped>
.sub-text {
  margin-top: 4px;
  color: #909399;
  font-size: 12px;
}

.amount-text {
  color: #f56c6c;
  font-weight: 600;
}

.detail-panel {
  padding: 0 20px 20px;
}

.section-block {
  margin-top: 20px;
}

.section-title {
  margin-bottom: 12px;
  font-size: 14px;
  font-weight: 600;
  color: #303133;
}
</style>