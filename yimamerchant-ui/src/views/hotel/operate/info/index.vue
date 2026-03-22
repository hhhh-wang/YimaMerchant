<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true">
      <el-form-item label="城市"><el-input v-model="queryParams.cityName" /></el-form-item>
      <el-form-item label="状态"><el-select v-model="queryParams.saleStatus" clearable><el-option label="上架" value="ON_SHELF" /><el-option label="下架" value="OFF_SHELF" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" size="mini" @click="getList">搜索</el-button></el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="LOGO" width="90"><template slot-scope="scope"><image-preview :src="scope.row.hotelLogo" :width="40" :height="40" /></template></el-table-column>
      <el-table-column label="酒店名称" prop="hotelName" min-width="180" />
      <el-table-column label="城市" prop="cityName" width="120" />
      <el-table-column label="状态" prop="saleStatus" width="100" />
      <el-table-column label="更新时间" prop="updateTime" width="160"><template slot-scope="scope">{{ parseTime(scope.row.updateTime) }}</template></el-table-column>
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button type="text" size="mini" @click="goEdit(scope.row)">编辑</el-button>
          <el-button type="text" size="mini" @click="toggleStatus(scope.row)">{{ scope.row.saleStatus === 'ON_SHELF' ? '下架' : '上架' }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listHotelInfo, updateHotelInfoStatus } from "@/api/hotel/operate/info"
export default {
  name: "HotelInfo",
  data() { return { loading: false, total: 0, list: [], queryParams: { pageNum: 1, pageSize: 10, cityName: undefined, saleStatus: undefined } } },
  created() { this.getList() },
  methods: {
    getList() { this.loading = true; listHotelInfo(this.queryParams).then(res => { this.list = res.rows || []; this.total = res.total || 0; this.loading = false }) },
    goEdit(row) { this.$router.push("/hotel/operate/info/edit/" + row.hotelId) },
    toggleStatus(row) { updateHotelInfoStatus({ hotelId: row.hotelId, saleStatus: row.saleStatus === "ON_SHELF" ? "OFF_SHELF" : "ON_SHELF" }).then(() => { this.$modal.msgSuccess("更新成功"); this.getList() }) }
  }
}
</script>
