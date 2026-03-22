<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true">
      <el-form-item label="酒店"><el-select v-model="queryParams.hotelId" filterable clearable><el-option v-for="item in hotelOptions" :key="item.id" :label="item.label" :value="item.id" /></el-select></el-form-item>
      <el-form-item label="房型名称"><el-input v-model="queryParams.roomTypeName" /></el-form-item>
      <el-form-item label="状态"><el-select v-model="queryParams.saleStatus" clearable><el-option label="在售" value="ON_SALE" /><el-option label="停售" value="OFF_SALE" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" size="mini" @click="getList">搜索</el-button><el-button type="primary" plain size="mini" @click="handleAdd">新增</el-button></el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="list">
      <el-table-column label="房型名称" prop="roomTypeName" />
      <el-table-column label="床型" prop="bedType" />
      <el-table-column label="入住人数" width="100"><template slot-scope="scope">{{ scope.row.adultCount }}/{{ scope.row.childCount }}</template></el-table-column>
      <el-table-column label="面积" prop="areaSize" />
      <el-table-column label="窗户" prop="windowStatus" />
      <el-table-column label="状态" prop="saleStatus" />
      <el-table-column label="操作" width="180"><template slot-scope="scope"><el-button type="text" size="mini" @click="handleEdit(scope.row)">编辑</el-button><el-button type="text" size="mini" @click="handleDelete(scope.row)">删除</el-button></template></el-table-column>
    </el-table>
    <el-dialog :title="title" :visible.sync="open" width="680px">
      <el-form :model="form" label-width="90px">
        <el-form-item label="酒店"><el-select v-model="form.hotelId" filterable><el-option v-for="item in hotelOptions" :key="item.id" :label="item.label" :value="item.id" /></el-select></el-form-item>
        <el-form-item label="房型名称"><el-input v-model="form.roomTypeName" /></el-form-item>
        <el-form-item label="床型"><el-input v-model="form.bedType" /></el-form-item>
        <el-form-item label="面积"><el-input v-model="form.areaSize" /></el-form-item>
      </el-form>
      <div slot="footer"><el-button type="primary" @click="submitForm">确定</el-button><el-button @click="open=false">取消</el-button></div>
    </el-dialog>
  </div>
</template>

<script>
import { addRoomType, delRoomType, getRoomType, listRoomType, updateRoomType } from "@/api/hotel/operate/roomType"
import { listHotelInfoOptions } from "@/api/hotel/operate/info"
export default {
  name: "HotelRoomType",
  data() { return { loading: false, list: [], hotelOptions: [], open: false, title: "", queryParams: { hotelId: undefined, roomTypeName: undefined, saleStatus: undefined }, form: {} } },
  created() { listHotelInfoOptions().then(res => { this.hotelOptions = res.data || [] }) },
  methods: {
    getList() { this.loading = true; listRoomType(this.queryParams).then(res => { this.list = res.rows || []; this.loading = false }) },
    handleAdd() { this.title = "新增房型"; this.form = {}; this.open = true },
    handleEdit(row) { this.title = "编辑房型"; getRoomType(row.id).then(res => { this.form = res.data || {}; this.open = true }) },
    submitForm() { const api = this.form.id ? updateRoomType : addRoomType; api(this.form).then(() => { this.$modal.msgSuccess("保存成功"); this.open = false; this.getList() }) },
    handleDelete(row) { delRoomType(row.id).then(() => { this.$modal.msgSuccess("删除成功"); this.getList() }) }
  }
}
</script>
