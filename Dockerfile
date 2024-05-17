# 该镜像需要依赖的基础镜像
FROM openjdk:8-jdk-alpine
# 指定维护者名称
MAINTAINER Mayday 420882008@qq.com
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
COPY config config
COPY deploy/yuqing/application.yml config/application.yml
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]