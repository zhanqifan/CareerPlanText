<template>
  <div class="main">
    <!-- 头部 -->
    <div class="title"></div>

    <div class="content">
      <!-- 左侧  -->
      <div class="left">
        <h1 id="left_top">目录</h1>
        <!-- 分类列表 -->
        <div class="list">
          <div v-for="Log in LogList" :key="Log.catalogueId">
            <p>{{ Log.catalogueName }}</p>
            <ul>
              <li
                class="hover_poiont"
                v-for="item in Log.child"
                :key="item.catalogueName"
                @click="goAnchor(item.catalogueId)"
              >
                {{ item.catalogueName }}
              </li>
            </ul>
          </div>
        </div>
      </div>

      <!-- 右侧 -->
      <div class="right">
        <!-- 表单标题 -->
        <div class="left_title">
          <p>
            {{
              this.positionDetail.positionName
                ? this.positionDetail.positionName
                : "新增目标"
            }}岗位
          </p>
          <div class="btn">
            <el-button
              type="success"
              @click="commit('2')"
              v-if="this.positionDetail.state != 3 ? false : true"
              >存为草稿</el-button
            >
            <el-button
              type="primary"
              v-if="
                this.positionDetail.state === 1
                  ? false
                  : this.positionDetail.state === 3
              "
              @click="commit('1')"
              >发布</el-button
            >
          </div>
        </div>
        <!-- 表单内容 -->
        <div class="form-content">
          <!-- 目标岗位 -->
          <div class="inp_top">
            <p>目标岗位名称</p>
            <el-form
              :model="positionDetail"
              style="margin-top: 30px"
              :rules="rules"
              ref="getName"
            >
              <el-form-item prop="positionName">
                <el-input
                  style="width: 300px; margin-left: 30px"
                  placeholder="请输入目标岗位"
                  v-model="positionDetail.positionName"
                  :disabled="positionDetail.state===3?false:changePositionName"
                ></el-input>
              </el-form-item>
            </el-form>
            <el-button style="margin:8px 0 0 5px" @click="changeObejctName" v-if="positionDetail.state===2"
           :type="changePositionName === true ? 'primary' : 'success'"
            >{{changePositionName===true?"编辑名称":"修改名称"}}</el-button>
          </div>

          <div class="list">
            <!-- 表单 -->
            <div class="tables">
              <Form1
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  0,
                  10
                )"
                :id="list.catalogueId"
                :state="positionDetail.state"
                :parentId="parentId[index]"
                :tableHeader="tableHeader[index]"
                :key="list.catalogueId"
                :list="list"
                ref="dataForm"
                @myForm="handleForm"
                @mySon="handlemyson"
              />
              <Form2
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  10,
                  12
                )"
                :id="list.catalogueId"
                :state="positionDetail.state"
                :parentId="parentId[index + 10]"
                :tableHeader="tableHeader[index + 10]"
                :key="list.catalogueId"
                :list="list"
                ref="dataForm"
                @myForm="handleForm"
                @mySon="handlemyson"
              />
              <Form1
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  12,
                  15
                )"
                :id="list.catalogueId"
                :state="positionDetail.state"
                :parentId="parentId[index + 12]"
                :tableHeader="tableHeader[index + 12]"
                :key="list.catalogueId"
                :list="list"
                ref="dataForm"
                @myForm="handleForm"
                @mySon="handlemyson"
              />
              <Form3
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  15,
                  20
                )"
                :id="list.catalogueId"
                :state="positionDetail.state"
                :parentId="parentId[index + 15]"
                :tableHeader="tableHeader[index + 15]"
                :key="list.catalogueId"
                :list="list"
                ref="dataForm"
                @myForm="handleForm"
                @mySon="handlemyson"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-dialog
      title="发布"
      :visible.sync="SecondObject"
      width="20%"
      append-to-body
    >
      <div>
        <p>确定发布该目标岗位吗?</p>
        <el-checkbox v-model="checked">将该目标岗位设置为主目标</el-checkbox>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="SecondObject = false">取 消</el-button>
        <el-button type="primary" @click="commitSon('1')">确 定</el-button>
      </span>
    </el-dialog>
    <div class="To_top">
      <el-button circle size="medium" @click="scrollToTop">目录</el-button>
    </div>
  </div>
</template>

<script>
import { getcateLog, AddPosition, changeName } from "@/api/student/position.js";
import Form1 from "../components/Form1.vue";
import Form2 from "../components/Form2.vue";
import Form3 from "../components/Form3.vue";
export default {
  props: [
    "positionDetail",
    "Computedstate",
    "ObjectVisible",
    "ComputedisMain",
    "getIndex",
  ],
  inject: ["comment"],
  components: {
    Form1,
    Form2,
    Form3,
  },
  data() {
    return {
      rules: {
        positionName: [
          {
            required: true,
            message: "岗位名称不为空",
            trigger: "blur",
          },
        ],
      },
      changePositionName: true,
      arrList: [], //多个子组件通知是否通过校验
      NamePass: "", //岗位名称校验判断值 true为未通过
      Pass: "", //父组件判断子组件是否完成校验 true为未通过
      Formvalue: 0, //通知子组件做校验
      checked: false, //是否更新主目标
      LogList: [], //目录列表
      parentId: [], //目录父id
      FormList: [], // 筛选出表单填写的每一行数据
      SecondObject: false, //第二次发布
      // 技能表格表头
      tableHeader: [
        {
          skill: "计划掌握的专业技能",
          point: "输入技能名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划掌握的其他技能",
          point: "输入技能名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的竞赛奖项",
          point: "输入奖项名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的学业奖项",
          point: "输入奖项名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的实践奖项",
          point: "输入奖项名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的活动奖项",
          point: "输入奖项名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的其他奖项",
          point: "输入奖项名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的职业资格证书",
          point: "输入证书名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的职业技能证书",
          point: "输入奖项名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划获得的其他证书",
          point: "输入奖项名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划实习的企业或行业",
          point: "输入实习的企业或行业名称",
          point1: "输入岗位名称",
          skillsName: "计划实习的岗位",
          start: "计划开始时间",
          end: "计划截止时间",
          content: "实习内容",
          control: "操作",
          control1: "实习内容",
        },
        {
          skill: "计划实践的项目",
          point: "输入项目名称",
          point1: "输入角色名称",
          skillsName: "计划实践的角色",
          start: "计划开始时间",
          end: "计划截止时间",
          content: "实践内容",
          control: "操作",
        },
        {
          skill: "计划发表的论文方向、主题、学科大类",
          point: "输入论文方向、主题名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划计划从事科研项目方向、主题",
          point: "输入科研项目方向、主题名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划从事专利成果方向、主题",
          point: "输入专利成果方向、主题名称",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划加入的学生会",
          point: "输入学生会名称",
          point1: "输入角色名称",
          skillsName: "计划担任的角色",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划加入的社团",
          point: "输入社团名称",
          point1: "输入角色名称",
          skillsName: "计划担任的角色",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划加入的其他组织",
          point: "输入其他组织名称",
          point1: "输入角色名称",
          skillsName: "计划担任的角色",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划培养的兴趣爱好",
          point: "请输入爱好名称",
          point1: "非必填",
          skillsName: "计划获得证书",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
        {
          skill: "计划实现的其他项目",
          point: "请输入爱好名称",
          point1: "非必填",
          skillsName: "备注",
          start: "计划开始时间",
          end: "计划截止时间",
          control: "操作",
        },
      ],
    };
  },

  methods: {
    // 获取目录列表
    async getLog() {
      const res = await getcateLog();
      this.LogList = res.data;
      console.log(this.LogList);
      this.parentId = this.LogList.flatMap((item) =>
        item.child.map((subItem) => subItem.parentId)
      );
    },

    // 提交||保存
    async commit(state) {
      this.arrList = []; //清空子组件表单通知校验队列
      await this.testForm(); //等待数值校验

      // console.log(this.Pass,this.NamePass)
      if (this.Pass == true || this.NamePass == true) {
        this.$message.error("表单不能有空");
        return;
      }
      // 预先判断是发布还是保存 且判断已发布岗位不多余2个
      if (state == 1 && this.Computedstate >= 2) {
        this.$alert("最多仅能发布两个目标岗位", "提示", {
          cancelButtonText: "返回",
        });
        return;
      }
      console.log(this.Computedstate);
      // 第一次发布岗位
      if (state == 1 && this.Computedstate < 1) {
        this.$confirm("确定发布该目标岗位吗", "发布", {
          confirmButtonText: "确实",
          cancelButtonText: "返回",
        })
          .then(() => {
            // 确定后直接发布
            this.checked = true;
            this.commitSon(state);
            console.log("判断1直接发布");
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消发布",
            });
          });
        return;
      }
      // 第二次发布岗位
      if (state == 1 && this.ComputedisMain === 1) {
        this.SecondObject = true;
        console.log("判断2询问发布");
        return;
      }
      // 草稿直接发布 不进入判定
      this.commitSon(state);
    },
    // 封装提交方法
    async commitSon(state) {
      // 数组筛选
      this.FormList = this.positionDetail.ctatlogueList
        .map((item) => item.skillsInfoList)
        .flat();

      //  请求参数整理
      let data = {
        positionName: this.positionDetail.positionName,
        state: state,
        skillsInfoList: this.FormList,
        isUpdate: this.checked === true ? true : false, //是否勾选了替换主目标
      };
      console.log(data);
      // 发送请求
      const res = await AddPosition(data);
      console.log(res);
      this.SecondObject = false; //对话框关闭
      // 通知父组件关闭对话框
      this.$emit("closeDialog", false);
      // 用户提示
      this.$message({
        message: res.msg,
        type: "success",
      });
    },
    // 修改草稿岗位名称
    async changeObejctName() {
      this.changePositionName = !this.changePositionName;
      // 再次禁用按钮时候发送修改请求
      if (this.changePositionName == true) {
        let data = {
          positionId: this.positionDetail.positionId,
          positionName: this.positionDetail.positionName,
        };
       const res=  await changeName(data);
        this.$message({
          message: res.msg,
          type: 'success'
        });
        // 更新岗位列表
        this.$emit('closeDialog',true)
      }
    },
    // 接受Form子组件数据并再向父通知
    handlemyson(targetPositionId, change) {
      if (targetPositionId) {
        this.$emit("getIndex", targetPositionId, change);
      }
    },
    // 测试表单按钮
    async testForm() {
      await this.positionDetail.ctatlogueList.forEach((element, index) => {
        this.$refs.dataForm[index].submitForm();
      });
      // 岗位名称
      await this.$refs.getName.validate((valid) => {
        if (valid) {
          this.NamePass = false;
          console.log(this.NamePass);
        } else {
          this.NamePass = true;
          console.log("岗位名称没通过");
          console.log(this.NamePass);
        }
      });
    },
    handleForm(value) {
      this.arrList.push(value);
      this.Pass = this.arrList.includes(0);
      console.log(this.arrList);
    },
    // 锚点定位
    goAnchor(id) {
      const targetElement = document.getElementById(id);
      if (targetElement) {
        targetElement.scrollIntoView({
          behavior: "smooth",
        });
      }
    },
    // 回到顶部
    scrollToTop() {
      const backtop = document.getElementById("left_top");
      if (backtop) {
        backtop.scrollIntoView({
          behavior: "smooth",
        });
      }
    },
  },

  created() {
    this.getLog(); //获取目录
    console.log(this.positionDetail);
  },
};
</script>

<style lang="scss" scoped>
.main {
  background-color: #f2f2f2;
  height: 100%;

  .title {
    display: flex;
    align-items: center;
    height: 70px;
    color: rgb(0, 6, 12);
    background-color: #f8f8f8;
    border-bottom: 2px solid #d8d8d8;
  }

  .content {
    display: flex;
    .left {
      background-color: white;
      width: 200px;
      height: 750px;
      .list {
        margin-left: 30px;

        p {
          font-weight: 800;
          margin: 5px 0;
        }

        ul {
          margin: 0 0;
          list-style: none;
          padding: 0 0 0 20px;
          li {
            &:hover {
              color: #1890ff;
              cursor: pointer;
            }
          }
        }
      }
    }

    .right {
      // background-color: white;
      margin-top: 20px;
      margin-left: 20px;
      width: 90%;
      margin-right: 20px;
      .left_title {
        height: 70px;
        background-color: white;
        border: 1px solid #d8d8d8;
        border-radius: 10px;
        padding-left: 20px;
        display: flex;
        align-items: center;
        justify-content: space-between;
        .btn {
          margin-right: 20px;
        }
        p {
          margin: 0;
          font-size: 20px;
        }
      }

      .form-content {
        margin-top: 20px;
        padding-left: 30px;
        background-color: white;
        .inp_top {
          display: flex;
          align-items: center;
          ::v-deep .el-form-item__error {
            padding-left: 30px;
          }
          p {
            font-size: 20px;
            font-weight: 900;
          }
        }
      }
    }
  }
  .To_top {
    position: fixed;
    bottom: 20px; /* 调整按钮距离底部的位置 */
  }
}
</style>