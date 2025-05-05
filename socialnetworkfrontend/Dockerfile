FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean install

FROM openjdk
WORKDIR /app
COPY --from=build /app/target/socialnetworkfrontend-1.0.jar /app/socialnetworkfrontend-1.0.jar
ENTRYPOINT ["java", "-jar", "socialnetworkfrontend-1.0.jar"]