<template>
  <div>
    <div class="title">
      <svg
        t="1700025724203"
        class="icon"
        viewBox="0 0 1024 1024"
        version="1.1"
        xmlns="http://www.w3.org/2000/svg"
        p-id="7002"
        width="16"
        height="16"
      >
        <path
          d="M868.352 568.32q32.768 0 53.248 19.456t20.48 52.224l0 221.184q0 35.84-19.968 54.784t-52.736 18.944l-706.56 0q-33.792 0-56.832-22.528t-23.04-55.296l0-212.992q0-35.84 19.968-55.808t54.784-19.968l710.656 0zM868.352 90.112q32.768 0 53.248 18.944t20.48 52.736l0 220.16q0 35.84-19.968 54.784t-52.736 18.944l-706.56 0q-33.792 0-56.832-22.528t-23.04-55.296l0-211.968q0-35.84 19.968-55.808t54.784-19.968l710.656 0z"
          p-id="7003"
          fill="#1296db"
        ></path>
      </svg>
      <p class="word">{{ list.catalogueName }}</p>
    </div>
    <div class="btn_row">
      <el-button
        @click="AddRow"
        v-if="state === 3 ? true : false"
        >新增一行</el-button
      >
    </div>
    <div class="table-boder">
      <!-- 自评 -->
      <el-table
        class="tables"
        :data="list.skillsInfoList"
        v-if="comment() === 3"
      >
        <el-table-column label="序号" type="index" width="50">
        </el-table-column>
        <el-table-column :label="tableHeader.skill">
          <template slot-scope="scope">
            <el-input
              :placeholder="tableHeader.point"
              v-model="scope.row.skillsName"
              disabled
              clearable
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.skillsName">
          <template slot-scope="scope">
            <el-input
              :placeholder="tableHeader.point1"
              v-model="scope.row.skillsName1"
              disabled
              clearable
            ></el-input>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.start">
          <template slot-scope="scope">
            <el-date-picker
              v-model="scope.row.startTime"
              disabled
              placeholder="请选择时间"
            >
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.end">
          <template slot-scope="scope">
            <el-date-picker
              v-model="scope.row.endTime"
              disabled
              placeholder="请选择时间"
            >
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="自评状态" align="center">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.evaluateState === 1 ? 'success' : 'danger'"
              >{{ scope.row.evaluateState === 1 ? "已完成" : "未完成" }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="完成状态" align="center">
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.completionStatus === 1 ? 'success' : 'danger'"
              >{{
                scope.row.completionStatus === 1 ? "已完成" : "未完成"
              }}</el-tag
            >
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              v-show="scope.row.evaluateState === 0"
              @click="ToComment(true, scope.row, 0)"
            >
              去自评
            </el-button>
            <el-button
              @click="ToComment(true, scope.row, 1)"
              v-show="scope.row.evaluateState === 1"
              >编辑自评</el-button
            >
            <el-button
              v-show="scope.row.evaluateState === 1"
              @click="delComment(scope.row)"
              >删除自评</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!-- 创建 -->
      <el-table class="tables" :data="list.skillsInfoList" v-else>
        <el-table-column label="序号" type="index" width="50">
        </el-table-column>
        <el-table-column :label="tableHeader.skill">
          <template slot-scope="scope">
            <el-form
              :model="scope.row"
              :rules="rules"
              style="margin-top: 16px"
              :ref="'formData1' + scope.$index"
            >
              <el-form-item prop="skillsName">
                <el-input
                  :placeholder="tableHeader.point"
                  :disabled="scope.row.inp"
                  v-model="scope.row.skillsName"
                  clearable
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.skillsName">
          <template slot-scope="scope">
            <el-form
              :model="scope.row"
              :rules="rules"
              style="margin-top: 16px"
              :ref="'formData2' + scope.$index"
            >
              <el-form-item prop="takeRole">
                <el-input
                  :placeholder="tableHeader.point1"
                  :disabled="scope.row.inp"
                  v-model="scope.row.takeRole"
                  clearable
                ></el-input>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.start">
          <template slot-scope="scope">
            <el-form
              :model="scope.row"
              :rules="rules"
              style="margin-top: 16px"
              :ref="'formData3' + scope.$index"
            >
              <el-form-item prop="startTime">
                <el-date-picker
                  v-model="scope.row.startTime"
                  placeholder="请选择时间"
                  :disabled="scope.row.PickStart"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.end">
          <template slot-scope="scope">
            <el-form
              :model="scope.row"
              :rules="rules"
              style="margin-top: 16px"
              :ref="'formData4' + scope.$index"
            >
              <el-form-item prop="endTime">
                <el-date-picker
                  v-model="scope.row.endTime"
                  :disabled="scope.row.PickEnd"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.content">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.content != ' '&&scope.row.content !=null">已完成</el-tag>
            <el-tag v-else>未完成</el-tag>
          </template>
        </el-table-column>
        <el-table-column :label="tableHeader.control">
          <template slot-scope="scope">
            <el-button
              type="danger"
              @click="deleteRow(scope.$index, scope.row)"
              :disabled="state === 1 ? true : state === 0 ? true : false"
              >删除</el-button
            >
            <el-button @click="scope.row.dialogContent = true"
              >实习内容</el-button
            >
            <el-button
              v-if="state === 1 ? true : state === 2 ? true : false"
              :type="scope.row.btn_public === 0 ? 'primary' : 'success'"
              @click="ChangeRow(scope.row, scope.$index)"
              >{{ scope.row.btn_public === 0 ? "编辑" : "保存" }}</el-button
            >
            <el-button v-if="scope.row.evaluateState===1 && state===0" @click="ToComment(true, scope.row, 2)"
              >查看自评</el-button
            >

            <!--实习内容 弹出框 -->
            <el-dialog
              :title="'计划' + tableHeader.content"
              :visible.sync="scope.row.dialogContent"
              append-to-body
            >
              <el-input
                type="textarea"
                :autosize="{ minRows: 2, maxRows: 4 }"
                placeholder="请输入内容"
                :disabled="scope.row.inp"
                v-model="scope.row.content"
              >
              </el-input>
              <span slot="footer" class="dialog-footer">
                <el-button @click="scope.row.dialogContent = false"
                  >取 消</el-button
                >
                <el-button
                  type="primary"
                  @click="scope.row.dialogContent = false"
                  >确 定</el-button
                >
              </span>
            </el-dialog>
          </template>
        </el-table-column>
      </el-table>

      <!-- 自评弹窗 -->
      <el-dialog
        title="自评"
        :visible.sync="dialogComment"
        width="25%"
        append-to-body
        ref="myDialog"
      >
        <el-form
          :model="formComment"
          :rules="formCommentrules"
          class="CommentForm"
          ref="CommentForm"
        >
          <el-form-item label="完成情况" label-width="formLabelWidth">
            <el-radio v-model="radio" label="1" :disabled="lookComment" >已完成</el-radio>
            <el-radio v-model="radio" label="0" :disabled="lookComment">未完成</el-radio>
          </el-form-item>
          <el-form-item
            label="完成时间"
            label-width="formLabelWidth"
            v-if="Picker"
            prop="Completiontime"
          >
            <el-date-picker
              v-model="formComment.Completiontime"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              type="date"
              placeholder="选择日期"
              :disabled="lookComment"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item v-if="PickeDate" prop="textarea">
            <el-input
              type="textarea"
              :rows="6"
              :placeholder="
                radio === '1' ? '请输入超时完成原因' : '请输入未完成原因'
              "
              :disabled="lookComment"
              v-model="formComment.textarea"
            >
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="ToComment(false)"   v-if="lookComment == false">取 消</el-button>
          <el-button
            type="primary"
            v-if="evaluateState === 0 && lookComment == false"
            @click="Subcommit(evaluateState, 'CommentForm')"
            >确 定</el-button
          >
          <el-button
            type="primary"
            v-if="evaluateState === 1 &&lookComment == false"
            @click="Subcommit(evaluateState, 'CommentForm')"
            >修改</el-button
          >
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  TodoComments,
  getComment,
  ChangeComment,
  DeleteComment,
} from "@/api/student/mycomment.js";
import { DeleteRow, updatePosition } from "@/api/student/position.js";

export default {
  props: ["list", "tableHeader", "parentId", "state"],
  inject: ["comment"],
  data() {
    return {
      formComment: {
        Completiontime: "", //日期选择
        textarea: "", //自评内容
      },
      formCommentrules: {
        Completiontime: [
          {
            required: true,
            message: "请选择时间",
            trigger: "blur",
          },
        ],
        textarea: [
          {
            required: true,
            message: "请输入你的原因",
            trigger: ["blur","change"],
          },
        ],
      },
      dialogComment: false, //自评弹层
      lookComment: false,//自评禁用
      // dialogContent: false, //实习内容弹层
      lineNumber: 0, //行号
      evaluateId: null, //自评id
      evaluateState: 0, //自评状态
      targetPositionId: "", //目标id(通知页面数据刷新用)
      radio: "1", //选项

      Id: "", // 目标岗位id
      tableTime: "", //表单的结束时间 用来与自评完成时间对比
      // // 控制弹出框
      // dialog: false,

      rules: {
        skillsName: [
          {
            required: true,
            message: "请输入名称",
            trigger: ["blur", "change"],
          },
        ],
        takeRole: [
          {
            required: true,
            message: "请输入名称",
            trigger: ["blur", "change"],
          },
        ],
        startTime: [
          {
            required: true,
            message: "请选择时间",
            trigger: ["blur", "change"],
          },
        ],
        endTime: [
          {
            required: true,
            message: "请选择时间",
            trigger: ["blur", "change"],
          },
        ],
      },
    };
  },
  methods: {
    // 添加一行
    AddRow() {
      this.list.skillsInfoList.push({
        firstId: this.parentId, //父目录id
        ctatlogueId: this.list.catalogueId, //技能目录id
        skillsName: "", //计划实践的项目
        lineNumber: this.lineNumber++,
        takeRole: "", //计划实践的角色
        startTime: "", //开始时间
        endTime: "", ///结束时间
        content: " ", //实习内容
        dialogContent: false,
      });
    },
    // 删除当前行
    async deleteRow(index, row) {
      if (this.state === 2 && row.id!=undefined) {
        const res = await DeleteRow(row.id);
        console.log(res);
        this.$message({
          message: "删除成功",
          type: "success",
        });
      }
      this.lineNumber = this.lineNumber - 1;
      // 草稿态可单行删除
      this.list.skillsInfoList.splice(index, 1);
    },
    // 输入框逻辑判断
    inputDisbale(row) {
      if (this.state == 1) {
        row.inp = true;
      } else if (this.state == 0) {
        row.inp = true;
      } else {
        row.inp = false;
      }
      this.$forceUpdate();
    },
    //  结束时间逻辑判定
    endTimeDisable(row) {
      //  发布状态
      if (this.state === 1) {
        row.PickEnd = row.modificationsNumber != 0 ? true : false;
      }
      // 草稿状态
      if (this.state === 2) {
        row.PickEnd = false;
      }
      // 废止状态;
      if (this.state === 0) {
        row.PickEnd = true;
      }
    },
    // 开始时间逻辑判断
    startTimeDisable(row) {
      // 发布态 时间比对
      if (this.state === 1) {
        const data11 = new Date(row.startTime).getTime();
        const data22 = new Date();
        row.PickStart = data11 < data22 ? true : false;
      }
      // 草稿态直接开放
      if (this.state === 2) {
        row.PickStart = false;
      }
      // 废止态直接禁用
      if (this.state === 0) {
        row.PickStart = true;
      }
    },

    // 弹层控制
    ToComment(value, row, sum) {
      this.dialogComment = value; //弹窗开关
      this.tableTime = row.endTime; //结束时间 用与用户的对比
      this.Id = row.id; //传参用的id
      this.formComment.Completiontime = row.completeTime; //用户选择的完成时间
      this.evaluateState = row.evaluateState;
      this.targetPositionId = row.targetPositionId;
      console.log(this.Id);
      
      // 去自评 先清空所有数据
      if(sum===0){
        this.formComment.Completiontime=''
        this.formComment.textarea=''
         this.radio="1"
         this.lookComment = false;//可编辑态
      }
      // 如果是编辑按钮 做数据回显
      if (sum === 1) {
        this.lookup(row);
        this.lookComment = false;//可编辑态
      } 
      // 查看态 禁用权限
      else if (sum === 2) {
        this.lookComment = true;//禁用态
        this.lookup(row);
      }
    },
    // 发送自评1 修改自评0
    async Subcommit(evaluateState, formName) {
      // 先进入表单校验
      await new Promise((resolve, reject) => {
        this.$refs[formName].validate((valid) => {
          if (!valid) {
            reject(); // 如果验证失败，退出整个 Promise
          } else {
            resolve(); // 如果验证通过，继续执行后面的代码
          }
        });
      });
      // 修改自评
      if (evaluateState === 1) {
        let secondata = {
          evaluateId: this.evaluateId,
          content: this.formComment.textarea + " ",
          completionStatus: this.radio,
          completeTime: this.formComment.Completiontime,
          skillsId: this.Id,
        };
        console.log(secondata);
        const res = await ChangeComment(secondata);
        this.$emit("mySon", this.targetPositionId); //通知爷组件刷新
        this.$message({
          type: "success",
          message: "修改成功请刷新",
        });
        this.dialogComment = false;
      }
      if (evaluateState === 0) {
        // 添加自评
        let data = {
          skillsId: this.Id,
          content: this.formComment.textarea + " ",
          completionStatus: this.radio,
          completeTime: this.formComment.Completiontime,
        };
        // console.log(data)
        const res = await TodoComments(data);
        this.$emit("mySon", this.targetPositionId); //通知爷组件刷新
        this.$message({
          type: "success",
          message: "自评成功请刷新",
        });
        this.dialogComment = false;
      }
    },
    // 查看自评
    async lookup(row) {
      const res = await getComment(row.id);
      console.log(res);
      // 以下为回显
      this.radio = row.completionStatus.toString();
      this.formComment.textarea = res.data.content; //文本回显
      this.evaluateId = res.data.evaluateId; //自评id
      this.formComment.Completiontime = res.data.completeTime; //日期
    },

    // 删除自评
    async delComment(row) {
      await this.lookup(row);
      //   console.log(this.evaluateId)
      this.$confirm(
        "是否确认删除该自评,删除后自评状态将更新为未自评",
        "删除自评",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(async () => {
          const res = await DeleteComment(this.evaluateId);
          this.$emit("mySon", row.targetPositionId); //通知爷组件刷新

          console.log(res);
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },

    // 保存修改
    async ChangeRow(row, index) {
      if (row.btn_public === 0) {
        // 进入发布态切换
        this.inputDisbale(row);
        //  开始时间是否开启判断
        this.startTimeDisable(row);

        this.endTimeDisable(row);
        if (row.inp && row.PickStart && row.PickEnd) {
          row.btn_public = 0;
          this.$message({
            message: "该行已不可修改",
            type: "warning",
          });
        } else {
          row.btn_public = 1;
          row.OriginTime = row.endTime;
        }

        return;
      }
      else if (row.btn_public === 1) {
        await this.checkForm("formData1" + index);
        await this.checkForm("formData2" + index);
        await this.checkForm("formData3" + index);
        await this.checkForm("formData4" + index);
        let changeDate = {
          id: row.id,
          skillsName: row.skillsName,
          endTime: row.endTime,
          startTime: row.startTime,
          take_role: row.takeRole,
          content: row.content + " ",
          targetPositionId: row.targetPositionId,
          modificationsNumber:
            row.OriginTime === row.endTime
              ? row.modificationsNumber
              : row.modificationsNumber + 1,
        };
        // console.log(changeDate);
        const res = await updatePosition(changeDate);
        // console.log(res);
        let change = true;
        this.$emit("mySon", row.targetPositionId, change);
        row.btn_public = 0;
        row.inp = true;
        row.PickStart = true;
        row.PickEnd = true;
        this.$message({
          message: res.msg,
          type: "success",
        });
      }
    },

    // 日期格式转换
    DataFormat(time) {
      let date = new Date(time);
      // 提取年、月、日
      let year = date.getFullYear();
      let month = (date.getMonth() + 1).toString().padStart(2, "0"); // 月份是从0开始的，所以要加1
      let day = date.getDate().toString().padStart(2, "0");
      // 格式化为 "YYYY-MM-DD"
      return `${year}-${month}-${day}`;
    },
    // 表单校验
    submitForm() {
      //  console.log(this.list.skillsInfoList)
      let list = [];
      this.list.skillsInfoList.forEach((element, index) => {
        list.push(
          this.checkForm("formData1" + index),
          this.checkForm("formData2" + index),
          this.checkForm("formData3" + index),
          this.checkForm("formData4" + index)
        );
      });
      Promise.all(list)
        .then(() => {
          this.$emit("myForm", 1); //当前表单校验成功告知父组件
          console.log("通过检测");
        })
        .catch(() => {
          this.$emit("myForm", 0);
          console.log("未通过");
        });
    },
    checkForm(formName) {
      return new Promise((resolve, reject) => {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            resolve();
          } else reject();
        });
      });
    },
  },

  // 自评计算属性
  computed: {
    //  完成情况控制时间显示
    Picker() {
      if (this.radio === "1") {
        return true;
      } else {
        return false;
      }
    },
    // 日期判断控制
    PickeDate() {
      const date1 = new Date(this.formComment.Completiontime).getTime();
      const date2 = new Date(this.tableTime).getTime();
      if (date1 > date2) {
        return true;
      } else if (this.Picker == false) {
        return true;
      } else {
        return false;
      }
    },
  },
  created() {
    console.log(this.list.skillsInfoList);
  },
};
</script>

<style lang="scss" scoped>
.title {
  display: flex;
  align-items: center;
  border-bottom: 1px solid #d9d9d9;
  margin-bottom: 20px;
  height: 30px;
  margin-top: 30px;
  p {
    color: #657bbc;
    font-weight: 900;
  }
}
.btn_row {
  margin-bottom: 20px;
}
.CommentForm {
  ::v-deep .el-form-item__error {
    margin-left: 80px;
  }
}
</style>