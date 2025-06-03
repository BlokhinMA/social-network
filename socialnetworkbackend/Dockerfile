FROM maven:3.9.9-amazoncorretto-24 AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean install -DskipTests

FROM amazoncorretto:24
RUN addgroup -S nonroot && adduser -S nonroot -G nonroot
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
RUN chown -R nonroot:nonroot /app
USER nonroot
ENTRYPOINT ["java", "-jar", "app.jar"]