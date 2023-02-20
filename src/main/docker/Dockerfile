FROM eclipse-temurin:8-jdk-alpine

#拥有者
MAINTAINER nie

VOLUME [ "/tmp" ]

# 设置时区
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone


# 添加arthas到容器中挂载路径为opt/arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas

# app.jar是你项目的jar包
COPY spring-boot-1.0.0.jar /app.jar

#对外暴露的端口
EXPOSE 9090

#运行命令
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom ","-Djasypt.encryptor.password=wtf12138++","-jar","/app.jar"]