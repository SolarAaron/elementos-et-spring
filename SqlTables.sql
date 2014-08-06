drop table if exists Nomina;
drop table if exists Usuario;

create table Usuario(id int not null primary key, nombre varchar(40), salario float);
create table Nomina(id int not null, id_u int not null unique, saldo float, primary key(id), foreign key(id_u) references Usuario(id));

-- create table X
-- create table Y
-- create table Z
-- create table Etc...