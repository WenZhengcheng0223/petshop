<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="订单编号" prop="orderNumber">
        <el-input v-model="queryParams.orderNumber" placeholder="请输入订单编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="支付方式" prop="orderPay">
        <el-select v-model="queryParams.orderPay" placeholder="请选择支付方式" clearable>
          <el-option v-for="dict in dict.type.order_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="发货状态" prop="isSend">
        <el-select v-model="queryParams.isSend" placeholder="请选择发货状态" clearable>
          <el-option v-for="dict in dict.type.order_out_status" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="发票抬头" prop="orderFapiaoTitle">
        <el-select v-model="queryParams.orderFapiaoTitle" placeholder="请选择发票抬头" clearable>
          <el-option v-for="dict in dict.type.order_fapiao_title" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="公司名称" prop="orderFapiaoCompany">
        <el-input v-model="queryParams.orderFapiaoCompany" placeholder="请输入公司名称" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="订单状态" prop="payStatus">
        <el-select v-model="queryParams.payStatus" placeholder="请选择订单状态" clearable>
          <el-option v-for="dict in dict.type.pay_status" :key="dict.value" :label="dict.label" :value="dict.value" />
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
          v-hasPermi="['order:detail:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['order:detail:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['order:detail:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['order:detail:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="detailList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键id" align="center" prop="orderId" :show-overflow-tooltip="true" v-if="false" />
      <el-table-column label="下订单会员id" align="center" prop="userId" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="订单编号" align="center" prop="orderNumber" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="订单总金额" align="center" prop="orderPrice" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="支付方式" align="center" prop="orderPay">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_status" :value="scope.row.orderPay" />
        </template>
      </el-table-column>
      <el-table-column label="发货状态" align="center" prop="isSend">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_out_status" :value="scope.row.isSend" />
        </template>
      </el-table-column>
      <el-table-column label="交易流水号码" align="center" prop="tradeNo" min-width="100" :show-overflow-tooltip="true" />
      <el-table-column label="发票抬头" align="center" prop="orderFapiaoTitle">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.order_fapiao_title" :value="scope.row.orderFapiaoTitle" />
        </template>
      </el-table-column>
      <el-table-column label="公司名称" align="center" prop="orderFapiaoCompany" min-width="100"
        :show-overflow-tooltip="true" />
      <el-table-column label="发票内容" align="center" prop="orderFapiaoContent" min-width="100"
        :show-overflow-tooltip="true" />
      <el-table-column label="收货人地址" align="center" prop="consigneeAddr" min-width="100"
        :show-overflow-tooltip="true" />
      <el-table-column label="订单状态" align="center" prop="payStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pay_status" :value="scope.row.payStatus" />
        </template>
      </el-table-column>
      <el-table-column label="记录生成时间" sortable align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="记录修改时间" sortable align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="120" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['order:detail:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['order:detail:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="会员ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入会员ID" />
        </el-form-item>
        <el-form-item label="订单总金额" prop="orderPrice">
          <el-input v-model="form.orderPrice" placeholder="请输入订单总金额" />
        </el-form-item>
        <el-form-item label="支付方式" prop="orderPay">
          <el-select v-model="form.orderPay" placeholder="请选择支付方式">
            <el-option v-for="dict in dict.type.order_status" :key="dict.value" :label="dict.label" :value="dict.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发货状态" prop="isSend">
          <el-select v-model="form.isSend" placeholder="请选择发货状态">
            <el-option v-for="dict in dict.type.order_out_status" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="交易流水号码" prop="tradeNo">
          <el-input v-model="form.tradeNo" placeholder="请输入交易流水号码" />
        </el-form-item>
        <el-form-item label="发票抬头" prop="orderFapiaoTitle">
          <el-select v-model="form.orderFapiaoTitle" placeholder="请选择发票抬头">
            <el-option v-for="dict in dict.type.order_fapiao_title" :key="dict.value" :label="dict.label"
              :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="公司名称" prop="orderFapiaoCompany">
          <el-input v-model="form.orderFapiaoCompany" placeholder="请输入公司名称" />
        </el-form-item>
        <el-form-item label="发票内容">
          <editor v-model="form.orderFapiaoContent" :min-height="192" />
        </el-form-item>
        <el-form-item label="收货人地址" prop="consigneeAddr">
          <el-input v-model="form.consigneeAddr" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="订单状态" prop="payStatus">
          <el-select v-model="form.payStatus" placeholder="请选择订单状态">
            <el-option v-for="dict in dict.type.pay_status" :key="dict.value" :label="dict.label" :value="dict.value">
            </el-option>
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
    updateDetail
  } from "@/api/order/detail";

  export default {
    name: "Detail",
    dicts: ['pay_status', 'order_status', 'order_out_status', 'order_fapiao_title'],
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
        // 订单表格数据
        detailList: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderNumber: undefined,
          orderPay: undefined,
          isSend: undefined,
          orderFapiaoTitle: undefined,
          orderFapiaoCompany: undefined,
          payStatus: undefined,
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
          userId: [{
            required: true,
            message: "下订单会员id不能为空",
            trigger: "blur"
          }],
          orderNumber: [{
            required: true,
            message: "订单编号不能为空",
            trigger: "blur"
          }],
          orderPrice: [{
            required: true,
            message: "订单总金额不能为空",
            trigger: "blur"
          }],
          orderPay: [{
            required: true,
            message: "支付方式不能为空",
            trigger: "change"
          }],
          isSend: [{
            required: true,
            message: "发货状态不能为空",
            trigger: "change"
          }],
          tradeNo: [{
            required: true,
            message: "交易流水号码不能为空",
            trigger: "blur"
          }],
          consigneeAddr: [{
            required: true,
            message: "收货人地址不能为空",
            trigger: "blur"
          }],
          payStatus: [{
            required: true,
            message: "订单状态不能为空",
            trigger: "change"
          }],
        }
      };
    },
    created() {
      this.getList();
    },
    methods: {
      /** 查询订单列表 */
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
          orderId: undefined,
          userId: undefined,
          orderNumber: undefined,
          orderPrice: undefined,
          orderPay: undefined,
          isSend: undefined,
          tradeNo: undefined,
          orderFapiaoTitle: undefined,
          orderFapiaoCompany: undefined,
          orderFapiaoContent: undefined,
          consigneeAddr: undefined,
          payStatus: undefined,
          createTime: undefined,
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
        this.ids = selection.map(item => item.orderId)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "添加订单";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.loading = true;
        this.reset();
        const orderId = row.orderId || this.ids
        getDetail(orderId).then(response => {
          this.loading = false;
          this.form = response.data;
          this.open = true;
          this.title = "修改订单";
        });
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.buttonLoading = true;
            if (this.form.orderId != null) {
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
        const orderIds = row.orderId || this.ids;
        this.$modal.confirm('是否确认删除订单编号为"' + orderIds + '"的数据项？').then(() => {
          this.loading = true;
          return delDetail(orderIds);
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
        this.download('order/detail/export', {
          ...this.queryParams
        }, `detail_${new Date().getTime()}.xlsx`)
      }
    }
  };
</script>
