<template>
  <div>
    <el-card class="user_info_card">
      <!-- 头部区域 -->
      <div class="user_header">
        <!-- 头像区域 -->
        <el-avatar :size="60"
                   src="../assets//img/avatar.jpg"
                   @error="errorHandler">
          <img src="../assets/img/avatar.jpg" />
        </el-avatar>
        <!-- 用户简略信息区域 -->
        <div class="header_info">
          <!-- 用户名  -->
          <div style="margin-bottom: 10px;font-weight: bold;font-size: 18px;">{{username}}</div>
          <!-- 邮箱  -->
          <div style="color: gray;font-size: 14px;">{{ email }}</div>
        </div>
      </div>
      <!-- 用户详情区域 -->
      <el-descriptions direction="vertical"
                       :column="2"
                       style="margin-top: 20px;"
                       border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user"></i>
            用户名
          </template>
          {{username}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-mobile-phone"></i>
            绑定的手机号
          </template>
          {{phone}}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-message"></i>
            绑定的邮箱
          </template>
          {{ email }}
        </el-descriptions-item>
      </el-descriptions>
      <!-- 按钮区域 -->
      <div class="button_block">
        <el-button type="primary"
                   style="letter-spacing: 1px;text-align: center;width: 12%;margin-right: 20px;"
                   @click.native="editUserInfoDialog=true">编辑个人信息</el-button>
        <el-button type="success"
                   style="margin-right: 10px;letter-spacing: 1px;text-align: center;width: 12%;"
                   @click.native="editPasswordDialog=true">修改密码</el-button>
      </div>
      <!-- 编辑个人信息 dialog -->
      <el-dialog title="编辑个人信息"
                 :visible.sync="editUserInfoDialog"
                 width="30%"
                 center
                 :before-close="closeEditUserInfo"
                 :close-on-click-modal="false">
        <el-form ref="editUserInfoForm"
                 :rules="editUserInfoRule"
                 class="demo-form-inline"
                 :model="editUserInfoForm"
                 label-width="90px">
          <el-form-item label="新用户名:"
                        prop="new_username">
            <el-input v-model="editUserInfoForm.new_username"
                      placeholder="请输入新用户名" />
          </el-form-item>
          <el-form-item label="新邮箱:"
                        prop="new_email">
            <el-input v-model="editUserInfoForm.new_email"
                      placeholder="请输入新邮箱" />
          </el-form-item>
          <el-form-item label="新手机号:"
                        prop="new_phone">
            <el-input v-model="editUserInfoForm.new_phone"
                      placeholder="请输入新手机号" />
          </el-form-item>
          <el-form-item class="dialog_button"
                        label-width="0px">
            <el-button type="primary"
                       @click="editUserInfo()">保 存</el-button>
            <el-button style="margin-right: 10px;"
                       @click="closeEditUserInfo()">取 消</el-button>

          </el-form-item>
        </el-form>
      </el-dialog>
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

    </el-card>
  </div>
</template>

<script>
import EditPasswordRequest from "../params/EditPasswordRequest" // 引入修改密码请求参数实例
import EditUserInfoRequest from "../params/EditUserInfoRequest"; // 引入编辑个人信息请求参数实例
import axios from '../util/axiosConfig'; // 引入axios实例
import { mapState, mapMutations } from "vuex";


export default {
  components: {},
  props: {},
  data () {
    return {
      // 编辑个人信息dialog的开关
      editUserInfoDialog: false,
      // 编辑个人信息的表单
      editUserInfoForm: {
        new_username: "",
        new_email: "",
        new_phone: ""
      },
      // 编辑个人信息表单的校验规则  TODO: 邮箱和手机号校验规则待更进
      editUserInfoRule: {
        new_username: [
          { min: 2, message: '用户名至少2个字符', trigger: 'blur' },
        ],
        new_email: [
          { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
        ],
        new_phone: [
          { pattern: /^1[0-9]{10}$/, message: '请输入有效的手机号(1开头的11位手机号)', trigger: 'blur' }
        ]
      },
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
    ...mapState(["username", "email", "phone"]),
  },
  methods: {
    ...mapMutations(['changeLogin']),
    // 取消编辑个人信息
    closeEditUserInfo () {
      this.editUserInfoDialog = false;
      // resetFields 方法只能重置带有 props 属性的元素
      this.$refs.editUserInfoForm.resetFields();
    },
    // 重新获取用户个人信息
    async getUserInfo () {
      try {
        const response = await axios.get("/user/getUserInfo", {
          params: {
            user_id: this.$store.state.userId,
          },
        });

        const { data: res } = response;
        console.log(res)
        if (res.success) {
          // 更新 Vuex 中的用户信息
          const userData = {
            token: res.data.token,
            email: res.data.email,
            phone: res.data.phone,
            userId: res.data.userId,
            username: res.data.username,
          };
          console.log(userData)
          // 将用户信息保存到 Vuex 中
          this.changeLogin(userData);
        } else {
          this.$message.error(res.message);
        }
      } catch (error) {
        console.error("请求失败:", error);
        this.$message.error("服务器连接失败，请稍后重试...");
      }
    },
    // 编辑个人信息处理函数
    editUserInfo () {
      this.$refs.editUserInfoForm.validate(async (valid) => {
        if (!valid) {
          console.log('error submit!!');
          this.$message.error("表单内容不符合规范！");
          return false;
        }
        // 构造请求参数
        const editUserInfoRequest = new EditUserInfoRequest(
          this.$store.state.userId, // 从vuex获取userId
          // 使用 trim() 删除前后空格，避免将空字符串传递给后端
          this.editUserInfoForm.new_username.trim() || null,
          this.editUserInfoForm.new_email.trim() || null,
          this.editUserInfoForm.new_phone.trim() || null
        );
        console.log(editUserInfoRequest);

        try {
          // 发送编辑个人信息请求
          const { data: res } = await axios.post(
            "/user/editUserInfo",
            editUserInfoRequest
          );
          console.log(res)
          if (res.success) {
            this.$message.success("个人信息修改成功！");
            // 重新获取用户个人信息
            this.getUserInfo();
            this.closeEditUserInfo();
          } else {
            this.$message.error(res.msg);
          }
        } catch (error) {
          console.error("请求失败:", error);
          this.$message.error("服务器连接失败，请稍后重试...");
        }
      });
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


  },
  created () { },
  mounted () { }
};
</script>
<style scoped>
.user_info_card {
  margin: 20px 0;
}
.user_header {
  padding: 10px 10px;
  padding-left: 20px;
  display: flex;
  flex-direction: row;
  border-bottom: 2px solid #ebeef5;
}
.header_info {
  margin-left: 15px;
  display: flex;
  flex-direction: column;
}
.button_block {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 30px;
}
.dialog_button {
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>