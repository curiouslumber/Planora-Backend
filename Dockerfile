# -------- Stage 1: Build the application --------
FROM maven:3.9.3-eclipse-temurin-17 AS builder

# Set working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies (leverage caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Now copy the rest of the source code
COPY src ./src

# Build the project (skip tests if needed)
RUN mvn clean package -DskipTests

# -------- Stage 2: Run the application --------
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /app/target/*.jar app.jar

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
