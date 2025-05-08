CREATE TABLE users
(
    id         varchar(36)  NOT NULL PRIMARY KEY,
    email      varchar(255) NOT NULL UNIQUE,
    name       varchar(255) NOT NULL,
    password   varchar(255) NOT NULL,
    created_at datetime(6)  NOT NULL
);