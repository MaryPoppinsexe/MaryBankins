--liquibase formatted sql

--changeset you:3
ALTER TABLE app_user ADD COLUMN role VARCHAR(20) NOT NULL;
--rollback ALTER TABLE app_user DROP COLUMN role;
