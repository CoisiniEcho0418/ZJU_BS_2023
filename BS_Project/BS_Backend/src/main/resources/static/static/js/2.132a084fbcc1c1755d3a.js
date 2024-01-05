webpackJsonp([2],{"3CfH":function(e,s,r){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var t=r("Xxa5"),o=r.n(t),a=r("exGp"),n=r.n(a),i=r("Dd8w"),l=r.n(i),d=r("PK7p"),c=r("Zrlr"),u=r.n(c),m=function e(s,r,t,o){u()(this,e),this.user_id=s,this.new_username=r,this.new_email=t,this.new_phone=o},p=r("8/0w"),f=r("NYxO"),w={components:{},props:{},data:function(){return{editUserInfoDialog:!1,editUserInfoForm:{new_username:"",new_email:"",new_phone:""},editUserInfoRule:{new_username:[{min:2,message:"用户名至少2个字符",trigger:"blur"}],new_email:[{type:"email",message:"请输入有效的邮箱地址",trigger:"blur"}],new_phone:[{pattern:/^1[0-9]{10}$/,message:"请输入有效的手机号(1开头的11位手机号)",trigger:"blur"}]},editPasswordDialog:!1,editPasswordForm:{oldPassword:"",newPassword:"",confirmPassword:""},editPasswordRule:{oldPassword:[{required:!0,message:"请输入原密码",trigger:"blur"}],newPassword:[{required:!0,message:"请输入新密码",trigger:"blur"}],confirmPassword:[{required:!0,message:"请确认新密码",trigger:"blur"}]}}},watch:{},computed:l()({},Object(f.e)(["username","email","phone"])),methods:l()({},Object(f.d)(["changeLogin"]),{closeEditUserInfo:function(){this.editUserInfoDialog=!1,this.$refs.editUserInfoForm.resetFields()},getUserInfo:function(){var e=this;return n()(o.a.mark(function s(){var r,t,a;return o.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:return s.prev=0,s.next=3,p.a.get("/user/getUserInfo",{params:{user_id:e.$store.state.userId}});case 3:r=s.sent,t=r.data,console.log(t),t.success?(a={token:t.data.token,email:t.data.email,phone:t.data.phone,userId:t.data.userId,username:t.data.username},console.log(a),e.changeLogin(a)):e.$message.error(t.message),s.next=13;break;case 9:s.prev=9,s.t0=s.catch(0),console.error("请求失败:",s.t0),e.$message.error("服务器连接失败，请稍后重试...");case 13:case"end":return s.stop()}},s,e,[[0,9]])}))()},editUserInfo:function(){var e,s=this;this.$refs.editUserInfoForm.validate((e=n()(o.a.mark(function e(r){var t,a,n;return o.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(r){e.next=4;break}return console.log("error submit!!"),s.$message.error("表单内容不符合规范！"),e.abrupt("return",!1);case 4:return t=new m(s.$store.state.userId,s.editUserInfoForm.new_username.trim()||null,s.editUserInfoForm.new_email.trim()||null,s.editUserInfoForm.new_phone.trim()||null),console.log(t),e.prev=6,e.next=9,p.a.post("/user/editUserInfo",t);case 9:a=e.sent,n=a.data,console.log(n),n.success?(s.$message.success("个人信息修改成功！"),s.getUserInfo(),s.closeEditUserInfo()):s.$message.error(n.msg),e.next=19;break;case 15:e.prev=15,e.t0=e.catch(6),console.error("请求失败:",e.t0),s.$message.error("服务器连接失败，请稍后重试...");case 19:case"end":return e.stop()}},e,s,[[6,15]])})),function(s){return e.apply(this,arguments)}))},editPassword:function(){var e,s=this;this.$refs.editPasswordForm.validate((e=n()(o.a.mark(function e(r){var t,a,n;return o.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(r){e.next=4;break}return console.log("error submit!!"),s.$message.error("表单内容不符合规范！"),e.abrupt("return",!1);case 4:if(s.editPasswordForm.newPassword==s.editPasswordForm.confirmPassword){e.next=6;break}return e.abrupt("return",s.$message.error("两次密码不正确，请重新输入！"));case 6:return t=new d.a(s.$store.state.username,s.editPasswordForm.oldPassword,s.editPasswordForm.newPassword),console.log(t),e.prev=8,e.next=11,p.a.post("/user/updatePassword",t);case 11:if(a=e.sent,n=a.data,console.log(n),!n.success){e.next=21;break}s.$message.success("密码修改成功，请重新登录！"),s.$store.commit("logout"),sessionStorage.clear(),s.$router.push("/login"),e.next=22;break;case 21:return e.abrupt("return",s.$message.error(n.msg));case 22:e.next=28;break;case 24:e.prev=24,e.t0=e.catch(8),console.error("请求失败:",e.t0),s.$message.error("服务器连接失败，请稍后重试...");case 28:case"end":return e.stop()}},e,s,[[8,24]])})),function(s){return e.apply(this,arguments)}))},closeEditPassword:function(){this.editPasswordDialog=!1,this.$refs.editPasswordForm.resetFields()}}),created:function(){},mounted:function(){}},g={render:function(){var e=this,s=e.$createElement,t=e._self._c||s;return t("div",[t("el-card",{staticClass:"user_info_card"},[t("div",{staticClass:"user_header"},[t("el-avatar",{attrs:{size:60,src:"../assets//img/avatar.jpg"},on:{error:e.errorHandler}},[t("img",{attrs:{src:r("jpVj")}})]),e._v(" "),t("div",{staticClass:"header_info"},[t("div",{staticStyle:{"margin-bottom":"10px","font-weight":"bold","font-size":"18px"}},[e._v(e._s(e.username))]),e._v(" "),t("div",{staticStyle:{color:"gray","font-size":"14px"}},[e._v(e._s(e.email))])])],1),e._v(" "),t("el-descriptions",{staticStyle:{"margin-top":"20px"},attrs:{direction:"vertical",column:2,border:""}},[t("el-descriptions-item",[t("template",{slot:"label"},[t("i",{staticClass:"el-icon-user"}),e._v("\n          用户名\n        ")]),e._v("\n        "+e._s(e.username)+"\n      ")],2),e._v(" "),t("el-descriptions-item",[t("template",{slot:"label"},[t("i",{staticClass:"el-icon-mobile-phone"}),e._v("\n          绑定的手机号\n        ")]),e._v("\n        "+e._s(e.phone)+"\n      ")],2),e._v(" "),t("el-descriptions-item",[t("template",{slot:"label"},[t("i",{staticClass:"el-icon-message"}),e._v("\n          绑定的邮箱\n        ")]),e._v("\n        "+e._s(e.email)+"\n      ")],2)],1),e._v(" "),t("div",{staticClass:"button_block"},[t("el-button",{staticStyle:{"letter-spacing":"1px","text-align":"center",width:"12%","margin-right":"20px"},attrs:{type:"primary"},nativeOn:{click:function(s){e.editUserInfoDialog=!0}}},[e._v("编辑个人信息")]),e._v(" "),t("el-button",{staticStyle:{"margin-right":"10px","letter-spacing":"1px","text-align":"center",width:"12%"},attrs:{type:"success"},nativeOn:{click:function(s){e.editPasswordDialog=!0}}},[e._v("修改密码")])],1),e._v(" "),t("el-dialog",{attrs:{title:"编辑个人信息",visible:e.editUserInfoDialog,width:"30%",center:"","before-close":e.closeEditUserInfo,"close-on-click-modal":!1},on:{"update:visible":function(s){e.editUserInfoDialog=s}}},[t("el-form",{ref:"editUserInfoForm",staticClass:"demo-form-inline",attrs:{rules:e.editUserInfoRule,model:e.editUserInfoForm,"label-width":"90px"}},[t("el-form-item",{attrs:{label:"新用户名:",prop:"new_username"}},[t("el-input",{attrs:{placeholder:"请输入新用户名"},model:{value:e.editUserInfoForm.new_username,callback:function(s){e.$set(e.editUserInfoForm,"new_username",s)},expression:"editUserInfoForm.new_username"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"新邮箱:",prop:"new_email"}},[t("el-input",{attrs:{placeholder:"请输入新邮箱"},model:{value:e.editUserInfoForm.new_email,callback:function(s){e.$set(e.editUserInfoForm,"new_email",s)},expression:"editUserInfoForm.new_email"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"新手机号:",prop:"new_phone"}},[t("el-input",{attrs:{placeholder:"请输入新手机号"},model:{value:e.editUserInfoForm.new_phone,callback:function(s){e.$set(e.editUserInfoForm,"new_phone",s)},expression:"editUserInfoForm.new_phone"}})],1),e._v(" "),t("el-form-item",{staticClass:"dialog_button",attrs:{"label-width":"0px"}},[t("el-button",{attrs:{type:"primary"},on:{click:function(s){return e.editUserInfo()}}},[e._v("保 存")]),e._v(" "),t("el-button",{staticStyle:{"margin-right":"10px"},on:{click:function(s){return e.closeEditUserInfo()}}},[e._v("取 消")])],1)],1)],1),e._v(" "),t("el-dialog",{attrs:{title:"修改密码",visible:e.editPasswordDialog,width:"30%",center:"","before-close":e.closeEditPassword,"close-on-click-modal":!1},on:{"update:visible":function(s){e.editPasswordDialog=s}}},[t("el-form",{ref:"editPasswordForm",staticClass:"demo-form-inline",attrs:{rules:e.editPasswordRule,model:e.editPasswordForm,"label-width":"90px"}},[t("el-form-item",{attrs:{label:"原密码:",prop:"oldPassword"}},[t("el-input",{attrs:{"show-password":"",placeholder:"请输入原密码"},model:{value:e.editPasswordForm.oldPassword,callback:function(s){e.$set(e.editPasswordForm,"oldPassword",s)},expression:"editPasswordForm.oldPassword"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"新密码:",prop:"newPassword"}},[t("el-input",{attrs:{"show-password":"",placeholder:"请输入新密码"},model:{value:e.editPasswordForm.newPassword,callback:function(s){e.$set(e.editPasswordForm,"newPassword",s)},expression:"editPasswordForm.newPassword"}})],1),e._v(" "),t("el-form-item",{attrs:{label:"确认密码:",prop:"confirmPassword"}},[t("el-input",{attrs:{"show-password":"",placeholder:"请确认密码"},model:{value:e.editPasswordForm.confirmPassword,callback:function(s){e.$set(e.editPasswordForm,"confirmPassword",s)},expression:"editPasswordForm.confirmPassword"}})],1),e._v(" "),t("el-form-item",{staticClass:"dialog_button",attrs:{"label-width":"0px"}},[t("el-button",{attrs:{type:"primary"},on:{click:function(s){return e.editPassword()}}},[e._v("保 存")]),e._v(" "),t("el-button",{staticStyle:{"margin-right":"10px"},on:{click:function(s){return e.closeEditPassword()}}},[e._v("取 消")])],1)],1)],1)],1)],1)},staticRenderFns:[]};var v=r("VU/8")(w,g,!1,function(e){r("7v05")},"data-v-04981a0a",null);s.default=v.exports},"7v05":function(e,s){},PK7p:function(e,s,r){"use strict";s.a=class{constructor(e,s,r){this.username=e,this.oldPassword=s,this.newPassword=r}}},Zrlr:function(e,s,r){"use strict";s.__esModule=!0,s.default=function(e,s){if(!(e instanceof s))throw new TypeError("Cannot call a class as a function")}},jpVj:function(e,s,r){e.exports=r.p+"static/img/avatar.11376b3.jpg"}});
//# sourceMappingURL=2.132a084fbcc1c1755d3a.js.map