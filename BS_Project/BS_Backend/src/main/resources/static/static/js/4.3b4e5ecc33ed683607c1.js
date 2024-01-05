webpackJsonp([4],{"2ZEU":function(e,r,s){"use strict";Object.defineProperty(r,"__esModule",{value:!0});var t=s("Xxa5"),o=s.n(t),a=s("exGp"),i=s.n(a),n=s("Dd8w"),l=s.n(n),c=s("NYxO"),m=s("8/0w"),u=s("Zrlr"),g=s.n(u),d=function e(r,s,t,o){g()(this,e),this.username=r,this.password=s,this.email=t,this.phone=o},p={components:{},props:{},data:function(){return{registerDialog:!1,loginLoading:!1,userToken:"",loginForm:{username:"",password:""},registerForm:{username:"",newPassword:"",confirmPassword:"",email:"",phone:""},loginRules:{username:[{required:!0,message:"请输入账号（用户名）",trigger:"blur"}],password:[{required:!0,message:"请输入密码",trigger:"blur"}]},registerRules:{username:[{required:!0,message:"请输入账号（用户名）",trigger:"blur"},{min:2,message:"用户名至少2个字符",trigger:"blur"}],newPassword:[{required:!0,message:"请输入密码",trigger:"blur"},{min:6,message:"密码长度至少6个字符",trigger:"blur"}],confirmPassword:[{required:!0,message:"请确认新密码",trigger:"blur"}],email:[{required:!0,message:"请输入邮箱",trigger:"blur"},{type:"email",message:"请输入有效的邮箱地址",trigger:"blur"}],phone:[{required:!0,message:"请输入邮箱",trigger:"blur"},{pattern:/^1[0-9]{10}$/,message:"请输入有效的手机号(1开头的11位手机号)",trigger:"blur"}]}}},watch:{},computed:{},methods:l()({},Object(c.d)(["changeLogin"]),{handleLogin:function(e){var r=this;this.$refs[e].validate(function(e){if(!e)return console.log("error submit!!"),r.$message.error("表单内容不符合规范！"),!1;m.a.post("/user/login",r.loginForm).then(function(e){if(console.log(e.data),e.data.success){var s={token:e.data.data.token,email:e.data.data.email,phone:e.data.data.phone,userId:e.data.data.userId,username:e.data.data.username};console.log(s),r.changeLogin(s),r.$router.push("/home"),r.$message.success("登录成功"),r.loginLoading=!0}else r.$message.error(e.data.msg),r.loginLoading=!1}).catch(function(e){console.log(e),r.$message.error("服务器连接失败，请稍后重试......"),r.loginLoading=!1})})},submitRegister:function(e){var r,s=this;this.$refs[e].validate((r=i()(o.a.mark(function e(r){var t,a,i;return o.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(r){e.next=4;break}return console.log("error submit!!"),s.$message.error("表单内容不符合规范！"),e.abrupt("return",!1);case 4:if(s.registerForm.newPassword==s.registerForm.confirmPassword){e.next=6;break}return e.abrupt("return",s.$message.error("两次密码不正确，请重新输入！"));case 6:return t=new d(s.registerForm.username,s.registerForm.confirmPassword,s.registerForm.email,s.registerForm.phone),e.prev=7,e.next=10,m.a.post("/user/register",t);case 10:if(a=e.sent,!(i=a.data).success){e.next=17;break}s.$message.success("注册成功，请登录！"),s.closeRegister(),e.next=18;break;case 17:return e.abrupt("return",s.$message.error(i.msg));case 18:e.next=24;break;case 20:e.prev=20,e.t0=e.catch(7),console.error("请求失败:",e.t0),s.$message.error("服务器连接失败，请稍后重试...");case 24:case"end":return e.stop()}},e,s,[[7,20]])})),function(e){return r.apply(this,arguments)}))},closeRegister:function(){this.registerDialog=!1,this.$refs.registerForm&&this.$refs.registerForm.resetFields()}}),created:function(){},mounted:function(){}},f={render:function(){var e=this,r=e.$createElement,s=e._self._c||r;return s("div",{staticClass:"login"},[s("el-card",{staticClass:"login_card"},[s("p",{staticClass:"login_title"},[e._v("登录")]),e._v(" "),s("p",{staticClass:"login_content"},[e._v("欢迎登录物联网设备后台管理系统")]),e._v(" "),s("el-form",{ref:"ruleForm-login",attrs:{model:e.loginForm,"status-icon":"",rules:e.loginRules}},[s("el-form-item",{attrs:{prop:"username"}},[s("el-input",{staticClass:"form_input",attrs:{placeholder:"请输入账号（用户名）",autocomplete:"off","prefix-icon":"el-icon-user"},model:{value:e.loginForm.username,callback:function(r){e.$set(e.loginForm,"username",r)},expression:"loginForm.username"}})],1),e._v(" "),s("el-form-item",{attrs:{prop:"password"}},[s("el-input",{staticClass:"form_input",attrs:{type:"password",placeholder:"请输入密码","prefix-icon":"el-icon-lock",autocomplete:"off","show-password":""},model:{value:e.loginForm.password,callback:function(r){e.$set(e.loginForm,"password",r)},expression:"loginForm.password"}})],1),e._v(" "),s("el-form-item",[s("div",{staticClass:"button_container"},[s("el-button",{staticStyle:{color:"white",width:"40%","margin-right":"20px"},attrs:{type:"primary"},on:{click:function(r){return e.handleLogin("ruleForm-login")}}},[e._v("登录")]),e._v(" "),s("el-button",{staticStyle:{color:"black",width:"40%"},nativeOn:{click:function(r){e.registerDialog=!0}}},[e._v("注册")])],1)])],1)],1),e._v(" "),s("el-dialog",{staticStyle:{"border-radius":"10%"},attrs:{title:"注册",visible:e.registerDialog,width:"30%",center:"","before-close":e.closeRegister,"close-on-click-modal":!1},on:{"update:visible":function(r){e.registerDialog=r}}},[s("el-form",{ref:"ruleForm-register",staticClass:"demo-form-inline",attrs:{rules:e.registerRules,model:e.registerForm,"label-width":"90px"}},[s("el-form-item",{attrs:{label:"邮箱:",prop:"email"}},[s("el-input",{attrs:{placeholder:"请输入邮箱"},model:{value:e.registerForm.email,callback:function(r){e.$set(e.registerForm,"email",r)},expression:"registerForm.email"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"手机号:",prop:"phone"}},[s("el-input",{attrs:{placeholder:"请输入手机号"},model:{value:e.registerForm.phone,callback:function(r){e.$set(e.registerForm,"phone",r)},expression:"registerForm.phone"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"用户名:",prop:"username"}},[s("el-input",{attrs:{placeholder:"请输入用户名"},model:{value:e.registerForm.username,callback:function(r){e.$set(e.registerForm,"username",r)},expression:"registerForm.username"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"密码:",prop:"newPassword"}},[s("el-input",{attrs:{"show-password":"",placeholder:"请输入密码"},model:{value:e.registerForm.newPassword,callback:function(r){e.$set(e.registerForm,"newPassword",r)},expression:"registerForm.newPassword"}})],1),e._v(" "),s("el-form-item",{attrs:{label:"确认密码:",prop:"confirmPassword"}},[s("el-input",{attrs:{"show-password":"",placeholder:"请确认密码"},model:{value:e.registerForm.confirmPassword,callback:function(r){e.$set(e.registerForm,"confirmPassword",r)},expression:"registerForm.confirmPassword"}})],1),e._v(" "),s("el-form-item",{attrs:{"label-width":"0px"}},[s("div",{staticClass:"dialog_button"},[s("el-button",{staticStyle:{"margin-right":"10px",width:"40%"},attrs:{type:"primary"},on:{click:function(r){return e.submitRegister("ruleForm-register")}}},[e._v("注 册")]),e._v(" "),s("el-button",{staticStyle:{width:"40%"},on:{click:function(r){return e.closeRegister()}}},[e._v("取 消")])],1)])],1)],1)],1)},staticRenderFns:[]};var h=s("VU/8")(p,f,!1,function(e){s("jIpk")},"data-v-6151cbbd",null);r.default=h.exports},Zrlr:function(e,r,s){"use strict";r.__esModule=!0,r.default=function(e,r){if(!(e instanceof r))throw new TypeError("Cannot call a class as a function")}},jIpk:function(e,r){}});
//# sourceMappingURL=4.3b4e5ecc33ed683607c1.js.map