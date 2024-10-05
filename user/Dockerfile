FROM maven:latest as dependency
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:resolve
COPY . /app
RUN mvn clean package -DskipTests

FROM openjdk:17 as final
WORKDIR /service
COPY --from=dependency /app/target/*jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]

