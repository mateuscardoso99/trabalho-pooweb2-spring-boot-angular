create table adm_estabelecimento(
    id serial primary key,
    estabelecimento_id bigint not null,
    foreign key (id) references usuario(id),
    foreign key (estabelecimento_id) references estabelecimento(id)
);