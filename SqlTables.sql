drop trigger if exists Del_DetalleVenta;
drop trigger if exists Upd_DetalleVenta;
drop trigger if exists Ins_DetalleVenta;
drop table if exists HL_DetalleVenta;

drop trigger if exists Del_Venta;
drop trigger if exists Upd_Venta;
drop trigger if exists Ins_Venta;
drop table if exists HL_Venta;

drop trigger if exists Del_Cliente;
drop trigger if exists Upd_Cliente;
drop trigger if exists Ins_Cliente;
drop table if exists HL_Cliente;

drop trigger if exists Del_Nomina;
drop trigger if exists Upd_Nomina;
drop trigger if exists Ins_Nomina;
drop table if exists HL_Nomina;

drop trigger if exists Del_Empleado;
drop trigger if exists Upd_Empleado;
drop trigger if exists Ins_Empleado;
drop table if exists HL_Empleado;

drop trigger if exists Del_Producto;
drop trigger if exists Upd_Producto;
drop trigger if exists Ins_Producto;
drop table if exists HL_Producto;

drop table if exists DetalleVenta;
drop table if exists Venta;
drop table if exists Cliente;
drop table if exists Nomina;
drop table if exists Empleado;
drop table if exists Producto;

create table Producto(cod_p char(5) not null primary key, descripcion varchar(80) not null, precio decimal(10,2) not null);
create table Empleado(id_e int not null primary key auto_increment, nombre varchar(40), salario decimal(10,2), password varchar(40) not null);
create table Nomina(id_n int not null primary key auto_increment, id_e int not null unique, saldo decimal(10,2), foreign key(id_e) references Empleado(id_e));
create table Cliente(id_c int not null auto_increment, nom_usuario varchar(20) not null unique, nombre varchar(40), password varchar(40) not null, primary key(id_c, nom_usuario));
create table Venta(id_v int not null primary key auto_increment, fecha timestamp not null default current_timestamp on update current_timestamp, id_e int not null, id_c int not null, nom_usuario varchar(40) not null, status int, foreign key(id_c, nom_usuario) references Cliente(id_c, nom_usuario), foreign key(id_e) references Empleado(id_e));
create table DetalleVenta(id_v int not null, cod_p char(5) not null, cantidad int not null, precio_actual decimal(10,2) not null, primary key(id_v, cod_p), foreign key(id_v) references Venta(id_v), foreign key(cod_p) references Producto(cod_p));

-- tablas y triggers de historia

create table HL_Producto(id_hp int not null primary key auto_increment, old_cod_p char(5) null, new_cod_p char(5) null, old_descripcion varchar(80) null, new_descripcion varchar(80) null, old_precio decimal(10,2) null, new_precio decimal(10,2) null);
delimiter ??
create trigger Ins_Producto before insert on Producto for each row begin
    insert into HL_Producto(new_cod_p, new_descripcion, new_precio) values(new.cod_p, new.descripcion, new.precio);
end??
create trigger Upd_Producto before update on Producto for each row begin
    update DetalleVenta set cod_p = new.cod_p where cod_p = old.cod_p;
    insert into HL_Producto(old_cod_p, new_cod_p, old_descripcion, new_descripcion, old_precio, new_precio) values(old.cod_p, new.cod_p, old.descripcion, new.descripcion, old.precio, new.precio);
end??
create trigger Del_Producto before delete on Producto for each row begin
    delete from DetalleVenta where cod_p = old.cod_p;
    insert into HL_Producto(old_cod_p, old_descripcion, old_precio) values(old.cod_p, old.descripcion, old.precio);
end??
delimiter ;

create table HL_Empleado(id_he int not null primary key auto_increment, old_id_e int null, new_id_e int null, old_nombre varchar(40) null, new_nombre varchar(40) null, old_salario decimal(10,2) null, new_salario decimal(10,2) null, old_password varchar(40) null, new_password varchar(40) null);
delimiter ??
create trigger Ins_Empleado before insert on Empleado for each row begin
    insert into HL_Empleado(new_id_e, new_nombre, new_salario, new_password) values(new.id_e, new.nombre, new.salario, new.password);
end??
create trigger Upd_Empleado before update on Empleado for each row begin
    update Nomina set id_e = new.id_e where id_e = old.id_e;
    update Venta set id_e = new.id_e where id_e = old.id_e;
    insert into HL_Empleado(old_id_e, new_id_e, old_nombre, new_nombre, old_salario, new_salario, old_password, new_password) values(old.id_e, new.id_e, old.nombre, new.nombre, old.salario, new.salario, old.password, new.password);
end??
create trigger Del_Empleado before delete on Empleado for each row begin
    delete from Nomina where id_e = old.id_e;
    delete from Venta where id_e = old.id_e;
    insert into HL_Empleado(old_id_e, old_nombre, old_salario, old_password) values(old.id_e, old.nombre, old.salario, old.password);
end??
delimiter ;

create table HL_Nomina(id_hn int not null primary key auto_increment, old_id_n int null, new_id_n int null, old_id_e int null, new_id_e int null, old_saldo decimal(10,2) null, new_saldo decimal(10,2) null);
create trigger Ins_Nomina before insert on Nomina for each row
    insert into HL_Nomina(new_id_n, new_id_e, new_saldo) values(new.id_n, new.id_e, new.saldo);
create trigger Upd_Nomina before update on Nomina for each row
    insert into HL_Nomina(old_id_n, new_id_n, old_id_e, new_id_e, old_saldo, new_saldo) values(old.id_n, new.id_n, old.id_e, new.id_e, old.saldo, new.saldo);
create trigger Del_Nomina before delete on Nomina for each row
    insert into HL_Nomina(old_id_n, old_id_e, old_saldo) values(old.id_n, old.id_e, old.saldo);

create table HL_Cliente(id_hc int not null primary key auto_increment, old_id_c int null, new_id_c int null, old_nom_usuario varchar(20) null, new_nom_usuario varchar(20) null, old_nombre varchar(40) null, new_nombre varchar(40) null, old_password varchar(40) null, new_password varchar(40) null);
delimiter ??
create trigger Ins_Cliente before insert on Cliente for each row begin
    insert into HL_Cliente(new_id_c, new_nom_usuario, new_nombre, new_password) values(new.id_c, new.nom_usuario, new.nombre, new.password);
end??
create trigger Upd_Cliente before update on Cliente for each row begin
    update Venta set id_c = new.id_c, nom_usuario = new.nom_usuario where id_c = old.id_c and nom_usuario = old.nom_usuario;
    insert into HL_Cliente(old_id_c, new_id_c, old_nom_usuario, new_nom_usuario, old_nombre, new_nombre, old_password, new_password) values(old.id_c, new.id_c, old.nom_usuario, new.nom_usuario, old.nombre, new.nombre, old.password, new.password);
end??
create trigger Del_Cliente before delete on Cliente for each row begin
    delete from Venta where id_c = old.id_c and nom_usuario = old.nom_usuario;
    insert into HL_Cliente(old_id_c, old_nom_usuario, old_nombre, old_password) values(old.id_c, old.nom_usuario, old.nombre, old.password);
end??
delimiter ;

create table HL_Venta(id_hv int not null primary key auto_increment, old_id_v int null, new_id_v int null, old_fecha timestamp null, new_fecha timestamp null, old_id_e int null, new_id_e int null, old_id_c int null, new_id_c int null, old_nom_usuario varchar(40) null, new_nom_usuario varchar(40) null, old_status int null, new_status int null);
delimiter ??
create trigger Ins_Venta before insert on Venta for each row begin
    insert into HL_Venta(new_id_v, new_fecha, new_id_e, new_id_c, new_nom_usuario, new_status) values(new.id_v, new.fecha, new.id_e, new.id_c, new.nom_usuario, new.status);
end??
create trigger Upd_Venta before update on Venta for each row begin
    update DetalleVenta set id_v = new.id_v where id_v = old.id_v;
    insert into HL_Venta(old_id_v, new_id_v, old_fecha, new_fecha, old_id_e, new_id_e, old_id_c, new_id_c, old_nom_usuario, new_nom_usuario, old_status, new_status) values(old.id_v, new.id_v, old.fecha, new.fecha, old.id_e, new.id_e, old.id_c, new.id_c, old.nom_usuario, new.nom_usuario, old.status, new.status);
end??
create trigger Del_Venta before delete on Venta for each row begin
    delete from DetalleVenta where id_v = old.id_v;
    insert into HL_Venta(old_id_v, old_fecha, old_id_e, old_id_c, old_nom_usuario, old_status) values(old.id_v, old.fecha, old.id_e, old.id_c, old.nom_usuario, old.status);
end??
delimiter ;

create table HL_DetalleVenta(id_hdv int not null primary key auto_increment, old_id_v int null, new_id_v int null, old_cod_p char(5) null, new_cod_p char(5) null, old_cantidad int null, new_cantidad int null, old_precio_actual decimal(10,2) null, new_precio_actual decimal(10,2) null);
create trigger Ins_DetalleVenta before insert on DetalleVenta for each row
    insert into HL_DetalleVenta(new_id_v, new_cod_p, new_cantidad, new_precio_actual) values(new.id_v, new.cod_p, new.cantidad, new.precio_actual);
create trigger Upd_DetalleVenta before update on DetalleVenta for each row
    insert into HL_DetalleVenta(old_id_v, new_id_v, old_cod_p, new_cod_p, old_cantidad, new_cantidad, old_precio_actual, new_precio_actual) values(old.id_V, new.id_v, old.cod_p, new.cod_p, old.cantidad, new.cantidad, old.precio_actual, new.precio_actual);
create trigger Del_DetalleVenta before delete on DetalleVenta for each row
    insert into HL_DetalleVenta(old_id_v, old_cod_p, old_cantidad, old_precio_actual) values(old.id_v, old.cod_p, old.cantidad, old.precio_actual);