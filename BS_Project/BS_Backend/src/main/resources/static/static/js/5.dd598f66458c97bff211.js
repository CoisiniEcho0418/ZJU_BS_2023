webpackJsonp([5],{BTLR:function(e,t){},DSBQ:function(e,t,i){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=i("Dd8w"),a=i.n(r),s=i("Xxa5"),c=i.n(s),o=i("exGp"),n=i.n(o),l=i("8/0w"),d={components:{},props:{},data:function(){return{addDeviceDialog:!1,editDeviceDialog:!1,searchForm:{current:1,size:10,device_id:"",device_name:"",device_type:null,is_active:null},tableData:[],total:0,deviceTypeList:[{id:1,type:"智能家居"},{id:2,type:"生命安全设备"},{id:3,type:"车载设备"},{id:4,type:"可穿戴设备"},{id:5,type:"工业智能网关"},{id:6,type:"其他"}],deviceStateList:[{id:0,state:"离线"},{id:1,state:"活跃"}],addDeviceForm:{device_name:"",device_type:null,device_description:"",is_active:null,registration_time:""},addDeviceRule:{device_name:[{required:!0,message:"请输入设备名称",trigger:"blur"}],device_type:[{required:!0,message:"请选择设备类型",trigger:"blur"}],device_description:[{required:!0,message:"请输入设备描述",trigger:"blur"}],is_active:[{required:!0,message:"请选择设备状态",trigger:"blur"}]},editDeviceForm:{device_id:"",device_name:"",device_type:null,device_description:"",is_active:null,last_update:""},editDeviceRule:{}}},watch:{},computed:{},methods:{tableRowClassName:function(e){e.row;var t=e.rowIndex;return 1===t?"warning-row":3===t?"success-row":""},handleSizeChange:function(e){this.searchForm.size=e,this.searchForm.current=1,this.getPageList()},handleCurrentChange:function(e){this.searchForm.current=e,this.getPageList()},changeView:function(e,t){console.log(e),this.$router.push({path:e,query:t})},changeRadio:function(e){null!==this.searchForm.is_active&&(this.searchForm.is_active="null"===this.searchForm.is_active?null:parseInt(this.searchForm.is_active)),console.log("searchForm.is_active:"),console.log(this.searchForm.is_active),this.getPageList()?this.$message.success("查询成功！"):this.$message.error("查询失败！")},deleteDevice:function(e){var t=this;return n()(c.a.mark(function i(){return c.a.wrap(function(i){for(;;)switch(i.prev=i.next){case 0:try{t.$confirm("此操作将永久删除该设备, 是否继续?","提示",{confirmButtonText:"确定",cancelButtonText:"取消",type:"error"}).then(n()(c.a.mark(function i(){var r,a;return c.a.wrap(function(i){for(;;)switch(i.prev=i.next){case 0:return i.next=2,l.a.delete("/device/delete/"+e);case 2:r=i.sent,a=r.data,console.log("删除结果：",a),a.success?(t.$message.success("设备删除成功！"),t.getPageList()):t.$message.error(a.msg);case 6:case"end":return i.stop()}},i,t)}))).catch(function(){t.$message({type:"info",message:"已取消删除"})})}catch(e){console.error("Error deleting device:",e),t.$message.error("删除设备出现异常！")}case 1:case"end":return i.stop()}},i,t)}))()},openDetail:function(e){var t=this;return n()(c.a.mark(function i(){var r,a;return c.a.wrap(function(i){for(;;)switch(i.prev=i.next){case 0:return i.prev=0,i.next=3,l.a.get("/device/"+e);case 3:r=i.sent,a=r.data,console.log("详情结果：",a),a.success?(t.$alert("上次更新日期："+a.data.lastUpdate,"详情",{confirmButtonText:"确定"}),t.getPageList()):t.$message.error(a.msg),i.next=13;break;case 9:i.prev=9,i.t0=i.catch(0),console.error("Error open detail:",i.t0),t.$message.error("查看详情出现异常！");case 13:case"end":return i.stop()}},i,t,[[0,9]])}))()},getPageList:function(){var e=this;return n()(c.a.mark(function t(){var i;return c.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return t.prev=0,t.next=3,l.a.get("/device/search-with-pagination",{params:a()({user_id:e.$store.state.userId},e.searchForm)});case 3:if(i=t.sent,console.log("分页请求结果:"),console.log(i),!i.data.success){t.next=14;break}return e.tableData=i.data.data.data,console.log("表格数据："),console.log(e.tableData),e.total=i.data.data.totalCount,t.abrupt("return",!0);case 14:return e.$message.error(i.data.msg),t.abrupt("return",!1);case 16:t.next=22;break;case 18:throw t.prev=18,t.t0=t.catch(0),console.error("Error fetching page list:",t.t0),t.t0;case 22:case"end":return t.stop()}},t,e,[[0,18]])}))()},handleSearch:function(){var e=this;return n()(c.a.mark(function t(){return c.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return e.searchForm.current=1,t.prev=1,t.next=4,e.getPageList();case 4:t.sent?e.$message.success("查询成功！"):e.$message.error("查询失败！"),t.next=12;break;case 8:t.prev=8,t.t0=t.catch(1),console.error("Error handling search:",t.t0),e.$message.error("查询出现异常！");case 12:case"end":return t.stop()}},t,e,[[1,8]])}))()},handleClear:function(){try{this.$refs.searchForm.resetFields(),this.getPageList()?this.$message.success("重置成功！"):this.$message.error("出现异常！")}catch(e){console.error("Error handling search:",e),this.$message.error("重置异常！")}},addDevice:function(){var e,t=this;try{this.$refs.addDeviceForm.validate((e=n()(c.a.mark(function e(i){var r,s,o;return c.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(i){e.next=4;break}return console.log("error submit!!"),t.$message.error("表单内容不符合规范！"),e.abrupt("return",!1);case 4:return r=a()({},t.addDeviceForm,{user_id:t.$store.state.userId}),e.next=7,l.a.post("/device/add",r);case 7:if(s=e.sent,!(o=s.data).success){e.next=16;break}t.$message.success("设备新增成功！"),t.addDeviceDialog=!1,t.$refs.addDeviceForm.resetFields(),t.getPageList(),e.next=17;break;case 16:return e.abrupt("return",t.$message.error(o.msg));case 17:case"end":return e.stop()}},e,t)})),function(t){return e.apply(this,arguments)}))}catch(e){console.error("新增设备时出错：",e)}},closeAddDevice:function(){this.addDeviceDialog=!1,this.$refs.addDeviceForm.resetFields()},openAddDeviceDialog:function(){this.addDeviceForm.registration_time=this.$moment().format("YYYY-MM-DD HH:mm:ss")},editDevice:function(){var e=this;return n()(c.a.mark(function t(){return c.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:e.$refs.editDeviceForm.validate(function(){var t=n()(c.a.mark(function t(i){var r,a;return c.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(i){t.next=4;break}return console.log("error submit!!"),e.$message.error("表单内容不符合规范！"),t.abrupt("return",!1);case 4:return t.prev=4,t.next=7,l.a.put("/device/update/"+e.editDeviceForm.device_id,{device_name:e.editDeviceForm.device_name.trim()||null,device_type:e.editDeviceForm.device_type,device_description:e.editDeviceForm.device_description.trim()||null,last_update:e.editDeviceForm.last_update,is_active:e.editDeviceForm.is_active});case 7:if(r=t.sent,a=r.data,console.log("修改设备配置结果:"),console.log(a),!a.success){t.next=19;break}return e.$message.success(a.msg),e.editDeviceDialog=!1,e.$refs.editDeviceForm.resetFields(),e.getPageList(),t.abrupt("return",!0);case 19:return e.$message.error(a.msg),t.abrupt("return",!1);case 21:t.next=27;break;case 23:return t.prev=23,t.t0=t.catch(4),console.error("更新设备配置时出错：",t.t0),t.abrupt("return",!1);case 27:case"end":return t.stop()}},t,e,[[4,23]])}));return function(e){return t.apply(this,arguments)}}());case 1:case"end":return t.stop()}},t,e)}))()},openEditDeviceDialog:function(){this.editDeviceForm.last_update=this.$moment().format("YYYY-MM-DD HH:mm:ss")},closeEditDevice:function(){this.editDeviceDialog=!1,this.$refs.editDeviceForm.resetFields()},editDeviceDialogHandler:function(e){this.editDeviceDialog=!0,this.editDeviceForm.device_id=e,console.log(e)}},created:function(){this.handleSearch()},mounted:function(){}},v={render:function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"config"},[i("el-card",{staticClass:"card_container"},[i("el-row",{attrs:{type:"flex",justify:"space-between"}},[i("el-form",{ref:"searchForm",staticClass:"demo-form-inline",attrs:{model:e.searchForm,size:"small",inline:""}},[i("el-form-item",{attrs:{label:"设备id:",prop:"device_id"}},[i("el-input",{staticStyle:{width:"170px"},attrs:{placeholder:"请输入设备id"},model:{value:e.searchForm.device_id,callback:function(t){e.$set(e.searchForm,"device_id","string"==typeof t?t.trim():t)},expression:"searchForm.device_id"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"设备名称:",prop:"device_name"}},[i("el-input",{staticStyle:{width:"170px"},attrs:{placeholder:"请输入设备名称"},model:{value:e.searchForm.device_name,callback:function(t){e.$set(e.searchForm,"device_name","string"==typeof t?t.trim():t)},expression:"searchForm.device_name"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"设备类型:",prop:"device_type"}},[i("el-select",{staticStyle:{width:"170px"},attrs:{placeholder:"请选择设备类型"},model:{value:e.searchForm.device_type,callback:function(t){e.$set(e.searchForm,"device_type",t)},expression:"searchForm.device_type"}},e._l(e.deviceTypeList,function(e){return i("el-option",{key:e.id,attrs:{label:e.type,value:e.id}})}),1)],1),e._v(" "),i("el-form-item",{staticStyle:{"margin-left":"30px"}},[i("el-button",{staticStyle:{"margin-right":"10px"},attrs:{type:"primary",icon:"el-icon-search",size:"small"},on:{click:function(t){return e.handleSearch()}}},[e._v("查询\n            ")]),e._v(" "),i("el-button",{attrs:{size:"small",icon:"el-icon-refresh-right"},on:{click:function(t){return e.handleClear()}}},[e._v("重置")])],1)],1)],1),e._v(" "),i("el-row",{staticClass:"rowSpace"},[i("el-col",{attrs:{span:4}},[i("el-button",{attrs:{type:"primary",size:"small"},nativeOn:{click:function(t){e.addDeviceDialog=!0}}},[e._v("新增设备")])],1),e._v(" "),i("el-col",{attrs:{span:8}},[i("div",[i("el-radio-group",{attrs:{size:"small"},on:{change:function(t){return e.changeRadio(t)}},model:{value:e.searchForm.is_active,callback:function(t){e.$set(e.searchForm,"is_active",t)},expression:"searchForm.is_active"}},[i("el-radio-button",{attrs:{label:"null"}},[e._v("全部")]),e._v(" "),i("el-radio-button",{attrs:{label:"0"}},[e._v("离线")]),e._v(" "),i("el-radio-button",{attrs:{label:"1"}},[e._v("活跃")])],1)],1)])],1),e._v(" "),i("el-dialog",{attrs:{title:"新增设备",visible:e.addDeviceDialog,width:"30%",center:"","before-close":e.closeAddDevice,"close-on-click-modal":!1},on:{"update:visible":function(t){e.addDeviceDialog=t},open:function(t){return e.openAddDeviceDialog()}}},[i("el-form",{ref:"addDeviceForm",staticClass:"demo-form-inline",attrs:{rules:e.addDeviceRule,model:e.addDeviceForm,"label-width":"90px"}},[i("el-form-item",{attrs:{label:"设备名称:",prop:"device_name"}},[i("el-input",{staticStyle:{width:"280px"},attrs:{placeholder:"请输入设备名称"},model:{value:e.addDeviceForm.device_name,callback:function(t){e.$set(e.addDeviceForm,"device_name",t)},expression:"addDeviceForm.device_name"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"设备描述:",prop:"device_description"}},[i("el-input",{staticStyle:{width:"280px"},attrs:{placeholder:"请输入设备描述"},model:{value:e.addDeviceForm.device_description,callback:function(t){e.$set(e.addDeviceForm,"device_description",t)},expression:"addDeviceForm.device_description"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"设备类型:",prop:"device_type"}},[i("el-select",{staticStyle:{width:"280px"},attrs:{placeholder:"请选择设备类型"},model:{value:e.addDeviceForm.device_type,callback:function(t){e.$set(e.addDeviceForm,"device_type",t)},expression:"addDeviceForm.device_type"}},e._l(e.deviceTypeList,function(e){return i("el-option",{key:e.id,attrs:{label:e.type,value:e.id}})}),1)],1),e._v(" "),i("el-form-item",{attrs:{label:"设备状态:",prop:"is_active"}},[i("el-select",{staticStyle:{width:"280px"},attrs:{placeholder:"请选择设备状态"},model:{value:e.addDeviceForm.is_active,callback:function(t){e.$set(e.addDeviceForm,"is_active",t)},expression:"addDeviceForm.is_active"}},e._l(e.deviceStateList,function(e){return i("el-option",{key:e.id,attrs:{label:e.state,value:e.id}})}),1)],1),e._v(" "),i("el-form-item",{attrs:{label:"注册时间:",prop:"registration_time"}},[i("el-input",{staticStyle:{width:"280px"},attrs:{disabled:!0},model:{value:e.addDeviceForm.registration_time,callback:function(t){e.$set(e.addDeviceForm,"registration_time",t)},expression:"addDeviceForm.registration_time"}})],1),e._v(" "),i("el-form-item",{staticClass:"dialog_button",attrs:{"label-width":"0px"}},[i("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.addDevice()}}},[e._v("保 存")]),e._v(" "),i("el-button",{staticStyle:{"margin-right":"10px"},on:{click:function(t){return e.closeAddDevice()}}},[e._v("取 消")])],1)],1)],1),e._v(" "),i("el-dialog",{attrs:{title:"修改设备配置",visible:e.editDeviceDialog,width:"30%",center:"","before-close":e.closeEditDevice,"close-on-click-modal":!1},on:{"update:visible":function(t){e.editDeviceDialog=t},open:function(t){return e.openEditDeviceDialog()}}},[i("el-form",{ref:"editDeviceForm",staticClass:"demo-form-inline",attrs:{rules:e.editDeviceRule,model:e.editDeviceForm,"label-width":"90px"}},[i("el-form-item",{attrs:{label:"设备id:",prop:"device_id"}},[i("el-input",{staticStyle:{width:"280px"},attrs:{disabled:!0},model:{value:e.editDeviceForm.device_id,callback:function(t){e.$set(e.editDeviceForm,"device_id",t)},expression:"editDeviceForm.device_id"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"设备名称:",prop:"device_name"}},[i("el-input",{staticStyle:{width:"280px"},attrs:{placeholder:"请输入设备名称"},model:{value:e.editDeviceForm.device_name,callback:function(t){e.$set(e.editDeviceForm,"device_name",t)},expression:"editDeviceForm.device_name"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"设备描述:",prop:"device_description"}},[i("el-input",{staticStyle:{width:"280px"},attrs:{placeholder:"请输入设备描述"},model:{value:e.editDeviceForm.device_description,callback:function(t){e.$set(e.editDeviceForm,"device_description",t)},expression:"editDeviceForm.device_description"}})],1),e._v(" "),i("el-form-item",{attrs:{label:"设备类型:",prop:"device_type"}},[i("el-select",{staticStyle:{width:"280px"},attrs:{placeholder:"请选择设备类型"},model:{value:e.editDeviceForm.device_type,callback:function(t){e.$set(e.editDeviceForm,"device_type",t)},expression:"editDeviceForm.device_type"}},e._l(e.deviceTypeList,function(e){return i("el-option",{key:e.id,attrs:{label:e.type,value:e.id}})}),1)],1),e._v(" "),i("el-form-item",{attrs:{label:"设备状态:",prop:"is_active"}},[i("el-select",{staticStyle:{width:"280px"},attrs:{placeholder:"请选择设备状态"},model:{value:e.editDeviceForm.is_active,callback:function(t){e.$set(e.editDeviceForm,"is_active",t)},expression:"editDeviceForm.is_active"}},e._l(e.deviceStateList,function(e){return i("el-option",{key:e.id,attrs:{label:e.state,value:e.id}})}),1)],1),e._v(" "),i("el-form-item",{attrs:{label:"修改时间:",prop:"last_update"}},[i("el-input",{staticStyle:{width:"280px"},attrs:{disabled:!0},model:{value:e.editDeviceForm.last_update,callback:function(t){e.$set(e.editDeviceForm,"last_update",t)},expression:"editDeviceForm.last_update"}})],1),e._v(" "),i("el-form-item",{staticClass:"dialog_button",attrs:{"label-width":"0px"}},[i("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.editDevice()}}},[e._v("保 存")]),e._v(" "),i("el-button",{staticStyle:{"margin-right":"10px"},on:{click:function(t){return e.closeEditDevice()}}},[e._v("取 消")])],1)],1)],1),e._v(" "),i("el-table",{staticStyle:{width:"100%"},attrs:{data:e.tableData,"row-class-name":e.tableRowClassName,border:""}},[i("el-table-column",{attrs:{prop:"index",label:"序号",width:"60"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n            "+e._s(t.$index+1)+"\n          ")]}}])}),e._v(" "),i("el-table-column",{attrs:{prop:"deviceId",label:"设备id"}}),e._v(" "),i("el-table-column",{attrs:{prop:"deviceName",label:"设备名称","show-overflow-tooltip":""}}),e._v(" "),i("el-table-column",{attrs:{prop:"deviceType",label:"设备类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("div",[e._v("\n              "+e._s({1:"智能家居",2:"生命安全设备",3:"车载设备",4:"可穿戴设备",5:"工业智能网关",6:"其他"}[t.row.deviceType])+"\n            ")])]}}])}),e._v(" "),i("el-table-column",{attrs:{prop:"deviceDescription",label:"设备描述","show-overflow-tooltip":""}}),e._v(" "),i("el-table-column",{attrs:{prop:"registrationTime",label:"创建日期"}}),e._v(" "),i("el-table-column",{attrs:{prop:"isActive",label:"设备状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("div",[i("el-tag",{attrs:{type:0==t.row.isActive?"danger":"success","disable-transitions":""}},[e._v(e._s(0==t.row.isActive?"离线":"活跃")+"\n              ")])],1)]}}])}),e._v(" "),i("el-table-column",{attrs:{label:"操作",width:"250"},scopedSlots:e._u([{key:"default",fn:function(t){return[i("el-button",{attrs:{type:"success",size:"small"},on:{click:function(i){return e.editDeviceDialogHandler(t.row.deviceId)}}},[e._v("编辑")]),e._v(" "),i("el-button",{attrs:{type:"danger",size:"small"},on:{click:function(i){return e.deleteDevice(t.row.deviceId)}}},[e._v("删除")]),e._v(" "),i("el-button",{attrs:{type:"primary",size:"small"},on:{click:function(i){return e.openDetail(t.row.deviceId)}}},[e._v("详情")])]}}])})],1),e._v(" "),i("el-pagination",{attrs:{"current-page":e.searchForm.current,"page-sizes":[5,10,15,20,25],"page-size":e.searchForm.size,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1)],1)},staticRenderFns:[]};var u=i("VU/8")(d,v,!1,function(e){i("BTLR")},null,null);t.default=u.exports}});
//# sourceMappingURL=5.dd598f66458c97bff211.js.map