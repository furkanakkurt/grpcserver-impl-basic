# Build
FROM maven:3.9-eclipse-temurin-21-jammy AS builder

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package

# Run
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

COPY --from=builder /app/target/grpcserver-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "app.jar"]
