<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select
        v-model="listQuery.databaseType"
        placeholder="数据库类型"
        class="filter-item"
      >
        <el-option
          v-for="item in databaseTypes"
          :value="item"
          :key="item"
          :label="item"
        ></el-option>
      </el-select>
      <el-input
        v-model="listQuery.caption"
        style="width: 200px"
        placeholder="数据源名称"
        class="filter-item"
      ></el-input>
      <el-button
        type="primary"
        class="filter-item"
        icon="el-icon-search"
        plain
        @click="queryData()"
        >查询</el-button
      >
      <el-button class="filter-item" type="primary" @click="handleCreate()"
        >新建数据源</el-button
      >
      <el-button class="filter-item" type="info" @click="fetchData()"
        >刷新列表</el-button
      >
    </div>

    <el-table
      :data="list"
      v-loading="listLoading"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="数据源名称" min-width="200" align="center">
        <template slot-scope="scope">
          {{scope.row.caption}}
        </template>
      </el-table-column>
      <el-table-column label="数据源描述" min-width="200" align="center">
        <template slot-scope="scope">
          {{scope.row.description}}
        </template>
      </el-table-column>
      <el-table-column label="数据库类型" min-width="200" align="center">
        <template slot-scope="scope">
          {{scope.row.databaseType}}
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="count > 0"
      :total="count"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.size"
      @pagination="fetchData()"
    />
  </div>
</template>
<script>
import { pageDatabase, dbTypes } from "@/api/database";
import Pagination from "@/components/Pagination";

export default {
  components: {
    Pagination,
  },
  data() {
    return {
      list: [],
      count: 0,
      databaseList: [],
      databaseTypes: [],
      listQuery: {
        caption: "",
        databaseType: "",
        page: 1,
        size: 20,
      },
    };
  },
  created() {
    pageDatabase(this.listQuery).then((res) => {
      this.databaseList = res.data;
    });
    dbTypes().then((res) => {
      this.databaseTypes = res.data;
    });
  },
  methods: {
    queryData() {
      this.listQuery.page = 1;
      this.fetchData();
    },
    fetchData() {
      pageDatabase(this.listQuery).then((res) => {
        this.list = res.data.items;
        this.count - res.data.count;
      });
    },
  },
};
</script>

<style scoped>
</style>
