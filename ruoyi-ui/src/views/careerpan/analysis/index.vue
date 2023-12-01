<template>
  <div class="main">
    <div class="top">个人数据分析</div>
   
    <div class="tab_btn">
      <el-button type="text">前端开发</el-button>
      <el-button type="text">大数据</el-button>
      <el-button type="text">后端开发</el-button>
      <el-button type="text">测试</el-button>
    </div>

          <div class="row_title">
            <p class="title1">目标总览</p>
            <p class="title2">数据截止于:2023-11-10</p>
          </div>
          <!-- 数据块 -->
          <div class="row_date">
            <div class="block_date">
              <p class="total">目标总数</p>
              <p class="word">8,846</p>
            </div>
            <div class="block_date">
              <p class="total">总体完成率</p>
              <p class="word">100%</p>
            </div>
            <div class="block_date">
              <p class="total">本年度截取项目数</p>
              <p class="word">8,846</p>
            </div>
            <div class="block_date">
              <p class="total">本月截止项目数</p>
              <p class="word">8,846</p>
            </div>
            <div class="block_date">
              <p class="total">年度目标完成率</p>
              <p class="word">88%</p>
            </div>
            <div class="block_date">
              <p class="total">发布时长</p>
              <p class="word">8,84天</p>
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

</template>

<script>
import * as echarts from "echarts";
import { listPosition } from "@/api/student/position.js";
export default {
  data() {
    return {
      activeName: 0,
      positionList: [],
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
    // draw_specific() {
    //   var Histogram = document.getElementsByClassName("Histogram");
    //   console.log(Histogram)
    //   for (var i = 0; i < Histogram.length; i++) {
    //     console.log('执行')
    //     var myChart = echarts.init(Histogram[i]);
    //     myChart.setOption({
    //   title: {
    //     text: "子目标数明细和完成率",
    //   },
    //   // 边距
    //   grid: {
    //     top: 90,
    //     left: 40,
    //     right: 60,
    //   },
    //   tooltip: {},
    //   xAxis: {
    //     data: [
    //       "专业技能",
    //       "其他技能",
    //       "竞赛奖项",
    //       "学业奖项",
    //       "实践奖项",
    //       "活动奖项",
    //       "其他奖项",
    //       "职业资格证书",
    //       "职业技能证书",
    //       "其他证书",
    //       "实习经历",
    //       "科研项目",
    //       "专利成果",
    //       "学生会",
    //       "兴趣爱好",
    //     ],
    //   },
    //   yAxis: [
    //     {
    //       type: "value",
    //       name: "项目数量",
    //       min: 0,
    //       max: 10,
    //       interval: 5,
    //     },
    //     {
    //       type: "value",
    //       name: "完成率",
    //       min: 0, //最小
    //       max: 100, //最大
    //       interval: 50, //数值差
    //       position: "right", //y轴位置
    //       axisLabel: {
    //         formatter: "{value} °%",
    //       },
    //     },
    //   ],
    //   series: [
    //     {
    //       barWidth: 30,
    //       type: "bar",
    //       data: [5, 3, 10, 4, 5, 2, 9, 3, 7, 6, 1, 3, 7, 6, 1],
    //       itemStyle: {
    //         color: "#3BA1FF", // 设置柱子颜色
    //       },
    //     },
    //     {
    //       name: "折线图",
    //       type: "line",
    //       yAxisIndex: 1,
    //       data: [30, 55, 70, 80, 50, 12, 60, 100, 40, 88, 55, 80, 50, 12, 60],
    //     },
    //   ],
    // })
    //   }
    // },
  },
  mounted() {
    

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
        data: [
          "专业技能",
          "其他技能",
          "竞赛奖项",
          "学业奖项",
          "实践奖项",
          "活动奖项",
          "其他奖项",
          "职业资格证书",
          "职业技能证书",
          "其他证书",
          "实习经历",
          "科研项目",
          "专利成果",
          "学生会",
          "兴趣爱好",
        ],
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
            formatter: "{value} °%",
          },
        },
      ],
      series: [
        {
          barWidth: 30,
          type: "bar",
          data: [5, 3, 10, 4, 5, 2, 9, 3, 7, 6, 1, 3, 7, 6, 1],
          itemStyle: {
            color: "#3BA1FF", // 设置柱子颜色
          },
        },
        {
          name: "折线图",
          type: "line",
          yAxisIndex: 1,
          data: [30, 55, 70, 80, 50, 12, 60, 100, 40, 88, 55, 80, 50, 12, 60],
        },
      ],
    });

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
      },
      xAxis: [
        {
          type: "value",
          name: "完成率",
          // 坐标轴标签名
          axisLabel: {
            show: false,
          },
        },
      ],
      yAxis: {
        data: [
          "专业技能",
          "其他技能",
          "竞赛奖项",
          "学业奖项",
          "实践奖项",
          "活动奖项",
        ],
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
          data: [1.3, 2.7, 0.5, 2.1, 0.9, 1.6, 2.4, 0.8, 1.2, 2.2], //数值
          itemStyle: {
            color: "#3BA1FF", // 设置柱子颜色
            borderRadius: 10, //边框锐值
          },
        },
      ],
    });

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

      //   tooltip: {
      //   trigger: "axis",
      //   axisPointer: {
      //     type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
      //   },
      // },
      xAxis: {
        //   type: "value",
        //   name: "完成率",
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

    var Circle1 = echarts.init(this.$refs.Circle1);
    Circle1.setOption({
      title: {
        text: "总体分析",
        left: "center",
        top: "center",
      },
      series: [
        {
          type: "pie",
          data: [
            {
              value: 335,
              name: "A",
            },
            {
              value: 234,
              name: "B",
            },
            {
              value: 1548,
              name: "C",
            },
          ],
          radius: ["50%", "70%"],
        },
      ],
    });

    var Circle2 = echarts.init(this.$refs.Circle2);
    Circle2.setOption({
      title: {
        text: "已完成分析",
        left: "center",
        top: "center",
      },
      series: [
        {
          type: "pie",

          data: [
            {
              value: 335,
              name: "A",
            },
            {
              value: 234,
              name: "B",
            },
          ],
          radius: ["50%", "70%"],
        },
      ],
    });

    var Circle3 = echarts.init(this.$refs.Circle3);
    Circle3.setOption({
      title: {
        text: "未完成",
        left: "center",
        top: "center",
      },
      series: [
        {
          type: "pie",
          data: [
            {
              value: 335,
              name: "A",
            },
            {
              value: 234,
              name: "B",
            },
            {
              value: 1548,
              name: "C",
            },
          ],
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
        data: ["2201", "2202", "2203", "2204", "2205", "2206", "2207"],
      },
      yAxis: {
        type: "value",
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
  .tab_btn{
    height: 30px;
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
        width: 350px;
        height: 350px;
      }
      .Circle2 {
        width: 350px;
        height: 350px;
      }
      .Circle3 {
        width: 350px;
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