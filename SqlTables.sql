drop table if exists DetalleVenta;
drop table if exists Venta;
drop table if exists Cliente;
drop table if exists Nomina;
drop table if exists Empleado;
drop table if exists Producto;

create table Producto(cod_p char(5) not null primary key, descripcion varchar(80) not null, precio decimal not null);
create table Empleado(id_e int not null primary key auto_increment, nombre varchar(40), salario float);
create table Nomina(id_n int not null primary key auto_increment, id_e int not null unique, saldo float, foreign key(id_e) references Empleado(id_e) on update cascade on delete cascade);
create table Cliente(id_c int not null primary key auto_increment, nombre varchar(40));
create table Venta(id_v int not null primary key auto_increment, fecha timestamp not null, id_e int not null, id_c int not null, foreign key(id_c) references Cliente(id_c) on update cascade on delete cascade, foreign key(id_e) references Empleado(id_e) on update cascade on delete cascade);
create table DetalleVenta(id_v int not null, cod_p char(5) not null, cantidad int not null, precio_actual decimal not null, primary key (id_v, cod_p), foreign key(id_v) references Venta(id_v) on update cascade on delete cascade, foreign key(cod_p) references Producto(cod_p) on update cascade on delete cascade);