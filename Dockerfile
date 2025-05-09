# Use a lightweight Ubuntu base image
FROM ubuntu:22.04

# Set environment variables for non-interactive installation
ENV DEBIAN_FRONTEND=noninteractive

# Update and install required packages: JDK and Maven
RUN apt-get update && apt-get install -y \
    openjdk-17-jdk \
    maven \
    && apt-get clean

# Set working directory inside the container
WORKDIR /app

# Copy the entire project folder into the container
COPY . .

# Build the application using Maven
RUN mvn clean package -DskipTests

# Expose the application port
EXPOSE 8080

# Set the entry point to run the application
CMD ["java", "-jar", "target/planora-0.0.1-SNAPSHOT.jar"]

