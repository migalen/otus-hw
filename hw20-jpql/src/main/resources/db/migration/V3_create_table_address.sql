create table address
(
    id        bigserial not null primary key,
    street    varchar(255),
    client_id bigint    not null,
    constraint address_client_fk foreign key (client_id) references client (id)
);
