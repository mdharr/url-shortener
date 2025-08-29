# Start with a base Java image. We use a slim version for a smaller image.
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from your target directory into the container.
# This assumes your project is built with 'mvn package'.
COPY target/url-shortener-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot application runs on (default is 8080).
EXPOSE 8080

# The command to run your application when the container starts.
ENTRYPOINT ["java", "-jar", "app.jar"]