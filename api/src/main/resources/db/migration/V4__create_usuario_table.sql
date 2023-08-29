create table usuario(
    id serial primary key,
    nome varchar(255) not null,
    email varchar(255) unique not null,
    senha varchar(255) not null,
    permissoes text[] not null,
    ativo boolean default true,
    endereco_id bigint,
    foreign key (endereco_id) references endereco(id)
);