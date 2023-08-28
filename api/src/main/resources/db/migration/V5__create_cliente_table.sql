create table cliente(
    id serial primary key,
    foreign key (id) references usuario(id)
);