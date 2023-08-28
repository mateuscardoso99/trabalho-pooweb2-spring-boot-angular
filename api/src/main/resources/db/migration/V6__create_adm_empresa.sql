create table adm_empresa(
    id serial primary key,
    empresa_id bigint not null,
    foreign key (id) references usuario(id),
    foreign key (empresa_id) references empresa(id)
);