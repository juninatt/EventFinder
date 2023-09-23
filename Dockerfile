# Use an official Java runtime as the base image
FROM openjdk:17.0.1-jdk-slim

# The application's JAR file
ARG JAR_FILE=target/EventFinder-1.0.jar

# Add the application's JAR file to the container
ADD ${JAR_FILE} app.jar

# Specify environment variables for MongoDB (Optional, can be overridden by docker-compose or Kubernetes)
ENV MONGODB_URI=mongodb://mongo:27017/
ENV MONGODB_DATABASE=eventsdb
ENV MONGODB_COLLECTION=events

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]


# Add the application's JAR file to the container
ADD ${JAR_FILE} app.jar

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
