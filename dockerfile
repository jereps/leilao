# Estágio de build usando Java 25
FROM maven:3-eclipse-temurin-25-alpine AS build
WORKDIR /app
COPY . .
ENV LANG=C.UTF-8
RUN mvn clean package -DskipTests

# Estágio de execução com Java 25
FROM eclipse-temurin:25-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
