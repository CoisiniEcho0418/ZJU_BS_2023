#### 注意事项

- 在使用 `mvn clean package` 编译打包iotclient时可能会碰到编译失败的问题。查了一些资料发现可能是JDK版本和项目中的pom.xml的maven指定的版本有出入，所以我对这部分稍作了调整，调整如下：

  ```
  <!--注意更新为当前项目中对应的JDK版本 -->
  <properties>
      <maven.compiler.source>21</maven.compiler.source>
      <maven.compiler.target>21</maven.compiler.target>
  </properties>
  ```



#### Reference

- MQTT服务器搭建参考链接：[一文搞懂MQTT，如何在SpringBoot中使用MQTT实现消息的订阅和发布-阿里云开发者社区 (aliyun.com)](https://developer.aliyun.com/article/1150694#slide-7)