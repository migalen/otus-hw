create table client
(
    id   bigserial not null primary key,
    name varchar(50),
    age  int
);
create table account
(
    id   varchar(36),
    type varchar(50),
    rest double precision
);
