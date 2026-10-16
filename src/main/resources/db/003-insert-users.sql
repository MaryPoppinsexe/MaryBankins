--liquibase formatted sql

--changeset you:4
INSERT INTO app_user (id, username, password, status, role, created_at, updated_at)
VALUES (
           gen_random_uuid(),
           'admin',
           '$2b$10$rd6Ydu3MXMbpsKwYQjr3l.HyRFPkOvdWgZKdgF./AKk5xXKbDua8C',
           true,
           'ADMIN',
           now(),
           now()
       );
