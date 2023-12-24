<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="岗位名称" prop="positionName">
        <el-input
          v-model="Name"
          placeholder="请输入岗位名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="目标状态" prop="state">
        <el-select v-model="value" placeholder="请选择岗位状态">
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="reset"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleRowClick(null)"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="dialogTableVisible = true"
          >设置主目标</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>
    <!-- @row-click="handleRowClick" -->
    <el-table v-loading="loading" :data="positionList">
      <el-table-column type="index" label="序号" width="55" align="center" />
      <el-table-column label="岗位名称" align="center" prop="positionName" />
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        width="160"
      >
      </el-table-column>
      <el-table-column
        label="总体完成率"
        align="center"
        prop="completionRate"
      />
      <el-table-column
        label="本年度1-6月项目数"
        align="center"
        prop="completedQuantity1"
      />
      <el-table-column
        label="本年度1-6月完成率"
        align="center"
        prop="completionRate1"
      />
      <el-table-column
        label="本年度7-12月项目数"
        align="center"
        prop="completedQuantity2"
      />
      <el-table-column
        label="本年度7-12月完成率"
        align="center"
        prop="completionRate2"
      />
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-tag
            :type="
              scope.row.state === 1
                ? 'primary'
                : scope.row.state === 2
                ? 'success'
                : 'danger'
            "
            >{{
              scope.row.state === 1
                ? "已发布"
                : scope.row.state === 2
                ? "草稿"
                : "已废止"
            }}</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column label="是否是主目标" align="center" prop="isMain">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.state != 0">{{
            scope.row.isMain === 1 ? "是" : "否"
          }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="教师批阅" align="center" prop="reviewsNumber">
        <template slot-scope="scope">
          <!-- 使用一个可点击的元素，比如 span，并绑定点击事件 -->
          <el-popover
            :disabled="scope.row.reviewsNumber > 0 ? false : true"
            placement="bottom"
            title="教师批阅"
            width="300"
            trigger="click"
          >
            <div
              v-for="item in TeacherComment"
              :key="item"
              style="margin-top: 10px"
            >
              {{ item.content }}<br />发布于:{{ item.updateTime }}
            </div>

            <el-link slot="reference" @click="handleReviewsClick(scope.row)">
              {{ scope.row.reviewsNumber }}
            </el-link>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <!-- 发布和草稿状态 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleRowClick(scope.row)"
            v-if="scope.row.state != 0"
            >编辑</el-button
          ><!-- 只给发布状态用 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-chat-dot-round"
            v-if="scope.row.state == 1"
            @click="handleComment(scope.row, 3)"
            >目标自评</el-button
          >
          <!-- 只给发布状态用 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="scope.row.state === 1"
            @click="ToStop(scope.row)"
            >废止</el-button
          >
          <el-button
            v-if="scope.row.state === 2"
            type="text"
            icon="el-icon-folder-add"
            @click="PublicRowClick(scope.row)"
            >发布</el-button
          >

          <!-- 只给废止状态用 -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            v-if="scope.row.state === 0"
            @click="SeeObject(scope.row)"
            >查看</el-button
          >
          <el-button
            v-if="scope.row.state != 1"
            icon="el-icon-scissors"
            size="mini"
            type="text"
            @click="DeleteRowClick(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="block">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="1"
        :page-sizes="[10, 20, 30, 40]"
        :page-size="10"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>

    <!-- 添加岗位弹出层 -->
    <div class="first">
      <el-dialog padding="0" :visible.sync="ObjectVisible" width="100%">
        <addposition
          :positionDetail="positionDetail"
          :ComputedisMain="ComputedisMain"
          :Computedstate="Computedstate"
          :ObjectVisible="ObjectVisible"
          @getIndex="getIndex"
          @closeDialog="handleDialog"
        />
      </el-dialog>
    </div>

    <!-- 设置主目标弹出层 -->
    <el-dialog
      title="设置主目标"
      :visible.sync="dialogTableVisible"
      width="25%"
      class="SetObject"
      @close="positionId = ''"
    >
      <el-select v-model="positionId" placeholder="请选择">
        <el-option
          v-for="item in ObjectList"
          :key="item.positionId"
          :label="item.positionName"
          :value="item.positionId"
        >
        </el-option>
      </el-select>
      <div slot="footer" class="dialog-footer">
        <el-button @click="canceldata()">取 消</el-button>
        <el-button type="primary" @click="SetObject()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listPosition,
  getPosition,
  StopPosition,
  delPosition,
  PublicPosition,
  SetPositoin,
  GetRead,
} from "@/api/student/position";

import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import addposition from "./addposition.vue";
export default {
  components: { addposition },
  provide() {
    return {
      comment: () => {
        return this.comment;
      },
    };
  },
  data() {
    return {
      checkedObject: false, // 草稿是否设为主目标
      loading: false, // 遮罩层
      SendObject: false, //草稿发布提示
      single: true, // 非单个禁用
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      ObjectList: [],
      // 总条数
      total: 0,
      positionId: "", // 岗位详情Id
      // 设置主目标弹出框
      dialogTableVisible: false,
      // 新增弹出框
      ObjectVisible: false,
      comment: null, //通知子组件为自评
      Computedstate: null, // 已发布岗位的个数
      ComputedisMain: null, // 主目标个数
      // 岗位列表数据
      positionList: [],
      // 新增假数据
      positionDetail: {
        positionName: "",
        // 技能列表
        ctatlogueList: [
          {
            catalogueId: 101,
            catalogueName: "专业技能",
            skillsInfoList: [],
          },
          {
            catalogueId: 102,
            catalogueName: "其他技能",
            skillsInfoList: [],
          },
          {
            catalogueId: 111,
            catalogueName: "竞赛奖项",
            skillsInfoList: [],
          },
          {
            catalogueId: 112,
            catalogueName: "学业奖项",
            skillsInfoList: [],
          },
          {
            catalogueId: 113,
            catalogueName: "实践奖项",
            skillsInfoList: [],
          },
          {
            catalogueId: 114,
            catalogueName: "活动奖项",
            skillsInfoList: [],
          },
          {
            catalogueId: 115,
            catalogueName: "其他奖项",
            skillsInfoList: [],
          },
          {
            catalogueId: 121,
            catalogueName: "职业资格证书",
            skillsInfoList: [],
          },
          {
            catalogueId: 122,
            catalogueName: "职业技能证书",
            skillsInfoList: [],
          },
          {
            catalogueId: 123,
            catalogueName: "其他证书",
            skillsInfoList: [],
          },
          {
            catalogueId: 131,
            catalogueName: "实习经历",
            skillsInfoList: [],
          },
          {
            catalogueId: 141,
            catalogueName: "实践经历",
            skillsInfoList: [],
          },
          {
            catalogueId: 151,
            catalogueName: "科研论文",
            skillsInfoList: [],
          },
          {
            catalogueId: 152,
            catalogueName: "科研项目",
            skillsInfoList: [],
          },
          {
            catalogueId: 153,
            catalogueName: "专利成果",
            skillsInfoList: [],
          },
          {
            catalogueId: 161,
            catalogueName: "学生会",
            skillsInfoList: [],
          },
          {
            catalogueId: 162,
            catalogueName: "社团",
            skillsInfoList: [],
          },
          {
            catalogueId: 163,
            catalogueName: "其他经历",
            skillsInfoList: [],
          },
          {
            catalogueId: 171,
            catalogueName: "兴趣爱好",
            skillsInfoList: [],
          },
          {
            catalogueId: 181,
            catalogueName: "其他方面",
            skillsInfoList: [],
          },
        ],
      },
      // 搜索框目标状态
      options: [
        {
          value: "0",
          label: "已废止",
        },
        {
          value: "1",
          label: "已发布",
        },
        {
          value: "2",
          label: "草稿",
        },
      ],
      value: "", //搜索框
      Name: "", //搜索岗位名称
      // 分页默认参数
      pageNum: 1,
      pageSize: 10,
      //教师评价
      TeacherComment: [],
    };
  },
  methods: {
    // /** 查询岗位列表 */
    async getList() {
      this.loading = true;
      const res = await listPosition();
      this.total = res.total;
      this.positionList = res.rows;
      this.loading = false;
      // 已发布岗位数量
      this.Computedstate = this.positionList.filter(
        (item) => item.state === 1
      ).length;
      console.log("已发布个数", this.Computedstate);
      // 筛选状态为发布岗位数组
      this.ObjectList = this.positionList.filter((item) => item.state === 1);
      // 计算主目标个数
      this.ComputedisMain = this.positionList.filter(
        (item) => item.isMain === 1
      ).length;
    },
    // 查看岗位详情
    async handleRowClick(row) {
      // console.log("我是行", row);
      // 点击编辑时候发请求
      if (row != null) {
        const res = await getPosition(row.positionId);
        console.log(res.data);
        this.comment = null;
        this.positionDetail = res.data;
        this.addString();

        console.log("我是真数据", this.positionDetail);
      }
      // 点击新增置空数组
      else {
        this.positionDetail.positionName = "";
        this.positionDetail.state = 3;
        this.comment = null;
        for (let i = 0; i < this.positionDetail.ctatlogueList.length; i++) {
          this.positionDetail.ctatlogueList[i].skillsInfoList = [];
        }
      }
      // console.log(res)
      this.ObjectVisible = true;
    },
    // 添加数据字段
    addString() {
      this.positionDetail.ctatlogueList.forEach((catalogue) => {
        // 遍历当前目录的 skillsInfoList
        catalogue.skillsInfoList.forEach((skillsInfo) => {
          //     // 在每个对象中添加新的属性
          // 更改为这样 由于是动态添加属性 保证后续组件修改保持响应式
          this.$set(skillsInfo, "btn_public", 0); // 编辑按钮的切换
          this.$set(skillsInfo, "inp", true); // 第一个输入框禁用
          this.$set(skillsInfo, "PickStart", true); // 开始日期禁用
          this.$set(skillsInfo, "PickEnd", true); // 结束日期禁用
          this.$set(skillsInfo, "dialogContent", false); // 实习内容对话框
        });
      });
    },
    // 删除草稿岗位
    DeleteRowClick(row) {
      this.$confirm("删除后将无法查看, 是否继续?", "删除岗位", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          await delPosition(row.positionId);
          await this.getList();
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {});
    },
    // 发布草稿岗位
    PublicRowClick(row) {
      if (this.Computedstate === 2) {
        this.$message.error("最多仅能发布两个目标岗位");
        return;
      }
      this.$confirm("确定发布该草稿岗位吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          await PublicPosition(row.positionId);
          this.getList();
          this.$message({
            type: "success",
            message: "发布成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
      // console.log(this.ComputedisMain)

      // this.$message({
      //   message: "发布成功",
      //   type: "success",
      // });
    },
    // 进入目标自评
    async handleComment(row, num) {
      if (row != null) {
        const res = await getPosition(row.positionId);
        this.positionDetail = res.data;
        console.log(res);
        this.comment = num; //自评关键字
      }

      this.ObjectVisible = true;
    },
    // 对话框
    handleDialog(boolean) {
      // console.log(boolean)
      this.ObjectVisible = boolean;
      this.getList();
      console.log("刷新");
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 重置按钮
    reset() {
      this.Name = "";
      this.value = "";
      this.getList();
    },
    /** 搜索按钮操作 */
    async handleQuery() {
      this.loading = true;
      const res = await listPosition(this.Name, this.value);
      console.log(res);
      this.loading = false;
      this.positionList = res.rows;
      // this.queryParams.pageNum = 1;
      // this.getList();
    },
    // 废止目标
    ToStop(row) {
      this.$confirm("确定废止目标吗？废止后无法恢复，仅支持查看。", "废止", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(async () => {
          await StopPosition(row.positionId); //废止
          await this.getList(); //刷新获取数据
          // 变更主目标条件
          if (this.ObjectList.length === 1 && this.ObjectList[0].isMain === 0) {
            await SetPositoin(this.ObjectList[0].positionId);
            await this.getList();
          }
          this.$message({
            type: "success",
            message: "废止成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消",
          });
        });
    },
    // 查看废止状态
    async SeeObject(row) {
      console.log(row);
      this.ObjectVisible = true;
      this.handleRowClick(row);
    },
    // 设置主目标取消按钮
    canceldata() {
      this.positionId = "";
      this.dialogTableVisible = false;
    },
    // 设置主目标确定按钮
    async SetObject() {
      if (this.positionId != "") {
        const res = await SetPositoin(this.positionId);
        this.$message({
          message: "修改主目标成功",
          type: "success",
        });
        this.dialogTableVisible = false;
      } else {
        this.$message("未选择要设置主目标返回页面");
        this.dialogTableVisible = false;
      }
      this.getList();
    },
    // 选择第几页
    async handleCurrentChange(num) {
      this.pageNum = num;
      this.loading = true;
      const res = await listPosition(
        this.Name,
        this.value,
        this.pageNum,
        this.pageSize
      );

      this.positionList = res.rows;
      this.loading = false;
    },
    // 页面展示数量
    async handleSizeChange(val) {
      this.pageSize = val;

      this.loading = true;
      const res = await listPosition(
        this.Name,
        this.value,
        this.pageNum,
        this.pageSize
      );

      this.positionList = res.rows;
      this.loading = false;
    },
    //  处理子组件刷新
    async getIndex(targetPositionId, change) {
      if (targetPositionId != "") {
        const res = await getPosition(targetPositionId);
        this.positionDetail = res.data;
        if (change == true) {
          this.addString();
        }
      }
    },
    // 获取教师评价
    async handleReviewsClick(row) {
      const res = await GetRead(row.positionId);
      this.TeacherComment = res.data;
    },
  },
  async created() {
    this.getList();
    const res = await ReviewList();
    console.log(res);
  },
};
</script>
<style scoped lang="scss">
.block {
  margin-top: 130px;
  margin-right: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
