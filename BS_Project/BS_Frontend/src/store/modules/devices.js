import axios from "../../util/axiosConfig"; // 引入axios实例

const state = {
  devices: [], // 存储设备信息的数组
  countList: [] // 存储各种设备数量的数组
};

const mutations = {
  setDevices(state, devices) {
    state.devices = devices.map(device => ({
      deviceId: device.deviceId,
      deviceName: device.deviceName,
      deviceType: device.deviceType,
      deviceDescription: device.deviceDescription,
      registrationTime: device.registrationTime,
      isActive: device.isActive
    }));
  },
  setCountList(state, countList) {
    state.countList = countList;
  }
};

const actions = {
  async fetchUserDevices({ commit, rootState }) {
    try {
      const response = await axios.get(`/device/user/${rootState.userId}`);
      const { data } = response;
      console.log("fetchUserDevices:", data);

      if (data.success === 1) {
        commit("setDevices", data.data.devices);
        commit("setCountList", data.data.countList);
      } else {
        console.error("获取用户设备失败:", data.msg);
      }
    } catch (error) {
      console.error("获取用户设备时发生错误:", error);
    }
  }
};

const getters = {
  getUserDevices: state => state.devices,
  getDeviceCountList: state => state.countList
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
