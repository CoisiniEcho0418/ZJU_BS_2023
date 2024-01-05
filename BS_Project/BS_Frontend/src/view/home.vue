<template>
  <div class="home">
    <el-container style="height: 100%;">
      <!-- 头部导航栏 -->
      <el-header class="header">
        <el-row>
          <el-col :span="4">
            <p class="system_name">物联网设备后台管理系统</p>
          </el-col>
          <el-col :offset="12"
                  :span="8"
                  style="min-width: 150px">
            <el-dropdown style="float: right; margin: 20px 10px">
              <span class="el-dropdown-link"
                    style="color: #fff; cursor: pointer; font-size: 16px;letter-spacing:1px;">
                管理员 &nbsp;&nbsp;<i class="fa fa-caret-down fa-1x"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="editPasswordDialog = true">修改密码</el-dropdown-item>
                <el-dropdown-item divided
                                  @click.native="logout()">退出系统</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <el-avatar shape="square"
                       :src="avatar"
                       style="margin: 10px; float: right"></el-avatar>
          </el-col>
        </el-row>
        <!-- 修改密码 dialog -->
        <el-dialog title="修改密码"
                   :visible.sync="editPasswordDialog"
                   width="30%"
                   center
                   :before-close="closeEditPassword"
                   :close-on-click-modal="false">
          <el-form ref="editPasswordForm"
                   :rules="editPasswordRule"
                   class="demo-form-inline"
                   :model="editPasswordForm"
                   label-width="90px">
            <el-form-item label="原密码:"
                          prop="oldPassword">
              <el-input v-model="editPasswordForm.oldPassword"
                        show-password
                        placeholder="请输入原密码" />
            </el-form-item>
            <el-form-item label="新密码:"
                          prop="newPassword">
              <el-input v-model="editPasswordForm.newPassword"
                        show-password
                        placeholder="请输入新密码" />
            </el-form-item>
            <el-form-item label="确认密码:"
                          prop="confirmPassword">
              <el-input v-model="editPasswordForm.confirmPassword"
                        show-password
                        placeholder="请确认密码" />
            </el-form-item>
            <el-form-item class="dialog_button"
                          label-width="0px">
              <el-button type="primary"
                         @click="editPassword()">保 存</el-button>
              <el-button style="margin-right: 10px;"
                         @click="closeEditPassword()">取 消</el-button>

            </el-form-item>
          </el-form>
        </el-dialog>

      </el-header>

      <!-- 主要区域 -->
      <!-- 删去了style="overflow: auto" -->
      <el-container>
        <!-- 侧边栏 -->
        <el-aside>
          <div class="toggle_button"
               @click="isCollapse=!isCollapse">
            <i v-if="isCollapse"
               class="el-icon-s-unfold"></i>
            <i v-if="!isCollapse"
               class="el-icon-s-fold"></i>
          </div>
          <el-menu :default-active="activePath"
                   router
                   :collapse="isCollapse"
                   class="el-menu-vertical-demo"
                   @open="handleOpen"
                   @close="handleClose">
            <el-menu-item index="/index"
                          @click="saveAcitivePath('/index')">
              <i class="el-icon-house"></i>
              <span slot="title">首页</span>
            </el-menu-item>
            <el-submenu index="/device"
                        @click="saveAcitivePath('/device')">
              <template slot="title">
                <i class="el-icon-setting"></i>
                <span>设备管理</span>
              </template>
              <el-menu-item index="/device/config"
                            @click="saveAcitivePath('/device/config')">
                <template slot="title">
                  <i class="el-icon-link"></i>
                  <span>设备配置</span>
                </template>
              </el-menu-item>
              <el-menu-item index="/device/message"
                            @click="saveAcitivePath('/device/message')">
                <template slot="title">
                  <i class="el-icon-chat-dot-round"></i>
                  <span>设备信息</span>
                </template>
              </el-menu-item>
            </el-submenu>
            <el-menu-item index="/user"
                          @click="saveAcitivePath('/user')">
              <i class="el-icon-user"></i>
              <span slot="title">用户管理</span>
            </el-menu-item>
          </el-menu>
        </el-aside>

        <el-container>
          <!-- 主要内容区 -->
          <el-main class="main">
            <Breadcrumb />
            <!-- 主要内容 -->
            <router-view></router-view>
          </el-main>
          <!-- 底部信息区 -->
          <el-footer class="footer">Copyright © 2023 HWJ</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Breadcrumb from "../components/Breadcrumb.vue";
import EditPasswordRequest from "../params/EditPasswordRequest" // 引入修改密码请求参数实例
import axios from '../util/axiosConfig'; // 引入axios实例

export default {
  components: { Breadcrumb },
  props: {},
  data () {
    return {
      // 存储用户的头像
      avatar: require("../assets//img/avatar.jpg"),
      isCollapse: false,
      // 被激活的菜单地址（默认是首页）
      activePath: "",
      // 用于控制修改密码dialog的打开与否
      editPasswordDialog: false,
      editPasswordForm: {
        oldPassword: "",
        newPassword: "",
        confirmPassword: "",
      },
      editPasswordRule: {
        oldPassword: [
          { required: true, message: "请输入原密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请输入新密码", trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请确认新密码", trigger: "blur" },
        ],
      },
    };
  },
  watch: {},
  computed: {
    userDevices () {
      return this.$store.getters['devices/getUserDevices'];
    },
  },
  methods: {
    // 保存菜单激活路径
    saveAcitivePath (path) {
      sessionStorage.setItem("activePath", path);
      this.activePath = path;
    },
    // 修改密码
    editPassword () {
      this.$refs.editPasswordForm.validate(async (valid) => {
        if (!valid) {
          console.log('error submit!!');
          this.$message.error("表单内容不符合规范！");
          return false;
        }
        if (this.editPasswordForm.newPassword != this.editPasswordForm.confirmPassword) {
          return this.$message.error("两次密码不正确，请重新输入！");
        }

        // 创建 EditPasswordRequest 对象
        const editPasswordRequest = new EditPasswordRequest(
          this.$store.state.username, // 从vuex获取username
          this.editPasswordForm.oldPassword,
          this.editPasswordForm.newPassword
        );
        console.log(editPasswordRequest)
        try {
          // 请求接口
          const { data: res } = await axios.post(
            "/user/updatePassword",
            editPasswordRequest
          );
          console.log(res)
          if (res.success) {
            this.$message.success("密码修改成功，请重新登录！");
            // 清除 Vuex 中的用户信息
            this.$store.commit("logout");
            sessionStorage.clear();
            this.$router.push("/login");
          } else {
            return this.$message.error(res.msg);
          }
        } catch (error) {
          console.error("请求失败:", error);
          this.$message.error("服务器连接失败，请稍后重试...");
        }
      });
    },
    // 取消关闭密码
    closeEditPassword () {
      this.editPasswordDialog = false;
      // Tips: resetFields 方法只能重置带有 props 属性的元素
      this.$refs.editPasswordForm.resetFields();
    },
    // 退出系统
    logout () {
      this.$confirm("确定要退出系统吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          // 清除 Vuex 中的用户信息
          this.$store.commit("logout");
          // 清除缓存
          sessionStorage.clear();
          this.$router.push("/login");
        })
        .catch(() => {
          return false;
        });
    },
  },
  created () {
    this.$store.dispatch("devices/fetchUserDevices"); // 向后端发请求获取用户所属设备的相关信息
    console.log("在Home中调用了fetchUserDevices")
    this.$store.dispatch("message/fetchRecentlyMessageCount"); // 向后端发请求获取最近七天设备消息
    console.log("在Home中调用了fetchRecentlyMessageCount")
    this.activePath = sessionStorage.getItem("activePath") ? sessionStorage.getItem("activePath") : "/index";
  },
  mounted () { }
};
</script>
<style scoped>
.header {
  background: #2661ef;
  padding: 0 10px;
  overflow: hidden;
}
.system_name {
  color: #fff;
  font-size: 18px;
}
.dialog_button {
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.main {
  background: #f2f3f5;
  overflow-y: hidden; /* 隐藏垂直滚动条 */
  /* background: rgba(255, 0, 255, 0.3); */
}
.footer {
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  background-color: #f2f3f5;
}
.footer:hover {
  color: #2661ef;
}
.home {
  width: 100%;
  height: 100%;
  /* background-image: url('../assets/img/home-background.jpg'); */
  position: absolute;
  left: 0;
  top: 0;
  /* background-size: 100% 100%; */
}
.toggle_button {
  background-color: aliceblue;
  font-size: 18px;
  text-align: center;
  letter-spacing: 1px;
  line-height: 24px;
  color: black;
  cursor: pointer;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.el-aside {
  background: white;
  width: auto !important;
}
.el-menu-item.is-active {
  color: #fff !important;
  font-size: 15px;
  font-weight: bold;
  background-color: #2661ef !important;
  border-radius: 2px;
  height: 50px;
  line-height: 50px;
  box-sizing: border-box;
  margin: 2px 5px 0px 2px;
}
</style>