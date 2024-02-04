<template>
  <div>
    <div class="main" v-loading="loading" v-if="isShow">
      <div class="top">个人数据分析</div>
      <div class="tab_btn">
        <el-select
          v-model="value"
          multiple
          placeholder="选择学院"
          @change="handlechange"
          clearable
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-select v-model="value1" multiple clearable placeholder="选择年级">
          <el-option
            v-for="item in options1"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-select v-model="value2" multiple clearable placeholder="选择系别">
          <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-select v-model="value3" clearable placeholder="选择目标类别">
          <el-option
            v-for="item in options3"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-select v-model="value4"  clearable placeholder="选择培养层次">
          <el-option
            v-for="item in options4"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
        <el-button type="primary" @click="Search">搜索</el-button>
      </div>
        <div class="row_title">
          <p class="title1">目标总览</p>
          <p class="title2">数据截止于:{{ AnalysisDate.deadlineDate }}</p>
        </div>
        <!-- 数据块 -->
        <div class="row_date">
          <div class="block_date">
            <p class="total">目标发布人数</p>
            <p class="word">{{ AnalysisDate.numberPublishers }}</p>
          </div>
          <div class="block_date">
            <p class="total">目标发布率</p>
            <p class="word">{{ AnalysisDate.publishingRate }}%</p>
          </div>
          <div class="block_date">
            <p class="total">人均项目数</p>
            <p class="word">{{ AnalysisDate.averageCompletionRate }}</p>
          </div>
          <div class="block_date">
            <p class="total">平均完成率</p>
            <p class="word">{{ AnalysisDate.annualAverageCompletionRate }}%</p>
          </div>
          <div class="block_date">
            <p class="total">年度平均项目数</p>
            <p class="word">{{ AnalysisDate.annualAverageNumberProjects }}</p>
          </div>
          <div class="block_date">
            <p class="total">年度平均完成率</p>
            <p class="word">{{ AnalysisDate.numberProjectsPerCapita }}%</p>
          </div>
        </div>
      <!-- 完成率分布图 -->
      <div class="Histogram" ref="Histogram"></div>
      <!-- 分类平均完成率明细和学院平均完成率排名 -->
      <div class="toLine">
        <div class="typeavager" ref="typeavager"></div>
        <div class="schoolDate" ref="schoolDate"></div>
      </div>
      <!-- 时效分析 -->
      <div class="row_Circle">
        <p>时效分析</p>
        <div class="Circle">
          <div class="Circle1" ref="Circle1"></div>
          <div class="Circle2" ref="Circle2"></div>
          <div class="Circle3" ref="Circle3"></div>
        </div>
        <!-- 圆点svg -->
        <div class="finished">
          <div>
            <svg
              t="1700728938632"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1837"
              width="16"
              height="16"
            >
              <path
                d="M514 512.4m-445.4 0a445.4 445.4 0 1 0 890.8 0 445.4 445.4 0 1 0-890.8 0Z"
                fill="#86DF6C"
                p-id="1838"
              ></path>
            </svg>
            <p>已完成</p>
          </div>
          <div>
            <svg
              t="1700728938632"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1837"
              width="16"
              height="16"
            >
              <path
                d="M514 512.4m-445.4 0a445.4 445.4 0 1 0 890.8 0 445.4 445.4 0 1 0-890.8 0Z"
                fill="#B2B2B2"
                p-id="1838"
              ></path>
            </svg>
            <p>未完成</p>
          </div>
          <div>
            <svg
              t="1700728938632"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1837"
              width="16"
              height="16"
            >
              <path
                d="M514 512.4m-445.4 0a445.4 445.4 0 1 0 890.8 0 445.4 445.4 0 1 0-890.8 0Z"
                fill="#249EFF"
                p-id="1838"
              ></path>
            </svg>
            <p>提前</p>
          </div>
          <div>
            <svg
              t="1700728938632"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1837"
              width="16"
              height="16"
            >
              <path
                d="M514 512.4m-445.4 0a445.4 445.4 0 1 0 890.8 0 445.4 445.4 0 1 0-890.8 0Z"
                fill="#7279FF"
                p-id="1838"
              ></path>
            </svg>
            <p>按时</p>
          </div>
          <div>
            <svg
              t="1700728938632"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1837"
              width="16"
              height="16"
            >
              <path
                d="M514 512.4m-445.4 0a445.4 445.4 0 1 0 890.8 0 445.4 445.4 0 1 0-890.8 0Z"
                fill="#6CDFDF"
                p-id="1838"
              ></path>
            </svg>
            <p>超时</p>
          </div>
          <div>
            <svg
              t="1700728938632"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1837"
              width="16"
              height="16"
            >
              <path
                d="M514 512.4m-445.4 0a445.4 445.4 0 1 0 890.8 0 445.4 445.4 0 1 0-890.8 0Z"
                fill="#FFB400"
                p-id="1838"
              ></path>
            </svg>
            <p>未过期</p>
          </div>
          <div>
            <svg
              t="1700728938632"
              class="icon"
              viewBox="0 0 1024 1024"
              version="1.1"
              xmlns="http://www.w3.org/2000/svg"
              p-id="1837"
              width="16"
              height="16"
            >
              <path
                d="M514 512.4m-445.4 0a445.4 445.4 0 1 0 890.8 0 445.4 445.4 0 1 0-890.8 0Z"
                fill="#D20E0E"
                p-id="1838"
              ></path>
            </svg>
            <p>过期</p>
          </div>
        </div>
      </div>
      <div class="toLine1">
        <div class="matrix" ref="matrix"></div>
        <!-- 近6月完成人次数 -->
        <div class="sixMonth" ref="sixMonth"></div>
      </div>
    </div>
    <div v-show="isShow === false">
      <el-empty :image-size="300" style="height: 900px"></el-empty>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { getanalysis,getCollegeList,getProfessionalList } from "@/api/teacher/analysis.js";
export default {
  data() {
    return {
      isShow: true, //空数据
      AnalysisDate: {}, //总数据
      collegeRankingList: [], //学院平均完成率排名
      TimeAnalysisList: {}, //平均时效分析
      moonCompletionsNumVOList: [], //近6月完成人数
      loading: true,
      options:[],//学院
      value:[],
      //年级
      options1: [
        { label: "2022", value: "2022" },
        { label: "2023", value: "2023" },
        { label: "2024", value: "2024" },
      ], 
      value1: [],
      // 系别
      options2:[],
      value2:"",
      //目标类别
       options3: [
        {
          label: "主目标",
          value: 1,
        },
        {
          label: "非主目标",
          value: 0,
        },
      ], 
      value3: [],
      //培养层次
      options4: [
        {
          label: "专升本",
          value: "专升本",
        },
        {
          label: "本科",
          value: "本科",
        },
      ], 
      value4: [],
    };
  },
  methods: {
    // 获取学院列表
  async getSchool(){
     const res = await getCollegeList();
    console.log(res);
    this.options = res.data.map((item) => {
      return { value: item.deptName, label: item.deptName };
    });
   }, 
    async getanaly() {
      try {
        const res = await getanalysis({college:['法学院']});//页面数据 后端原因 这里暂时写死传参 空传参拿不到数据
        this.getSchool()//学院列表
        console.log(res.data);
        this.AnalysisDate = res.data;
        this.collegeRankingList = res.data.collegeRankingList;
        this.TimeAnalysisList = {
          completionsNum: res.data.completionsNum, //完成数
          unfinishedNum: res.data.unfinishedNum, //未完成数
          timeoutCompletionsNum: res.data.timeoutCompletionsNum, //超时完成数
          beforeCompletionsNum: res.data.beforeCompletionsNum, //提前完成数
          justCompletionsNum: res.data.justCompletionsNum, //按时完成数
          expiredTargetNum: res.data.expiredTargetNum, //未完成过期数
          notExpiredTargetNum: res.data.notExpiredTargetNum, //未完成未过期数
        };
        this.moonCompletionsNumVOList = res.data.moonCompletionsNumVOList;
        this.isShow = true;
      } catch {
        this.isShow = false;
      }
    },
    // 获取系别
    async handlechange(){
      console.log(this.value)
       // 学院为空时 系部状态变更
      if (this.value.length === 0) {
        this.options2 = [{ label: "请先选择学院", value: "0" }];
        this.value2 = [];
        return;
      }
      const res = await getProfessionalList(this.value);
      console.log(res)
      this.options2 = res.data.map((item) => {
        return { value: item.deptName, label: item.deptName };
      });
      console.log(res);
    },
    // 搜索
   async Search(){
      const res =await getanalysis({college:this.value,professionalName:this.value1,grade:this.value2,isMain:this.value3,cultivationLevel:this.value4})
     console.log(res)
    },
    // 完成率分布图 空数据写死
    complete() {
      var Histogram = echarts.init(this.$refs.Histogram);
      Histogram.setOption({
        title: {
          text: "完成率分布图",
        },
        tooltip: {
          formatter: function (params) {
            return "完成人数: " + params.value + "人";
          },
        },
        xAxis: {
          name: "人数",
          axisLine: {
            show: true,
          },
          splitLine: {
            show: false, // 不显示网格线
          },
        },
        yAxis: {
          axisTick: {
            //是否显示坐标刻度线
            show: false,
          },
          type: "category",
          name: "完成率分类",
          data: ["0-20%", "21-40%", "41-60%", "61-80%", "81-100%"],
        },
        series: [
          {
            name: "完成人数",
            type: "bar",
            barGap: "40px", // 表示柱子之间的间隔是柱子宽度的 30%
            data: [36, 89, 30, 10, 100],
            itemStyle: {
              color: "#3BA1FF", // 设置柱子颜色
            },
          },
        ],
      });
    },
    // 分类平均完成率明细 后端没写
    leida() {
      var typeavager = echarts.init(this.$refs.typeavager);
      typeavager.setOption({
        title: {
          text: "分类平均完成率明细",
        },
        tooltip: {},
        legend: {
          data: ["本院平均", "本校平均"],
          right: "37%",
          textStyle: {
            // 设置图例文字的样式
            fontSize: 16, // 你可以根据需要调整这个值来改变字体大小
          },
          top: "5%",
        },
        radar: {
          center: ["50%", "55%"],
          name: {
            textStyle: {
              color: "#fff",
              backgroundColor: "#999",
              borderRadius: 3,
              padding: [3, 5],
            },
          },
          indicator: [
            { name: "其他方面", max: 30000 },
            { name: "兴趣爱好", max: 30000 },
            { name: "校园活动", max: 30000 },
            { name: "科研经历", max: 30000 },
            { name: "实践经历", max: 30000 },
            { name: "实习经历", max: 30000 },
            { name: "证书分类", max: 30000 },
            { name: "奖项经历", max: 30000 },
            { name: "技能分类", max: 30000 },
          ],
        },
        series: [
          {
            name: "预算 vs 开销（Budget vs spending）",
            type: "radar",
            // areaStyle: {normal: {}},
            data: [
              {
                value: [
                  14300, 10000, 28000, 25000, 20000, 19000, 14300, 10000, 28000,
                  25000, 20000, 19000,
                ],
                name: "本院平均",
              },
              {
                value: [
                  15000, 14000, 28000, 21000, 22000, 25000, 14000, 28000, 21000,
                  22000, 25000, 14000,
                ],
                name: "本校平均",
              },
            ],
          },
        ],
      });
    },
    // 学院平均完成率排名
    schoolAvager() {
      var schoolDate = echarts.init(this.$refs.schoolDate);
      schoolDate.setOption({
        title: {
          text: "学院平均完成率排名",
        },
        xAxis: {
          name: "完成率",
          data: this.collegeRankingList.map((item) => item.collegeName),
        },
        tooltip: {
          formatter: function (params) {
            return params.name + ":" + params.value + "%";
          },
        },
        yAxis: {
          axisLabel: {
            show: false,
          },
          type: "value",
          name: "完成率",
          min: 0, //最小
          max: 100, //最大
          interval: 50, //数值差
          axisLabel: {
            formatter: "{value}%", // 通过 formatter 显示百分比
          },
          // splitLine: {
          //   show: false, // 不显示网格线
          // },
          axisLine: {
            show: true,
          },
        },
        series: [
          {
            name: "销量",
            type: "bar",
            data: this.collegeRankingList.map(
              (item) => item.completionRate * 100
            ), //不确定后端传小数还是整数 先作为假设为小数*100
            itemStyle: {
              color: "#3BA1FF", // 设置柱子颜色
            },
          },
        ],
      });
    },
    // 数据都是0显示不出来 写死方便展示
    circle() {
      this.Circle1 = echarts.init(this.$refs.Circle1);
      this.Circle1.setOption({
        title: {
          text: "总体分析",
          left: "center",
          top: "center",
        },
        tooltip: {
          formatter: function (params) {
            return params.name + ": " + params.value + "项";
          },
        },
        series: [
          {
            type: "pie",
            data: [
              {
                value: 1,
                name: "未完成",
                itemStyle: {
                  color: "#B2B2B2", // 设置柱子颜色
                },
              },
              {
                value: 2,
                name: "已完成",
                itemStyle: {
                  color: "#86DF6C", // 设置柱子颜色
                },
              },
            ],
            label: {
              formatter: function (data) {
                return `${data.percent.toFixed(1)}%`;
              },
            },
            radius: ["50%", "70%"],
          },
        ],
      });
      // 时效分析2
      this.Circle2 = echarts.init(this.$refs.Circle2);
      this.Circle2.setOption({
        title: {
          text: "已完成分析",
          left: "center",
          top: "center",
        },
        tooltip: {
          formatter: function (params) {
            return params.name + ": " + params.value + "项";
          },
        },
        series: [
          {
            type: "pie",

            data: [
              {
                value: 3,
                name: "按时",
              },
              {
                value: 4,
                name: "超时",
                itemStyle: {
                  color: "#6CDFDF", // 设置柱子颜色
                },
              },

              {
                value: this.TimeAnalysisList.beforeCompletionsNum,
                name: "提前",
                itemStyle: {
                  color: "#249EFF", // 设置柱子颜色
                },
              },
            ],
            label: {
              formatter: function (data) {
                return `${data.percent.toFixed(1)}%`;
              },
            },
            radius: ["50%", "70%"],
          },
        ],
      });
      // 时效分析3
      this.Circle3 = echarts.init(this.$refs.Circle3);
      this.Circle3.setOption({
        title: {
          text: "未完成",
          left: "center",
          top: "center",
        },
        tooltip: {
          formatter: function (params) {
            return params.name + ": " + params.value + "项";
          },
        },
        series: [
          {
            type: "pie",
            data: [
              {
                value: 2,
                name: "未过期",
                itemStyle: {
                  color: "#FFB400", // 设置柱子颜色
                },
              },
              {
                value: 1,
                name: "过期",
                itemStyle: {
                  color: "#D20E0E", // 设置柱子颜色
                },
              },
            ],
            label: {
              formatter: function (data) {
                return `${data.percent.toFixed(1)}%`;
              },
            },
            // 内半径和外半径(圆大小)
            radius: ["50%", "70%"],
          },
        ],
      });
    },
    // 类别占比矩阵 没设计
    matrix() {},
    // 学院平均完成率排名
    RecentlySixMonth() {
      var sixMonth = echarts.init(this.$refs.sixMonth);
      sixMonth.setOption({
        title: {
          text: "近6月完成人次数",
        },
        xAxis: {
          name: "完成时间",
          data: this.moonCompletionsNumVOList.map((item) => item.moon),
        },
        tooltip: {
          formatter: function (params) {
            return "完成:" + params.value + "人次";
          },
        },
        yAxis: {
          axisLabel: {
            show: false,
          },
          type: "value",
          name: "完成率",
          min: 0, //最小
          max: 1000, //最大
          interval: 500, //数值差
          axisLabel: {
            formatter: "{value}人次", // 通过 formatter 显示百分比
          },
          // splitLine: {
          //   show: false, // 不显示网格线
          // },
          axisLine: {
            show: true,
          },
        },
        series: [
          {
            type: "bar",
            data: this.moonCompletionsNumVOList.map(
              (item) => item.moonCompletionsPersonNum
            ), //不确定后端传小数还是整数 先作为假设为小数*100
            itemStyle: {
              color: "#3BA1FF", // 设置柱子颜色
            },
          },
        ],
      });
    },
  },
  async mounted() {
    await this.getanaly();
    this.complete();
    this.leida();
    this.schoolAvager();
    this.circle();
    this.RecentlySixMonth();
    this.loading = false;
    // 绘制图表
  },
};
</script>

<style scoped lang="scss">
.main {
  background-color: #f3f3f4;
  height: 100%;
  width: 100%;
  padding: 20px 20px;
  
  ::v-deep .circular {
    position: absolute;
    margin-top: -30%;
    height: 200px;
    width: 200px;
  }
  .top {
    height: 60px;
    background-color: white;
    border: 1px solid #d8d8d8;
    border-radius: 5px;
    padding-left: 15px;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
  .tab_btn {
    margin-top: 10px;
    height: auto;
    border-radius: 10px 10px 0 0;
    background-color: white;
    padding-left: 30px;
    padding-top: 10px;
    .el-select {
      width: 300px;
      margin-bottom: 10px;
    }
  }
   .row_title {
    display: flex;
    width: 100%;
    justify-content: space-between;
    .title1 {
      font-weight: 900;
      font-size: 17px;
    }
  }
  .row_date {
    display: flex;
    justify-content: space-between;
    .block_date {
      width: 220px;
      height: 140px;
      background-color: white;
      padding-left: 30px;
      display: flex;
      flex-direction: column;
      .total {
        font-size: 15px;
        color: #c0c0c0;
        font-weight: 700;
      }
      .word {
        margin: 0;
        font-size: 30px;
      }
    }
  }
  .Histogram {
    padding-top: 20px;
    margin-top: 30px;
    background-color: white;
    width: 100%;
    height: 350px;
    border-radius: 6px;
  }
  .toLine {
    display: flex;
    justify-content: space-between;
    .typeavager {
      padding-top: 20px;
      margin-top: 30px;
      background-color: white;
      width: 50%;
      height: 450px;

      border-radius: 6px;
    }
    .schoolDate {
      padding-top: 20px;
      margin-top: 30px;
      background-color: white;
      width: 49%;
      height: 450px;
      border-radius: 6px;
    }
  }
  .row_Circle {
    padding-top: 3px;
    padding-left: 5px;
    font-weight: 900;
    font-size: 20px;
    margin-top: 30px;
    background-color: white;
    .Circle {
      display: flex;
      justify-content: space-around;
      .Circle1 {
        width: 450px;
        height: 350px;
      }
      .Circle2 {
        width: 450px;
        height: 350px;
      }
      .Circle3 {
        width: 450px;
        height: 350px;
      }
    }
    .finished {
      display: flex;
      justify-content: center;
      div {
        margin-right: 30px;
        display: flex;
        font-size: 14px;
        align-items: center;
        font-weight: 600;
      }
    }
  }
  .toLine1 {
    display: flex;
    justify-content: space-between;
    .matrix {
      padding-top: 20px;
      margin-top: 30px;
      background-color: white;
      width: 50%;
      height: 450px;

      border-radius: 6px;
    }
    .sixMonth {
      padding-top: 20px;
      margin-top: 30px;
      background-color: white;
      width: 49%;
      height: 450px;
      border-radius: 6px;
    }
  }
}
</style>