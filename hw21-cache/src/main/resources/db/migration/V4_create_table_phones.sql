create table phones
(
    id        bigserial not null primary key,
    number    varchar(255),
    client_id bigint not null,
    constraint phone_client_fk foreign key (client_id) references client (id)
);
