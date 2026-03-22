<template>
  <div class="app-container">
    <el-page-header @back="$router.back()" content="账单详情" />
    <el-card v-loading="loading" style="margin-top: 16px;">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="账单号">{{ detail.billNo }}</el-descriptions-item>
        <el-descriptions-item label="酒店">{{ detail.hotelName }}</el-descriptions-item>
        <el-descriptions-item label="结算金额">{{ detail.totalSettlementAmount }}</el-descriptions-item>
        <el-descriptions-item label="状态">{{ detail.billStatus }}</el-descriptions-item>
      </el-descriptions>
      <el-divider>订单明细</el-divider>
      <el-table :data="detail.orderList || []"><el-table-column label="订单号" prop="orderNo" /><el-table-column label="房型" prop="roomTypeName" /><el-table-column label="订单金额" prop="orderAmount" /><el-table-column label="结算金额" prop="settlementAmount" /></el-table>
    </el-card>
  </div>
</template>

<script>
import { getHotelBill } from "@/api/hotel/finance/bill"
export default {
  name: "HotelBillDetail",
  data() { return { loading: false, detail: {} } },
  created() { this.loading = true; getHotelBill(this.$route.params.billNo).then(res => { this.detail = res.data || {}; this.loading = false }) }
}
</script>
