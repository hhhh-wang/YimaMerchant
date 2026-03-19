<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          :disabled="!!hotelRow"
          @click="handleAdd"
          v-hasPermi="['hotel:info:edit']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="!hotelRow"
          @click="handleEdit"
          v-hasPermi="['hotel:info:edit']"
        >编辑</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button icon="el-icon-refresh" size="mini" @click="loadInfo">刷新</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="hotelRow ? [hotelRow] : []" empty-text="暂无酒店信息，请点击“新增”完善资料">
      <el-table-column label="酒店名称" prop="hotelName" min-width="180" :show-overflow-tooltip="true" />
      <el-table-column label="联系电话" prop="phone" width="140" />
      <el-table-column label="状态" prop="status" width="120">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="140" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleEdit(scope.row)" v-hasPermi="['hotel:info:edit']">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { getHotelInfo } from "@/api/hotel/info"

export default {
  name: "HotelInfoList",
  dicts: ["hotel_status"],
  data() {
    return {
      loading: false,
      hotelRow: null
    }
  },
  created() {
    this.loadInfo()
  },
  methods: {
    loadInfo() {
      this.loading = true
      getHotelInfo()
        .then(res => {
          this.hotelRow = res.data || null
        })
        .finally(() => {
          this.loading = false
        })
    },
    handleAdd() {
      this.$router.push({ path: "/hotel/info/data", query: { mode: "add" } })
    },
    handleEdit() {
      this.$router.push({ path: "/hotel/info/data", query: { mode: "edit" } })
    }
  }
}
</script>

<style scoped>
</style>

