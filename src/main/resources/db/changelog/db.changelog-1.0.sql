--liquibase formatted sql

--changeset egorsemenovv:1
CREATE TABLE comment
(
    id           SERIAL PRIMARY KEY,
    user_id      BIGINT,
    body         VARCHAR(512) NOT NULL,
    track_id     BIGINT,
    rating       SMALLINT NOT NULL
);

