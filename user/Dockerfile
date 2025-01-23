FROM maven:latest as builder
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean package -DskipTests
FROM openjdk:17-slim as final
WORKDIR /service
COPY --from=builder /app/target/*.jar app.jar
RUN rm -rf /tmp/* /var/cache/*
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
