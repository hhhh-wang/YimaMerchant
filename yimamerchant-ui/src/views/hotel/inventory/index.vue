<template>
  <div class="app-container">
    <!-- 搜索区域 -->
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="90px"
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
      <el-form-item label="是否可预订" prop="bookableFlag">
        <el-select
          v-model="queryParams.bookableFlag"
          placeholder="请选择是否可预订"
          clearable
          style="width: 180px"
        >
          <el-option
            v-for="dict in dict.type.hotel_bookable_flag"
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
      <el-col :span="24">
        <el-button type="primary" plain icon="el-icon-edit" size="mini" :disabled="multiple" @click="handleBatchEditPrice" v-hasPermi="['hotel:inventory:batchEdit']">
          批量设置价格
        </el-button>
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="multiple" @click="handleBatchEditStock" v-hasPermi="['hotel:inventory:batchEdit']">
          批量设置库存
        </el-button>
        <el-button type="warning" plain icon="el-icon-edit" size="mini" :disabled="multiple" @click="handleBatchEditPriceAndStock" v-hasPermi="['hotel:inventory:batchEdit']">
          批量同时设置价格与库存
        </el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
      </el-col>
    </el-row>

    <!-- 列表 -->
    <el-table
      v-loading="loading"
      :data="inventoryList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="房型名称" prop="roomTypeName" min-width="180" :show-overflow-tooltip="true" />
      <el-table-column label="销售价" prop="salePrice" width="120" align="right">
        <template slot-scope="scope">
          <span>{{ scope.row.salePrice | price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="划线价" prop="marketPrice" width="120" align="right">
        <template slot-scope="scope">
          <span>{{ scope.row.marketPrice | price }}</span>
        </template>
      </el-table-column>
      <el-table-column label="默认库存" prop="baseStock" width="110" align="center" />
      <el-table-column label="已售数量" prop="soldNum" width="110" align="center" />
      <el-table-column label="剩余可售" prop="availableNum" width="110" align="center" />
      <el-table-column label="是否可预订" prop="bookableFlag" width="110">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_bookable_flag" :value="scope.row.bookableFlag" />
        </template>
      </el-table-column>
      <el-table-column label="上架状态" prop="saleStatus" width="110">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.hotel_room_sale_status" :value="scope.row.saleStatus" />
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="320" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEditPrice(scope.row)"
            v-hasPermi="['hotel:inventory:edit']"
          >
            价格
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEditStock(scope.row)"
            v-hasPermi="['hotel:inventory:edit']"
          >
            库存
          </el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.bookableFlag === 'Y' ? 'el-icon-close' : 'el-icon-circle-check'"
            @click="handleChangeBookableFlag(scope.row)"
            v-hasPermi="['hotel:inventory:changeStatus']"
          >
            {{ scope.row.bookableFlag === 'Y' ? '关闭预订' : '开启预订' }}
          </el-button>
          <el-button
            size="mini"
            type="text"
            :icon="scope.row.saleStatus === '1' ? 'el-icon-bottom' : 'el-icon-top'"
            @click="handleChangeSaleStatus(scope.row)"
            v-hasPermi="['hotel:inventory:changeStatus']"
          >
            {{ scope.row.saleStatus === '1' ? '下架' : '上架' }}
          </el-button>
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

    <!-- 批量：设置价格 -->
    <el-dialog title="批量设置价格" :visible.sync="batchPriceOpen" width="520px" append-to-body>
      <el-form :model="batchPriceForm" ref="batchPriceFormRef" label-width="120px">
        <el-form-item label="销售价">
          <el-input-number v-model="batchPriceForm.basePrice" :min="0" :precision="2" :step="1" style="width: 260px" />
        </el-form-item>
        <el-form-item label="划线价">
          <el-input-number v-model="batchPriceForm.marketPrice" :min="0" :precision="2" :step="1" style="width: 260px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatchEditPrice">确 定</el-button>
        <el-button @click="batchPriceOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 批量：设置库存 -->
    <el-dialog title="批量设置库存" :visible.sync="batchStockOpen" width="520px" append-to-body>
      <el-form :model="batchStockForm" ref="batchStockFormRef" label-width="120px">
        <el-form-item label="默认库存">
          <el-input-number v-model="batchStockForm.baseStock" :min="0" style="width: 260px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatchEditStock">确 定</el-button>
        <el-button @click="batchStockOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 批量：同时设置价格与库存 -->
    <el-dialog title="批量同时设置价格与库存" :visible.sync="batchAllOpen" width="560px" append-to-body>
      <el-form :model="batchAllForm" ref="batchAllFormRef" label-width="120px">
        <el-form-item label="销售价">
          <el-input-number v-model="batchAllForm.basePrice" :min="0" :precision="2" :step="1" style="width: 260px" />
        </el-form-item>
        <el-form-item label="划线价">
          <el-input-number v-model="batchAllForm.marketPrice" :min="0" :precision="2" :step="1" style="width: 260px" />
        </el-form-item>
        <el-form-item label="默认库存">
          <el-input-number v-model="batchAllForm.baseStock" :min="0" style="width: 260px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatchEditPriceAndStock">确 定</el-button>
        <el-button @click="batchAllOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 单行：设置价格 -->
    <el-dialog title="修改房型默认价格" :visible.sync="singlePriceOpen" width="520px" append-to-body>
      <el-form :model="singlePriceForm" ref="singlePriceFormRef" label-width="120px">
        <el-form-item label="销售价">
          <el-input-number v-model="singlePriceForm.basePrice" :min="0" :precision="2" :step="1" style="width: 260px" />
        </el-form-item>
        <el-form-item label="划线价">
          <el-input-number v-model="singlePriceForm.marketPrice" :min="0" :precision="2" :step="1" style="width: 260px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitSingleEditPrice">确 定</el-button>
        <el-button @click="singlePriceOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 单行：设置库存 -->
    <el-dialog title="修改房型默认库存" :visible.sync="singleStockOpen" width="520px" append-to-body>
      <el-form :model="singleStockForm" ref="singleStockFormRef" label-width="120px">
        <el-form-item label="默认库存">
          <el-input-number v-model="singleStockForm.baseStock" :min="0" style="width: 260px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitSingleEditStock">确 定</el-button>
        <el-button @click="singleStockOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listInventory,
  updateBasePrice,
  updateBaseStock,
  batchUpdateInventory,
  changeBookableFlag,
  changeSaleStatus
} from '@/api/hotel/inventory'

export default {
  name: 'HotelInventory',
  dicts: ['hotel_room_sale_status', 'hotel_bookable_flag'],
  data() {
    return {
      loading: false,
      // 选中数组
      ids: [],
      single: true,
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      total: 0,
      // 列表数据
      inventoryList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomTypeName: undefined,
        saleStatus: undefined,
        bookableFlag: undefined
      },
      // 批量：价格
      batchPriceOpen: false,
      batchPriceForm: {
        basePrice: undefined,
        marketPrice: undefined
      },
      // 批量：库存
      batchStockOpen: false,
      batchStockForm: {
        baseStock: undefined
      },
      // 批量：价格与库存
      batchAllOpen: false,
      batchAllForm: {
        basePrice: undefined,
        marketPrice: undefined,
        baseStock: undefined
      },
      // 单行：价格
      singlePriceOpen: false,
      singlePriceForm: {
        roomTypeId: undefined,
        basePrice: undefined,
        marketPrice: undefined
      },
      // 单行：库存
      singleStockOpen: false,
      singleStockForm: {
        roomTypeId: undefined,
        baseStock: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  filters: {
    price(val) {
      if (val === null || val === undefined) {
        return ''
      }
      return Number(val).toFixed(2)
    }
  },
  methods: {
    // 查询库存价格列表
    getList() {
      this.loading = true
      listInventory(this.queryParams)
        .then(res => {
          const data = res || {}
          this.inventoryList = data.rows || []
          this.total = data.total || 0
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
      this.resetForm('queryForm')
      this.handleQuery()
    },

    // 批量：设置价格
    handleBatchEditPrice() {
      this.batchPriceForm = { basePrice: undefined, marketPrice: undefined }
      this.batchPriceOpen = true
    },
    submitBatchEditPrice() {
      if (!this.ids.length) {
        this.$message.warning('请先选择要操作的房型')
        return
      }
      if (this.batchPriceForm.basePrice === undefined || this.batchPriceForm.basePrice === null) {
        this.$message.warning('请填写销售价')
        return
      }
      batchUpdateInventory({
        roomTypeIds: this.ids,
        basePrice: this.batchPriceForm.basePrice,
        marketPrice: this.batchPriceForm.marketPrice
      })
        .then(() => {
          this.$message.success('批量设置价格成功')
          this.batchPriceOpen = false
          this.getList()
        })
    },

    // 批量：设置库存
    handleBatchEditStock() {
      this.batchStockForm = { baseStock: undefined }
      this.batchStockOpen = true
    },
    submitBatchEditStock() {
      if (!this.ids.length) {
        this.$message.warning('请先选择要操作的房型')
        return
      }
      if (this.batchStockForm.baseStock === undefined || this.batchStockForm.baseStock === null) {
        this.$message.warning('请填写默认库存')
        return
      }
      batchUpdateInventory({
        roomTypeIds: this.ids,
        baseStock: this.batchStockForm.baseStock
      })
        .then(() => {
          this.$message.success('批量设置库存成功')
          this.batchStockOpen = false
          this.getList()
        })
    },

    // 批量：同时设置价格与库存
    handleBatchEditPriceAndStock() {
      this.batchAllForm = { basePrice: undefined, marketPrice: undefined, baseStock: undefined }
      this.batchAllOpen = true
    },
    submitBatchEditPriceAndStock() {
      if (!this.ids.length) {
        this.$message.warning('请先选择要操作的房型')
        return
      }
      if (this.batchAllForm.basePrice === undefined || this.batchAllForm.basePrice === null) {
        this.$message.warning('请填写销售价')
        return
      }
      if (this.batchAllForm.baseStock === undefined || this.batchAllForm.baseStock === null) {
        this.$message.warning('请填写默认库存')
        return
      }
      batchUpdateInventory({
        roomTypeIds: this.ids,
        basePrice: this.batchAllForm.basePrice,
        marketPrice: this.batchAllForm.marketPrice,
        baseStock: this.batchAllForm.baseStock
      })
        .then(() => {
          this.$message.success('批量设置价格与库存成功')
          this.batchAllOpen = false
          this.getList()
        })
    },

    // 单行：价格
    handleEditPrice(row) {
      const id = row && row.id
      if (!id) return
      this.singlePriceForm = {
        roomTypeId: id,
        basePrice: row.salePrice,
        marketPrice: row.marketPrice
      }
      this.singlePriceOpen = true
    },
    submitSingleEditPrice() {
      if (!this.singlePriceForm.roomTypeId) return
      if (this.singlePriceForm.basePrice === undefined || this.singlePriceForm.basePrice === null) {
        this.$message.warning('请填写销售价')
        return
      }
      updateBasePrice({
        roomTypeId: this.singlePriceForm.roomTypeId,
        basePrice: this.singlePriceForm.basePrice,
        marketPrice: this.singlePriceForm.marketPrice
      })
        .then(() => {
          this.$message.success('修改价格成功')
          this.singlePriceOpen = false
          this.getList()
        })
    },

    // 单行：库存
    handleEditStock(row) {
      const id = row && row.id
      if (!id) return
      this.singleStockForm = {
        roomTypeId: id,
        baseStock: row.baseStock
      }
      this.singleStockOpen = true
    },
    submitSingleEditStock() {
      if (!this.singleStockForm.roomTypeId) return
      if (this.singleStockForm.baseStock === undefined || this.singleStockForm.baseStock === null) {
        this.$message.warning('请填写默认库存')
        return
      }
      updateBaseStock({
        roomTypeId: this.singleStockForm.roomTypeId,
        baseStock: this.singleStockForm.baseStock
      })
        .then(() => {
          this.$message.success('修改库存成功')
          this.singleStockOpen = false
          this.getList()
        })
    },

    // 可预订
    handleChangeBookableFlag(row) {
      const target = row.bookableFlag === 'Y' ? 'N' : 'Y'
      changeBookableFlag({ roomTypeId: row.id, bookableFlag: target })
        .then(() => {
          this.$message.success('操作成功')
          this.getList()
        })
    },

    // 上下架
    handleChangeSaleStatus(row) {
      const targetSaleStatus = row.saleStatus === '1' ? '0' : '1'
      changeSaleStatus({ roomTypeId: row.id, saleStatus: targetSaleStatus })
        .then(() => {
          this.$message.success('操作成功')
          this.getList()
        })
    }
  }
}
</script>

<style scoped>
</style>

