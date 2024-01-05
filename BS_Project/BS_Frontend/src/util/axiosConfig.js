import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8080/", // 设置请求的基础URL
  timeout: 5000, // 请求超时时间
  headers: {
    "Content-Type": "application/json" // 设置请求头为JSON格式
  }
});

export default instance;
