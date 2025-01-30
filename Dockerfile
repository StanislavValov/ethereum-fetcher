# Use the official OpenJDK base image for Java Spring Boot application
FROM openjdk:23-jdk as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven/Gradle build output JAR file into the container
COPY target/etherium /app/etherium.jar

# Use PostgreSQL official image to run PostgreSQL database
FROM postgres:latest

# Set PostgreSQL environment variables
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres
ENV POSTGRES_DB=postgres

# Expose the PostgreSQL port
EXPOSE 5432

# Expose the port for the Spring Boot application
EXPOSE 8084

# Run PostgreSQL as the main process
# We will set up a PostgreSQL service in one container and Spring Boot in another
# Start PostgreSQL first
CMD ["postgres"]
