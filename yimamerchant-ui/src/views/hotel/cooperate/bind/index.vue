<template>
  <div class="app-container">
    <el-form :model="queryParams" size="small" :inline="true">
      <el-form-item label="BD"><el-input v-model="queryParams.bdUserName" clearable /></el-form-item>
      <el-form-item label="酒店"><el-input v-model="queryParams.hotelName" clearable /></el-form-item>
      <el-form-item label="状态"><el-select v-model="queryParams.bindStatus" clearable><el-option label="绑定中" value="BOUND" /><el-option label="已解绑" value="UNBOUND" /></el-select></el-form-item>
      <el-form-item><el-button type="primary" size="mini" @click="getList">搜索</el-button></el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5"><el-button type="primary" plain size="mini" @click="bindOpen=true">批量绑定</el-button></el-col>
      <el-col :span="1.5"><el-button type="warning" plain size="mini" :disabled="multiple" @click="handleUnbind">批量解绑</el-button></el-col>
    </el-row>
    <el-table v-loading="loading" :data="list" @selection-change="selectionChange">
      <el-table-column type="selection" width="55" />
      <el-table-column label="BD姓名" prop="bdUserName" />
      <el-table-column label="BD工号" prop="bdUserCode" />
      <el-table-column label="酒店名称" prop="hotelName" />
      <el-table-column label="绑定时间" prop="bindTime"><template slot-scope="scope">{{ parseTime(scope.row.bindTime) }}</template></el-table-column>
      <el-table-column label="状态" prop="bindStatus" />
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog title="批量绑定" :visible.sync="bindOpen" width="520px">
      <el-form :model="bindForm" label-width="90px">
        <el-form-item label="BD"><el-select v-model="bindForm.bdUserId" filterable><el-option v-for="item in bdOptions" :key="item.id" :label="item.label + ' / ' + item.subLabel" :value="item.id" /></el-select></el-form-item>
        <el-form-item label="酒店"><el-select v-model="bindForm.hotelIds" multiple filterable><el-option v-for="item in hotelOptions" :key="item.id" :label="item.label" :value="item.id" /></el-select></el-form-item>
        <el-form-item label="备注"><el-input v-model="bindForm.remark" type="textarea" /></el-form-item>
      </el-form>
      <div slot="footer"><el-button type="primary" @click="submitBind">确定</el-button><el-button @click="bindOpen=false">取消</el-button></div>
    </el-dialog>
  </div>
</template>

<script>
import { batchBind, batchUnbind, listBdOptions, listBind, listHotelOptions } from "@/api/hotel/cooperate/bind"
export default {
  name: "HotelBind",
  data() { return { loading: false, total: 0, list: [], ids: [], multiple: true, bindOpen: false, bdOptions: [], hotelOptions: [], queryParams: { pageNum: 1, pageSize: 10 }, bindForm: { bdUserId: undefined, hotelIds: [], remark: undefined } } },
  created() { this.getList(); this.loadOptions() },
  methods: {
    getList() { this.loading = true; listBind(this.queryParams).then(res => { this.list = res.rows || []; this.total = res.total || 0; this.loading = false }) },
    loadOptions() { listBdOptions().then(res => { this.bdOptions = res.data || [] }); listHotelOptions().then(res => { this.hotelOptions = res.data || [] }) },
    selectionChange(selection) { this.ids = selection.map(item => item.id); this.multiple = !selection.length },
    submitBind() { batchBind(this.bindForm).then(() => { this.$modal.msgSuccess("绑定成功"); this.bindOpen = false; this.getList() }) },
    handleUnbind() { batchUnbind(this.ids.join(",")).then(() => { this.$modal.msgSuccess("解绑成功"); this.getList() }) }
  }
}
</script>
