# Use an official Maven image to build the application
FROM maven:3.8.4-openjdk-11 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests
FROM openjdk:11-jre-slim-buster
WORKDIR /app
COPY --from=builder /app/target/SYW-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]