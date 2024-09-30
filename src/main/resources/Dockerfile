
# Use JDK 17 as the base image
FROM openjdk:17-alpine

# Expose the desired port
EXPOSE 80

# Define the argument for the JAR file location
ARG JAR_FILE=build/libs/*.jar

# Copy the JAR file to the container
COPY ${JAR_FILE} app.jar

# Specify the entrypoint command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app.jar"]
