# Use an official Java runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . .

# Install Maven and build the project (skip tests)
RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Specify the command to run your Java application
ENTRYPOINT ["java", "-jar", "target/CryptoParser-1.0-SNAPSHOT.jar"]
