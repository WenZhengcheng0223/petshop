<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="图片名称" prop="fileName">
        <el-input v-model="queryParams.fileName" placeholder="请输入图片名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-upload" size="mini" @click="handleAdd"
          v-hasPermi="['goods:swiper:add']">上传</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['goods:swiper:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['goods:swiper:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="swiperList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="图片主键" align="center" prop="ossId" :show-overflow-tooltip="true" v-if="false" />
      <el-table-column label="图片名称" align="center" prop="fileName" min-width="100" :show-overflow-tooltip="true" />

      <el-table-column label="图片展示" align="center" prop="url" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.url" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="图片状态" align="center" prop="status" min-width="100" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" @change="updateStatus(scope.row)" active-color="#13ce66"
            :active-value="1" :inactive-value="0" inactive-color="#ff4949">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" sortable width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="上传人" align="center" prop="createBy" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-download" @click="handleDownload(scope.row)"
            v-hasPermi="['goods:swipper:download']">下载</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['goods:swiper:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改轮播图对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="上传图片">
          <image-upload v-model="form.file" url="/goods/swiper/upload" :swiper="true" @delOss="handleDeleteImg" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import {
    listSwiper,
    getSwiper,
    delSwiper,
    addSwiper,
    updateSwiper
  } from "@/api/goods/swiper";

  export default {
    name: "Swiper",
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
        // 轮播图表格数据
        swiperList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          fileName: undefined,
          status: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          ossId: [{
            required: true,
            message: "图片主键不能为空",
            trigger: "blur"
          }],
          status: [{
            required: true,
            message: "图片状态不能为空",
            trigger: "blur"
          }],
          url: [{
            required: true,
            message: "图片展示不能为空",
            trigger: "blur"
          }],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询轮播图列表 */
      getList() {
        this.loading = true;
        listSwiper(this.queryParams).then(response => {
          this.swiperList = response.rows;
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
          file: undefined,
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
        this.ids = selection.map(item => item.ossId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "上传图片";
      },

      /** 提交按钮 */
      submitForm() {
        this.open = false;
        this.getList();
      },

      // 图片上传表单删除单个图片 由子组件触发
      handleDeleteImg(ossId) {
        delSwiper(ossId)
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ossIds = row.ossId || this.ids;

        this.$modal.confirm('是否确认删除轮播图编号为"' + ossIds + '"的数据项？').then(() => {
          this.loading = true;
          return delSwiper(ossIds);
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
        this.download('goods/swiper/export', {
          ...this.queryParams
        }, `swiper_${new Date().getTime()}.xlsx`)
      },
      /** 下载按钮操作 */
      handleDownload(row) {
        console.log(row)
        this.$download.swiper(row.ossId)
      },

      // 修改图片状态开关
      updateStatus(row) {
        console.log(row)
        updateSwiper(row).then(response => {
          this.$modal.msgSuccess("修改成功");
          // this.getList();
        }).finally(() => {
          this.buttonLoading = false;
        });
      }
    }
  };
</script>
