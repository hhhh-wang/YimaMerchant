<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="酒店名称"><el-input v-model="queryParams.hotelName" placeholder="请输入酒店名称" clearable @keyup.enter.native="handleQuery" /></el-form-item>
      <el-form-item label="申请状态">
        <el-select v-model="queryParams.applyStatus" clearable placeholder="请选择状态">
          <el-option label="待审核" value="PENDING" />
          <el-option label="已通过" value="APPROVED" />
          <el-option label="已驳回" value="REJECTED" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请时间">
        <el-date-picker v-model="dateRange" type="daterange" value-format="yyyy-MM-dd" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增申请</el-button></el-col>
      <el-col :span="1.5"><el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete">批量删除</el-button></el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>
    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="酒店名称" prop="hotelName" min-width="180" />
      <el-table-column label="申请状态" prop="applyStatus" width="120" />
      <el-table-column label="申请人" prop="applicantName" width="120" />
      <el-table-column label="联系方式" prop="contactPhone" width="120" />
      <el-table-column label="申请时间" prop="applyTime" width="160"><template slot-scope="scope">{{ parseTime(scope.row.applyTime) }}</template></el-table-column>
      <el-table-column label="最后操作时间" prop="lastOperateTime" width="160"><template slot-scope="scope">{{ parseTime(scope.row.lastOperateTime) }}</template></el-table-column>
      <el-table-column label="操作" fixed="right" width="240">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="handleDetail(scope.row)">查看</el-button>
          <el-button v-if="scope.row.applyStatus==='PENDING'" type="text" size="mini" @click="handleApprove(scope.row)">通过</el-button>
          <el-button v-if="scope.row.applyStatus==='PENDING'" type="text" size="mini" @click="handleReject(scope.row)">驳回</el-button>
          <el-button type="text" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="新增申请" :visible.sync="open" width="760px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-row :gutter="16">
          <el-col :span="12"><el-form-item label="酒店名称"><el-input v-model="form.hotelName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="联系人"><el-input v-model="form.contactName" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="联系电话"><el-input v-model="form.contactPhone" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="省编码"><el-input v-model="form.provinceCode" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="市编码"><el-input v-model="form.cityCode" /></el-form-item></el-col>
          <el-col :span="12"><el-form-item label="区编码"><el-input v-model="form.districtCode" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="详细地址"><el-input v-model="form.address" /></el-form-item></el-col>
          <el-col :span="24"><el-form-item label="申请备注"><el-input v-model="form.applyRemark" type="textarea" :rows="3" /></el-form-item></el-col>
        </el-row>
      </el-form>
      <div slot="footer"><el-button type="primary" @click="submitForm">确定</el-button><el-button @click="open=false">取消</el-button></div>
    </el-dialog>

    <el-drawer :visible.sync="drawer" title="申请详情" size="40%">
      <div class="drawer-body">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="酒店名称">{{ detail.hotelName }}</el-descriptions-item>
          <el-descriptions-item label="联系人">{{ detail.contactName }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ detail.contactPhone }}</el-descriptions-item>
          <el-descriptions-item label="地址">{{ detail.address }}</el-descriptions-item>
          <el-descriptions-item label="备注">{{ detail.applyRemark }}</el-descriptions-item>
        </el-descriptions>
        <el-divider>审核日志</el-divider>
        <el-timeline>
          <el-timeline-item v-for="(item,index) in auditLogs" :key="index" :timestamp="parseTime(item.operateTime)">{{ item.operateType }} {{ item.operateRemark || '' }}</el-timeline-item>
        </el-timeline>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { addPending, approvePending, delPending, getPending, listPending, rejectPending } from "@/api/hotel/cooperate/pending"
export default {
  name: "HotelPending",
  data() {
    return { loading: false, showSearch: true, total: 0, list: [], ids: [], multiple: true, open: false, drawer: false, dateRange: [], auditLogs: [], detail: {}, queryParams: { pageNum: 1, pageSize: 10, hotelName: undefined, applyStatus: undefined }, form: {} }
  },
  created() { this.getList() },
  methods: {
    buildQuery() { return { ...this.queryParams, beginApplyTime: this.dateRange && this.dateRange[0], endApplyTime: this.dateRange && this.dateRange[1] } },
    getList() { this.loading = true; listPending(this.buildQuery()).then(res => { this.list = res.rows || []; this.total = res.total || 0; this.loading = false }) },
    handleQuery() { this.queryParams.pageNum = 1; this.getList() },
    resetQuery() { this.dateRange = []; this.resetForm("queryForm"); this.handleQuery() },
    handleSelectionChange(selection) { this.ids = selection.map(item => item.id); this.multiple = !selection.length },
    handleAdd() { this.form = {}; this.open = true },
    submitForm() { addPending(this.form).then(() => { this.$modal.msgSuccess("提交成功"); this.open = false; this.getList() }) },
    handleDetail(row) { getPending(row.id).then(res => { this.detail = res.data || {}; this.auditLogs = (res.data && res.data.params && res.data.params.auditLogs) || []; this.drawer = true }) },
    handleApprove(row) { this.$prompt("请输入审核备注", "审核通过", { inputValue: "审核通过" }).then(({ value }) => approvePending({ id: row.id, auditRemark: value, commissionMode: "BASE_PRICE" }).then(() => { this.$modal.msgSuccess("审核成功"); this.getList() })) },
    handleReject(row) { this.$prompt("请输入驳回原因", "审核驳回").then(({ value }) => rejectPending({ id: row.id, auditRemark: value }).then(() => { this.$modal.msgSuccess("驳回成功"); this.getList() })) },
    handleDelete(row) { const ids = row.id || this.ids.join(","); this.$modal.confirm("确认删除选中的申请吗？").then(() => delPending(ids)).then(() => { this.$modal.msgSuccess("删除成功"); this.getList() }) }
  }
}
</script>

<style scoped>
.drawer-body { padding: 0 16px 16px; }
</style>
