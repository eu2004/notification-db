FROM openjdk:11.0.4-jre-slim
COPY ./target/notification-db-1.0-SNAPSHOT.jar /usr/local/lib/notification-db-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/notification-db-1.0-SNAPSHOT.jar"]