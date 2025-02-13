FROM eclipse-temurin:17-jdk-focal as build

WORKDIR /workspace/app

# copy important build files
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# build dependencies layer separately
RUN chmod +x ./mvnw && \
    sed -i 's/\r$//' ./mvnw
RUN ./mvnw dependency:go-offline

# copy source code
COPY src ./src

# build the app
RUN ./mvnw package -DskipTests


# runtime stage
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copy the built JAR from the build stage
COPY --from=build /workspace/app/target/qlish-backend-0.0.1-SNAPSHOT.jar /app/qlish-backend-0.0.1-SNAPSHOT.jar

# Create a non-root user and adjust permissions
RUN addgroup -S spring && adduser -S spring -G spring && \
    chown -R spring:spring /app

USER spring

# Health check
HEALTHCHECK --interval=30s --timeout=3s \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/backend-0.0.1-SNAPSHOT.jar"]