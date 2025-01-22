FROM maven:3.9.9-amazoncorretto-8-al2023 as builder

WORKDIR /app

COPY . /app

RUN mvn clean package -DskipTests

FROM openjdk:8u102

WORKDIR /app

COPY --from=builder /app/target/stonedt-portal-*.jar /app/app.jar

COPY config/application-docker.yml /app/config/application.yml
COPY config/config.properties /app/config/config.properties
COPY config/application.properties /app/config/application.properties

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
