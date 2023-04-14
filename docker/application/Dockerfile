# Use the official Azul Zulu OpenJDK 17 image as the base image
FROM azul/zulu-openjdk:17

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build output to the container
COPY target/energycontrol-backend.jar /app/energycontrol-backend.jar

# Set the container entrypoint to execute the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/energycontrol-backend.jar"]
