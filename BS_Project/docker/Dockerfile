###指定java17环境镜像
FROM openjdk:17
VOLUME /tmp
COPY BS_Backend-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c "touch /app.jar"
###声明启动端口号
EXPOSE 8080
###配置容器启动后执行的命令
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=test", "--server.port=8080", "/log/app.log"]
