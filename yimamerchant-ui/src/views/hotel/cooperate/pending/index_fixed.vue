<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="酒店名称">
        <el-input v-model="queryParams.hotelName" placeholder="请输入酒店名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="申请状态">
        <el-select v-model="queryParams.applyStatus" clearable placeholder="请选择申请状态">
          <el-option label="待审核" value="PENDING" />
          <el-option label="已通过" value="APPROVED" />
          <el-option label="已驳回" value="REJECTED" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请时间">
        <el-date-picker
          v-model="dateRange"
          type="daterange"
          value-format="yyyy-MM-dd"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button v-hasPermi="['hotel:cooperate:pending:add']" type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">
          新增申请
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button v-hasPermi="['hotel:cooperate:pending:remove']" type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">
          批量删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="酒店名称" prop="hotelName" min-width="180" />
      <el-table-column label="申请状态" prop="applyStatus" width="120">
        <template slot-scope="scope">
          <el-tag :type="statusTagMap[scope.row.applyStatus] || 'info'" size="small">
            {{ statusLabelMap[scope.row.applyStatus] || scope.row.applyStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="酒店经营状态" prop="businessStatus" width="120">
        <template slot-scope="scope">{{ businessStatusLabelMap[scope.row.businessStatus] || '-' }}</template>
      </el-table-column>
      <el-table-column label="申请人" prop="applicantName" width="120" />
      <el-table-column label="联系电话" prop="contactPhone" width="140" />
      <el-table-column label="所在地区" min-width="180">
        <template slot-scope="scope">{{ formatArea(scope.row) }}</template>
      </el-table-column>
      <el-table-column label="申请时间" prop="applyTime" width="160">
        <template slot-scope="scope">{{ parseTime(scope.row.applyTime) }}</template>
      </el-table-column>
      <el-table-column label="最后操作时间" prop="lastOperateTime" width="160">
        <template slot-scope="scope">{{ parseTime(scope.row.lastOperateTime) }}</template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="300">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleDetail(scope.row)">详情</el-button>
          <el-button
            v-if="scope.row.applyStatus !== 'APPROVED'"
            v-hasPermi="['hotel:cooperate:pending:edit']"
            type="text"
            size="mini"
            @click="handleEdit(scope.row)"
          >
            编辑
          </el-button>
          <el-button
            v-if="scope.row.applyStatus === 'PENDING'"
            v-hasPermi="['hotel:cooperate:pending:audit']"
            type="text"
            size="mini"
            @click="handleApprove(scope.row)"
          >
            通过
          </el-button>
          <el-button
            v-if="scope.row.applyStatus === 'PENDING'"
            v-hasPermi="['hotel:cooperate:pending:audit']"
            type="text"
            size="mini"
            @click="handleReject(scope.row)"
          >
            驳回
          </el-button>
          <el-button v-hasPermi="['hotel:cooperate:pending:remove']" type="text" size="mini" @click="handleDelete(scope.row)">删除</el-button>
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

    <el-drawer :visible.sync="drawer" title="申请详情" size="45%">
      <div class="drawer-body">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="酒店名称">{{ detail.hotelName }}</el-descriptions-item>
          <el-descriptions-item label="联系人">{{ detail.contactName || '-' }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ detail.contactPhone || '-' }}</el-descriptions-item>
          <el-descriptions-item label="酒店经营状态">{{ businessStatusLabelMap[detail.businessStatus] || '-' }}</el-descriptions-item>
          <el-descriptions-item label="所在地区">{{ formatArea(detail) }}</el-descriptions-item>
          <el-descriptions-item label="详细地址">{{ detail.address || '-' }}</el-descriptions-item>
          <el-descriptions-item label="经纬度">{{ formatCoordinate(detail) }}</el-descriptions-item>
          <el-descriptions-item label="入住/离店时间">{{ formatStayTime(detail) }}</el-descriptions-item>
          <el-descriptions-item label="酒店封面图">
            <image-preview v-if="detail.coverImage" :src="detail.coverImage" :width="80" :height="80" />
            <span v-else>-</span>
          </el-descriptions-item>
          <el-descriptions-item label="酒店轮播图">
            <div v-if="bannerImageList.length" class="image-list">
              <image-preview v-for="(item, index) in bannerImageList" :key="index" :src="item" :width="80" :height="80" />
            </div>
            <span v-else>-</span>
          </el-descriptions-item>
          <el-descriptions-item label="酒店简介">{{ detail.hotelDesc || '-' }}</el-descriptions-item>
          <el-descriptions-item label="预订须知">{{ detail.bookingNotice || '-' }}</el-descriptions-item>
          <el-descriptions-item label="取消规则说明">{{ detail.cancelPolicy || '-' }}</el-descriptions-item>
          <el-descriptions-item label="开票说明">{{ detail.invoiceNotice || '-' }}</el-descriptions-item>
          <el-descriptions-item label="停车说明">{{ detail.parkingNotice || '-' }}</el-descriptions-item>
          <el-descriptions-item label="申请备注">{{ detail.applyRemark || '-' }}</el-descriptions-item>
        </el-descriptions>
        <el-divider>审核日志</el-divider>
        <el-timeline>
          <el-timeline-item v-for="(item, index) in auditLogs" :key="index" :timestamp="parseTime(item.operateTime)">
            {{ auditTypeLabelMap[item.operateType] || item.operateType }}
            <span v-if="item.operateRemark">{{ `: ${item.operateRemark}` }}</span>
          </el-timeline-item>
        </el-timeline>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { approvePending, delPending, getPending, listPending, rejectPending } from "@/api/hotel/cooperate/pending"

const statusLabelMap = {
  PENDING: "待审核",
  APPROVED: "已通过",
  REJECTED: "已驳回"
}

const statusTagMap = {
  PENDING: "warning",
  APPROVED: "success",
  REJECTED: "danger"
}

const businessStatusLabelMap = {
  OPEN: "营业中",
  SUSPENDED: "暂停营业",
  PREPARING: "筹备中"
}

const auditTypeLabelMap = {
  SUBMIT: "提交申请",
  UPDATE: "编辑申请",
  APPROVE: "审核通过",
  REJECT: "审核驳回"
}

export default {
  name: "HotelPending",
  data() {
    return {
      loading: false,
      showSearch: true,
      total: 0,
      list: [],
      ids: [],
      multiple: true,
      drawer: false,
      dateRange: [],
      auditLogs: [],
      detail: {},
      statusLabelMap,
      statusTagMap,
      businessStatusLabelMap,
      auditTypeLabelMap,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        hotelName: undefined,
        applyStatus: undefined
      }
    }
  },
  computed: {
    bannerImageList() {
      return this.detail.bannerImages ? this.detail.bannerImages.split(',').filter(Boolean) : []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    buildQuery() {
      return {
        ...this.queryParams,
        beginApplyTime: this.dateRange && this.dateRange[0],
        endApplyTime: this.dateRange && this.dateRange[1]
      }
    },
    getList() {
      this.loading = true
      listPending(this.buildQuery()).then(res => {
        this.list = res.rows || []
        this.total = res.total || 0
      }).finally(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.dateRange = []
      this.resetForm('queryForm')
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.multiple = !selection.length
    },
    handleAdd() {
      this.$router.push('/hotel/cooperate/pending-add')
    },
    handleEdit(row) {
      this.$router.push('/hotel/cooperate/pending-edit/' + row.id)
    },
    handleDetail(row) {
      getPending(row.id).then(res => {
        this.detail = res.data || {}
        this.auditLogs = (res.data && res.data.params && res.data.params.auditLogs) || []
        this.drawer = true
      })
    },
    handleApprove(row) {
      this.$prompt('请输入审核备注', '审核通过', {
        inputValue: '审核通过'
      }).then(({ value }) => {
        return approvePending({ id: row.id, auditRemark: value, commissionMode: 'BASE_PRICE' })
      }).then(() => {
        this.$modal.msgSuccess('审核成功')
        this.getList()
      })
    },
    handleReject(row) {
      this.$prompt('请输入驳回原因', '审核驳回').then(({ value }) => {
        return rejectPending({ id: row.id, auditRemark: value })
      }).then(() => {
        this.$modal.msgSuccess('驳回成功')
        this.getList()
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids.join(',')
      this.$modal.confirm('确认删除选中的申请记录吗？').then(() => {
        return delPending(ids)
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.getList()
      })
    },
    formatArea(row) {
      return [row.provinceName, row.cityName, row.districtName].filter(Boolean).join(' / ') || '-'
    },
    formatCoordinate(row) {
      if (row.longitude === undefined || row.longitude === null || row.latitude === undefined || row.latitude === null) {
        return '-'
      }
      return `${row.longitude}, ${row.latitude}`
    },
    formatStayTime(row) {
      if (!row.checkinTime && !row.checkoutTime) {
        return '-'
      }
      return `${row.checkinTime || '--'} / ${row.checkoutTime || '--'}`
    }
  }
}
</script>

<style scoped>
.drawer-body {
  padding: 0 16px 16px;
}

.image-list {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}
</style>
