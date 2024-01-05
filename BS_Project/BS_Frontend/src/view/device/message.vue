<template>
  <div class="message">
    <el-card>
      <!-- 头部选择栏 -->
      <div class="select_header">
        <div class="tips">
          请选则您要查看的设备:
        </div>
        <el-select v-model="value"
                   clearable
                   placeholder="请选择设备"
                   class="mySelect"
                   @change="$forceUpdate()">
          <!-- 调用forceUpdate()来强制刷新，使点击选中的项显示出来 -->
          <el-option v-for="item in deviceList"
                     :key="item.deviceId"
                     :label="item.deviceName"
                     :value="item.deviceId">
            <span style="color: #409eff; float: left;">(name)</span>
            <span style="float: left;">{{ item.deviceName }}</span>
            <span style="color: #409eff; float: right;">(id)</span>
            <span style="float: right;">{{ item.deviceId }}</span>
          </el-option>
        </el-select>
        <el-button type="primary"
                   style="margin-left: 30px;"
                   @click="selectDevice()">确 定</el-button>
      </div>
      <!-- 数据表 -->
      <el-table :data="displayedData"
                style="width: 100%;"
                :row-class-name="tableRowClassName"
                border>
        <el-table-column prop="index"
                         label="序号"
                         width="60">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="messageType"
                         width="140"
                         label="信息类别">
          <template slot-scope="scope">
            <el-tag :type="scope.row.messageType === 0 ? 'success' : 'danger'"
                    disable-transitions>{{scope.row.messageType === 0 ? '正常' : '异常'}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="value"
                         label="设备数据"
                         width="140">
        </el-table-column>
        <el-table-column prop="messageContent"
                         label="信息内容"
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="latitude"
                         label="经度">
        </el-table-column>
        <el-table-column prop="longitude"
                         label="纬度"
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="timestamp"
                         label="时间">
        </el-table-column>

      </el-table>
      <!-- 分页 -->
      <el-pagination @current-change="handleCurrentChange"
                     :current-page="current"
                     :page-size="5"
                     layout="total, prev, pager, next, jumper"
                     style="margin-top: 20px;"
                     :total="total">
      </el-pagination>
      <!-- 百度地图 -->
      <baidu-map class="map"
                 ref="bmMapRef"
                 :center="map.center"
                 inertial-dragging
                 :scroll-wheel-zoom="true"
                 :zoom="map.zoom"
                 @moving="syncCenterAndZoom"
                 @moveend="syncCenterAndZoom"
                 @zoomend="syncCenterAndZoom">
        <!-- 定位控件   anchor="BMAP_ANCHOR_BOTTOM_RIGHT"代表放在右下角 -->
        <bm-geolocation anchor="BMAP_ANCHOR_BOTTOM_LEFT"
                        :showAddressBar="true"
                        :autoLocation="true"></bm-geolocation>
        <!-- 缩放控件   anchor代表控件停靠位置   anchor="BMAP_ANCHOR_TOP_RIGHT"代表放在右上角-->
        <bm-navigation anchor="BMAP_ANCHOR_TOP_RIGHT"></bm-navigation>
        <!-- 城市列表 -->
        <bm-city-list anchor="BMAP_ANCHOR_TOP_LEFT"></bm-city-list>
        <!-- 缩略图 -->
        <bm-overview-map anchor="BMAP_ANCHOR_BOTTOM_RIGHT"
                         :isOpen="true"></bm-overview-map>
        <!-- 折线 -->
        <bm-polyline :path="polylinePath"
                     stroke-color="blue"
                     :stroke-opacity="0.5"
                     :stroke-weight="2"></bm-polyline>
        <!-- 模板中渲染点 -->
        <bm-marker v-for="(position, index) in markerPosition"
                   :position="position"
                   :key="index">
          <!-- 如果有对应的标注文本，渲染标注 -->
          <bm-label v-if='markerLabel[index].content!=""'
                    :content="markerLabel[index].content"
                    :labelStyle="markerLabelStyle[index]"
                    :offset="{width: -35, height: 30}" />
        </bm-marker>
      </baidu-map>
      <!-- 显示&编辑经纬度和缩放等级 -->
      <div class="map_editor">
        <div class="first_row">
          <div>经 度</div>
          <div>纬 度</div>
          <div>缩 放</div>
        </div>
        <div class="second_row">
          <input v-model.number="map.center.lng"
                 class="my_input">
          <input v-model.number="map.center.lat"
                 class="my_input">
          <input v-model.number="map.zoom"
                 class="my_input">
        </div>
      </div>

    </el-card>

  </div>
</template>

<script>
import axios from '../../util/axiosConfig'; // 引入axios实例
import icon from "@/assets/img/MapMarker.png" // 引入百度地图点图标

export default {
  components: {},
  props: {},
  data () {
    return {
      // 地图信息
      map: {
        center: {
          lng: 120.120794,
          lat: 30.259157
        },
        zoom: 12,
      },
      // 可查看的设备的列表 
      deviceList: [],
      // 设备信息列表
      /*  示例：
    "messageId": 2,
    "timestamp": "2023-12-17 20:35:00",
    "messageType": 0,
    "messageContent": "正常消息 2",
    "latitude": 30.234567,
    "longitude": 120.765432
      */
      messageData: [
        // {
        //   index: 1,
        //   message_type: 0,
        //   messageContent: "device data message",
        //   latitude: 30.915,
        //   longitude: 119.804,
        //   timestamp: "2023-10-23 20:12:00",
        // 	 value:90
        // },
      ],

      // 信息总条数
      total: 0,
      // 当前页
      current: 1,
      // 绘制折线的点的信息
      polylinePath: [
        // { lng: 119.804, lat: 30.915 },
        // { lng: 120.405, lat: 29.920 },
        // { lng: 120.223493, lat: 30.407445 }
      ],
      // 点的坐标
      markerPosition: [
        // { lng: 119.804, lat: 30.915 },
      ],
      // 点下面的标注
      markerLabel: [
        // {content:""}
      ],
      // 点标注的样式
      markerLabelStyle: [
        // {color:red}
      ],
      historyInterval: null,
    };
  },
  watch: {},
  computed: {
    // 表格中展示的信息（5条/页）
    // 使用计算属性来实现 displayedData
    displayedData () {
      const startIndex = (this.current - 1) * 5; // 假设每页显示5条数据
      const endIndex = startIndex + 5; // 假设每页显示5条数据
      return this.messageData.slice(startIndex, endIndex); // 假设每页显示5条数据
    },
  },
  methods: {
    // 选取对应的设备消息
    async selectDevice () {
      // 清除之前的计时器
      clearInterval(this.historyInterval);
      // 获取当前选择的设备
      const selectedDevice = this.deviceList.find(
        (device) => device.deviceId === this.value
      );
      console.log("选中的设备:", selectedDevice)

      if (selectedDevice) {
        // 设置 this.value 为所选设备的 deviceId
        this.value = selectedDevice.deviceId;
        // 调用函数发送请求
        this.fetchDeviceHistory(selectedDevice.deviceId);
      }
    },
    // 添加标注
    addMarkers () {
      // 先清除之前的标注
      this.clearMarkers();

      // 遍历消息数据，根据消息类型添加标注
      this.messageData.forEach((item, index) => {
        const markerPosition = { lng: item.longitude, lat: item.latitude };

        // 将点的坐标添加到数组中
        this.markerPosition.push(markerPosition);

        // 如果是警告信息，添加标注文本
        if (item.messageType === 1) {

          // 将标注文本的内容添加到数组中
          this.markerLabel.push({ content: "异常设备: " + item.messageContent });

          // 将标注文本的样式添加到数组中
          this.markerLabelStyle.push({ color: 'red', fontSize: '13px' });

        } else {
          // 如果不是警告信息，添加一个空对象，保持三个数组元素的对应
          this.markerLabel.push({ content: "" });
          this.markerLabelStyle.push({});
        }
      });

      // 在添加完标注后，更新地图
      this.$nextTick(() => {
        this.$refs.bmMapRef.panTo(this.markerPosition[this.markerPosition.length - 1]); // 将地图中心移动到最后一个点
      });
    },
    // 清除标注
    clearMarkers () {
      // 清空坐标、标注内容和标注样式数组
      this.markerPosition = [];
      this.markerLabel = [];
      this.markerLabelStyle = [];
    },
    // 发送请求获取设备历史消息
    async fetchDeviceHistory (deviceId) {
      const requestData = async () => {
        try {
          // 发起请求获取设备历史消息
          const response = await axios.get(`/message/device-history/${deviceId}`);
          const responseData = response.data;

          console.log("设备消息返回结果:")
          console.log(responseData);
          if (responseData.success) {
            this.$message.success("设备信息查询成功(实时更新)！");
            // 将获取到的消息数据中的 longitude 和 latitude 加到 polylinePath 中
            this.polylinePath = responseData.data.map(item => ({
              lng: item.longitude,
              lat: item.latitude
            }));
            // 更新地图的center
            if (responseData.data[0]) {
              this.map.center.lng = responseData.data[0].longitude
              this.map.center.lat = responseData.data[0].latitude
            }
            // 更新messageData和total
            this.messageData = responseData.data;
            this.total = responseData.data.length;
            // 在成功获取设备历史消息后调用更新 displayedData 和 total 的方法
            this.updateDisplayedDataAndTotal();

            // 添加标注
            this.addMarkers();
          } else {
            this.$message.error(responseData.msg);
          }
        } catch (error) {
          console.error('获取设备历史消息时发生错误:', error);
        }
      };

      // 首次执行请求
      await requestData();

      // 设置定时器每隔一段时间重新发送请求
      this.historyInterval = setInterval(requestData, 30000);
    },
    handleCurrentChange (currentPage) {
      /// 更新当前页码
      this.current = currentPage;

      // 根据新的页码获取数据
      this.updateDisplayedDataAndTotal();
    },
    // 移动地图回调处理函数
    syncCenterAndZoom (e) {
      const { lng, lat } = e.target.getCenter()
      this.map.center.lng = lng
      this.map.center.lat = lat
      this.map.zoom = e.target.getZoom()
    },
    // 获取设备数据并更新列表
    async fetchUserDevicesAndUpdateList () {
      try {
        // 调用 Vuex action 获取设备数据
        await this.$store.dispatch("devices/fetchUserDevices");
        // 更新设备列表
        this.deviceList = this.$store.getters["devices/getUserDevices"];
        console.log(this.deviceList);
        // 在成功获取设备数据后更新 displayedData 和 total
        this.updateDisplayedDataAndTotal();
      } catch (error) {
        console.error('获取设备数据时发生错误:', error);
      }
    },
    // 更新 displayedData 和 total 的方法
    updateDisplayedDataAndTotal () {
      const startIndex = (this.current - 1) * 5; // 每页显示5条数据
      const endIndex = startIndex + 5; // 每页显示5条数据

      this.displayedData = this.messageData.slice(startIndex, endIndex);
      console.log("current", this.current)
      console.log("messageData", this.messageData);
      console.log("displayedData", this.displayedData)
      this.total = this.messageData.length;
    },

  },
  created () {
    // 首次加载数据
    this.fetchUserDevicesAndUpdateList();

    // // 然后每隔15秒更新一次设备数据
    // this.updateInterval = setInterval(() => {
    //   this.fetchUserDevicesAndUpdateList();
    // }, 30000);
  },
  mounted () {
    // 在页面加载后调用 Vuex action 获取设备数据
    this.$store.dispatch("devices/fetchUserDevices").then(() => {
      // 在设备数据加载完成后更新 devicelist
      this.deviceList = this.$store.getters["devices/getUserDevices"];
      console.log(this.deviceList)
    });
  },
  beforeDestroy () {
    // 清除定时器
    // clearInterval(this.updateInterval);
    clearInterval(this.historyInterval);
  },
  // 在切换 tab 前清除定时器
  beforeTabChange () {
    clearInterval(this.historyInterval); // 清除时间间隔
    // 其他操作，比如切换 tab
  },

};
</script>
<style scoped>
.message {
  margin: 20px 0;
}
.select_header {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  margin-top: 20px;
  margin-bottom: 30px;
  height: 30px;
}
.tips {
  font-weight: bold;
  letter-spacing: 1px;
  height: 30px;
  line-height: 30px;
  text-align: center;
  margin-right: 20px;
}
.mySelect {
  display: flex;
  align-items: center;
  width: 40%;
}
.map {
  width: 100%;
  height: 500px;
  margin-top: 20px;
  padding: 10px 10px;
  margin-right: 5px;
  padding: 5px 5px;
}
.map_editor {
  margin-top: 5px;
  padding: 5px 5px;
}
.first_row {
  display: flex;
  justify-content: space-around;
  font-weight: bold;
  font-size: 16px;
  text-align: center;
  align-items: center;
  margin-bottom: 10px;
  border-bottom: 2px solid #ebeef5;
  height: 40px;
  line-height: 40px;
}
.second_row {
  display: flex;
  justify-content: space-around;
  font-weight: bold;
  font-size: 16px;
  height: 40px;
  line-height: 40px;
}
input {
  border: none; /* 移除默认边框 */
  outline: none; /* 取消默认的轮廓线 */
  font-weight: bold;
  font-size: 15px;
  width: 27%;
  border-bottom: 1px solid #dbdbdb;
  border-top: 0px;
  border-left: 0px;
  border-right: 0px;
}
input:focus {
  font-weight: bold;
  font-size: 15px;
  width: 27%;
  border-bottom: 1px solid #03a9f4;
}
input:hover {
  font-weight: bold;
  font-size: 15px;
  width: 27%;
  border-bottom: 1px solid #03a9f4;
  border-top: 0px;
  border-left: 0px;
  border-right: 0px;
}
</style>