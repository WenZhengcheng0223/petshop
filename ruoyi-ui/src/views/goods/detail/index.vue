<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="商品名称" prop="goodsName">
        <el-input v-model="queryParams.goodsName" placeholder="请输入商品名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="商品情况" prop="isDel">
        <el-select v-model="queryParams.isDel" placeholder="请选择商品情况" clearable>
          <el-option v-for="dict in dict.type.goods_situation" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="是否促销" prop="isPromote">
        <el-select v-model="queryParams.isPromote" placeholder="请选择是否促销" clearable>
          <el-option v-for="dict in dict.type.is_promote" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="商品状态" prop="goodsState">
        <el-select v-model="queryParams.goodsState" placeholder="请选择商品状态" clearable>
          <el-option v-for="dict in dict.type.goods_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['goods:detail:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['goods:detail:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['goods:detail:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['goods:detail:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="goodsId" :show-overflow-tooltip="true" v-if="false" />
      <el-table-column label="商品名称" align="center" prop="goodsName" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="商品价格" align="center" prop="goodsPrice" sortable min-width="100"
        :show-overflow-tooltip="true" />
      <el-table-column label="商品数量" align="center" prop="goodsNumber" sortable min-width="100"
        :show-overflow-tooltip="true" />
      <el-table-column label="商品重量" align="center" prop="goodsWeight" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="类型id" align="center" prop="catId" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="商品详情介绍" align="center" prop="goodsIntroduce" min-width="100"
        :show-overflow-tooltip="true" />
      <el-table-column label="图片logo" align="center" prop="goodsBigLogo" width="160">
        <template slot-scope="scope">
          <div style="display: flex;justify-content: space-around;align-items: center; flex-wrap: wrap;">
            <div v-for="(item,index) in scope.row.goodsOss" :key="index">
              <image-preview :src="item.url" :width="50" :height="50" v-if="item.url != null&&item.url !== ''" />
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="促销情况" align="center" prop="isPromote">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.is_promote" :value="scope.row.isPromote" />
        </template>
      </el-table-column>
      <el-table-column label="商品状态" align="center" prop="goodsState">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.goods_status" :value="scope.row.goodsState" />
        </template>
      </el-table-column>
      <el-table-column label="商品情况" align="center" prop="isDel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.goods_situation" :value="scope.row.isDel" />
        </template>
      </el-table-column>
      <el-table-column label="一级分类id" align="center" prop="catOneId" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="二级分类id" align="center" prop="catTwoId" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="三级分类id" align="center" prop="catThreeId" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="热卖数量" align="center" prop="hotMumber" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:detail:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['goods:detail:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改商品对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="商品名称" prop="goodsName">
          <el-input v-model="form.goodsName" placeholder="请输入商品名称" />
        </el-form-item>
        <el-form-item label="商品价格" prop="goodsPrice">
          <el-input v-model="form.goodsPrice" placeholder="请输入商品价格" />
        </el-form-item>
        <el-form-item label="商品数量" prop="goodsNumber">
          <el-input v-model="form.goodsNumber" placeholder="请输入商品数量" />
        </el-form-item>
        <el-form-item label="商品重量" prop="goodsWeight">
          <el-input v-model="form.goodsWeight" placeholder="请输入商品重量" />
        </el-form-item>
        <el-form-item label="类型id" prop="catId">
          <el-input v-model="form.catId" placeholder="请输入类型id" />
        </el-form-item>
        <el-form-item label="商品详情介绍">
          <editor v-model="form.goodsIntroduce" :min-height="192" />
        </el-form-item>
        <el-form-item label="图片logo">
          <image-upload v-model="form.goodsOss" @delOss="delOss" />
        </el-form-item>
        <el-form-item label="商品情况" prop="isDel">
          <el-select v-model="form.isDel" placeholder="请选择商品情况">
            <el-option v-for="dict in dict.type.goods_situation" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="一级分类id" prop="catOneId">
          <el-input v-model="form.catOneId" placeholder="请输入一级分类id" />
        </el-form-item>
        <el-form-item label="二级分类id" prop="catTwoId">
          <el-input v-model="form.catTwoId" placeholder="请输入二级分类id" />
        </el-form-item>
        <el-form-item label="三级分类id" prop="catThreeId">
          <el-input v-model="form.catThreeId" placeholder="请输入三级分类id" />
        </el-form-item>
        <el-form-item label="热卖数量" prop="hotMumber">
          <el-input v-model="form.hotMumber" placeholder="请输入热卖数量" />
        </el-form-item>
        <el-form-item label="是否促销" prop="isPromote">
          <el-select v-model="form.isPromote" placeholder="请选择是否促销">
            <el-option v-for="dict in dict.type.is_promote" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品状态" prop="goodsState">
          <el-select v-model="form.goodsState" placeholder="请选择商品状态">
            <el-option v-for="dict in dict.type.goods_status" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
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
    listDetail,
    getDetail,
    delDetail,
    addDetail,
    updateDetail,
    removeImg
  } from "@/api/goods/detail";

  export default {
    name: "Detail",
    dicts: ['goods_situation', 'is_promote', 'goods_status'],
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
        // 商品表格数据
        detailList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          goodsName: undefined,
          isDel: undefined,
          isPromote: undefined,
          goodsState: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          goodsId: [{
            required: true,
            message: "主键id不能为空",
            trigger: "blur"
          }],
          goodsName: [{
            required: true,
            message: "商品名称不能为空",
            trigger: "blur"
          }],
          goodsPrice: [{
            required: true,
            message: "商品价格不能为空",
            trigger: "blur"
          }],
          goodsNumber: [{
            required: true,
            message: "商品数量不能为空",
            trigger: "blur"
          }],
          catId: [{
            required: true,
            message: "类型id不能为空",
            trigger: "blur"
          }],
          isDel: [{
            required: true,
            message: "商品情况不能为空",
            trigger: "change"
          }],
          catOneId: [{
            required: true,
            message: "一级分类id不能为空",
            trigger: "blur"
          }],
          catTwoId: [{
            required: true,
            message: "二级分类id不能为空",
            trigger: "blur"
          }],
          catThreeId: [{
            required: true,
            message: "三级分类id不能为空",
            trigger: "blur"
          }],
          hotMumber: [{
            required: true,
            message: "热卖数量不能为空",
            trigger: "blur"
          }],
          isPromote: [{
            required: true,
            message: "是否促销不能为空",
            trigger: "change"
          }],
          goodsState: [{
            required: true,
            message: "商品状态不能为空",
            trigger: "change"
          }],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      delOss(oss) {
        console.log("deloss------:" + oss)
        removeImg(oss)
      },
      input() {
        console.log("input")
        console.log(this.form.goodsSmallLogo)
      },

      /** 查询商品列表 */
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
          goodsId: undefined,
          goodsName: undefined,
          goodsPrice: undefined,
          goodsNumber: undefined,
          goodsWeight: undefined,
          catId: undefined,
          goodsIntroduce: undefined,
          goodsOss: undefined,
          isDel: undefined,
          catOneId: undefined,
          catTwoId: undefined,
          catThreeId: undefined,
          hotMumber: undefined,
          isPromote: undefined,
          goodsState: undefined,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined,
          deleteTime: undefined,

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
        this.ids = selection.map(item => item.goodsId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加商品";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.loading = true;
        this.reset();
        const goodsId = row.goodsId || this.ids
        getDetail(goodsId).then(response => {
          this.loading = false;
          this.form = response.data;
          this.open = true;
          this.title = "修改商品";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.buttonLoading = true;
            console.log(this.form)
            if (this.form.goodsId != null) {
              updateDetail(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }).finally(() => {
                this.buttonLoading = false;
              });
            } else {
              addDetail(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              }).finally(() => {
                this.buttonLoading = false;
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const goodsIds = row.goodsId || this.ids;
        this.$modal.confirm('是否确认删除商品编号为"' + goodsIds + '"的数据项？').then(() => {
          this.loading = true;
          return delDetail(goodsIds);
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
        this.download('goods/detail/export', {
          ...this.queryParams
        }, `detail_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
