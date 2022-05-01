<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="微信名称" prop="nickName">
        <el-input v-model="queryParams.nickName" placeholder="请输入微信名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="性别" prop="userSex">
        <el-select v-model="queryParams.userSex" placeholder="请选择性别" clearable>
          <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="手机" prop="userTel">
        <el-input v-model="queryParams.userTel" placeholder="请输入手机" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="帐号状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择帐号状态" clearable>
          <el-option v-for="dict in dict.type.user_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="最后登录时间" prop="loginDate">
        <el-date-picker clearable v-model="queryParams.loginDate" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择最后登录时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['person:detail:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['person:detail:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增id" align="center" prop="userId" :show-overflow-tooltip="true" v-if="false" />
      <el-table-column label="微信编号" align="center" prop="openid" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="微信名称" align="center" prop="nickName" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="头像地址" align="center" prop="avatarUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.avatarUrl" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="性别" align="center" prop="userSex">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.userSex" />
        </template>
      </el-table-column>
      <el-table-column label="手机" align="center" prop="userTel" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="爱好" align="center" prop="userHobby" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="帐号状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" @change="updateStatus(scope.row)" active-color="#13ce66"
            :active-value="0" :inactive-value="1" inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="最后登录IP" align="center" prop="loginIp" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="最后登录时间" align="center" prop="loginDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['person:detail:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改会员对话框 -->

  </div>
</template>

<script>
  import {
    listDetail,
    getDetail,
    delDetail,
    addDetail,
    updateDetail
  } from "@/api/person/detail";

  export default {
    name: "Detail",
    dicts: ['user_status', 'sys_user_sex'],
    data() {
      return {
        // 按钮loading
        buttonLoading: false,
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        // 总条数
        total: 0,
        // 会员表格数据
        detailList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          nickName: undefined,
          userSex: undefined,
          userTel: undefined,
          status: undefined,
          loginDate: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验

      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询会员列表 */
      getList() {
        this.loading = true;
        listDetail(this.queryParams).then(response => {
          this.detailList = response.rows;
          this.total = response.total;
          this.loading = false;
        });
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          userId: undefined,
          openid: undefined,
          nickName: undefined,
          avatarUrl: undefined,
          userSex: undefined,
          userTel: undefined,
          userHobby: undefined,
          status: 0,
          loginIp: undefined,
          loginDate: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined
        };
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.userId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },


      /** 删除按钮操作 */
      handleDelete(row) {
        const userIds = row.userId || this.ids;
        this.$modal.confirm('是否确认删除会员编号为"' + userIds + '"的数据项？').then(() => {
          this.loading = true;
          return delDetail(userIds);
        }).then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).finally(() => {
          this.loading = false;
        });
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('person/detail/export', {
          ...this.queryParams
        }, `detail_${new Date().getTime()}.xlsx`)
      },
      updateStatus(row) {
        console.log(row)
        updateDetail(row).then(response => {
          this.$modal.msgSuccess("修改成功");
        }).finally(() => {
          this.buttonLoading = false;
        });
      }
    }
  };
</script>
