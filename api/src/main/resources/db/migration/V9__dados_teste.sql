begin;
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','camobi','voluntários da pátria', '2322',null,'-29.708641','-53.723055');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','centro','rua A', '3453',null,'-29.694080','-53.774703');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','patronato','Av. Walter Jobim', '454',null,'-29.700368','-53.840743');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','centro','Av. Borges de Medeiros', '3453',null,'-29.687195','-53.822006');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','centro','Rua Duque de Caxias', '321',null,'-29.705774','-53.821495');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','KM3','Av. Osvaldo Cruz', '122',null,'-29.690546,','-53.785885');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','urlândia','Rua C', '11',null,'-29.719494','-53.808029');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','itararé','Av. dos Ferroviários', '243',null,'-29.676581','-53.808534');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','Passo Dareia','R. Venâncio Aires', '2525',null,'-29.688950','-53.829175');
    insert into endereco(cidade,uf,bairro,rua,numero,complemento,latitude,longitude) values('santa maria','RS','T. Neves','Av. Paulo Lauda', '3632',null,'-29.694540','-53.874680');
    insert into empresa(nome,razaosocial,endereco_id) values('empresa 1','empresa 1',1);
    insert into empresa(nome,razaosocial,endereco_id) values('empresa 2','empresa 2',2);
    insert into estabelecimento(nome,razaosocial,horario_funcionamento,endereco_id,empresa_id) values('estab 1','estab 1','08:00 ás 17:00',3,1);
    insert into estabelecimento(nome,razaosocial,horario_funcionamento,endereco_id,empresa_id) values('estab 2','estab 2','09:00 ás 22:00',4,2);
    insert into usuario(nome,email,senha,permissoes,endereco_id) values('joão','joao@gmail.com','$2a$11$NN91Lz01NlKX5XFfJ186PO6q21lZN0bp8ZyljHENUFjhB5z5b8mXa','{"CLIENTE"}',5);
    insert into usuario(nome,email,senha,permissoes,endereco_id) values('adm empresa 1','adm-emp-1@empresa1.com','$2a$11$NN91Lz01NlKX5XFfJ186PO6q21lZN0bp8ZyljHENUFjhB5z5b8mXa','{"ADMIN_EMPRESA"}',6);
    insert into usuario(nome,email,senha,permissoes,endereco_id) values('adm estab 1','adm-estab-1@estab1.com','$2a$11$NN91Lz01NlKX5XFfJ186PO6q21lZN0bp8ZyljHENUFjhB5z5b8mXa','{"ADMIN_ESTABELECIMENTO"}',7);
    insert into usuario(nome,email,senha,permissoes,endereco_id) values('jonas','jonas@gmail.com','$2a$11$NN91Lz01NlKX5XFfJ186PO6q21lZN0bp8ZyljHENUFjhB5z5b8mXa','{"CLIENTE"}',8);
    insert into usuario(nome,email,senha,permissoes,endereco_id) values('adm empresa 2','adm-emp-2@empresa2.com','$2a$11$NN91Lz01NlKX5XFfJ186PO6q21lZN0bp8ZyljHENUFjhB5z5b8mXa','{"ADMIN_EMPRESA"}',9);
    insert into usuario(nome,email,senha,permissoes,endereco_id) values('adm estab 2','adm-estab-2@gmail.com','$2a$11$NN91Lz01NlKX5XFfJ186PO6q21lZN0bp8ZyljHENUFjhB5z5b8mXa','{"ADMIN_ESTABELECIMENTO"}',10);
    insert into usuario(nome,email,senha,permissoes,endereco_id) values('adm sistema','adm@admgeral.com','$2a$11$NN91Lz01NlKX5XFfJ186PO6q21lZN0bp8ZyljHENUFjhB5z5b8mXa','{"ADMIN_SISTEMA"}',null);    
    insert into cliente(id) values(1);
    insert into cliente(id) values(4);
    insert into adm_empresa(id,empresa_id) values(2,1);
    insert into adm_empresa(id,empresa_id) values(5,2);
    insert into adm_estabelecimento(id,estabelecimento_id) values(3,1);
    insert into adm_estabelecimento(id,estabelecimento_id) values(6,2);
    insert into pedido(descricao,status_pedido,estabelecimento_id,cliente_id) values('pedido 1','PENDENTE',1,1);
    insert into pedido(descricao,status_pedido,estabelecimento_id,cliente_id) values('pedido 2','PENDENTE',1,4);
    insert into pedido(descricao,status_pedido,estabelecimento_id,cliente_id) values('pedido 3','PENDENTE',2,1);
    insert into pedido(descricao,status_pedido,estabelecimento_id,cliente_id) values('pedido 4','PENDENTE',2,1);
    insert into pedido(descricao,status_pedido,estabelecimento_id,cliente_id) values('pedido 5','PENDENTE',2,4);
    insert into pedido(descricao,status_pedido,estabelecimento_id,cliente_id) values('pedido 6','PENDENTE',1,1);
    insert into pedido(descricao,status_pedido,estabelecimento_id,cliente_id) values('pedido 7','PENDENTE',2,4);
commit;