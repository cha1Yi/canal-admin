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
      element-loading-text="加载中..."
      border
      fit
      highlight-current-row
    >
      <el-table-column label="数据源名称" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.caption }}
        </template>
      </el-table-column>
      <el-table-column label="数据源描述" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column label="数据库类型" min-width="200" align="center">
        <template slot-scope="scope">
          {{ scope.row.databaseType }}
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

    <el-dialog
      :title="textMap[dialogStatus]"
      :visible.sync="dialogFormVisible"
      width="600px"
    >
      <el-form
        :model="database"
        :rules="rules"
        ref="dataForm"
        label-position="left"
        label-width="120px"
        style="width: 400px; margin-left: 30px"
      >
        <el-form-item label="数据源名称" prop="caption">
          <el-input
            v-model="database.caption"
            placeholder="请输入数据源名称"
          ></el-input>
        </el-form-item>
        <el-form-item label="数据源描述" prop="description">
          <el-input
            v-model="database.description"
            placeholder="请输入数据源描述"
            type="textarea"
            maxlength="100"
            show-word-limit=true
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="dataOperation()">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  pageDatabase,
  dbTypes,
  addDatabase,
  updateDatabase,
} from "@/api/database";
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
      listLoading: true,
      listQuery: {
        caption: "",
        databaseType: "",
        page: 1,
        size: 20,
      },
      database: {
        id: undefined,
        caption: null,
        description: null,
        databaseType: "MYSQL",
        host: null,
        port: 3306,
        schema: null,
        username: null,
        password: null,
        attrs: null,
        created: null,
        updated: null,
      },
      dialogStatus: "create",
      dialogFormVisible: false,
      textMap: {
        create: "新建数据源",
        update: "修改数据源",
      },
      rules: {
        caption: [
          {
            required: true,
            message: "数据源名称不可为空",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    dbTypes().then((res) => {
      this.databaseTypes = res.data;
    });
    this.fetchData();
  },
  methods: {
    queryData() {
      this.listQuery.page = 1;
      this.fetchData();
    },
    fetchData() {
      this.listLoading = true;
      pageDatabase(this.listQuery)
        .then((res) => {
          this.list = res.data.items;
          this.count - res.data.count;
        })
        .finally(() => {
          this.listLoading = false;
        });
    },
    resetModel() {
      this.database = {
        id: undefined,
        caption: null,
        description: null,
        databaseType: null,
        host: null,
        port: undefined,
        schema: null,
        username: null,
        password: null,
        attrs: null,
        created: null,
        updated: null,
      };
    },
    handleCreate() {
      this.resetModel();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    dataOperation() {
      this.$refs["dataForm"].validate((valiad) => {
        if (valiad) {
          if (this.dialogStatus === "create") {
            addDatabase(this.database).then((res) => {
              this.operationRes(res);
            });
          }
          if (this.dialogStatus === "update") {
            updateDatabase(this.database).then((res) => {
              this.operationRes(res);
            });
          }
        }
      });
    },
    operationRes(res) {
      if (res.data === "success") {
        this.fetchData();
        this.dialogFormVisible = false;
        this.$message({
          message: this.textMap[this.dialogStatus] + ",成功！",
          type: "success",
        });
      } else {
        this.$message({
          message: this.textMap[this.dialogStatus] + ",失败！",
          type: "error",
        });
      }
    },
  },
};
</script>

<style scoped>
</style>
