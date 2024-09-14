FROM ubuntu:latest
LABEL authors="Dido"
# Use a base image with JDK 22
FROM eclipse-temurin:latest

# Set a label
LABEL maintainer="your-email@example.com"

# Copy the JAR file into the Docker image
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]

