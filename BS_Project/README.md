### 前后端项目Docker构建

------

1. 找到此目录下的 `docker`目录，其中放置了用`maven`打包好的前后端整合在一起的`jar`包（`BS_Backend-0.0.1-SNAPSHOT.jar`）和`Dockerfile`
2. 在docker目录下执行如下命令：
   - **`docker build -t bs_project .`**　   #打包命令　注意有个``.``　（首次打包可能会比较慢）
   - **`docker images`**      #查看image命令（查看**`bs_project`**这个镜像是否已经构建完成）
   - **`docker run -d -p 8080:8080 bs_project`**        #运行Image命令镜像运行后在浏览器端使用 `localhost:8080` 访问。



> Tips1： 此项目已经把前端打包整合到了后端`springboot`项目中，故最终只有一个`jar`包，用`docker`构建镜像并运行之后即可启动项目。
>
> Tips2：此项目的数据库部署在远程服务器上（服务器的公网IP是47.115.208.169），所以当项目启动时，初始化可能需要一定的时间（数据库连接，数据库线程池分配等步骤需要耗费一定的时间）。当在浏览器上输入url打开项目时，最好先等待一会儿再执行操作，如果碰到有些操作短暂地失效（数据库处理操作可能还未返回结果），可以先等待片刻后再次执行。
>
> Tips3：由于服务端的mysql数据库与本地存在12h的时差，所以在首页的预览图可能会出现时间匹配不上的现象，这是因为当后端往服务端存储时间数据时，数据会被减去12h再存储。
>
> Tips4：考虑到MQTT客户端只发送deviceid0001-device0005的消息，我在系统中设置了，每当有新用户注册，便会为其自动新增五个设备名称为device0001-device0005的设备，且后续查看设备消息时只能查看device0001 - device0005的设备消息，其它设备默认接受不到消息



### MQTT客户端构建

------

1. 找到当前目录下的`iot.properties`文件，将其替换原本`iotclient`下的配置文件，然后依照原本`iotclient`下的构建命令即可启动MQTT客户端并连接到我部署在远程服务器上的MQTT服务器（emqx服务器）。

2. 构建命令如下：

   > （先替换`iot.properties`配置文件，替换路径为`iotclient\iotclient\src\main\resources\iot.properties`）
   >
   > 1、代码编译
   > `mvn clean package`
   >
   > 2、运行
   >
   > - 将`iot.properties`文件放在`iotclient-1.0.0.jar`同一目录
   > - 修改`iot.properties`配置（这一步可能不需要）
   > - 执行命令`java -jar iotclient-1.0.0.jar`



> Tips1：考虑到我只修改了`iot.properties`配置文件，所以`MQTT`客户端我没有打包成docker。
>
> Tips2：因为我将`mqtt`服务器部署在了远程服务器上，而不是本地，所以我修改了相应的配置（注释掉了`localhost`，换成了自己服务器的公网IP）：
>
> ```
> # server=tcp://localhost:1883
> server=tcp://47.115.208.169:1883
> ```



### 项目源代码

------

除了提供了`jar`包和`Dockerfil`e文件，我还提供了项目的源代码，前端部分代码放在`BS_Frontend`目录下，后端部分代码放在`BS_Backend`下，需要注意的是，因为我已经将前端部分打包成了静态文件并放置在了后端springboot项目的`resources\static`目录下，所以其实只运行后端代码就可以启动整个项目。**于此同时，我还在后端项目的`target`目录下存放了用`maven`打包好的jar包（`BS_Backend-0.0.1-SNAPSHOT.jar`，JDK版本是17），所以只要下载对应的JDK版本，并在对应目录执行如下命令：**

**`java -jar BS_Backend-0.0.1-SNAPSHOT.jar`，就可以启动项目。<u>当docker构建失败时，可以尝试使用这种方法运行该项目。</u>**



### iotclient客户端代码

------

在该目录下还存放了我之前使用过并构建好的**`iotclient`**代码部分，不过这部分参考意义不大，因为我修改了`pom.xml`中相关依赖的版本，在不同JDK环境下可能会运行不了。**<u>所以在构建MQTT客户端时，建议使用上面的提供的构建方法而不是直接用我提供的`iotclient`代码部分，该部分仅供参考。</u>**



### SQL建表语句

------

在该目录下的`SQL`子目录下，放置了两份sql建表语句（一份包含数据和结构`bs_database(data_and_schema).sql`，一份仅包含结构`bs_database(only_schema).sql`）。不过由于我已经将数据库部署到了远程服务器上并修改了后端数据库相关的配置文件，这部分sql建表语句作用不大，仅供参考。



### 注意事项

------

> 考虑到MQTT客户端只发送deviceid0001-device0005的消息，我在系统中设置了，每当有新用户注册，便会为其自动新增五个设备名称为device0001-device0005的设备，且后续查看设备消息时只能查看device0001 - device0005的设备消息，其它设备默认接受不到消息。（重述）



### USER账号示例

------

该项目提供注册功能，若想查看更丰富的数据，可以使用下面提供的账号和密码：

`username`：Tester

`password`：admin123
