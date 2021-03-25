create table IF NOT EXISTS users
(
    id       bigserial not null primary key,
    name     varchar(255),
    login    varchar(255),
    password varchar(255)
);
