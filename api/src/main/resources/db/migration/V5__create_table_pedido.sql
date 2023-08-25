create table pedido(
    id serial primary key,
    descricao varchar(255) not null,
    status_pedido varchar(255) not null,
    usuario_id bigint,
    estabelecimento_id bigint,
    foreign key (usuario_id) references usuario(id),
    foreign key (estabelecimento_id) references estabelecimento(id)
);