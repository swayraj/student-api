# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
ENV OLLAMA_MODE=demo
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
