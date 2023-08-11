FROM eclipse-temurin:17-jdk-alpine

MAINTAINER alpha.com

ARG JAR_FILE=target/rabbitmqdemo-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]