# Usar uma imagem Maven oficial para construir o projeto
FROM maven:3.8.1-openjdk-11 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -Pdocker -DskipTests

# Usar uma imagem oficial do OpenJDK para executar o aplicativo
FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=build /app/target/SYW-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]