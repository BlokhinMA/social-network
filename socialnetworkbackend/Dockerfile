FROM maven:3.9.9-amazoncorretto-24 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean install

FROM amazoncorretto:24
WORKDIR /app
COPY --from=build /app/target/socialnetworkbackend-1.0.jar /app/socialnetworkbackend-1.0.jar
ENTRYPOINT ["java", "-jar", "socialnetworkbackend-1.0.jar"]