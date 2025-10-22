# Stage 1: build
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app

# Copy Maven wrapper and pom.xml first to cache dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Give mvnw execution permissions
RUN chmod +x mvnw

# Copy source code
COPY src src

# Build the JAR
RUN ./mvnw clean package -DskipTests

# Stage 2: runtime
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the JAR from build stage
COPY --from=build /app/target/communityhelp-0.0.1-SNAPSHOT.jar app.jar

# Expose port (Render uses $PORT)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
