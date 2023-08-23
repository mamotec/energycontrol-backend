# Verwende ein ARM-kompatibles OpenJDK-Basisimage
FROM eclipse-temurin:17-jdk as builder
RUN apt-get update && \
    apt-get install -y maven \

# Setze das Arbeitsverzeichnis im Container
WORKDIR /app

# Kopiere die Projektquelle in das Arbeitsverzeichnis
COPY . /app

# Installiere Maven


# Baue das Projekt mit Maven
RUN mvn clean package -DskipTests

# Use the official Azul Zulu OpenJDK 17 image as the base image
FROM eclipse-temurin:17-jdk

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build output to the container
COPY --from=builder /app/target/energycontrol-backend.jar /app/energycontrol-backend.jar

# Set the container entrypoint to execute the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/energycontrol-backend.jar"]
