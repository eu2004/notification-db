FROM openjdk:11.0.4-jre-slim
COPY ./target/notification-db-1.0-SNAPSHOT.jar /usr/local/lib/notification-db-1.0-SNAPSHOT.jar
COPY ./target/lib /usr/local/lib/lib
EXPOSE 8080

ENV POSTGRES_HOST=postgres-dev
ENV POSTGRES_PORT=5432
ENV POSTGRES_DB_NAME=postgres
ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=postgres

ENTRYPOINT ["java","-jar","/usr/local/lib/notification-db-1.0-SNAPSHOT.jar"]