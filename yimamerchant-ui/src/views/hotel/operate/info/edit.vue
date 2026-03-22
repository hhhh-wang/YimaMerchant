<template>
  <div class="app-container">
    <el-page-header @back="$router.back()" content="酒店信息编辑" />
    <el-card style="margin-top: 16px;">
      <el-form :model="form" label-width="100px">
        <el-tabs>
          <el-tab-pane label="基础信息">
            <el-row :gutter="16">
              <el-col :span="12"><el-form-item label="酒店名称"><el-input v-model="form.hotelName" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="英文名"><el-input v-model="form.hotelNameEn" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="品牌"><el-input v-model="form.brandName" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="星级"><el-input v-model="form.starLevel" /></el-form-item></el-col>
              <el-col :span="24"><el-form-item label="地址"><el-input v-model="form.address" /></el-form-item></el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="政策设置">
            <el-form-item label="入住时间"><el-input v-model="form.checkinTime" /></el-form-item>
            <el-form-item label="离店时间"><el-input v-model="form.checkoutTime" /></el-form-item>
            <el-form-item label="儿童政策"><el-input v-model="form.childPolicy" type="textarea" /></el-form-item>
            <el-form-item label="宠物政策"><el-input v-model="form.petPolicy" type="textarea" /></el-form-item>
            <el-form-item label="取消政策"><el-input v-model="form.cancelPolicy" type="textarea" /></el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <div class="text-right"><el-button type="primary" @click="submit">保存</el-button></div>
    </el-card>
  </div>
</template>

<script>
import { getHotelInfo, updateHotelInfo } from "@/api/hotel/operate/info"
export default {
  name: "HotelInfoEdit",
  data() { return { form: {} } },
  created() { getHotelInfo(this.$route.params.hotelId).then(res => { this.form = res.data || {} }) },
  methods: { submit() { updateHotelInfo(this.form).then(() => { this.$modal.msgSuccess("保存成功"); this.$router.back() }) } }
}
</script>
