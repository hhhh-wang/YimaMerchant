<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="clearfix"><span>酒店业务参数配置</span><el-button style="float:right" type="primary" size="mini" @click="submit">保存</el-button></div>
      <el-table v-loading="loading" :data="list">
        <el-table-column label="分组" prop="configGroup" width="120" />
        <el-table-column label="配置名称" prop="configName" min-width="180" />
        <el-table-column label="配置键" prop="configKey" min-width="220" />
        <el-table-column label="配置值" min-width="260">
          <template slot-scope="scope"><el-input v-model="scope.row.configValue" /></template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getHotelConfig, saveHotelConfig } from "@/api/hotel/config"
export default {
  name: "HotelConfig",
  data() { return { loading: false, list: [] } },
  created() { this.loading = true; getHotelConfig().then(res => { this.list = res.data || []; this.loading = false }) },
  methods: { submit() { saveHotelConfig(this.list).then(() => this.$modal.msgSuccess("保存成功")) } }
}
</script>
