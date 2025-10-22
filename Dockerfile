# Use OpenJDK 17 lightweight image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copy the source code
COPY src src

# Make Maven wrapper executable
RUN chmod +x mvnw

# Build the Spring Boot application (skip tests for faster build)
RUN ./mvnw clean package -DskipTests

# Copy the generated JAR into container
COPY target/communityhelp-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render sets PORT via environment variable)
EXPOSE 8080

# Command to run the JAR
ENTRYPOINT ["java","-jar","/app/app.jar"]
