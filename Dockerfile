FROM maven:3.8.4-jdk-11-slim AS build
WORKDIR /apps/notification-db
COPY src ./src
COPY pom.xml .
RUN mvn -f pom.xml clean install -X -Dspring.profiles.active=dev

FROM openjdk:11.0.4-jre-slim
COPY --from=build /apps/notification-db/target/lib /usr/local/lib/lib
COPY --from=build /apps/notification-db/target/notification-db-*-SNAPSHOT.jar /usr/local/lib/notification-db-release.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/notification-db-release.jar", "-Dspring.profiles.active=dev"]