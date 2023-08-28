create table pedido(
    id serial primary key,
    descricao varchar(255) not null,
    status_pedido varchar(255) not null,
    cliente_id bigint,
    estabelecimento_id bigint,
    foreign key (cliente_id) references cliente(id),
    foreign key (estabelecimento_id) references estabelecimento(id)
);