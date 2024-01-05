import axios from "../../util/axiosConfig"; // 引入axios实例

const state = {
  totalCount: 0,
  count: [], //存储总消息数的数组
  normalCount: [], // 存储正常消息数的数组
  abnormalCount: [] // 存储不正常消息数的数组
};

const mutations = {
  setTotalCount(state, totalCount) {
    state.totalCount = totalCount;
  },
  setCount(state, count) {
    state.count = count;
  },
  setNormalCount(state, normalCount) {
    state.normalCount = normalCount;
  },
  setAbnormalCount(state, abnormalCount) {
    state.abnormalCount = abnormalCount;
  }
};

const actions = {
  async fetchTotalMessageCount({ commit, rootState }) {
    try {
      const response = await axios.get("/message/total-count", {
        params: {
          user_id: rootState.userId
        }
      });

      const { data } = response;
      console.log("fetchTotalMessageCount:");
      console.log(data);

      if (data.success === 1) {
        commit("setTotalCount", data.data);
      } else {
        console.error("获取总消息数失败:", data.msg);
      }
    } catch (error) {
      console.error("获取总消息数时发生错误:", error);
    }
  },

  async fetchRecentlyMessageCount({ commit, rootState }) {
    function getRecentSevenDays() {
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

      return days;
    }
    // 将日期转换为 YY-MM-DD HH:MM:SS 的形式
    function formatDate(date) {
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
    try {
      const response = await axios.get("/message/received-count", {
        params: {
          user_id: rootState.userId,
          today: today
        }
      });

      const { data } = response;
      console.log("fetchRecentlyMessageCount:", data);

      if (data.success == 1) {
        const counts = data.data || [];
        // 获取后端返回的日期和对应的数量
        const dateList = counts.map(entry => entry.date);
        const nCount = counts.map(entry => entry.normalCount);
        const abnCount = counts.map(entry => entry.abnormalCount);

        // 构造完整的七天数据，缺失的天数对应的数量设为0（格式为YYYY-MM-DD）
        const completeDateArray = getRecentSevenDays();
        console.log("completeDateArray", completeDateArray);
        const completeNCountArray = completeDateArray.map(date => {
          const index = dateList.indexOf(date);
          return index !== -1 ? nCount[index] : 0;
        });
        const completeABNCountArray = completeDateArray.map(date => {
          const index = dateList.indexOf(date);
          return index !== -1 ? abnCount[index] : 0;
        });
        commit("setNormalCount", completeNCountArray);
        commit("setAbnormalCount", completeABNCountArray);
      } else {
        console.error("获取总消息数失败:", data.msg);
      }
    } catch (error) {
      console.error("获取总消息数时发生错误:", error);
    }
  }
};

const getters = {
  getTotalMessageCount: state => state.totalCount,
  getCount: state => state.count,
  getNormalCount: state => state.normalCount,
  getAbnormalCount: state => state.abnormalCount
};

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters
};
