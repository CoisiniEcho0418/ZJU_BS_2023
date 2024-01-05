<template>
  <div class="login">

    <el-card class="login_card">
      <p class="login_title">登录</p>
      <p class="login_content">欢迎登录物联网设备后台管理系统</p>
      <el-form :model="loginForm"
               status-icon
               :rules="loginRules"
               ref="ruleForm-login">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username"
                    placeholder="请输入账号（用户名）"
                    autocomplete="off"
                    prefix-icon="el-icon-user"
                    class="form_input"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password"
                    placeholder="请输入密码"
                    prefix-icon="el-icon-lock"
                    v-model="loginForm.password"
                    autocomplete="off"
                    show-password
                    class="form_input"></el-input>
        </el-form-item>
        <el-form-item>
          <div class="button_container">
            <el-button type="primary"
                       @click="handleLogin('ruleForm-login')"
                       style="color: white; width: 40%;margin-right: 20px;">登录</el-button>
            <el-button @click.native="registerDialog = true"
                       style="color: black;width: 40%;">注册</el-button>
          </div>
        </el-form-item>

      </el-form>
    </el-card>

    <!-- 注册 dialog -->
    <el-dialog title="注册"
               :visible.sync="registerDialog"
               width="30%"
               center
               style="border-radius: 10%;"
               :before-close="closeRegister"
               :close-on-click-modal="false">
      <el-form ref="ruleForm-register"
               :rules="registerRules"
               class="demo-form-inline"
               :model="registerForm"
               label-width="90px">
        <el-form-item label="邮箱:"
                      prop="email">
          <el-input v-model="registerForm.email"
                    placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="手机号:"
                      prop="phone">
          <el-input v-model="registerForm.phone"
                    placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="用户名:"
                      prop="username">
          <el-input v-model="registerForm.username"
                    placeholder="请输入用户名" />
        </el-form-item>

        <el-form-item label="密码:"
                      prop="newPassword">
          <el-input v-model="registerForm.newPassword"
                    show-password
                    placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="确认密码:"
                      prop="confirmPassword">
          <el-input v-model="registerForm.confirmPassword"
                    show-password
                    placeholder="请确认密码" />
        </el-form-item>
        <el-form-item label-width="0px">
          <div class="dialog_button">
            <el-button type="primary"
                       style="margin-right: 10px;width: 40%;"
                       @click="submitRegister('ruleForm-register')">注 册</el-button>
            <el-button style="width: 40%;"
                       @click="closeRegister()">取 消</el-button>
          </div>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import { mapMutations } from 'vuex';
import axios from '../util/axiosConfig'; // 引入axios实例
import RegisterParams from '../params/RegisterParams'; // 引入请求体参数示例

export default {
  components: {},
  props: {},
  data () {
    return {
      // 注册窗口开启标志
      registerDialog: false,
      loginLoading: false,
      userToken: '',
      loginForm: {
        username: "",
        password: ""
      },
      registerForm: {
        username: "",
        newPassword: "",
        confirmPassword: "",
        email: "",
        phone: ""
      },
      loginRules: {
        username: [
          { required: true, message: "请输入账号（用户名）", trigger: 'blur' }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: 'blur' }
        ]
      },
      // TODO: 邮箱和手机号校验规则待更进
      registerRules: {
        username: [
          { required: true, message: "请输入账号（用户名）", trigger: 'blur' },
          { min: 2, message: '用户名至少2个字符', trigger: 'blur' },
        ],
        newPassword: [
          { required: true, message: "请输入密码", trigger: 'blur' },
          { min: 6, message: '密码长度至少6个字符', trigger: 'blur' },
        ],
        confirmPassword: [
          { required: true, message: "请确认新密码", trigger: "blur" }
        ],
        email: [
          { required: true, message: "请输入邮箱", trigger: 'blur' },
          { type: 'email', message: '请输入有效的邮箱地址', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: "请输入邮箱", trigger: 'blur' },
          { pattern: /^1[0-9]{10}$/, message: '请输入有效的手机号(1开头的11位手机号)', trigger: 'blur' }
        ],
      }

    };
  },
  watch: {},
  computed: {},
  methods: {
    ...mapMutations(['changeLogin']),
    // 登录处理函数
    handleLogin (formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios.post("/user/login", this.loginForm)
            .then((res) => {
              console.log(res.data);
              if (res.data.success) {
                const userData = {
                  token: res.data.data.token,
                  email: res.data.data.email,
                  phone: res.data.data.phone,
                  userId: res.data.data.userId,
                  username: res.data.data.username,
                };
                console.log(userData)
                // 将用户信息保存到 Vuex 中
                this.changeLogin(userData);

                // sessionStorage.setItem("user", JSON.stringify(res.data.data.user));
                // sessionStorage.setItem("token", res.data.token);
                this.$router.push("/home");
                this.$message.success("登录成功")
                this.loginLoading = true;
              } else {
                this.$message.error(res.data.msg);
                this.loginLoading = false;
              }
            })
            .catch((err) => {
              console.log(err)
              this.$message.error("服务器连接失败，请稍后重试......");
              this.loginLoading = false;
            });
        } else {
          console.log('error submit!!');
          this.$message.error("表单内容不符合规范！");
          return false;
        }
      });
    },
    // 注册处理函数
    submitRegister (formName) {
      this.$refs[formName].validate(async (valid) => {
        if (!valid) {
          console.log('error submit!!');
          this.$message.error("表单内容不符合规范！");
          return false;
        }
        if (this.registerForm.newPassword != this.registerForm.confirmPassword) {
          return this.$message.error("两次密码不正确，请重新输入！");
        }
        // 创建 RegisterParams 对象
        const registerParams = new RegisterParams(
          this.registerForm.username,
          this.registerForm.confirmPassword,
          this.registerForm.email,
          this.registerForm.phone
        );

        // 请求接口
        try {
          const { data: res } = await axios.post("/user/register", registerParams);

          if (res.success) {
            this.$message.success("注册成功，请登录！");
            // 关闭注册表单
            this.closeRegister();
          } else {
            return this.$message.error(res.msg);
          }
        } catch (error) {
          console.error("请求失败:", error);
          this.$message.error("服务器连接失败，请稍后重试...");
        }
      });
    },
    // 取消注册
    closeRegister () {
      this.registerDialog = false;
      // 检查是否存在 $refs.registerForm
      if (this.$refs.registerForm) {
        this.$refs.registerForm.resetFields();
      }
    },

  },
  created () { },
  mounted () { }
};
</script>
<style scoped>
.login {
  width: 100%;
  height: 100%;
  background-image: url('../assets/img/login-background.jpg');
  position: absolute;
  left: 0;
  top: 0;
  background-size: 100% 100%;
}

.login_card {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  width: 23%;
  min-width: 400px;
  height: 320px;
  min-height: 300px;
  border-radius: 10%;
  text-align: center;
  background: rgba(255, 255, 255, 0.3);
}

.login_title {
  font-size: 25px;
  font-weight: bold;
}

.login_content {
  letter-spacing: 2px;
  color: white;
  font-weight: bold;
}

.form_input {
  width: 100%;
}
.el-button {
  width: 70%;
  font-weight: bold;
  letter-spacing: 2px;
}
.button_container {
  display: flex;
  align-items: center;
  justify-content: center;
}
.dialog_button {
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
