create table usuario(
    id serial primary key,
    nome varchar(255) not null,
    email varchar(255) unique not null,
    senha varchar(255) not null,
    permissoes text[] not null,
    endereco_id bigint not null,
    empresa_id bigint,
    estabelecimento_id bigint,
    foreign key (endereco_id) references endereco(id),
    foreign key (empresa_id) references empresa(id),
    foreign key (estabelecimento_id) references estabelecimento(id)
);