create table estabelecimento(
    id serial primary key,
    nome varchar(255) not null,
    razaosocial varchar(255) not null,
    horario_funcionamento varchar(50) not null,
    endereco_id bigint,
    empresa_id bigint not null,
    foreign key (empresa_id) references empresa(id),
    foreign key (endereco_id) references endereco(id)
);