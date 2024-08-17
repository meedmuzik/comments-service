--liquibase formatted sql

--changeset egorsemenovv:1
CREATE TABLE comment
(
    id           SERIAL PRIMARY KEY,
    user_id      REFERENCES users(id),
    body         VARCHAR(512) NOT NULL,
    track_id     LONG REFERENCES tracks(id),
    rating       SMALLINT NOT NULL
);

