FROM arm64v8/openjdk:17-jdk-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml /app/
COPY src /app/src/

# Build the project using Maven
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Use the official Azul Zulu OpenJDK 17 image as the base image
FROM eclipse-temurin:17-jdk-focal

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build output to the container
COPY --from=builder /app/target/energycontrol-backend.jar /app/energycontrol-backend.jar

# Set the container entrypoint to execute the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/energycontrol-backend.jar"]
