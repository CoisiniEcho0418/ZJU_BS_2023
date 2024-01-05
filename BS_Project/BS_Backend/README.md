# 物联网设备后台管理系统

> ZJU BS Backend  
>
> 后端框架:     SpringBoot  + MyBatis  +  MySQL
>
> 项目管理:     Maven

## 运行方式

### 编写配置文件

在目录`src/main/resources`下新建`application.properties`文件，内容参考`application_template.properties`，仅需修改`EDIT ME`中带尖括号的信息即可（注意有两个地方，分别是数据库连接设置和JWT秘钥设置）。

其中，第一个 `EDIT ME` 块主要是SpringBoot整合MyBatis需要的数据库连接配置，你可以参考这篇文章进行配置 [SpringBoot整合Mybatis](https://blog.csdn.net/junR_980218/article/details/124805813) 

第一个 `EDIT ME` 块主要是用于生成 `JWT` 令牌的加密秘钥，你可以任取一串字符串作为秘钥

```properties
#-----EDIT ME-----#
# 数据库连接配置
spring.datasource.driver-class-name=<your_datasource_driver-class-name>
# 数据库连接URL
spring.datasource.url=<your_datasource_url>
# 数据库用户名
spring.datasource.username=<your_datasource_username>
# 数据库密码
spring.datasource.password=<your_datasource_password>
#------------------#

#-----EDIT ME-----#
# JWT 配置 （任意字符串秘钥）
jwt.secretKey=<"your_token_secret_key">
#------------------#

# MyBatis 配置
# 使用标准输出作为 MyBatis 的日志实现
mybatis.configuration.log-impl=org.apache.ibatis.logging.stdout.StdOutImpl
# 将下划线分隔的命名转换为驼峰命名（例如，a_column -> aColumn）
mybatis.configuration.map-underscore-to-camel-case=true
# 扫描 MyBatis 类型别名的包
mybatis.type-aliases-package=com.hwj.bs_backend.pojo

# 日志配置
# 根日志级别
logging.level.root=info
# 解除以下行的注释以设置不同包的特定日志级别
# logging.level.com.hwj.bs_backend.controller=DEBUG
logging.level.com.hwj.bs_backend.service=DEBUG
logging.level.com.hwj.bs_backend.pojo=DEBUG

# MQTT配置信息
mqtt.url=tcp://127.0.0.1:1883
mqtt.username=admin
mqtt.password=public
mqtt.client.id=consumer-id
mqtt.default.topic=topic

```

### Notes

- 在启动应用程序之前，请确保数据库服务器能够连接成功，你可以打开IDE或者相应的数据库软件来检测连接是否成功。

### 编译运行

项目使用`maven`进行依赖管理，请自行参考`maven`的使用方法。或使用`IntelliJ IDEA`打开项目，使用`IDEA`自带的`maven`插件进行依赖的更新。当所有依赖更新完之后点击运行即可启动后端项目（这之前得先保证上一步的配置文件配置无误）。

或者你也可以参考其他方式运行`maven`项目，可以参考这个 [总结启动maven项目的3种方式](https://blog.csdn.net/qq_43392001/article/details/99625275)

### 数据库建表语句

数据库的建表语句存储在在目录`src/main/resources`下，文件名为 `bs_database.sql` ，示意图如下：

![image-20240104162318533](D:\Desktop\BS体系设计\大作业\BS_Project\BS_Backend\image-20240104162318533.png)
