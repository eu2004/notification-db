-- liquibase formatted sql

-- changeset liquibase:1
CREATE TABLE test_table (test_id INT, test_column VARCHAR, PRIMARY KEY (test_id));

CREATE TABLE notification_user (user_id INT, country_code INT NOT NULL, phone_number INT NOT NULL, email VARCHAR(255) NOT NULL, created_at TIMESTAMP  NOT NULL, PRIMARY KEY (user_id), UNIQUE(email));

CREATE TABLE notification_user_device (device_id INT, device_token VARCHAR(512) NOT NULL, user_id INT NOT NULL, PRIMARY KEY (device_id));

-- changeset liquibase:2
CREATE TABLE test_table2 (test_id INT, test_column VARCHAR, PRIMARY KEY (test_id));

-- changeset liquibase:3
ALTER TABLE notification_user ADD address VARCHAR(255) NULL;

-- changeset liquibase:4
CREATE  SEQUENCE  notification_user_seq  AS  int  START  WITH  1  INCREMENT  BY  1  MINVALUE  1  MAXVALUE  1000000;
CREATE  SEQUENCE  notification_user_device_seq  AS  int  START  WITH  1  INCREMENT  BY  1  MINVALUE  1  MAXVALUE  1000000;



