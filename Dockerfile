# Use Eclipse Temurin JDK for Java 17
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built JAR into the container
COPY target/*.jar app.jar

# Expose the port your app runs on (Spring Boot defaults to 8080)
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
