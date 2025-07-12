# Build stage
FROM maven:3.8.6-eclipse-temurin-17 AS builder
WORKDIR /workspace

# Copy only necessary files
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build application
RUN mvn clean package -DskipTests

# Run stage
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
ENV OLLAMA_MODE=demo
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
