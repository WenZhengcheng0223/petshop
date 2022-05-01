<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="分类名称" prop="catName">
        <el-input v-model="queryParams.catName" placeholder="请输入分类名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="分类层级" prop="catLevel">
        <el-select v-model="queryParams.catLevel" placeholder="请选择分类层级" clearable>
          <el-option v-for="dict in dict.type.cat_level" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="分类情况" prop="catDeleted">
        <el-select v-model="queryParams.catDeleted" placeholder="请选择分类情况" clearable>
          <el-option v-for="dict in dict.type.goods_situation" :key="dict.value" :label="dict.label"
            :value="dict.value" />
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
          v-hasPermi="['goods:category:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="info" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">展开/折叠</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-if="refreshTable" v-loading="loading" :data="categoryList" row-key="catId"
      :default-expand-all="isExpandAll" :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
      <el-table-column label="分类名称" prop="catName" />
      <el-table-column label="分类父ID" align="center" prop="catPid" width="100" />
      <el-table-column label="分类层级" align="center" prop="catLevel">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.cat_level" :value="scope.row.catLevel" />
        </template>
      </el-table-column>
      <el-table-column label="分类情况" align="center" prop="catDeleted" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.goods_situation" :value="scope.row.catDeleted" />
        </template>
      </el-table-column>
      <el-table-column label="图标" align="center" prop="catIcon" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.catIcon" :width="50" :height="50"
            v-if="scope.row.catIcon != null&&scope.row.catIcon!=''" />
        </template>
      </el-table-column>
      <el-table-column label="图标地址" align="center" prop="catSrc" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['goods:category:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
            v-hasPermi="['goods:category:add']">新增</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['goods:category:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改分类对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="catName">
          <el-input v-model="form.catName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类父ID" prop="catPid">
          <treeselect v-model="form.catPid" :options="categoryOptions" :normalizer="normalizer"
            placeholder="请选择分类父ID" />
        </el-form-item>
        <el-form-item label="分类层级" prop="catLevel">
          <el-select v-model="form.catLevel" placeholder="请选择分类层级">
            <el-option v-for="dict in dict.type.cat_level" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分类情况" prop="catDeleted">
          <el-select v-model="form.catDeleted" placeholder="请选择分类情况">
            <el-option v-for="dict in dict.type.goods_situation" :key="dict.value" :label="dict.label"
              :value="parseInt(dict.value)"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="图标">
          <my-upload v-model="oss" :limit="1" @delOss="updateIcon" />
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
    listCategory,
    getCategory,
    delCategory,
    addCategory,
    updateCategory,
    deleteImg
  } from "@/api/goods/category";
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";
  import myUpload from "../../components/myUpload.vue";
  export default {
    name: "Category",
    dicts: ['cat_level', 'goods_situation'],
    components: {
      Treeselect,
      myUpload
    },
    data() {
      return {
        oss: undefined,
        // 按钮loading
        buttonLoading: false,
        // 遮罩层
        loading: true,
        // 显示搜索条件
        showSearch: true,
        // 分类表格数据
        categoryList: [],
        // 分类树选项
        categoryOptions: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否展开，默认全部展开
        isExpandAll: true,
        // 重新渲染表格状态
        refreshTable: true,
        // 查询参数
        queryParams: {
          catName: undefined,
          catLevel: undefined,
          catDeleted: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          catId: [{
            required: true,
            message: "分类ID不能为空",
            trigger: "blur"
          }],
          catName: [{
            required: true,
            message: "分类名称不能为空",
            trigger: "blur"
          }],
          catPid: [{
            required: true,
            message: "分类父ID不能为空",
            trigger: "blur"
          }],
          catLevel: [{
            required: true,
            message: "分类层级不能为空",
            trigger: "change"
          }],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询分类列表 */
      getList() {
        this.loading = true;
        listCategory(this.queryParams).then(response => {
          this.categoryList = this.handleTree(response.data, "catId", "catPid");
          this.loading = false;
        });
      },
      /** 转换分类数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.catId,
          label: node.catName,
          children: node.children
        };
      },
      /** 查询分类下拉树结构 */
      getTreeselect() {
        listCategory().then(response => {
          this.categoryOptions = [];
          const data = {
            catId: 0,
            catName: '顶级节点',
            children: []
          };
          data.children = this.handleTree(response.data, "catId", "catPid");
          this.categoryOptions.push(data);
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
          catId: null,
          catName: null,
          catPid: null,
          catLevel: null,
          catDeleted: null,
          catIcon: null,
          catSrc: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null
        };
        this.oss = [];
        this.resetForm("form");
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm("queryForm");
        this.handleQuery();
      },
      /** 新增按钮操作 */
      handleAdd(row) {
        this.reset();
        this.getTreeselect();
        if (row != null && row.catId) {
          this.form.catPid = row.catId;
        } else {
          this.form.catPid = 0;
        }
        this.oss.push({
          name: -1,
          url: null
        })
        this.open = true;
        this.title = "添加分类";
      },
      /** 展开/折叠操作 */
      toggleExpandAll() {
        this.refreshTable = false;
        this.isExpandAll = !this.isExpandAll;
        this.$nextTick(() => {
          this.refreshTable = true;
        });
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.loading = true;
        this.reset();
        this.getTreeselect();
        if (row != null) {
          this.form.catPid = row.catId;
        }
        getCategory(row.catId).then(response => {
          this.loading = false;
          this.form = response.data;
          this.oss.push({
            name: this.form.catId,
            url: this.form.catIcon
          })
          this.open = true;
          this.title = "修改分类";
        });
      },

      // 更新图标地址为空
      updateIcon(update) {
        console.log("---------cat-------")
        console.log(update)
        deleteImg(update.catId, update.catIcon).then(response => {
          console.log(response)
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            console.log(this.oss)
            if (this.oss.length == 0) {
              this.form.catIcon = ""
            } else {
              this.form.catIcon = this.oss[0].url
            }
            this.buttonLoading = true;
            if (this.form.catId != null) {
              updateCategory(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              }).finally(() => {
                this.buttonLoading = false;
              });
            } else {
              this.form.catIcon = this.oss[0].url
              addCategory(this.form).then(response => {
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
        this.$modal.confirm('是否确认删除分类编号为"' + row.catId + '"的数据项？').then(() => {
          this.loading = true;
          return delCategory(row.catId);
        }).then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).finally(() => {
          this.loading = false;
        });
      }
    }
  };
</script>
