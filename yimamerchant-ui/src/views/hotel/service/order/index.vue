<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true">
      <el-form-item label="订单号"><el-input v-model="queryParams.orderNo" /></el-form-item>
      <el-form-item label="状态"><el-input v-model="queryParams.orderStatus" /></el-form-item>
      <el-form-item><el-button type="primary" size="mini" @click="getList">搜索</el-button></el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="订单号" prop="orderNo" min-width="180" />
      <el-table-column label="酒店" prop="hotelName" min-width="160" />
      <el-table-column label="房型" prop="roomTypeName" />
      <el-table-column label="入住/离店" width="180"><template slot-scope="scope">{{ parseTime(scope.row.checkinDate, '{y}-{m}-{d}') }} / {{ parseTime(scope.row.checkoutDate, '{y}-{m}-{d}') }}</template></el-table-column>
      <el-table-column label="金额" prop="orderAmount" />
      <el-table-column label="状态" prop="orderStatus" />
      <el-table-column label="操作" width="150"><template slot-scope="scope"><el-button type="text" size="mini" @click="showDetail(scope.row)">详情</el-button><el-button type="text" size="mini" @click="confirmOrder(scope.row)">确认</el-button></template></el-table-column>
    </el-table>
    <el-drawer :visible.sync="drawer" title="订单详情" size="45%"><pre>{{ JSON.stringify(detail, null, 2) }}</pre></el-drawer>
  </div>
</template>

<script>
import { confirmHotelOrder, getHotelOrder, listHotelOrder } from "@/api/hotel/service/order"
export default {
  name: "HotelOrder",
  data() { return { loading: false, list: [], drawer: false, detail: {}, queryParams: { orderNo: undefined, orderStatus: undefined } } },
  methods: {
    getList() { this.loading = true; listHotelOrder(this.queryParams).then(res => { this.list = res.rows || []; this.loading = false }) },
    showDetail(row) { getHotelOrder(row.orderNo).then(res => { this.detail = res.data || {}; this.drawer = true }) },
    confirmOrder(row) { confirmHotelOrder({ orderNo: row.orderNo }).then(() => { this.$modal.msgSuccess("确认成功"); this.getList() }) }
  }
}
</script>
