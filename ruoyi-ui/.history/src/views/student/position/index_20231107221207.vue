<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位名称" prop="positionName">
        <el-input v-model="queryParams.positionName" placeholder="请输入岗位名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="目标状态" prop="state">
        <el-input v-model="queryParams.state" placeholder="请输入目标状态" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="修改次数" prop="modificationsNumber">
        <el-input v-model="queryParams.modificationsNumber" placeholder="请输入修改次数" clearable
          @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="批阅次数" prop="reviewsNumber">
        <el-input v-model="queryParams.reviewsNumber" placeholder="请输入批阅次数" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['student:position:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['student:position:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['student:position:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['student:position:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="positionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="岗位id" align="center" prop="positionId" />
      <el-table-column label="岗位名称" align="center" prop="positionName" />
      <el-table-column label="目标状态(0:已发布，1:已废止，2:草稿)" align="center" prop="state" />
      <el-table-column label="是否是组目标" align="center" prop="isMain" />
      <el-table-column label="修改次数" align="center" prop="modificationsNumber" />
      <el-table-column label="批阅次数" align="center" prop="reviewsNumber" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['student:position:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['student:position:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改岗位管理对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="岗位名称" prop="positionName">
          <el-input v-model="form.positionName" placeholder="请输入岗位名称" />
        </el-form-item>
        <el-form-item label="目标状态(0:已发布，1:已废止，2:草稿)" prop="state">
          <el-input v-model="form.state" placeholder="请输入目标状态(0:已发布，1:已废止，2:草稿)" />
        </el-form-item>
        <el-form-item label="是否是组目标" prop="isMain">
          <el-input v-model="form.isMain" placeholder="请输入是否是组目标" />
        </el-form-item>
        <el-form-item label="修改次数" prop="modificationsNumber">
          <el-input v-model="form.modificationsNumber" placeholder="请输入修改次数" />
        </el-form-item>
        <el-form-item label="批阅次数" prop="reviewsNumber">
          <el-input v-model="form.reviewsNumber" placeholder="请输入批阅次数" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listPosition, getPosition, delPosition, addPosition, updatePosition } from "@/api/student/position";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Position",
  data() {
    return {
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
      // 岗位管理表格数据
      positionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        positionid: null,
        Name: null,
        state: null,
        isMain: null,
        modificationsNumber: null,
        reviewsNumber: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        positionName: [
          { required: true, message: "岗位名称不能为空", trigger: "blur" }
        ],
        state: [
          { required: true, message: "目标状态(0:已发布，1:已废止，2:草稿)不能为空", trigger: "blur" }
        ],
        isMain: [
          { required: true, message: "是否是组目标不能为空", trigger: "blur" }
        ],
        modificationsNumber: [
          { required: true, message: "修改次数不能为空", trigger: "blur" }
        ],
        reviewsNumber: [
          { required: true, message: "批阅次数不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询岗位管理列表 */
    getList() {
      this.loading = true;
      listPosition(this.queryParams).then(response => {
        this.positionList = response.rows;
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
        positionId: null,
        positionName: null,
        state: null,
        isMain: null,
        modificationsNumber: null,
        reviewsNumber: null,
        createBy: null,
        createTime: null
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
      this.ids = selection.map(item => item.positionId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加岗位管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const positionId = row.positionId || this.ids
      getPosition(positionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改岗位管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.positionId != null) {
            updatePosition(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addPosition(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const positionIds = row.positionId || this.ids;
      this.$modal.confirm('是否确认删除岗位管理编号为"' + positionIds + '"的数据项？').then(function () {
        return delPosition(positionIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('student/position/export', {
        ...this.queryParams
      }, `position_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
