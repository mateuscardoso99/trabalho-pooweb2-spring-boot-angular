create table endereco(
    id serial primary key,
    cidade varchar(255) not null,
    uf char(2) not null,
    bairro varchar(255) not null,
    rua varchar(255) not null,
    numero varchar(20) not null,
    complemento varchar(50),
    latitude varchar(100),
    longitude varchar(100)
);