FROM maven:3.8.4-jdk-11-slim AS build
WORKDIR /apps/notification-db
COPY src ./src
COPY pom.xml .

ARG JASYPT_ENCRYPTOR_PASSWORD

RUN mvn -f pom.xml clean install -X -Dspring.profiles.active=dev -Djasypt.encryptor.password=$JASYPT_ENCRYPTOR_PASSWORD

FROM openjdk:11.0.4-jre-slim
COPY --from=build /apps/notification-db/target/lib /usr/local/lib/lib
COPY --from=build /apps/notification-db/target/notification-db-*-SNAPSHOT.jar /usr/local/lib/notification-db-release.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/notification-db-release.jar"]