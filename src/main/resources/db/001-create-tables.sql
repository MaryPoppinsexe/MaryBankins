--liquibase formatted sql

--changeset you:1
CREATE TABLE app_user (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          username VARCHAR(255) NOT NULL,
                          password VARCHAR(255) NOT NULL,
                          status VARCHAR NOT NULL,
                          created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                          updated_at TIMESTAMP WITH TIME ZONE
);
--rollback DROP TABLE app_user;

--changeset you:2
CREATE TABLE person (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        date_of_birth DATE NOT NULL,
                        nif VARCHAR(20) NOT NULL UNIQUE,
                        phone_number VARCHAR(20) NOT NULL UNIQUE,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        user_id UUID NOT NULL UNIQUE,
                        created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                        updated_at TIMESTAMP WITH TIME ZONE,
                        CONSTRAINT fk_person_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);
--rollback DROP TABLE person;