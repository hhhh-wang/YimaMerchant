<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true">
      <el-form-item label="酒店"><el-select v-model="queryParams.hotelId" filterable><el-option v-for="item in hotelOptions" :key="item.id" :label="item.label" :value="item.id" /></el-select></el-form-item>
      <el-form-item label="房型"><el-select v-model="roomTypeIds" multiple filterable><el-option v-for="item in roomTypeOptions" :key="item.id" :label="item.roomTypeName" :value="item.id" /></el-select></el-form-item>
      <el-form-item label="月份"><el-date-picker v-model="month" type="month" value-format="yyyy-MM" /></el-form-item>
      <el-form-item><el-button type="primary" size="mini" @click="getList">加载</el-button></el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="房型" prop="roomTypeName" />
      <el-table-column label="日期" prop="bizDate"><template slot-scope="scope">{{ parseTime(scope.row.bizDate, '{y}-{m}-{d}') }}</template></el-table-column>
      <el-table-column label="结算价" prop="settlementPrice" />
      <el-table-column label="卖价" prop="salePrice" />
      <el-table-column label="库存" prop="inventory" />
      <el-table-column label="房态" prop="roomStatus" />
    </el-table>
  </div>
</template>

<script>
import { getPriceCalendar } from "@/api/hotel/operate/price"
import { listRoomType } from "@/api/hotel/operate/roomType"
import { listHotelInfoOptions } from "@/api/hotel/operate/info"
export default {
  name: "HotelPrice",
  data() { return { loading: false, hotelOptions: [], roomTypeOptions: [], roomTypeIds: [], month: "", queryParams: { hotelId: undefined }, list: [] } },
  created() { listHotelInfoOptions().then(res => { this.hotelOptions = res.data || [] }) },
  watch: { 'queryParams.hotelId'(val) { if (!val) return; listRoomType({ hotelId: val }).then(res => { this.roomTypeOptions = res.rows || [] }) } },
  methods: { getList() { this.loading = true; getPriceCalendar({ hotelId: this.queryParams.hotelId, roomTypeIds: this.roomTypeIds.join(","), month: this.month }).then(res => { this.list = res.data || []; this.loading = false }) } }
}
</script>
