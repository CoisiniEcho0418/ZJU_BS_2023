## B/S体系软件设计

------

这是浙江大学2023学年秋冬学期BS体系软件设计课程作业仓库。

该仓库为docker打包版，前后端项目分离版请前往[CoisiniEcho0418/Iot_BS_Project: This is a course project of 2023 ZJU BS course. (github.com)](https://github.com/CoisiniEcho0418/Iot_BS_Project)

项目框架：VUE + SpringBoot + Mybatis



胡晓军老师要求最后的作业用docker打包，下面提供一点快速实现docker打包前后端项目的经验，帮助大家少走弯路：

- 建议大家在最后部署阶段，去阿里云免费领取一台学生服务器（大概是1+6的免费套餐），然后把自己的数据库和mqtt服务器部署在服务器上，后端远程连接数据库和mqtt服务器。服务器操作系统建议选择Ubutun，配置mysql数据较为简单~~（个人观点×）~~
- 把mqtt服务器部署在自己的云服务器上的教程可以参考这篇文章：[快速搭建个人MQTT服务器（基于EMQX） - 小文の知识窝 (wentxw.cc)](https://www.wentxw.cc/565.html)
- 在Ubutun服务器上部署mysql的教程：[阿里云服务器[Ubuntu 20.04\]配置MySQL并实现远程连接_阿里云服务器ubuntu放开mysql-CSDN博客](https://blog.csdn.net/m0_53110497/article/details/121388290)
- 如果前端和后端单独打包成docker容器，解决互相之间的通信问题可能比较麻烦，我参考了身边大佬的做法：把前端打包成静态文件整合到后端项目中，然后再把整合好的项目文件用maven打包成jar包，然后用docker把这个jar包构建成一个镜像。最后就只需要一个docker文件，运行一个docker容器就可以启动整个前后端项目，而且不用考虑前后端通信问题。相关教程可以参考：
  1. 前端打包成静态文件（打包命令是npm run build）：https://blog.csdn.net/weixin_43609432/article/details/120690787
  2. [Springboot2 Vue 前后端分离 整合打包 docker镜像 - 御简 - 博客园 (cnblogs.com)](https://www.cnblogs.com/SamNicole1809/p/12096914.html)
  3. 最后可以用IDEA自带的docker插件来进行docker的打包构建工作，但是前提是要先在Windows下下载Docker Desktop，参考教程：[Docker Desktop 安装使用教程-CSDN博客](https://blog.csdn.net/GoodburghCottage/article/details/131413312)

