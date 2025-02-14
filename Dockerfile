# Use an official OpenJDK runtime as a parent image
FROM openjdk:8-jre-slim
# Set the working directory inside the container
WORKDIR /app
# Copy the current directory contents into the container at /app
COPY target/groceryBookingApplication.jar /app/
# Expose the port on which the application will run
EXPOSE 8080
# Define the command to run the application
CMD ["java", "-jar", "groceryBookingApplication.jar"]
