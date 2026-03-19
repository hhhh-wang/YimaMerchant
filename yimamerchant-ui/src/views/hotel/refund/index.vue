<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="90px"
    >
      <el-form-item label="订单号" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          placeholder="请输入订单号"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="退款单号" prop="refundNo">
        <el-input
          v-model="queryParams.refundNo"
          placeholder="请输入退款单号"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="房型名称" prop="roomTypeName">
        <el-input
          v-model="queryParams.roomTypeName"
          placeholder="请输入房型名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item label="退款状态" prop="refundStatus">
        <el-select
          v-model="queryParams.refundStatus"
          placeholder="请选择退款状态"
          clearable
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.hotel_refund_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="申请时间">
        <el-col :span="11">
          <el-date-picker
            v-model="queryParams.beginApplyTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="开始时间"
            style="width: 180px"
          />
        </el-col>
        <el-col :span="2" class="text-center">-</el-col>
        <el-col :span="11">
          <el-date-picker
            v-model="queryParams.endApplyTime"
            type="datetime"
            value-format="yyyy-MM-dd HH:mm:ss"
            placeholder="结束时间"
            style="width: 180px"
          />
        </el-col>
      </el-form-item>

      <el-form-item label="入住日期">
        <el-col :span="11">
          <el-date-picker
            v-model="queryParams.beginCheckInDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="开始日期"
            style="width: 180px"
          />
        </el-col>
        <el-col :span="2" class="text-center">-</el-col>
        <el-col :span="11">
          <el-date-picker
            v-model="queryParams.endCheckInDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="结束日期"
            style="width: 180px"
          />
        </el-col>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="24">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
      </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="refundList" border>
      <el-table-column label="退款单号" prop="refundNo" min-width="160" :show-overflow-tooltip="true" />
      <el-table-column label="订单号" prop="orderNo" min-width="160" :show-overflow-tooltip="true" />
      <el-table-column label="入住人" prop="guestName" width="110" :show-overflow-tooltip="true" />
      <el-table-column label="联系电话" prop="guestPhone" width="140" :show-overflow-tooltip="true" />
      <el-table-column label="房型名称" prop="roomTypeName" min-width="160" :show-overflow-tooltip="true" />
      <el-table-column label="入住日期" prop="checkInDate" width="120" />
      <el-table-column label="离店日期" prop="checkOutDate" width="120" />

      <el-table-column label="订单金额" prop="orderAmount" width="140" align="right">
        <template slot-scope="scope">
          <span>{{ scope.row.orderAmount | price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请退款金额" prop="applyRefundAmount" width="160" align="right">
        <template slot-scope="scope">
          <span>{{ scope.row.applyRefundAmount | price }}</span>
        </template>
      </el-table-column>

      <el-table-column label="退款状态" prop="refundStatus" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_refund_status" :value="scope.row.refundStatus" />
        </template>
      </el-table-column>

      <el-table-column label="申请时间" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="处理时间" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="240" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['hotel:refund:detail']"
          >
            详情
          </el-button>

          <el-button
            v-if="scope.row.refundStatus === '1'"
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="openAudit(scope.row)"
            v-hasPermi="['hotel:refund:audit']"
          >
            同意退款
          </el-button>
          <el-button
            v-if="scope.row.refundStatus === '1'"
            size="mini"
            type="text"
            icon="el-icon-close"
            @click="openAudit(scope.row)"
            v-hasPermi="['hotel:refund:audit']"
          >
            拒绝退款
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

    <!-- 退款详情与审核 -->
    <el-dialog title="退款详情" :visible.sync="detailOpen" width="860px" append-to-body>
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="退款单号">{{ detail.refundNo }}</el-descriptions-item>
        <el-descriptions-item label="订单号">{{ detail.orderNo }}</el-descriptions-item>
        <el-descriptions-item label="房型名称">{{ detail.roomTypeName }}</el-descriptions-item>
        <el-descriptions-item label="入住人">{{ detail.guestName }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ detail.guestPhone }}</el-descriptions-item>
        <el-descriptions-item label="入住日期">{{ detail.checkInDate }}</el-descriptions-item>
        <el-descriptions-item label="离店日期">{{ detail.checkOutDate }}</el-descriptions-item>
        <el-descriptions-item label="订单金额">
          {{ detail.orderAmount | price }}
        </el-descriptions-item>
        <el-descriptions-item label="申请退款金额">
          {{ detail.applyRefundAmount | price }}
        </el-descriptions-item>
        <el-descriptions-item label="退款原因" :span="2">
          <div style="white-space: pre-wrap">{{ detail.refundReason }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="取消规则" :span="2">
          <div style="white-space: pre-wrap">{{ detail.cancelRule }}</div>
        </el-descriptions-item>
        <el-descriptions-item label="退款状态" :span="1">
          <dict-tag :options="dict.type.hotel_refund_status" :value="detail.refundStatus" />
        </el-descriptions-item>
        <el-descriptions-item label="审核备注" :span="1">{{ detail.auditRemark }}</el-descriptions-item>
        <el-descriptions-item label="审核人" :span="1">{{ detail.auditBy }}</el-descriptions-item>
        <el-descriptions-item label="申请时间" :span="1">{{ parseTime(detail.createTime) }}</el-descriptions-item>
        <el-descriptions-item label="处理时间" :span="1">{{ parseTime(detail.auditTime) }}</el-descriptions-item>
      </el-descriptions>

      <!-- 审核 -->
      <div v-if="detail.refundStatus === '1'" style="margin-top: 14px">
        <el-form :model="{ auditRemark }" label-width="120px" size="small">
          <el-form-item label="审核备注（拒绝必填）">
            <el-input
              type="textarea"
              :rows="3"
              v-model="auditRemark"
              placeholder="请输入审核备注"
            />
          </el-form-item>
        </el-form>
      </div>

      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>

        <el-button
          v-if="detail.refundStatus === '1'"
          type="primary"
          @click="handleApprove"
          v-hasPermi="['hotel:refund:audit']"
        >
          同意退款
        </el-button>
        <el-button
          v-if="detail.refundStatus === '1'"
          type="danger"
          @click="handleReject"
          v-hasPermi="['hotel:refund:audit']"
        >
          拒绝退款
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRefund, getRefundDetail, approveRefund, rejectRefund } from '@/api/hotel/refund'

export default {
  name: 'HotelRefund',
  dicts: ['hotel_refund_status'],
  data() {
    return {
      loading: false,
      // 是否显示搜索条件
      showSearch: true,
      total: 0,
      // 列表数据
      refundList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: undefined,
        refundNo: undefined,
        roomTypeName: undefined,
        refundStatus: undefined,
        beginApplyTime: undefined,
        endApplyTime: undefined,
        beginCheckInDate: undefined,
        endCheckInDate: undefined
      },
      // 详情弹窗
      detailOpen: false,
      detail: {},
      // 审核备注
      auditRemark: ''
    }
  },
  created() {
    this.getList()
  },
  filters: {
    price(val) {
      if (val === null || val === undefined) {
        return ''
      }
      return Number(val).toFixed(2)
    }
  },
  methods: {
    // 查询退款列表
    getList() {
      this.loading = true
      listRefund(this.queryParams)
        .then(res => {
          const data = res || {}
          this.refundList = data.rows || []
          this.total = data.total || 0
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 搜索
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 打开详情/审核
    openAudit(row) {
      this.handleDetail(row)
    },
    // 获取详情
    handleDetail(row) {
      const id = row && row.id
      if (!id) return
      getRefundDetail(id).then(res => {
        this.detail = (res && res.data) || {}
        this.auditRemark = ''
        this.detailOpen = true
      })
    },
    // 同意退款
    handleApprove() {
      if (!this.detail || !this.detail.id) return
      const payload = {
        id: this.detail.id,
        auditRemark: this.auditRemark
      }
      approveRefund(payload).then(() => {
        this.$message.success('操作成功')
        this.detailOpen = false
        this.getList()
      })
    },
    // 拒绝退款
    handleReject() {
      if (!this.detail || !this.detail.id) return
      if (!this.auditRemark) {
        this.$message.warning('拒绝退款未填审核备注')
        return
      }
      const payload = {
        id: this.detail.id,
        auditRemark: this.auditRemark
      }
      rejectRefund(payload).then(() => {
        this.$message.success('操作成功')
        this.detailOpen = false
        this.getList()
      })
    }
  }
}
</script>

<style scoped>
.text-center {
  text-align: center;
}
</style>

