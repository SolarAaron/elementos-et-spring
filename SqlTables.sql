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
create table Empleado(id_e int not null primary key auto_increment, nombre varchar(40), salario decimal(10,2));
create table Nomina(id_n int not null primary key auto_increment, id_e int not null unique, saldo decimal(10,2), foreign key(id_e) references Empleado(id_e) on update cascade on delete cascade);
create table Cliente(id_c int not null primary key auto_increment, nombre varchar(40));
create table Venta(id_v int not null primary key auto_increment, fecha timestamp not null, id_e int not null, id_c int not null, foreign key(id_c) references Cliente(id_c) on update cascade on delete cascade, foreign key(id_e) references Empleado(id_e) on update cascade on delete cascade);
create table DetalleVenta(id_v int not null, cod_p char(5) not null, cantidad int not null, precio_actual decimal(10,2) not null, primary key (id_v, cod_p), foreign key(id_v) references Venta(id_v) on update cascade on delete cascade, foreign key(cod_p) references Producto(cod_p) on update cascade on delete cascade);

-- tablas y triggers de historia

create table HL_Producto(id_hp int not null primary key auto_increment, old_cod_p char(5) null, new_cod_p char(5) null, old_descripcion varchar(80) null, new_descripcion varchar(80) null, old_precio decimal(10,2) null, new_precio decimal(10,2) null);
create trigger Ins_Producto before insert on Producto for each row
    insert into HL_Producto(new_cod_p, new_descripcion, new_precio) values(new.cod_p, new.descripcion, new.precio);
create trigger Upd_Producto before update on Producto for each row
    insert into HL_Producto(old_cod_p, new_cod_p, old_descripcion, new_descripcion, old_precio, new_precio) values(old.cod_p, new.cod_p, old.descripcion, new.descripcion, old.precio, new.precio);
create trigger Del_Producto before delete on Producto for each row
    insert into HL_Producto(old_cod_p, old_descripcion, old_precio) values(old.cod_p, old.descripcion, old.precio);

create table HL_Empleado(id_he int not null primary key auto_increment, old_id_e int null, new_id_e int null, old_nombre varchar(40) null, new_nombre varchar(40) null, old_salario decimal(10,2) null, new_salario decimal(10,2) null);
create trigger Ins_Empleado before insert on Empleado for each row
    insert into HL_Empleado(new_id_e, new_nombre, new_salario) values(new.id_e, new.nombre, new.salario);
create trigger Upd_Empleado before update on Empleado for each row
    insert into HL_Empleado(old_id_e, new_id_e, old_nombre, new_nombre, old_salario, new_salario) values(old.id_e, new.id_e, old.nombre, new.nombre, old.salario, new.salario);
create trigger Del_Empleado before delete on Empleado for each row
    insert into HL_Empleado(old_id_e, old_nombre, old_salario) values(old.id_e, old.nombre, old.salario);

create table HL_Nomina(id_hn int not null primary key auto_increment, old_id_n int null, new_id_n int null, old_id_e int null, new_id_e int null, old_saldo decimal(10,2) null, new_saldo decimal(10,2) null);
create trigger Ins_Nomina before insert on Nomina for each row
    insert into HL_Nomina(new_id_n, new_id_e, new_saldo) values(new.id_n, new.id_e, new.saldo);
create trigger Upd_Nomina before update on Nomina for each row
    insert into HL_Nomina(old_id_n, new_id_n, old_id_e, new_id_e, old_saldo, new_saldo) values(old.id_n, new.id_n, old.id_e, new.id_e, old.saldo, new.saldo);
create trigger Del_Nomina before delete on Nomina for each row
    insert into HL_Nomina(old_id_n, old_id_e, old_saldo) values(old.id_n, old.id_e, old.saldo);

create table HL_Cliente(id_hc int not null primary key auto_increment, old_id_c int null, new_id_c int null, old_nombre varchar(40) null, new_nombre varchar(40) null);
create trigger Ins_Cliente before insert on Cliente for each row
    insert into HL_Cliente(new_id_c, new_nombre) values(new.id_c, new.nombre);
create trigger Upd_Cliente before update on Cliente for each row
    insert into HL_Cliente(old_id_c, new_id_c, old_nombre, new_nombre) values(old.id_c, new.id_c, old.nombre, new.nombre);
create trigger Del_Cliente before delete on Cliente for each row
    insert into HL_Cliente(old_id_c, old_nombre) values(old.id_c, old.nombre);

create table HL_Venta(id_hv int not null primary key auto_increment, old_id_v int null, new_id_v int null, old_fecha timestamp null, new_fecha timestamp null, old_id_e int null, new_id_e int null, old_id_c int null, new_id_c int null);
create trigger Ins_Venta before insert on Venta for each row
    insert into HL_Venta(new_id_v, new_fecha, new_id_e, new_id_c) values(new.id_v, new.fecha, new.id_e, new.id_c);
create trigger Upd_Venta before update on Venta for each row
    insert into HL_Venta(old_id_v, new_id_v, old_fecha, new_fecha, old_id_e, new_id_e, old__id_c, new_id_c) values(old.id_v, new.id_v, old.fecha, new.fecha, old.id_e, new.id_e, old.id_c, new.id_c);
create trigger Del_Venta before delete on Venta for each row
    insert into HL_Venta(old_id_v, old_fecha, old_id_e, old_id_c) values(old.id_v, old.fecha, old.id_e, old.id_c);

create table HL_DetalleVenta(id_hdv int not null primary key auto_increment, old_id_v int null, new_id_v int null, old_cod_p char(5) null, new_cod_p char(5) null, old_cantidad int null, new_cantidad int null, old_precio_actual decimal(10,2) null, new_precio_actual decimal(10,2) null);
create trigger Ins_DetalleVenta before insert on DetalleVenta for each row
    insert into HL_DetalleVenta(new_id_v, new_cod_p, new_cantidad, new_precio_actual) values(new.id_v, new.cod_p, new.cantidad, new.precio_actual);
create trigger Upd_DetalleVenta before update on DetalleVenta for each row
    insert into HL_DetalleVenta(old_id_v, new_id_v, old_cod_p, new_cod_p, old_cantidad, new_cantidad, old_precio_actual, new_precio_actual) values(old.id_V, new.id_v, old.cod_p, new.cod_p, old.cantidad, new.cantidad, old.precio_actual, new.precio_actual);
create trigger Del_DetalleVenta before delete on DetalleVenta for each row
    insert into HL_DetalleVenta(old_id_v, old_cod_p, old_cantidad, old_precio_actual) values(old.id_v, old.cod_p, old.cantidad, old.precio_actual);