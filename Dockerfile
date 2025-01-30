# Use the official OpenJDK base image for Java Spring Boot application
FROM maven:3.8.6 AS build
WORKDIR /build
COPY pom.xml .
COPY src ./src
COPY .env .
RUN mvn clean package

EXPOSE 8080
CMD ["mvn", "exec:java"]