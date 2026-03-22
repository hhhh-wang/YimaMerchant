<template>
  <div class="app-container">
    <el-page-header @back="$router.back()" content="合作酒店详情" />
    <el-card v-loading="loading" style="margin-top: 16px;">
      <el-tabs>
        <el-tab-pane label="基础信息">
          <el-descriptions :column="2" border>
            <el-descriptions-item label="酒店名称">{{ form.hotelName }}</el-descriptions-item>
            <el-descriptions-item label="合作状态">{{ form.cooperateStatus }}</el-descriptions-item>
            <el-descriptions-item label="联系人">{{ form.contactName }}</el-descriptions-item>
            <el-descriptions-item label="联系电话">{{ form.contactPhone }}</el-descriptions-item>
            <el-descriptions-item label="地址" :span="2">{{ form.address }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="合同管理">
          <el-table :data="form.contracts || []"><el-table-column label="合同编号" prop="contractNo" /><el-table-column label="合同名称" prop="contractName" /><el-table-column label="开始日期" prop="contractStartDate" /><el-table-column label="结束日期" prop="contractEndDate" /></el-table>
        </el-tab-pane>
        <el-tab-pane label="账号管理">
          <el-table :data="form.accounts || []"><el-table-column label="账号名" prop="accountName" /><el-table-column label="昵称" prop="nickName" /><el-table-column label="手机号" prop="mobile" /><el-table-column label="状态" prop="accountStatus" /></el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import { getPartner } from "@/api/hotel/cooperate/partner"
export default {
  name: "HotelPartnerDetail",
  data() { return { loading: false, form: {} } },
  created() { this.getInfo() },
  methods: {
    getInfo() { this.loading = true; getPartner(this.$route.params.hotelId).then(res => { this.form = res.data || {}; this.loading = false }) }
  }
}
</script>
