<template>
  <div class="overview">
    <!-- 第一行 -->
    <div class="first_row">
      <!-- 第一行第一个card -->
      <div class="block1">
        <el-card class="info">
          <el-button type="primary"
                     icon="el-icon-bangzhu"
                     circle />
          <h2 class="num-info">{{ totalDevices }}</h2>
          <p class="desc">总设备数</p>
        </el-card>
        <el-card class="info">
          <el-button type="success"
                     icon="el-icon-connection"
                     circle />
          <h2 class="num-info">{{activeDevices}}</h2>
          <p class="desc">活跃设备数</p>
        </el-card>
        <el-card class="info">
          <el-button type="danger"
                     icon="el-icon-message"
                     circle />
          <h2 class="num-info">{{ totalMessages }}</h2>
          <p class="desc">总消息数</p>
        </el-card>
      </div>

      <!-- 第一行第二个card -->
      <div class="block2"
           id="first-chart">
        <!-- 利用Echarts来生成图表 -->
        <!-- <el-card class="e-chart"
                 id="first-chart"></el-card> -->
      </div>
    </div>

    <!-- 第二行 -->
    <div class="second_row">
      <!-- 第二行第一个card -->
      <div class="block3"
           id="second-chart">
      </div>

      <!-- 第二行第二个card -->
      <div class="block4"
           id="third-chart">
      </div>
    </div>
  </div>
</template>

<script>
// 在组件中引入 Vuex
import { mapGetters, mapActions } from 'vuex';
import axios from "../util/axiosConfig"

export default {
  components: {},
  props: {},
  data () {
    return {
    };
  },
  watch: {},
  computed: {
    ...mapGetters('devices', ['getUserDevices']),
    ...mapGetters('message', ['getTotalMessageCount']),
    totalDevices () {
      // 获取后台传递的设备数
      const backendTotalDevices = this.getUserDevices.length;
      console.log(backendTotalDevices)
      // 判断数据是否为空或 undefined，如果是，则赋值为0
      return backendTotalDevices !== undefined && backendTotalDevices !== null
        ? backendTotalDevices
        : 0;
    },
    totalMessages () {
      // 获取后台传递的总消息数
      const backendTotalMessages = this.getTotalMessageCount;
      console.log(backendTotalMessages)
      // 判断数据是否为空或 undefined，如果是，则赋值为0
      return backendTotalMessages !== undefined && backendTotalMessages !== null
        ? backendTotalMessages
        : 0;
    },
    activeDevices () {
      // 获取后台传递的设备数
      const backendActiveDevices = this.getUserDevices.filter(device => device.isActive).length;
      console.log(backendActiveDevices)
      // 判断数据是否为空或 undefined，如果是，则赋值为0
      return backendActiveDevices !== undefined && backendActiveDevices !== null
        ? backendActiveDevices
        : 0;
    },
  },
  methods: {
    ...mapActions('devices', ['fetchUserDevices']),
    ...mapActions('message', ['fetchTotalMessageCount', 'fetchRecentlyMessageCount']),
    // 显示第一个图表信息
    async showFirstEcharts () {
      // 获取最近七天的日期（mm/dd的格式）
      const recentSevenDaysFormatted = this.getRecentSevenDaysFormatted();
      console.log(recentSevenDaysFormatted);

      // 将日期转换为 YY-MM-DD HH:MM:SS 的形式
      function formatDate (date) {
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, "0");
        const day = date
          .getDate()
          .toString()
          .padStart(2, "0");
        const hours = date
          .getHours()
          .toString()
          .padStart(2, "0");
        const minutes = date
          .getMinutes()
          .toString()
          .padStart(2, "0");
        const seconds = date
          .getSeconds()
          .toString()
          .padStart(2, "0");

        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      }
      const date = new Date();
      const today = formatDate(date);
      console.log("today", today);

      const response = await axios.get('/device/new-devices-count', {
        params: {
          user_id: this.$store.state.userId,
          today,
        },
      });
      console.log("first table data:", response)

      const counts = response.data.data || [];
      // 获取后端返回的日期和对应的数量
      const dateArray = counts.map(entry => entry.date);
      const countArray = counts.map(entry => entry.count);

      // 构造完整的七天数据，缺失的天数对应的数量设为0
      const completeDateArray = this.getRecentSevenDays();
      const completeCountArray = completeDateArray.map(date => {
        const index = dateArray.indexOf(date);
        return index !== -1 ? countArray[index] : 0;
      });


      var chartDom = document.getElementById('first-chart');
      var myChart = this.$echarts.init(chartDom);
      var option;

      option = {
        title: {
          text: '最近七天新增设备数'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Number of New Devices in the Last Seven Days']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          show: true,
          orient: 'vertical',
          left: 'right',
          feature: {
            saveAsImage: { show: true },
            dataView: { show: true, readOnly: false },

          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: recentSevenDaysFormatted, // 用最近七天的日期代替
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: 'Number of New Devices in the Last Seven Days.',
            type: 'line',
            stack: 'Total',
            data: completeCountArray
          }
        ]
      };
      myChart.setOption(option);

    },
    // 显示第二个图表
    showSecondEcharts () {
      const countList = this.$store.getters["devices/getDeviceCountList"];
      // 确保 countList 包含数据
      if (countList.length > 0) {
        var chartDom = document.getElementById('second-chart');
        var myChart = this.$echarts.init(chartDom);
        var option;

        option = {
          title: {
            text: '设备类型分布图'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            bottom: '-3%',
            left: 'center',
            width: "60%"
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            feature: {
              saveAsImage: { show: true },
              dataView: { show: true, readOnly: false }
            }
          },
          series: [
            {
              name: '该类型设备总数',
              type: 'pie',
              radius: ['40%', '70%'],
              avoidLabelOverlap: false,
              itemStyle: {
                borderRadius: 10,
                borderColor: '#fff',
                borderWidth: 2
              },
              label: {
                show: false,
                position: 'center'
              },
              emphasis: {
                label: {
                  show: true,
                  fontSize: 40,
                  fontWeight: 'bold'
                }
              },
              labelLine: {
                show: false
              },
              data: countList.map((count, index) => ({
                value: count,
                name: this.mapDeviceType(index + 1),
              })),
            }
          ]
        };
        myChart.setOption(option);
      }

    },
    // 显示第三个图表
    showThirdEcharts () {
      // 获取最近七天的日期（mm/dd的格式）
      const recentSevenDaysFormatted = this.getRecentSevenDaysFormatted();
      console.log("recentSevenDaysFormatted", recentSevenDaysFormatted);
      // 获取最近七天的正常消息数列表和不正常消息数列表
      const normalCountList = this.$store.getters["message/getNormalCount"];
      const abnormalCountList = this.$store.getters["message/getAbnormalCount"];

      // 确保 normalCountList 和 abnormalCountList 包含数据
      if (normalCountList.length > 0 && abnormalCountList.length > 0) {
        var chartDom = document.getElementById('third-chart');
        var myChart = this.$echarts.init(chartDom);
        var option;

        const labelOption = {
          show: true,
          position: 'insideBottom',
          distance: 15,
          align: 'left',
          verticalAlign: 'middle',
          rotate: 90,
          formatter: '{c}  {name|{a}}',
          fontSize: 16,
          rich: {
            name: {}
          }
        };
        option = {
          title: {
            text: '最近七天消息图'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'shadow'
            }
          },
          legend: {
            bottom: '0%',
            left: 'center',
            data: ['正常', '异常']
          },
          toolbox: {
            show: true,
            orient: 'vertical',
            left: 'right',
            top: 'center',
            feature: {
              mark: { show: true },
              dataView: { show: true, readOnly: false },
              magicType: { show: true, type: ['line', 'bar', 'stack'] },
              restore: { show: true },
              saveAsImage: { show: true }
            }
          },
          xAxis: [
            {
              type: 'category',
              axisTick: { show: false },
              data: recentSevenDaysFormatted
            }
          ],
          yAxis: [
            {
              type: 'value'
            }
          ],
          series: [
            {
              name: '正常',
              type: 'bar',
              barGap: 0,
              label: labelOption,
              emphasis: {
                focus: 'series'
              },
              color: "#66b1ff",
              data: normalCountList
            },
            {
              name: '异常',
              type: 'bar',
              label: labelOption,
              emphasis: {
                focus: 'series'
              },
              color: "#ee360d",
              data: abnormalCountList
            },
          ]
        };

        myChart.setOption(option);
      }


    },
    getRecentSevenDays () {
      const days = [];

      for (let i = 6; i >= 0; i--) {
        const date = new Date();
        date.setDate(date.getDate() - i);
        const year = date.getFullYear();
        const month = (date.getMonth() + 1).toString().padStart(2, "0");
        const day = date
          .getDate()
          .toString()
          .padStart(2, "0");
        const formattedDate = `${year}-${month}-${day}`;
        days.push(formattedDate);
      }
      console.log("RecentSevenDays", days)
      return days;
    },
    // 获取最近七天的日期，并以 mm/dd 格式输出
    getRecentSevenDaysFormatted () {
      const today = new Date();
      const recentDaysFormatted = Array.from({ length: 7 }, (_, index) => {
        const day = new Date(today);
        day.setDate(today.getDate() - index);
        return day.toLocaleDateString('en-US', { month: '2-digit', day: '2-digit' });
      }).reverse(); // 反转数组以实现升序排列

      return recentDaysFormatted;
    },
    // 根据设备类型编号映射设备类型名称
    mapDeviceType (type) {
      switch (type) {
        case 1:
          return "智能家居";
        case 2:
          return "生命安全设备";
        case 3:
          return "车载设备";
        case 4:
          return "可穿戴设备";
        case 5:
          return "工业智能网关";
        case 6:
          return "其他";
        default:
          return "未知";
      }
    },

  },
  created () {
    // 调用 Vuex action 来获取设备数据
    this.fetchUserDevices();
    this.fetchTotalMessageCount();
    this.fetchRecentlyMessageCount();

  },
  mounted () {
    this.$nextTick(async () => {
      await this.fetchUserDevices();
      await this.fetchTotalMessageCount();
      await this.fetchRecentlyMessageCount();

      this.showFirstEcharts();
      this.showSecondEcharts();
      this.showThirdEcharts();
    });
  }
};
</script>
<style scoped>
.overview {
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  height: 100%;
}
.first_row {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px 20px;
  height: 300px;
}
.block1 {
  flex: 1;
  display: flex;
  justify-content: space-around; /* 均匀排列每个元素 */
  background: white;
  min-width: 200px;
  height: 100%;
  padding: 20px 20px;
  margin-right: 30px;
  border-radius: 10px;
}
.block2 {
  flex: 1;
  display: flex;
  justify-content: space-around; /* 均匀排列每个元素 */
  background: white;
  min-width: 200px;
  height: 100%;
  width: 100%;
  padding: 20px 20px;
  border-radius: 10px;
}
.info {
  background: white;
  width: 26%;
  height: 70%;
  align-self: center;
  text-align: center;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: center;
}
/* .e-chart {
  padding-top: 10px;
  width: 50%;
  height: auto;
  min-width: 500px;
} */
.second_row {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px 20px;
  height: 300px;
  margin-top: 20px;
}
.block3 {
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  background: white;
  min-width: 200px;
  height: 100%;
  width: 100%;
  padding: 20px 20px;
  border-radius: 10px;
  margin-right: 30px;
}
.block4 {
  flex: 1;
  display: flex;
  justify-content: space-around; /* 均匀排列每个元素 */
  background: white;
  min-width: 200px;
  height: 100%;
  width: 100%;
  padding: 20px 20px;
  border-radius: 10px;
}
</style>