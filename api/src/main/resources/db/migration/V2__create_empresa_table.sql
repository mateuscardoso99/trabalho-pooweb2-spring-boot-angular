create table empresa(
    id serial primary key,
    nome varchar(255) not null,
    razaosocial varchar(255) not null,
    endereco_id bigint,
    foreign key (endereco_id) references endereco(id)
);