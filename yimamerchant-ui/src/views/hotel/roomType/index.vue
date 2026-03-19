<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="80px"
    >
      <el-form-item label="房型名称" prop="roomTypeName">
        <el-input
          v-model="queryParams.roomTypeName"
          placeholder="请输入房型名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配置状态" prop="configStatus">
        <el-select
          v-model="queryParams.configStatus"
          placeholder="请选择配置状态"
          clearable
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.hotel_room_config_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="上架状态" prop="saleStatus">
        <el-select
          v-model="queryParams.saleStatus"
          placeholder="请选择上架状态"
          clearable
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.hotel_room_sale_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['hotel:roomType:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleEdit"
          v-hasPermi="['hotel:roomType:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['hotel:roomType:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <!-- 列表 -->
    <el-table
      v-loading="loading"
      :data="roomTypeList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="房型名称" prop="roomTypeName" min-width="160" :show-overflow-tooltip="true" />
      <el-table-column label="房型编码" prop="roomTypeCode" width="140" />
      <el-table-column label="图片" width="120">
        <template slot-scope="scope">
          <el-image
            v-if="scope.row.firstImage"
            :src="scope.row.firstImage"
            :preview-src-list="scope.row.roomImages || []"
            style="width: 60px; height: 60px"
            fit="cover"
          />
        </template>
      </el-table-column>
      <el-table-column label="床型" prop="bedType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_room_bed_type" :value="scope.row.bedType" />
        </template>
      </el-table-column>
      <el-table-column label="可住人数" prop="peopleLimit" width="90" align="center" />
      <el-table-column label="默认价格" prop="basePrice" width="110" align="right">
        <template slot-scope="scope">
          <span>{{ scope.row.basePrice | price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="默认库存" prop="baseStock" width="90" align="center" />
      <el-table-column label="剩余可售" prop="availableNum" width="100" align="center" />
      <el-table-column label="配置状态" prop="configStatus" width="110">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_room_config_status" :value="scope.row.configStatus" />
        </template>
      </el-table-column>
      <el-table-column label="上架状态" prop="saleStatus" width="110">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_room_sale_status" :value="scope.row.saleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="可预订" prop="bookableFlag" width="90">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_bookable_flag" :value="scope.row.bookableFlag" />
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['hotel:roomType:query']"
          >详情</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
            v-hasPermi="['hotel:roomType:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.saleStatus === '1' ? 'el-icon-bottom' : 'el-icon-top'"
            @click="handleChangeSaleStatus(scope.row)"
            v-hasPermi="['hotel:roomType:changeStatus']"
          >{{ scope.row.saleStatus === '1' ? '下架' : '上架' }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 新增 / 编辑 弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="房型名称" prop="roomTypeName">
              <el-input v-model="form.roomTypeName" placeholder="请输入房型名称" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="房型编码" prop="roomTypeCode">
              <el-input v-model="form.roomTypeCode" placeholder="请输入房型编码" maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="房型图片" prop="roomImages">
              <!-- 这里可替换成你项目里的统一上传组件 -->
              <el-input
                v-model="roomImagesStr"
                placeholder="请输入图片URL，多个用逗号分隔，后续可替换成上传组件"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="床型" prop="bedType">
              <el-select v-model="form.bedType" placeholder="请选择床型">
                <el-option
                  v-for="dict in dict.type.hotel_room_bed_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="可住人数" prop="peopleLimit">
              <el-input-number v-model="form.peopleLimit" :min="1" :max="10" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="面积" prop="area">
              <el-input v-model="form.area" placeholder="如 30㎡" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="楼层" prop="floorDesc">
              <el-input v-model="form.floorDesc" placeholder="如 10-15层" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="窗型" prop="windowType">
              <el-select v-model="form.windowType" placeholder="请选择窗型">
                <el-option
                  v-for="dict in dict.type.hotel_window_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="早餐数" prop="breakfastCount">
              <el-input-number v-model="form.breakfastCount" :min="0" :max="10" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="是否可加床" prop="extraBedFlag">
              <el-radio-group v-model="form.extraBedFlag">
                <el-radio label="Y">是</el-radio>
                <el-radio label="N">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排序号" prop="sortNum">
              <el-input-number v-model="form.sortNum" :min="0" :max="9999" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="默认价格" prop="basePrice">
              <el-input-number v-model="form.basePrice" :min="0" :precision="2" :step="1" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="划线价" prop="marketPrice">
              <el-input-number v-model="form.marketPrice" :min="0" :precision="2" :step="1" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="默认库存" prop="baseStock">
              <el-input-number v-model="form.baseStock" :min="0" :max="9999" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="配置状态" prop="configStatus">
              <el-radio-group v-model="form.configStatus">
                <el-radio
                  v-for="dict in dict.type.hotel_room_config_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="可预订" prop="bookableFlag">
              <el-radio-group v-model="form.bookableFlag">
                <el-radio
                  v-for="dict in dict.type.hotel_bookable_flag"
                  :key="dict.value"
                  :label="dict.value"
                >{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="房型描述" prop="description">
              <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入房型描述" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="设施配置" prop="facilityIds">
              <el-checkbox-group v-model="form.facilityIds">
                <el-checkbox
                  v-for="item in facilityOptions"
                  :key="item.id"
                  :label="item.id"
                >{{ item.facilityName }}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="2" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情弹窗（只读） -->
    <el-dialog title="房型详情" :visible.sync="detailOpen" width="720px" append-to-body>
      <div v-if="detail">
      <el-descriptions :column="2" border size="small">
        <el-descriptions-item label="房型名称">{{ detail.roomTypeName }}</el-descriptions-item>
        <el-descriptions-item label="房型编码">{{ detail.roomTypeCode }}</el-descriptions-item>
        <el-descriptions-item label="床型">
          <dict-tag :options="dict.type.hotel_room_bed_type" :value="detail.bedType" />
        </el-descriptions-item>
        <el-descriptions-item label="可住人数">{{ detail.peopleLimit }}</el-descriptions-item>
        <el-descriptions-item label="面积">{{ detail.area }}</el-descriptions-item>
        <el-descriptions-item label="楼层">{{ detail.floorDesc }}</el-descriptions-item>
        <el-descriptions-item label="窗型">
          <dict-tag :options="dict.type.hotel_window_type" :value="detail.windowType" />
        </el-descriptions-item>
        <el-descriptions-item label="早餐数">{{ detail.breakfastCount }}</el-descriptions-item>
        <el-descriptions-item label="默认价格">{{ detail.basePrice | price }}</el-descriptions-item>
        <el-descriptions-item label="划线价">{{ detail.marketPrice | price }}</el-descriptions-item>
        <el-descriptions-item label="默认库存">{{ detail.baseStock }}</el-descriptions-item>
        <el-descriptions-item label="已售数量">{{ detail.soldNum }}</el-descriptions-item>
        <el-descriptions-item label="剩余可售">{{ detail.availableNum }}</el-descriptions-item>
        <el-descriptions-item label="配置状态">
          <dict-tag :options="dict.type.hotel_room_config_status" :value="detail.configStatus" />
        </el-descriptions-item>
        <el-descriptions-item label="上架状态">
          <dict-tag :options="dict.type.hotel_room_sale_status" :value="detail.saleStatus" />
        </el-descriptions-item>
        <el-descriptions-item label="可预订">
          <dict-tag :options="dict.type.hotel_bookable_flag" :value="detail.bookableFlag" />
        </el-descriptions-item>
      </el-descriptions>
      <div style="margin-top: 10px">
        <div style="margin-bottom: 5px; font-weight: 600">房型图片</div>
        <el-image
          v-for="(img, idx) in (detail.roomImages || [])"
          :key="idx"
          :src="img"
          :preview-src-list="detail.roomImages || []"
          style="width: 80px; height: 80px; margin-right: 10px"
          fit="cover"
        />
      </div>
      <div style="margin-top: 10px">
        <div style="margin-bottom: 5px; font-weight: 600">房型描述</div>
        <div>{{ detail.description }}</div>
      </div>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  listRoomType,
  getRoomType,
  addRoomType,
  updateRoomType,
  delRoomType,
  changeRoomTypeSaleStatus,
  checkRoomTypeComplete
} from "@/api/hotel/roomType"
import { listFacility } from "@/api/hotel/facility"

export default {
  name: "HotelRoomType",
  dicts: [
    "hotel_room_config_status",
    "hotel_room_sale_status",
    "hotel_bookable_flag",
    "hotel_room_bed_type",
    "hotel_window_type"
  ],
  data() {
    return {
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      total: 0,
      roomTypeList: [],
      // 新增/编辑弹窗
      title: "",
      open: false,
      // 详情弹窗
      detailOpen: false,
      detail: null,
      // 设施列表
      facilityOptions: [],
      // 表单
      form: {
        id: undefined,
        roomTypeName: undefined,
        roomTypeCode: undefined,
        roomImages: [],
        bedType: undefined,
        peopleLimit: 2,
        area: undefined,
        floorDesc: undefined,
        windowType: undefined,
        breakfastCount: 0,
        extraBedFlag: "N",
        description: undefined,
        basePrice: undefined,
        marketPrice: undefined,
        baseStock: 0,
        configStatus: "1",
        saleStatus: "0",
        bookableFlag: "Y",
        sortNum: 0,
        remark: undefined,
        facilityIds: []
      },
      // 表单校验
      rules: {
        roomTypeName: [{ required: true, message: "房型名称不能为空", trigger: "blur" }],
        roomTypeCode: [{ required: true, message: "房型编码不能为空", trigger: "blur" }],
        bedType: [{ required: true, message: "床型不能为空", trigger: "change" }],
        peopleLimit: [{ required: true, message: "可住人数不能为空", trigger: "change" }],
        configStatus: [{ required: true, message: "配置状态不能为空", trigger: "change" }],
        bookableFlag: [{ required: true, message: "是否可预订不能为空", trigger: "change" }]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomTypeName: undefined,
        configStatus: undefined,
        saleStatus: undefined
      },
      // 图片输入字符串（逗号分隔）
      roomImagesStr: ""
    }
  },
  created() {
    this.getList()
    this.loadFacilityOptions()
  },
  filters: {
    price(val) {
      if (val === null || val === undefined) {
        return ""
      }
      return Number(val).toFixed(2)
    }
  },
  methods: {
    /** 查询设施列表（默认只取启用） */
    loadFacilityOptions() {
      listFacility({ status: "1" }).then(res => {
        this.facilityOptions = res.data || []
      })
    },
    /** 查询房型列表 */
    getList() {
      this.loading = true
      listRoomType(this.queryParams)
        .then(res => {
          this.roomTypeList = res.rows || []
          this.total = res.total || 0
        })
        .finally(() => {
          this.loading = false
        })
    },
    // 多选框选中
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 搜索
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    // 重置
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 取消
    cancel() {
      this.open = false
    },
    // 重置表单
    reset() {
      this.form = {
        id: undefined,
        roomTypeName: undefined,
        roomTypeCode: undefined,
        roomImages: [],
        bedType: undefined,
        peopleLimit: 2,
        area: undefined,
        floorDesc: undefined,
        windowType: undefined,
        breakfastCount: 0,
        extraBedFlag: "N",
        description: undefined,
        basePrice: undefined,
        marketPrice: undefined,
        baseStock: 0,
        configStatus: "1",
        saleStatus: "0",
        bookableFlag: "Y",
        sortNum: 0,
        remark: undefined,
        facilityIds: []
      }
      this.roomImagesStr = ""
      this.resetForm("form")
    },
    // 新增
    handleAdd() {
      this.reset()
      this.title = "新增房型"
      this.open = true
    },
    // 修改
    handleEdit(row) {
      const id = row && row.id ? row.id : this.ids[0]
      if (!id) {
        this.$message.warning("请先选择要修改的房型")
        return
      }
      this.reset()
      getRoomType(id).then(res => {
        const data = res.data || {}
        this.form = Object.assign({}, this.form, data)
        this.form.facilityIds = data.facilityIds || []
        this.form.roomImages = data.roomImages || []
        this.roomImagesStr = (this.form.roomImages || []).join(",")
        this.title = "修改房型"
        this.open = true
      })
    },
    // 详情
    handleDetail(row) {
      getRoomType(row.id).then(res => {
        this.detail = res.data || null
        this.detailOpen = true
      })
    },
    // 删除
    handleDelete(row) {
      const ids = row && row.id ? [row.id] : this.ids
      if (!ids.length) {
        this.$message.warning("请先选择要删除的房型")
        return
      }
      this.$confirm("是否确认删除选中的房型？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        // 当前接口为单个删除，如需批量可循环调用或扩展后端
        return delRoomType(ids[0])
      }).then(() => {
        this.$message.success("删除成功")
        this.getList()
      }).catch(() => {})
    },
    // 上下架
    handleChangeSaleStatus(row) {
      const targetStatus = row.saleStatus === "1" ? "0" : "1"
      const doChange = () => {
        changeRoomTypeSaleStatus({ id: row.id, saleStatus: targetStatus }).then(() => {
          this.$message.success("操作成功")
          this.getList()
        })
      }
      if (targetStatus === "1") {
        // 上架前先调用服务端完整性校验
        checkRoomTypeComplete(row.id)
          .then(res => {
            const data = res.data || {}
            if (data.pass === false) {
              this.$confirm(
                (data.message || "房型基础信息不完整") +
                  (data.missingFields && data.missingFields.length
                    ? "，缺失字段：" + data.missingFields.join(",")
                    : "") +
                  "，确认仍要上架吗？",
                "提示",
                {
                  confirmButtonText: "继续上架",
                  cancelButtonText: "取消",
                  type: "warning"
                }
              )
                .then(() => doChange())
                .catch(() => {})
            } else {
              doChange()
            }
          })
          .catch(() => {
            // 校验接口异常时，给出提示但仍允许手动确认
            this.$confirm("校验接口异常，确认仍要上架该房型吗？", "提示", {
              confirmButtonText: "继续上架",
              cancelButtonText: "取消",
              type: "warning"
            })
              .then(() => doChange())
              .catch(() => {})
          })
      } else {
        doChange()
      }
    },
    // 提交新增/修改
    submitForm() {
      // 同步 roomImagesStr 到 form.roomImages
      if (this.roomImagesStr) {
        this.form.roomImages = this.roomImagesStr
          .split(",")
          .map(s => s.trim())
          .filter(s => !!s)
      } else {
        this.form.roomImages = []
      }
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return
        }
        const isEdit = !!this.form.id
        const api = isEdit ? updateRoomType : addRoomType
        api(this.form).then(() => {
          this.$message.success(isEdit ? "修改成功" : "新增成功")
          this.open = false
          this.getList()
        })
      })
    }
  }
}
</script>

<style scoped>
</style>

