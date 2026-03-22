<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true">
      <el-form-item label="酒店ID"><el-input v-model="queryParams.hotelId" /></el-form-item>
      <el-form-item label="状态"><el-input v-model="queryParams.billStatus" /></el-form-item>
      <el-form-item><el-button type="primary" size="mini" @click="handleQuery">搜索</el-button><el-button type="primary" plain size="mini" @click="open=true">生成账单</el-button></el-form-item>
    </el-form>
    <el-row :gutter="16" class="mb16">
      <el-col :span="12"><el-card>当前总账单数：{{ stats.totalCount || 0 }}</el-card></el-col>
      <el-col :span="12"><el-card>当前总金额：{{ stats.monthlySalesAmount || 0 }}</el-card></el-col>
    </el-row>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="账单号" prop="billNo" min-width="180" />
      <el-table-column label="酒店名称" prop="hotelName" min-width="160" />
      <el-table-column label="结算周期" width="210"><template slot-scope="scope">{{ parseTime(scope.row.statementStartDate, '{y}-{m}-{d}') }} ~ {{ parseTime(scope.row.statementEndDate, '{y}-{m}-{d}') }}</template></el-table-column>
      <el-table-column label="订单数" prop="orderCount" />
      <el-table-column label="结算金额" prop="totalSettlementAmount" />
      <el-table-column label="状态" prop="billStatus" />
      <el-table-column label="操作" width="120"><template slot-scope="scope"><el-button type="text" size="mini" @click="$router.push('/hotel/finance/bill-detail/' + scope.row.billNo)">详情</el-button></template></el-table-column>
    </el-table>
    <el-dialog title="生成账单" :visible.sync="open" width="500px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="酒店ID"><el-input v-model="form.hotelId" /></el-form-item>
        <el-form-item label="开始日期"><el-date-picker v-model="form.statementStartDate" type="date" value-format="yyyy-MM-dd" /></el-form-item>
        <el-form-item label="结束日期"><el-date-picker v-model="form.statementEndDate" type="date" value-format="yyyy-MM-dd" /></el-form-item>
      </el-form>
      <div slot="footer"><el-button type="primary" @click="submitGenerate">生成</el-button><el-button @click="open=false">取消</el-button></div>
    </el-dialog>
  </div>
</template>

<script>
import { generateHotelBill, getHotelBillStatistics, listHotelBill } from "@/api/hotel/finance/bill"
export default {
  name: "HotelBill",
  data() { return { loading: false, open: false, list: [], stats: {}, queryParams: { hotelId: undefined, billStatus: undefined }, form: { generateMode: "MANUAL" } } },
  created() { this.handleQuery() },
  methods: {
    handleQuery() { this.loading = true; listHotelBill(this.queryParams).then(res => { this.list = res.rows || []; this.loading = false }); getHotelBillStatistics(this.queryParams).then(res => { this.stats = res.data || {} }) },
    submitGenerate() { generateHotelBill(this.form).then(() => { this.$modal.msgSuccess("账单已生成"); this.open = false; this.handleQuery() }) }
  }
}
</script>

<style scoped>.mb16{margin-bottom:16px;}</style>
