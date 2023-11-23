<template>
  <div class="main">
    <!-- 头部 -->
    <div class="title">
      <h1>新增目标岗位</h1>
    </div>

    <div class="content">
      <!-- 左侧  -->
      <div class="left">
        <h1>目录</h1>
        <!-- 分类列表 -->
        <div class="list" :class="{ fixed: isFixed }">
          <div v-for="Log in LogList" :key="Log.catalogueId">
            <p>{{ Log.catalogueName }}</p>
            <ul>
              <li v-for="item in Log.child" :key="item.catalogueName">
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
          <p>新增目标岗位</p>
          <div class="btn" v-if="comment() === 3">
            <el-button type="primary">保存</el-button>
          </div>
          <div class="btn" v-else>
            <el-button
              type="success"
              @click="commit('2')"
              v-if="this.positionDetail.state!=0"
              :disabled="
                this.positionDetail.state === 1
                  ? true
                  : this.positionDetail.state === 0
                  ? true
                  : false
              "
              >存为草稿</el-button
            >
            <el-button type="primary" v-if="this.positionDetail.state!=0" @click="commit('1')">发布</el-button>
          </div>
        </div>
        <!-- 表单内容 -->
        <div class="form-content">
          <!-- 目标岗位 -->
          <div class="inp_top">
            <p>目标岗位名称</p>
            <el-input
              style="width: 300px; margin-left: 30px"
              placeholder="请输入目标岗位"
              v-model="positionDetail.positionName"
              :disabled="
                this.positionDetail.state === 1
                  ? true
                  : this.positionDetail.state != 0
                  ? false
                  : true
              "
            ></el-input>
          </div>

          <div class="list">
            <!-- 表单 -->
            <div class="tables">
              <Form1
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  0,
                  10
                )"
                :state="positionDetail.state"
                :parentId="parentId[index]"
                :tableHeader="tableHeader[index]"
                :key="list.catalogueId"
                :list="list"
                ref="child"
              />
              <Form2
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  10,
                  12
                )"
                :state="positionDetail.state"
                :parentId="parentId[index + 10]"
                :tableHeader="tableHeader[index + 10]"
                :key="list.catalogueId"
                :list="list"
                ref="child"
              />
              <Form1
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  12,
                  15
                )"
                :state="positionDetail.state"
                :parentId="parentId[index + 12]"
                :tableHeader="tableHeader[index + 12]"
                :key="list.catalogueId"
                :list="list"
                ref="child"
              />
              <Form3
                v-for="(list, index) in positionDetail.ctatlogueList.slice(
                  15,
                  20
                )"
                :state="positionDetail.state"
                :parentId="parentId[index + 15]"
                :tableHeader="tableHeader[index + 15]"
                :key="list.catalogueId"
                :list="list"
                ref="child"
              />
            </div>
          </div>
        </div>
      </div>
    </div>



    <el-dialog title="发布" :visible.sync="SecondObject" width="20%"  append-to-body>
      <div>
        <p>确定发布该目标岗位吗?</p>
        <el-checkbox v-model="checked">将该目标岗位设置为主目标</el-checkbox>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="SecondObject = false">取 消</el-button>
        <el-button type="primary" @click="commitSon('1')"
          >确 定</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getcateLog,
  AddPosition,
  updatePosition,
  listPosition
} from "@/api/student/position.js";
import Form1 from "../components/Form1.vue";
import Form2 from "../components/Form2.vue";
import Form3 from "../components/Form3.vue";
export default {
  props: ["positionDetail", "Computedstate", "ObjectVisible","ComputedisMain","getIndex"],
  inject: ["comment"],
  components: {
    Form1,
    Form2,
    Form3,
  },
  data() {
    return {
      checked: false, //是否更新主目标
      LogList: [], //目录列表
      parentId: [], //目录父id
      FormList: [], // 筛选出表单填写的每一行数据
      childList: [],
      isFixed: false,
      SecondObject:false,//第二次发布
      scrollDistance: 0,
      isFixed: false,
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
  // 把state是发布||草稿||废止状态数据共享出来 不需要给子组件一个个绑定数据

  methods: {
    // 获取目录列表
    async getLog() {
      const res = await getcateLog();
      this.LogList = res.data;
      // console.log(res)
      this.parentId = this.LogList.flatMap((item) =>
        item.child.map((subItem) => subItem.parentId)
      );

      // console.log(this.parentId)
    },
    // 目录可见
    handleScroll() {
      // 滚动条距离
      this.scrollDistance =
        window.scrollY || document.documentElement.scrollTop;

      this.isFixed = this.scrollDistance >= 200;
      // console.log(this.scrollDistance, this.isFixed);
    },
    // 提交||保存
    async commit(state, checked) {
       this.$refs.child.sayHi();
      // 预先判断是发布还是保存 且判断已发布岗位不多余2个
      if (state == 1 && this.Computedstate >= 2) {
        this.$alert("最多仅能发布两个目标岗位", "提示", {
          cancelButtonText: "返回",
        });
        return;
      }
      // 第一次发布岗位
      if (state == 1 && this.Computedstate < 1) {
        this.$confirm("确定发布该目标岗位吗", "发布", {
          confirmButtonText: "确实",
          cancelButtonText: "返回",
        })
          .then(() => {
            // 确定后直接发布
            this.commitSon(state, true);
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
        this.SecondObject=true
        return
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
        isUpdate: this.checked === true ? true : false,
      };
      console.log(data);
      // 发送请求
      const res = await AddPosition(data);
      // console.log(res);
      this.SecondObject=false//对话框关闭
      // 通知父组件关闭对话框
      this.$emit("closeDialog", false);
        this.$emit("getIndex",'调用父组件刷新页面');
      // 用户提示
      this.$message({
        message: res.msg ,
        type: "success",
      });
    },
  },

  created() {
    this.getLog(); //获取目录
  },

  mounted() {
    console.log("我是positionDetail", this.positionDetail.state);
    // 检测滚动条变化更新
    // window.addEventListener("scroll", this.handleScroll);
  },
};
</script>

<style lang="scss" scoped>
.main {
  background-color: #f2f2f2;
  height: auto;

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
        &.fixed {
          position: fixed;
          top: 0; /* 添加其吸顶效果的样式 */
        }
        p {
          font-weight: 800;
          margin: 5px 0;
        }

        ul {
          margin: 0 0;
          list-style: none;
          padding: 0 0 0 20px;
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
          p {
            font-size: 20px;
            font-weight: 900;
          }
        }
        .list {
        }
      }
    }
  }
}
</style>