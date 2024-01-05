class RegisterParams {
  constructor(username, password, email, phone) {
    // 成员变量会在构造函数中动态创建
    this.username = username;
    this.password = password;
    this.email = email;
    this.phone = phone;
  }
}

export default RegisterParams;
