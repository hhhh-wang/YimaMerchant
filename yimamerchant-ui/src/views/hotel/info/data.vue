<template>
  <div class="app-container">
    <el-card shadow="never">
      <div slot="header" class="card-header">
        <span>{{ pageTitle }}</span>
        <div class="header-actions">
          <el-button icon="el-icon-back" size="mini" @click="goBack">返回</el-button>
          <el-button type="primary" icon="el-icon-check" size="mini" :loading="saving" @click="submitForm" v-hasPermi="['hotel:info:edit']">保存</el-button>
        </div>
      </div>

      <el-skeleton :rows="10" animated v-if="loading" />

      <el-form v-else ref="form" :model="form" :rules="rules" label-width="110px" size="small">
        <el-divider content-position="left">基础信息</el-divider>

        <el-form-item label="酒店名称" prop="hotelName">
          <el-input v-model="form.hotelName" maxlength="50" show-word-limit placeholder="请输入酒店名称" />
        </el-form-item>

        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" maxlength="20" placeholder="请输入联系电话" />
        </el-form-item>

        <el-form-item label="酒店状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="d in dict.type.hotel_status" :key="d.value" :label="d.value">{{ d.label }}</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="省/市/区编码" required>
          <el-row :gutter="8">
            <el-col :span="8">
              <el-form-item prop="provinceCode" label-width="0">
                <el-input v-model="form.provinceCode" placeholder="省编码" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="cityCode" label-width="0">
                <el-input v-model="form.cityCode" placeholder="市编码" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item prop="districtCode" label-width="0">
                <el-input v-model="form.districtCode" placeholder="区编码" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form-item>

        <el-form-item label="详细地址" prop="address">
          <el-input v-model="form.address" maxlength="255" show-word-limit placeholder="请输入详细地址" />
        </el-form-item>

        <el-form-item label="经度">
          <el-input-number v-model="form.longitude" :precision="6" :step="0.000001" :controls="false" style="width: 240px" />
        </el-form-item>
        <el-form-item label="纬度">
          <el-input-number v-model="form.latitude" :precision="6" :step="0.000001" :controls="false" style="width: 240px" />
        </el-form-item>

        <el-form-item label="入住时间" prop="checkInTime">
          <el-time-picker v-model="form.checkInTime" value-format="HH:mm" format="HH:mm" placeholder="选择入住时间" />
        </el-form-item>
        <el-form-item label="离店时间" prop="checkOutTime">
          <el-time-picker v-model="form.checkOutTime" value-format="HH:mm" format="HH:mm" placeholder="选择离店时间" />
        </el-form-item>

        <el-divider content-position="left">图片</el-divider>

        <el-form-item label="封面图" prop="hotelCover">
          <image-upload v-model="form.hotelCover" :limit="1" />
        </el-form-item>

        <el-form-item label="轮播图">
          <image-upload v-model="hotelImagesStr" :limit="9" />
          <div class="help-text">建议最多 9 张，拖拽可排序</div>
        </el-form-item>

        <el-divider content-position="left">文案信息</el-divider>

        <el-form-item label="酒店简介">
          <el-input v-model="form.intro" type="textarea" :rows="4" placeholder="请输入酒店简介" />
        </el-form-item>
        <el-form-item label="预订须知">
          <el-input v-model="form.bookingNotice" type="textarea" :rows="4" placeholder="请输入预订须知" />
        </el-form-item>
        <el-form-item label="取消规则">
          <el-input v-model="form.cancelRule" type="textarea" :rows="4" placeholder="请输入取消规则" />
        </el-form-item>
        <el-form-item label="开票说明">
          <el-input v-model="form.invoiceDesc" type="textarea" :rows="3" placeholder="请输入开票说明" />
        </el-form-item>
        <el-form-item label="停车说明">
          <el-input v-model="form.parkingDesc" type="textarea" :rows="3" placeholder="请输入停车说明" />
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getHotelInfo, saveHotelInfo } from "@/api/hotel/info"

export default {
  name: "HotelInfoData",
  dicts: ["hotel_status"],
  data() {
    return {
      loading: false,
      saving: false,
      pageTitle: "新增酒店信息",
      hotelImagesStr: "",
      form: this.getEmptyForm(),
      rules: {
        hotelName: [{ required: true, message: "酒店名称不能为空", trigger: "blur" }],
        hotelCover: [{ required: true, message: "酒店封面图不能为空", trigger: "blur" }],
        phone: [{ required: true, message: "联系电话不能为空", trigger: "blur" }],
        provinceCode: [{ required: true, message: "省编码不能为空", trigger: "blur" }],
        cityCode: [{ required: true, message: "市编码不能为空", trigger: "blur" }],
        districtCode: [{ required: true, message: "区编码不能为空", trigger: "blur" }],
        address: [{ required: true, message: "详细地址不能为空", trigger: "blur" }],
        checkInTime: [{ required: true, message: "入住时间不能为空", trigger: "change" }],
        checkOutTime: [{ required: true, message: "离店时间不能为空", trigger: "change" }],
        status: [{ required: true, message: "酒店状态不能为空", trigger: "change" }]
      }
    }
  },
  created() {
    this.initPage()
  },
  methods: {
    getEmptyForm() {
      return {
        hotelName: "",
        hotelCover: "",
        phone: "",
        provinceCode: "",
        cityCode: "",
        districtCode: "",
        address: "",
        longitude: undefined,
        latitude: undefined,
        checkInTime: "",
        checkOutTime: "",
        intro: "",
        bookingNotice: "",
        cancelRule: "",
        invoiceDesc: "",
        parkingDesc: "",
        status: "1",
        remark: ""
      }
    },
    toArrayFromUploadString(str) {
      if (!str) return []
      return String(str)
        .split(",")
        .map(s => s.trim())
        .filter(Boolean)
    },
    initPage() {
      this.loading = true
      getHotelInfo()
        .then(res => {
          const data = res.data
          const mode = (this.$route.query && this.$route.query.mode) || "edit"
          if (!data) {
            this.pageTitle = "新增酒店信息"
            this.form = this.getEmptyForm()
            this.hotelImagesStr = ""
            return
          }
          // 即使是 add 模式，如果已经存在，也按编辑展示（单店模型）
          this.pageTitle = mode === "add" ? "新增酒店信息（已存在将覆盖更新）" : "编辑酒店信息"
          this.form = Object.assign(this.getEmptyForm(), data)
          this.hotelImagesStr = Array.isArray(data.hotelImages) ? data.hotelImages.join(",") : ""
        })
        .finally(() => {
          this.loading = false
        })
    },
    goBack() {
      // 返回列表页（菜单页）
      //this.$router.go(-1)
      this.$router.push({ path: "/hotel/hotelInfo" })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) return
        this.saving = true
        const payload = Object.assign({}, this.form, {
          hotelImages: this.toArrayFromUploadString(this.hotelImagesStr)
        })
        // 后端 save 不需要传 id / merchantId
        delete payload.id
        delete payload.merchantId
        delete payload.createTime
        delete payload.updateTime

        saveHotelInfo(payload)
          .then(() => {
            this.$modal.msgSuccess("保存成功")
            this.goBack()
          })
          .finally(() => {
            this.saving = false
          })
      })
    }
  }
}
</script>

<style scoped>
.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.header-actions {
  display: flex;
  gap: 8px;
}
.help-text {
  margin-top: 6px;
  color: #909399;
  font-size: 12px;
}
</style>

