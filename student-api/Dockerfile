# Build stage (JDK needed for compilation)
FROM eclipse-temurin:17-jdk-jammy AS builder
WORKDIR /workspace
COPY . .
RUN chmod +x mvnw && ./mvnw clean package

# Run stage (JRE only - smaller image)
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app
COPY --from=builder /workspace/target/*.jar app.jar
ENV OLLAMA_MODE=demo
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]