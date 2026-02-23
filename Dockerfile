# Docker base image
FROM eclipse-temurin:21-jre-alpine
# Set the working directory
WORKDIR /app
# Create a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup -u 1001
# Copy the jar file to the docker image
COPY target/*.jar app.jar
# Let the appuser own the app for execution
RUN chown appuser:appgroup app.jar
# Expose the SSL port
EXPOSE 8443
# Switch to appuser
USER 1001
# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]