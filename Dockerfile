# We use 2 stages to build the docker image
# Stage 1: Extraction (Temporary build stage)
FROM eclipse-temurin:21-jre-ubi10-minimal AS builder
WORKDIR /app
COPY target/*.jar app.jar
# Extract the Spring Boot layered JAR into 4 specific folders:
# 1. dependencies (static libs): change rarely and quiet big in size
# 2. spring-boot-loader (internal spring code): does not change at all
# 3. snashot-dependencies: change quiet often
# 4. application (own code): changes all the time
RUN java -Djarmode=layertools -jar app.jar extract

# Stage 2: Final runtime image
# We use the same base image here
FROM eclipse-temurin:21-jre-ubi10-minimal
WORKDIR /app
# Create a non-root user
RUN microdnf install -y shadow-utils && \
    groupadd -r appgroup && \
    useradd -r -g appgroup -u 1001 appuser && \
    microdnf remove -y shadow-utils && \
    microdnf clean all
# We copy the extracted directories from the builder stage.
# Docker will cache layers that didn't change (note: the order matters)
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./
# Expose the SSL port
EXPOSE 8443
# Switch to appuser
USER 1001
# Since we extracted the JAR, we cannot use "java -jar" anymore.
# We must use Spring Boot's JarLauncher to start the application.
ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]