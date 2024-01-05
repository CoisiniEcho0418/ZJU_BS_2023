<template>
  <div class="config">
    <el-card class="card_container">
      <!-- 查询条件 -->
      <el-row type="flex"
              justify="space-between">
        <el-form ref="searchForm"
                 :model="searchForm"
                 size="small"
                 class="demo-form-inline"
                 inline>
          <el-form-item label="设备id:"
                        prop="device_id">
            <el-input v-model.trim="searchForm.device_id"
                      style="width:170px;"
                      placeholder="请输入设备id" />
          </el-form-item>
          <el-form-item label="设备名称:"
                        prop="device_name">
            <el-input v-model.trim="searchForm.device_name"
                      style="width:170px;"
                      placeholder="请输入设备名称" />
          </el-form-item>
          <el-form-item label="设备类型:"
                        prop="device_type">
            <el-select v-model="searchForm.device_type"
                       style="width:170px;"
                       placeholder="请选择设备类型">
              <el-option v-for="item in deviceTypeList"
                         :key="item.id"
                         :label="item.type"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="设备状态:"
                        prop="is_active">
            <el-select v-model="searchForm.is_active"
                       style="width:170px;"
                       placeholder="请选择设备状态">
              <el-option v-for="item in deviceStateList"
                         :key="item.id"
                         :label="item.state"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item> -->
          <el-form-item style="margin-left: 30px;">
            <el-button type="primary"
                       icon="el-icon-search"
                       size="small"
                       style="margin-right: 10px;"
                       @click="handleSearch()">查询
            </el-button>
            <el-button size="small"
                       icon="el-icon-refresh-right"
                       @click="handleClear()">重置</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row class="rowSpace">
        <el-col :span="4">
          <el-button type="primary"
                     size="small"
                     @click.native="addDeviceDialog = true">新增设备</el-button></el-col>
        <el-col :span="8">
          <div>
            <el-radio-group size="small"
                            @change="changeRadio($event)"
                            v-model="searchForm.is_active">
              <el-radio-button label=null>全部</el-radio-button>
              <el-radio-button label=0>离线</el-radio-button>
              <el-radio-button label=1>活跃</el-radio-button>
            </el-radio-group>
          </div>
        </el-col>
      </el-row>
      <!-- 新增设备dialog -->
      <el-dialog title="新增设备"
                 :visible.sync="addDeviceDialog"
                 width="30%"
                 center
                 @open="openAddDeviceDialog()"
                 :before-close="closeAddDevice"
                 :close-on-click-modal="false">
        <el-form ref="addDeviceForm"
                 :rules="addDeviceRule"
                 class="demo-form-inline"
                 :model="addDeviceForm"
                 label-width="90px">
          <el-form-item label="设备名称:"
                        prop="device_name">
            <el-input v-model="addDeviceForm.device_name"
                      style="width: 280px;"
                      placeholder="请输入设备名称" />
          </el-form-item>
          <el-form-item label="设备描述:"
                        prop="device_description">
            <el-input v-model="addDeviceForm.device_description"
                      style="width: 280px;"
                      placeholder="请输入设备描述" />
          </el-form-item>
          <el-form-item label="设备类型:"
                        prop="device_type">
            <el-select v-model="addDeviceForm.device_type"
                       style="width: 280px;"
                       placeholder="请选择设备类型">
              <el-option v-for="item in deviceTypeList"
                         :key="item.id"
                         :label="item.type"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="设备状态:"
                        prop="is_active">
            <el-select v-model="addDeviceForm.is_active"
                       style="width: 280px;"
                       placeholder="请选择设备状态">
              <el-option v-for="item in deviceStateList"
                         :key="item.id"
                         :label="item.state"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="注册时间:"
                        prop="registration_time">
            <el-input v-model="addDeviceForm.registration_time"
                      :disabled="true"
                      style="width: 280px;" />
          </el-form-item>
          <el-form-item class="dialog_button"
                        label-width="0px">
            <el-button type="primary"
                       @click="addDevice()">保 存</el-button>
            <el-button style="margin-right: 10px;"
                       @click="closeAddDevice()">取 消</el-button>

          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- 编辑设备dialog -->
      <el-dialog title="修改设备配置"
                 :visible.sync="editDeviceDialog"
                 width="30%"
                 center
                 @open="openEditDeviceDialog()"
                 :before-close="closeEditDevice"
                 :close-on-click-modal="false">
        <el-form ref="editDeviceForm"
                 :rules="editDeviceRule"
                 class="demo-form-inline"
                 :model="editDeviceForm"
                 label-width="90px">
          <el-form-item label="设备id:"
                        prop="device_id">
            <el-input v-model="editDeviceForm.device_id"
                      :disabled="true"
                      style="width: 280px;" />
          </el-form-item>
          <el-form-item label="设备名称:"
                        prop="device_name">
            <el-input v-model="editDeviceForm.device_name"
                      style="width: 280px;"
                      placeholder="请输入设备名称" />
          </el-form-item>
          <el-form-item label="设备描述:"
                        prop="device_description">
            <el-input v-model="editDeviceForm.device_description"
                      style="width: 280px;"
                      placeholder="请输入设备描述" />
          </el-form-item>
          <el-form-item label="设备类型:"
                        prop="device_type">
            <el-select v-model="editDeviceForm.device_type"
                       style="width: 280px;"
                       placeholder="请选择设备类型">
              <el-option v-for="item in deviceTypeList"
                         :key="item.id"
                         :label="item.type"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="设备状态:"
                        prop="is_active">
            <el-select v-model="editDeviceForm.is_active"
                       style="width: 280px;"
                       placeholder="请选择设备状态">
              <el-option v-for="item in deviceStateList"
                         :key="item.id"
                         :label="item.state"
                         :value="item.id"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="修改时间:"
                        prop="last_update">
            <el-input v-model="editDeviceForm.last_update"
                      :disabled="true"
                      style="width: 280px;" />
          </el-form-item>
          <el-form-item class="dialog_button"
                        label-width="0px">
            <el-button type="primary"
                       @click="editDevice()">保 存</el-button>
            <el-button style="margin-right: 10px;"
                       @click="closeEditDevice()">取 消</el-button>

          </el-form-item>
        </el-form>
      </el-dialog>
      <!-- 数据表 -->
      <el-table :data="tableData"
                style="width: 100%"
                :row-class-name="tableRowClassName"
                border>
        <el-table-column prop="index"
                         label="序号"
                         width="60">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column prop="deviceId"
                         label="设备id">
        </el-table-column>
        <el-table-column prop="deviceName"
                         label="设备名称"
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="deviceType"
                         label="设备类型">
          <template slot-scope="scope">
            <div>
              {{
        {
          1: "智能家居",
          2: "生命安全设备",
          3: "车载设备",
          4: "可穿戴设备",
          5: "工业智能网关",
		  6:"其他"
        }[scope.row.deviceType]
      }}
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="deviceDescription"
                         label="设备描述"
                         show-overflow-tooltip>
        </el-table-column>
        <el-table-column prop="registrationTime"
                         label="创建日期">
        </el-table-column>
        <el-table-column prop="isActive"
                         label="设备状态">
          <template slot-scope="scope">
            <div>
              <el-tag :type="scope.row.isActive == 0 ? 'danger' : 'success'"
                      disable-transitions>{{scope.row.isActive == 0 ? '离线' : '活跃'}}
              </el-tag>
            </div>
          </template>
        </el-table-column>
        <el-table-column label="操作"
                         width="250">
          <template slot-scope="scope">
            <el-button type="success"
                       size="small"
                       @click="editDeviceDialogHandler(scope.row.deviceId) ">编辑</el-button>
            <el-button type="danger"
                       size="small"
                       @click="deleteDevice(scope.row.deviceId)">删除</el-button>

            <el-button type="primary"
                       size="small"
                       @click="openDetail(scope.row.deviceId)">详情</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination @size-change="handleSizeChange"
                     @current-change="handleCurrentChange"
                     :current-page="searchForm.current"
                     :page-sizes="[5, 10, 15, 20,25]"
                     :page-size="searchForm.size"
                     layout="total, sizes, prev, pager, next, jumper"
                     :total="total">
      </el-pagination>
    </el-card>
  </div>
</template>

<script>
import axios from '../../util/axiosConfig'; // 引入axios实例

export default {
  components: {},
  props: {},
  data () {
    return {
      // 用于控制新增设备dialog的打开与否
      addDeviceDialog: false,
      // 用于控制编辑设备dialog的打开与否
      editDeviceDialog: false,
      // 查询表单的信息
      searchForm: {
        // 当前页
        current: 1,
        // 每页显示的条数
        size: 10,
        device_id: "",
        device_name: "",
        device_type: null,
        is_active: null
      },
      // 设备的数据表信息  1:智能家居 2:生命安全设备 3:车载设备 4:可穿戴设备 5:工业智能网关 6:其他
      /* 
    deviceDescription: (...),
    deviceId: (...),
    deviceName: (...),
    deviceType: (...),
    isActive: (...),
    registrationTime: (...),
      */
      tableData: [],
      // 设备总数
      total: 0,
      // 查询模块里的设备类型列表
      deviceTypeList: [
        { id: 1, type: "智能家居" },
        { id: 2, type: "生命安全设备" },
        { id: 3, type: "车载设备" },
        { id: 4, type: "可穿戴设备" },
        { id: 5, type: "工业智能网关" },
        { id: 6, type: "其他" }
      ],
      // 查询模块的设备状态列表
      deviceStateList: [
        { id: 0, state: "离线" },
        { id: 1, state: "活跃" }
      ],
      // 新增设备表单
      addDeviceForm: {
        device_name: "",
        device_type: null,
        device_description: "",
        is_active: null,
        registration_time: ""
      },
      // 新增设备表单校验规则
      addDeviceRule: {
        device_name: [
          { required: true, message: "请输入设备名称", trigger: "blur" },
        ],
        device_type: [
          { required: true, message: "请选择设备类型", trigger: "blur" }
        ],
        device_description: [
          { required: true, message: "请输入设备描述", trigger: "blur" },
        ],
        is_active: [
          { required: true, message: "请选择设备状态", trigger: "blur" },
        ],
      },
      // 编辑设备表单
      editDeviceForm: {
        device_id: "",
        device_name: "",
        device_type: null,
        device_description: "",
        is_active: null,
        last_update: ""
      },
      // 编辑设备表单校验规则
      editDeviceRule: {

      },

    };
  },
  watch: {},
  computed: {},
  methods: {
    // 控制数据表某一行的颜色
    tableRowClassName ({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
    // 切换每页显示条数
    handleSizeChange (val) {
      this.searchForm.size = val;
      this.searchForm.current = 1;
      this.getPageList();
    },
    // 跳转至某一页
    handleCurrentChange (val) {
      this.searchForm.current = val;
      this.getPageList();
    },
    // 路由跳转
    changeView (url, queryParams) {
      console.log(url);
      this.$router.push({
        path: url,
        query: queryParams,
      });
    },
    // 切换tab(实质上是根据设备状态（is_active）来查询)
    changeRadio (value) {
      // 判断是否选择了全部，如果没有选择全部，则设置is_active参数
      if (this.searchForm.is_active !== null) {
        this.searchForm.is_active = this.searchForm.is_active === 'null' ? null : parseInt(this.searchForm.is_active);
      }
      console.log("searchForm.is_active:")
      console.log(this.searchForm.is_active)
      const flag = this.getPageList();
      if (flag) {
        this.$message.success("查询成功！");
      } else {
        this.$message.error("查询失败！")
      }
    },
    // 删除设备
    async deleteDevice (device_id) {
      try {
        this.$confirm('此操作将永久删除该设备, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'error'
        }).then(async () => {
          // 发送删除设备的请求
          const { data: res } = await axios.delete(`/device/delete/${device_id}`);
          console.log("删除结果：", res)

          if (res.success) {
            this.$message.success("设备删除成功！");
            // 删除成功后刷新页面
            this.getPageList();
          } else {
            this.$message.error(res.msg);
          }
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });
        });

      } catch (error) {
        console.error('Error deleting device:', error);
        this.$message.error("删除设备出现异常！");
      }
    },
    // 查看设备详情
    async openDetail (device_id) {
      try {
        // 发送删除设备的请求
        const { data: res } = await axios.get(`/device/${device_id}`);
        console.log("详情结果：", res)
        if (res.success) {
          this.$alert("上次更新日期：" + res.data.lastUpdate, '详情', {
            confirmButtonText: '确定',
            // callback: action => {
            //   this.$message({
            //     type: 'info',
            //     message: `action: ${action}`
            //   });
            // }
          });
          // 删除成功后刷新页面
          this.getPageList();
        } else {
          this.$message.error(res.msg);
        }


      } catch (error) {
        console.error('Error open detail:', error);
        this.$message.error("查看详情出现异常！");
      }
    },
    // 获取设备信息（在初始化时调用）
    async getPageList () {
      try {
        const result = await axios.get('/device/search-with-pagination', {
          params: {
            user_id: this.$store.state.userId,
            ...this.searchForm,
          },
        });
        console.log("分页请求结果:")
        console.log(result)
        if (result.data.success) {
          this.tableData = result.data.data.data;
          console.log("表格数据：")
          console.log(this.tableData)
          this.total = result.data.data.totalCount;
          return true;
        } else {
          this.$message.error(result.data.msg);
          return false;
        }
      } catch (error) {
        console.error('Error fetching page list:', error);
        throw error; // 抛出错误，让上层调用者处理
      }
    },
    // 查询模块的查询事件
    async handleSearch () {
      this.searchForm.current = 1; // 重置页码为第一页

      try {
        const flag = await this.getPageList();
        if (flag) {
          this.$message.success("查询成功！");
        } else {
          this.$message.error("查询失败！");
        }
      } catch (error) {
        console.error('Error handling search:', error);
        this.$message.error("查询出现异常！");
      }
    },
    // 查询模块的重置事件
    handleClear () {
      try {
        // resetFields()是组件自带的重置函数
        this.$refs["searchForm"].resetFields();

        const flag = this.getPageList();
        if (flag) {
          this.$message.success("重置成功！");
        } else {
          this.$message.error("出现异常！")
        }
      } catch (error) {
        console.error('Error handling search:', error);
        this.$message.error("重置异常！");
      }
    },
    // 新增设备
    addDevice () {
      try {
        this.$refs.addDeviceForm.validate(async (valid) => {
          if (!valid) {
            console.log('error submit!!');
            this.$message.error("表单内容不符合规范！");
            return false;
          }

          // 将user_id添加到请求参数中
          const requestData = {
            ...this.addDeviceForm,
            user_id: this.$store.state.userId,
          };
          // 发送添加设备的请求
          const { data: res } = await axios.post(
            "/device/add",
            requestData
          );

          if (res.success) {
            this.$message.success("设备新增成功！");
            this.addDeviceDialog = false; // 关闭对话框
            this.$refs.addDeviceForm.resetFields(); // 重置表单

            // 成功添加后，发送分页请求
            this.getPageList();
          } else {
            return this.$message.error(res.msg);
          }
        });
      } catch (error) {
        console.error('新增设备时出错：', error);
      }

    },
    // 取消新增设备（重置表单）
    closeAddDevice () {
      this.addDeviceDialog = false;
      // Tips: resetFields 方法只能重置带有 props 属性的元素
      this.$refs.addDeviceForm.resetFields();
    },
    // 新增设备dialog打开的回调函数
    openAddDeviceDialog () {
      this.addDeviceForm.registration_time = this.$moment().format('YYYY-MM-DD HH:mm:ss')
    },
    // 修改设备配置
    async editDevice () {

      this.$refs.editDeviceForm.validate(async (valid) => {
        if (!valid) {
          console.log('error submit!!');
          this.$message.error("表单内容不符合规范！");
          return false;
        }
        try {
          const { data: res } = await axios.put(`/device/update/${this.editDeviceForm.device_id}`, {
            // 使用 trim() 删除前后空格，避免将空字符串传递给后端
            device_name: this.editDeviceForm.device_name.trim() || null,
            device_type: this.editDeviceForm.device_type,
            device_description: this.editDeviceForm.device_description.trim() || null,
            last_update: this.editDeviceForm.last_update,
            is_active: this.editDeviceForm.is_active,
          });

          console.log("修改设备配置结果:")
          console.log(res)

          if (res.success) {
            this.$message.success(res.msg);
            this.editDeviceDialog = false;
            this.$refs.editDeviceForm.resetFields();
            this.getPageList(); // 更新后刷新页面
            return true;
          } else {
            this.$message.error(res.msg);
            return false
          }
        } catch (error) {
          console.error('更新设备配置时出错：', error);
          return false
        }
      });


    },
    // 编辑设备dialog打开的回调函数
    openEditDeviceDialog () {
      this.editDeviceForm.last_update = this.$moment().format('YYYY-MM-DD HH:mm:ss')
    },
    // 取消编辑设备（重置表单）
    closeEditDevice () {
      this.editDeviceDialog = false;
      // Tips: resetFields 方法只能重置带有 props 属性的元素
      this.$refs.editDeviceForm.resetFields();
    },
    // 
    editDeviceDialogHandler (id) {
      this.editDeviceDialog = true;
      this.editDeviceForm.device_id = id
      console.log(id);
    }


  },
  created () {
    // 模拟发送一次分页请求来获取初始数据
    this.handleSearch();
  },
  mounted () {

  }
};
</script>
<style >
.config {
  margin: 20px 0;
}

.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}

.el-table {
  margin: 20px 0px;
}

.dialog_button {
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>