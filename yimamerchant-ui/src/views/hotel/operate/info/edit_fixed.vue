<template>
  <div class="app-container">
    <el-page-header @back="$router.back()" content="Hotel Info Edit" />
    <el-card style="margin-top: 16px;">
      <el-form :model="form" label-width="110px">
        <el-tabs>
          <el-tab-pane label="Basic Info">
            <el-row :gutter="16">
              <el-col :span="12"><el-form-item label="Hotel Name"><el-input v-model="form.hotelName" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="Hotel EN"><el-input v-model="form.hotelNameEn" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="Brand"><el-input v-model="form.brandName" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="Star"><el-input v-model="form.starLevel" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="Phone"><el-input v-model="form.contactPhone" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="Biz Status">
                <el-select v-model="form.businessStatus" style="width: 100%">
                  <el-option label="Open" value="OPEN" />
                  <el-option label="Suspended" value="SUSPENDED" />
                  <el-option label="Preparing" value="PREPARING" />
                </el-select>
              </el-form-item></el-col>
              <el-col :span="8"><el-form-item label="Province"><el-input v-model="form.provinceName" /></el-form-item></el-col>
              <el-col :span="8"><el-form-item label="City"><el-input v-model="form.cityName" /></el-form-item></el-col>
              <el-col :span="8"><el-form-item label="District"><el-input v-model="form.districtName" /></el-form-item></el-col>
              <el-col :span="24"><el-form-item label="Address"><el-input v-model="form.address" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="Longitude"><el-input v-model="form.longitude" /></el-form-item></el-col>
              <el-col :span="12"><el-form-item label="Latitude"><el-input v-model="form.latitude" /></el-form-item></el-col>
              <el-col :span="24"><el-form-item label="Cover"><image-upload v-model="form.coverImage" :limit="1" /></el-form-item></el-col>
              <el-col :span="24"><el-form-item label="Banners"><image-upload v-model="form.bannerImages" :limit="8" /></el-form-item></el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="Policies">
            <el-form-item label="Checkin"><el-time-picker v-model="form.checkinTime" value-format="HH:mm" format="HH:mm" style="width: 100%" /></el-form-item>
            <el-form-item label="Checkout"><el-time-picker v-model="form.checkoutTime" value-format="HH:mm" format="HH:mm" style="width: 100%" /></el-form-item>
            <el-form-item label="Hotel Intro"><el-input v-model="form.hotelDesc" type="textarea" :rows="4" /></el-form-item>
            <el-form-item label="Booking Notice"><el-input v-model="form.bookingNotice" type="textarea" :rows="4" /></el-form-item>
            <el-form-item label="Cancel Rule"><el-input v-model="form.cancelPolicy" type="textarea" :rows="4" /></el-form-item>
            <el-form-item label="Invoice Notice"><el-input v-model="form.invoiceNotice" type="textarea" :rows="4" /></el-form-item>
            <el-form-item label="Parking Notice"><el-input v-model="form.parkingNotice" type="textarea" :rows="4" /></el-form-item>
            <el-form-item label="Child Policy"><el-input v-model="form.childPolicy" type="textarea" /></el-form-item>
            <el-form-item label="Pet Policy"><el-input v-model="form.petPolicy" type="textarea" /></el-form-item>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <div class="text-right"><el-button type="primary" @click="submit">Save</el-button></div>
    </el-card>
  </div>
</template>

<script>
import { getHotelInfo, updateHotelInfo } from "@/api/hotel/operate/info"

export default {
  name: "HotelInfoEdit",
  data() {
    return { form: {} }
  },
  created() {
    getHotelInfo(this.$route.params.hotelId).then(res => {
      this.form = res.data || {}
    })
  },
  methods: {
    submit() {
      this.form.provinceCode = this.form.provinceName
      this.form.cityCode = this.form.cityName
      this.form.districtCode = this.form.districtName
      updateHotelInfo(this.form).then(() => {
        this.$modal.msgSuccess('Save success')
        this.$router.back()
      })
    }
  }
}
</script>
