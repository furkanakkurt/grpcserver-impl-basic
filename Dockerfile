# Build stage
FROM maven:3.9-eclipse-temurin-21-jammy AS builder

# Set the working directory
WORKDIR /app

# Copy the POM file
COPY pom.xml .

# Copy the source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built artifact from builder stage
COPY --from=builder /app/target/grpcserver-0.0.1-SNAPSHOT.jar app.jar

# Expose the gRPC port (default is 9090)
EXPOSE 9090

# Set the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]
