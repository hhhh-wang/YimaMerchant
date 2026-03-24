<template>
  <div class="app-container">
    <el-page-header :content="pageTitle" @back="$router.back()" />
    <el-card class="form-card">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-divider content-position="left">基础信息</el-divider>
        <el-row :gutter="16">
          <el-col :span="12">
            <el-form-item label="酒店名称" prop="hotelName">
              <el-input v-model.trim="form.hotelName" maxlength="128" show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系电话" prop="contactPhone">
              <el-input v-model.trim="form.contactPhone" maxlength="32" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="contactName">
              <el-input v-model.trim="form.contactName" maxlength="64" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="酒店经营状态" prop="businessStatus">
              <el-select v-model="form.businessStatus" placeholder="请选择经营状态" style="width: 100%">
                <el-option v-for="item in businessStatusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="省" prop="provinceName">
              <el-input v-model.trim="form.provinceName" maxlength="64" placeholder="请输入省" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="市" prop="cityName">
              <el-input v-model.trim="form.cityName" maxlength="64" placeholder="请输入市" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="区" prop="districtName">
              <el-input v-model.trim="form.districtName" maxlength="64" placeholder="请输入区/县" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="详细地址" prop="address">
              <el-input v-model.trim="form.address" maxlength="255" show-word-limit />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="经度" prop="longitude">
              <el-input v-model.trim="form.longitude" placeholder="121.473701" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="纬度" prop="latitude">
              <el-input v-model.trim="form.latitude" placeholder="31.230416" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">图片与时间</el-divider>
        <el-row :gutter="16">
          <el-col :span="24">
            <el-form-item label="酒店封面图" prop="coverImage">
              <image-upload v-model="form.coverImage" :limit="1" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="酒店轮播图" prop="bannerImages">
              <image-upload v-model="form.bannerImages" :limit="8" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入住时间" prop="checkinTime">
              <el-time-picker
                v-model="form.checkinTime"
                value-format="HH:mm"
                format="HH:mm"
                placeholder="请选择入住时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="离店时间" prop="checkoutTime">
              <el-time-picker
                v-model="form.checkoutTime"
                value-format="HH:mm"
                format="HH:mm"
                placeholder="请选择离店时间"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">文案信息</el-divider>
        <el-form-item label="酒店简介" prop="hotelDesc">
          <el-input v-model="form.hotelDesc" type="textarea" :rows="5" maxlength="2000" show-word-limit />
        </el-form-item>
        <el-form-item label="预订须知" prop="bookingNotice">
          <el-input v-model="form.bookingNotice" type="textarea" :rows="4" maxlength="2000" show-word-limit />
        </el-form-item>
        <el-form-item label="取消规则说明" prop="cancelPolicy">
          <el-input v-model="form.cancelPolicy" type="textarea" :rows="4" maxlength="2000" show-word-limit />
        </el-form-item>
        <el-form-item label="开票说明" prop="invoiceNotice">
          <el-input v-model="form.invoiceNotice" type="textarea" :rows="4" maxlength="2000" show-word-limit />
        </el-form-item>
        <el-form-item label="停车说明" prop="parkingNotice">
          <el-input v-model="form.parkingNotice" type="textarea" :rows="4" maxlength="2000" show-word-limit />
        </el-form-item>
        <el-form-item label="申请备注" prop="applyRemark">
          <el-input v-model="form.applyRemark" type="textarea" :rows="3" maxlength="500" show-word-limit />
        </el-form-item>

        <div class="footer-actions">
          <el-button @click="$router.back()">取消</el-button>
          <el-button type="primary" :loading="submitLoading" @click="submitForm">保存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { addPending, getPending, updatePending } from "@/api/hotel/cooperate/pending"

const businessStatusOptions = [
  { label: "营业中", value: "OPEN" },
  { label: "暂停营业", value: "SUSPENDED" },
  { label: "筹备中", value: "PREPARING" }
]

const emptyForm = () => ({
  id: undefined,
  hotelName: "",
  contactName: "",
  contactPhone: "",
  provinceCode: "",
  provinceName: "",
  cityCode: "",
  cityName: "",
  districtCode: "",
  districtName: "",
  address: "",
  longitude: "",
  latitude: "",
  coverImage: "",
  bannerImages: "",
  checkinTime: "",
  checkoutTime: "",
  hotelDesc: "",
  bookingNotice: "",
  cancelPolicy: "",
  invoiceNotice: "",
  parkingNotice: "",
  businessStatus: "OPEN",
  applyRemark: ""
})

export default {
  name: "HotelPendingForm",
  data() {
    const validateCoordinate = (rule, value, callback) => {
      if (value === undefined || value === null || value === "") {
        callback()
        return
      }
      if (!/^-?\d+(\.\d+)?$/.test(String(value))) {
        callback(new Error("请输入正确的坐标"))
        return
      }
      callback()
    }
    return {
      submitLoading: false,
      businessStatusOptions,
      form: emptyForm(),
      rules: {
        hotelName: [{ required: true, message: "请输入酒店名称", trigger: "blur" }],
        coverImage: [{ required: true, message: "请上传酒店封面图", trigger: "change" }],
        bannerImages: [{ required: true, message: "请上传酒店轮播图", trigger: "change" }],
        contactPhone: [{ required: true, message: "请输入联系电话", trigger: "blur" }],
        provinceName: [{ required: true, message: "请输入省", trigger: "blur" }],
        cityName: [{ required: true, message: "请输入市", trigger: "blur" }],
        districtName: [{ required: true, message: "请输入区/县", trigger: "blur" }],
        address: [{ required: true, message: "请输入详细地址", trigger: "blur" }],
        checkinTime: [{ required: true, message: "请选择入住时间", trigger: "change" }],
        checkoutTime: [{ required: true, message: "请选择离店时间", trigger: "change" }],
        hotelDesc: [{ required: true, message: "请输入酒店简介", trigger: "blur" }],
        bookingNotice: [{ required: true, message: "请输入预订须知", trigger: "blur" }],
        cancelPolicy: [{ required: true, message: "请输入取消规则说明", trigger: "blur" }],
        invoiceNotice: [{ required: true, message: "请输入开票说明", trigger: "blur" }],
        parkingNotice: [{ required: true, message: "请输入停车说明", trigger: "blur" }],
        businessStatus: [{ required: true, message: "请选择酒店经营状态", trigger: "change" }],
        longitude: [{ validator: validateCoordinate, trigger: "blur" }],
        latitude: [{ validator: validateCoordinate, trigger: "blur" }]
      }
    }
  },
  computed: {
    isEdit() {
      return !!this.$route.params.id
    },
    pageTitle() {
      return this.isEdit ? "编辑申请" : "新增申请"
    }
  },
  created() {
    if (this.isEdit) {
      this.loadDetail()
    }
  },
  methods: {
    loadDetail() {
      getPending(this.$route.params.id).then(res => {
        const data = res.data || {}
        if (data.applyStatus === "APPROVED") {
          this.$modal.msgWarning("已审核通过的数据不支持编辑")
          this.$router.back()
          return
        }
        this.form = Object.assign(emptyForm(), data)
      })
    },
    buildPayload() {
      const payload = Object.assign({}, this.form)
      payload.provinceCode = payload.provinceName
      payload.cityCode = payload.cityName
      payload.districtCode = payload.districtName
      payload.longitude = payload.longitude === "" ? null : payload.longitude
      payload.latitude = payload.latitude === "" ? null : payload.latitude
      return payload
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) {
          return
        }
        const api = this.isEdit ? updatePending : addPending
        this.submitLoading = true
        api(this.buildPayload()).then(() => {
          this.$modal.msgSuccess("保存成功")
          this.$router.push("/hotel/cooperate/pending")
        }).finally(() => {
          this.submitLoading = false
        })
      })
    }
  }
}
</script>

<style scoped>
.form-card {
  margin-top: 16px;
}

.footer-actions {
  margin-top: 24px;
  text-align: right;
}
</style>
