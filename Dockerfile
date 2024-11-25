# Use the OpenJDK 17 image as the base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the backend project files to the working directory
COPY . .

# Build the backend application using Maven
RUN ./mvnw package -DskipTests

# Specify the command to run the backend application
CMD ["java", "-jar", "target/demo-0.0.1-SNAPSHOT.jar"]