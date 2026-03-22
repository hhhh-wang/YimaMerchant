<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true">
      <el-form-item label="酒店名称"><el-input v-model="queryParams.hotelName" clearable @keyup.enter.native="getList" /></el-form-item>
      <el-form-item label="合作状态">
        <el-select v-model="queryParams.cooperateStatus" clearable>
          <el-option label="正常" value="NORMAL" />
          <el-option label="暂停" value="PAUSED" />
          <el-option label="终止" value="TERMINATED" />
        </el-select>
      </el-form-item>
      <el-form-item><el-button type="primary" size="mini" @click="handleQuery">搜索</el-button><el-button size="mini" @click="resetQuery">重置</el-button></el-form-item>
    </el-form>
    <el-row :gutter="16" class="mb16">
      <el-col :span="8"><el-card shadow="hover"><div slot="header">总合作酒店数</div><div class="stat-num">{{ stats.totalCount || 0 }}</div></el-card></el-col>
      <el-col :span="8"><el-card shadow="hover"><div slot="header">正常合作数</div><div class="stat-num">{{ stats.normalCount || 0 }}</div></el-card></el-col>
      <el-col :span="8"><el-card shadow="hover"><div slot="header">本月新增数</div><div class="stat-num">{{ stats.monthlyNewCount || 0 }}</div></el-card></el-col>
    </el-row>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="酒店ID" prop="hotelId" width="90" />
      <el-table-column label="酒店名称" prop="hotelName" min-width="180" />
      <el-table-column label="合作状态" prop="cooperateStatus" width="100" />
      <el-table-column label="签约时间" prop="signDate" width="160"><template slot-scope="scope">{{ parseTime(scope.row.signDate) }}</template></el-table-column>
      <el-table-column label="所属BD" prop="bdUserName" width="120" />
      <el-table-column label="本月订单数" prop="monthlyOrderCount" width="100" />
      <el-table-column label="本月销售额" prop="monthlySaleAmount" width="120" />
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="goDetail(scope.row)">详情</el-button>
          <el-button type="text" size="mini" @click="changeStatus(scope.row)">状态调整</el-button>
          <el-button type="text" size="mini" @click="resetPwd(scope.row)">重置密码</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { getPartnerStatistics, listPartner, resetPartnerPassword, updatePartnerStatus } from "@/api/hotel/cooperate/partner"
export default {
  name: "HotelPartner",
  data() { return { loading: false, total: 0, list: [], stats: {}, queryParams: { pageNum: 1, pageSize: 10, hotelName: undefined, cooperateStatus: undefined } } },
  created() { this.getList(); this.getStats() },
  methods: {
    getList() { this.loading = true; listPartner(this.queryParams).then(res => { this.list = res.rows || []; this.total = res.total || 0; this.loading = false }) },
    getStats() { getPartnerStatistics(this.queryParams).then(res => { this.stats = res.data || {} }) },
    handleQuery() { this.queryParams.pageNum = 1; this.getList(); this.getStats() },
    resetQuery() { this.resetForm("queryForm"); this.handleQuery() },
    goDetail(row) { this.$router.push("/hotel/cooperate/partner/detail/" + row.hotelId) },
    changeStatus(row) { this.$prompt("请输入处理原因", "调整合作状态", { inputValue: "业务调整" }).then(({ value }) => updatePartnerStatus({ hotelId: row.hotelId, cooperateStatus: row.cooperateStatus === "NORMAL" ? "PAUSED" : "NORMAL", operateReason: value }).then(() => { this.$modal.msgSuccess("更新成功"); this.handleQuery() })) },
    resetPwd(row) { resetPartnerPassword(row.hotelId).then(() => this.$modal.msgSuccess("密码已重置")) }
  }
}
</script>

<style scoped>
.mb16 { margin-bottom: 16px; }
.stat-num { font-size: 28px; font-weight: 700; color: #303133; }
</style>
