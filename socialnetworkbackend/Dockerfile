FROM maven:3.9.9-amazoncorretto-24 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean install -DskipTests

FROM amazoncorretto:24
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]