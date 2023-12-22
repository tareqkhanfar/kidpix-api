# Use a base image with Java (like OpenJDK)
FROM openjdk:17

# Set a working directory inside the container
WORKDIR /app

# Copy the JAR file from your target folder to the container
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo-0.0.1-SNAPSHOT.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run your app
ENTRYPOINT ["java", "-jar", "/app/demo-0.0.1-SNAPSHOT.jar"]
