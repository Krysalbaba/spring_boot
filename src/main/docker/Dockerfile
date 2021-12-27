FROM openjdk:8-alpine
#拥有者
#LABEL maintainer "xcbeyond"
VOLUME [ "/tmp" ]

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8
# app.jar是你项目的jar包
COPY spring-boot-1.0.0.jar /app.jar

#对外暴露的端口
EXPOSE 9090
 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]