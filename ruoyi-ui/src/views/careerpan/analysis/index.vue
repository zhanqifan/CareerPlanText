<template>
  <div>
    <div
      class="main"
      v-loading="loading"
      element-loading-text="拼命加载中"
      element-loading-spinner="el-icon-loading"
    >
      <div class="top">个人数据分析</div>

      <div class="tab_btn">
        <el-button
          :class="{ active: index === listactive }"
          v-for="(item, index) in positionList"
          :key="item.id"
          type="text"
          @click="listactive = index"
          >{{ item.positionName }}</el-button
        >
      </div>

      <div class="row_title">
        <p class="title1">目标总览</p>
        <p class="title2">数据截止于:{{ Studentlist.deadlineDate }}</p>
      </div>
      <!-- 数据块 -->
      <div class="row_date">
        <div class="block_date">
          <p class="total">目标总数</p>
          <p class="word">{{ Studentlist.targetNum }}</p>
        </div>
        <div class="block_date">
          <p class="total">总体完成率</p>
          <p class="word">{{ Studentlist.completionRate }}%</p>
        </div>
        <div class="block_date">
          <p class="total">本年度截取项目数</p>
          <p class="word">{{ Studentlist.yearClose }}</p>
        </div>
        <div class="block_date">
          <p class="total">本月截止项目数</p>
          <p class="word">{{ Studentlist.moonClose }}</p>
        </div>
        <div class="block_date">
          <p class="total">年度目标完成率</p>
          <p class="word">{{ Studentlist.yearCompletionRate }}</p>
        </div>
        <div class="block_date">
          <p class="total">发布时长</p>
          <p class="word">{{ Studentlist.releaseDuration }}天</p>
        </div>
      </div>
      <!-- 柱状图 -->
      <div class="Histogram" ref="Histogram"></div>
      <!-- 大类完成率 -->
      <div class="toLine">
        <div class="Column" ref="Column"></div>
        <!-- 同年级系聚类分析 -->
        <div class="Scatter_plot" ref="Scatter_plot"></div>
      </div>
      <!-- 时效分析 -->
      <div class="row_Circle">
        <p>时效分析</p>
        <div class="Circle">
          <div class="Circle1" ref="Circle1"></div>
          <div class="Circle2" ref="Circle2"></div>
          <div class="Circle3" ref="Circle3"></div>
        </div>
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
      <!-- 月度完成目标数 -->
      <div class="Month" ref="Month"></div>
    </div>
  </div>
</template>

<script>
import * as echarts from "echarts";
import { listPosition } from "@/api/student/position.js";
import { Getanalysis } from "@/api/student/mycomment.js";
export default {
  data() {
    return {
      loading: false,
      activeName: 0,
      positionList: [],
      Studentlist: {}, //总数据
      SubAnalysList: [], // 子目标数明细和完成率
      firstAnalysisList: [], // 大类完成率
      clusterAnalysisVos: [], //同年级系聚类分析
      listactive: 0, //控制高亮
    };
  },
  methods: {
    async getList() {
      const res = await listPosition();
      this.positionList = res.rows;
      this.positionList.forEach((item) => {
        item.id = this.activeName++;
      });
      console.log(this.positionList);
    },
    async StudentDate() {
      this.loading = true;
      let positionId = "a580b08a3d9644db92dd666ade768087";
      const res = await Getanalysis(positionId);
      // console.log(res);
      this.Studentlist = res.data;
      this.SubAnalysList = res.data.subAnalysisList;
      this.firstAnalysisList = res.data.firstAnalysisList;
      this.clusterAnalysisVos = res.data.clusterAnalysisVos;
      this.loading = false;
      // console.log(this.clusterAnalysisVos);
    },
  },

  async mounted() {
    await this.StudentDate();

    // 子目标数明细和完成率
    var Histogram = echarts.init(this.$refs.Histogram);
    Histogram.setOption({
      title: {
        text: "子目标数明细和完成率",
      },
      // 边距
      grid: {
        top: 90,
        left: 40,
        right: 60,
      },
      tooltip: {},
      xAxis: {
        data: this.SubAnalysList.map((item) => item.secondaryName),
      },
      yAxis: [
        {
          type: "value",
          name: "项目数量",
          min: 0,
          max: 10,
          interval: 5,
        },

        {
          type: "value",
          name: "完成率",
          min: 0, //最小
          max: 100, //最大
          interval: 50, //数值差
          position: "right", //y轴位置
          axisLabel: {
            formatter: "{value}%", // 通过 formatter 显示百分比
          },
        },
      ],
      series: [
        {
          barWidth: 30,
          type: "bar",
          data: this.SubAnalysList.map((item) => item.secondaryCompletionNum),
          itemStyle: {
            color: "#3BA1FF", // 设置柱子颜色
          },
        },

        {
          name: "折线图",
          type: "line",
          yAxisIndex: 1,
          data: this.SubAnalysList.map((item) =>
            (item.secondaryCompletionRate * 100).toFixed(1)
          ),
        },
      ],
    });

    // 大类完成率
    var Column = echarts.init(this.$refs.Column);
    Column.setOption({
      title: {
        text: "大类完成率",
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
        },
        formatter: function (params) {
          // 在这里进行格式化，将小数转为百分比
          var percent = (params[0].data * 100).toFixed(2) + "%";
          return params[0].name + ": " + percent;
        },
      },
      xAxis: [
        {
          type: "value",
          name: "完成率",

          // 坐标轴标签名
          axisLabel: {
            show: false,
          },
          max: 1,
        },
      ],
      yAxis: {
        data: this.firstAnalysisList.map((item) => item.firstName),
        axisLine: {
          show: false, //是否显示坐标轴轴线
        },
        axisTick: {
          //是否显示坐标刻度线
          show: false,
        },
      },
      series: [
        {
          barWidth: 10, //柱状宽度
          type: "bar",
          data: this.firstAnalysisList.map((item) => item.firstCompletionRate), //数值
          itemStyle: {
            color: "#3BA1FF", // 设置柱子颜色
            borderRadius: 10, //边框锐值
          },
        },
      ],
    });

    // 同年级系聚类分析
    var Scatter_plot = echarts.init(this.$refs.Scatter_plot);
    Scatter_plot.setOption({
      title: [
        { text: "同年级系聚类分析", left: "15" },
        {
          text: `{circle|}本人主目标`,
          textAlign: "left", // 设置标题文本的水平对齐方式为左对齐
          left: "76%", // 调整标题位置，例如设置为右侧 80%
          textStyle: {
            fontSize: 12,
            rich: {
              circle: {
                height: 10,
                backgroundColor: {
                  image: require("@/assets/circle/circle.png"),
                },
              },
            },
          },
        },
        {
          text: "{circle|}  他人目标",
          textAlign: "left", // 设置标题文本的水平对齐方式为左对齐
          left: "87%", // 调整标题位置，例如设置为右侧 80%
          textStyle: {
            fontSize: 12,
            rich: {
              circle: {
                height: 10,
                backgroundColor: {
                  image: require("@/assets/circle/circle1.png"),
                },
              },
            },
          },
        },
      ],
      tooltip: {
       
      },
      xAxis: {
        type: "value",
        name: "完成率",
        min: 0, //最小
        max: 10, //最大
        interval: 1, //数值差
        position: "right", //y轴位置
        axisLabel: {
          formatter: "{value} °%",
        },
        // 坐标轴标签名
        axisLabel: {
          show: false,
        },
      },
      yAxis: [
        {
          data: [0, 2, 4, 6, 8],
          axisTick: {
            //是否显示坐标刻度线
            show: false,
          },
          axisLine: {
            show: false, //是否显示坐标轴轴线
          },
        },
      ],
      series: [
        {
          symbolSize: 10,
          itemStyle: {
            color: "#6CDFDF", // 设置圆点的颜色
          },
          data: [1, 3, 6, 4, 5, 7, 1, 3, 4],
          type: "scatter",
        },
        {
          symbolSize: 10,
          itemStyle: {
            color: "#f4ea2a", // 设置圆点的颜色
          },
          data: [2, 4, 7, 3, 1, 7, 8, 5, 6],
          type: "scatter",
        },
      ],
    });

    // 时效分析1
    var Circle1 = echarts.init(this.$refs.Circle1);
    Circle1.setOption({
      title: {
        text: "总体分析",
        left: "center",
        top: "center",
      },
      tooltip: {
        formatter: function (params) {
          return params.name + ": " + params.percent.toFixed(1) + "%";
        },
      },
      series: [
        {
          type: "pie",
          data: [
            {
              value: 234,
              name: "未完成",
               itemStyle: {
                color: "#B2B2B2", // 设置柱子颜色
              },
            },
            {
              value: 335,
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
    var Circle2 = echarts.init(this.$refs.Circle2);
    Circle2.setOption({
      title: {
        text: "已完成分析",
        left: "center",
        top: "center",
      },
      tooltip: {
        formatter: function (params) {
          return params.name + ": " + params.percent.toFixed(1) + "%";
        },
      },
      series: [
        {
          type: "pie",

          data: [
            {
              value: 134,
              name: "按时",
            },
            {
              value: 234,
              name: "超时",
                  itemStyle: {
                color: "#6CDFDF", // 设置柱子颜色
              },
              
            },
            
              {
              value: 335,
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
    var Circle3 = echarts.init(this.$refs.Circle3);
    Circle3.setOption({
      title: {
        text: "未完成",
        left: "center",
        top: "center",
      },
      tooltip: {
        formatter: function (params) {
          return params.name + ": " + params.percent.toFixed(1) + "%";
        },
      },
      series: [
        {
          type: "pie",
          data: [
              {
              value: 234,
              name: "未过期",
               itemStyle: {
                color: "#FFB400", // 设置柱子颜色
              },
            },
            {
              value: 335,
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

    var Month = echarts.init(this.$refs.Month);
    Month.setOption({
      title: {
        text: "月度完成目标数",
      },
      // 边距
      grid: {
        top: 90,
        left: 60,
        right: 60,
      },
      tooltip: {
        trigger: "axis",
        axisPointer: {
          type: "cross",
        },
        formatter: function (params) {
          let result = ""; // 用于存储提示信息

          params.forEach((item) => {
            result += `${item.seriesName}: ${item.value}%<br/>`; // 系列名和数据值
          });

          // 添加数据点之间的对比信息
          if (params.length > 1) {
            for (let i = 0; i < params.length - 1; i++) {
              const diff = Math.abs(params[i].value - params[i + 1].value);
              result += `与${params[i + 1].seriesName}的对比: ${diff}%<br/>`;
            }
          }

          return result;
        },
      },
      xAxis: {
        type: "category",
        name:'月份',
        data: ["2201", "2202", "2203", "2204", "2205", "2206", "2207"],
      },
      yAxis: {
        type: "value",
         name:'项目数',
        min: 0,
        max: 100,
      },
      series: [
        {
          data: [40, 30, 40, 50, 20, 30, 60],
          type: "line",
          smooth: true,
          symbol: "none", // 设置节点为'none'
          lineStyle: {
            color: "#69BCFF", // 将折线颜色设为透明
          },
        },
        {
          data: [70, 50, 10, 60, 30, 80, 10],
          type: "line",
          smooth: true,
          symbol: "none", // 设置节点为'none'
        },
      ],
    });
  },
  created() {
    this.getList();
  },
};
</script>

<style scoped lang="scss" >
.main {
  background-color: #f3f3f4;
  height: 100%;
  width: 100%;
  padding: 20px 20px;

  ::deep .el-tabs__header {
    height: 50px;
    border: 1px solid black;
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
    height: 60px;
    border-radius: 10px 10px 0 0;
    background-color: white;
    display: flex;
    padding-left: 30px;
    button {
      font-size: 17px;
      font-weight: 900;
      font-family: system-ui;
      color: #696969;
      &.active {
        border-bottom: 5px #2a77ff solid;
      }
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
    height: 300px;

    border-radius: 6px;
  }
  .toLine {
    display: flex;
    justify-content: space-between;
    .Column {
      padding-top: 20px;
      margin-top: 30px;
      background-color: white;
      width: 49%;
      height: 300px;

      border-radius: 6px;
    }
    .Scatter_plot {
      padding-top: 20px;
      margin-top: 30px;
      background-color: white;
      width: 49%;
      height: 300px;
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
  .Month {
    padding-top: 20px;
    margin-top: 30px;
    background-color: white;
    height: 350px;
  }
}
</style>