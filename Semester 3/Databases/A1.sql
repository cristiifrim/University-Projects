use master
go

drop database mushroom_farm
go

create database mushroom_farm
go

use mushroom_farm
go

create table mushroom_types (
	mtid int primary key identity,
	name varchar(20) not null
)

create table mushrooms (
	mid int primary key identity,
	mtype int foreign key references mushroom_types(mtid) not null,
	scientificname varchar(50) not null,
	commonname varchar(50)
)

create table farms (
	fid int primary key identity,
	faddress varchar(60) not null
)

create table employees (
	eid int primary key identity,
	firstname varchar(30) not null,
	surname varchar(30),
	cnp varchar(13) not null
)

create table salaries (
	income float not null,
	employeeid int foreign key references employees(eid) not null,
	constraint pk_Salaries primary key (employeeid)
)

create table plantations (
	pid int primary key identity,
	farmid int foreign key references farms(fid) not null,
	mushroomid int foreign key references mushrooms(mid) not null,
	employeeid int foreign key references employees(eid) not null,
)

create table gatherings (
	amount float,
	plantationid int foreign key references plantations(pid) not null,
	gdate date not null,
	constraint pk_Gatherings primary key (plantationid)
)

create table clients (
	cid int primary key identity,
	cname varchar(60) unique not null,
	cnp bigint
)

create table orders (
	oid int primary key identity,
	mushroomid int foreign key references mushrooms(mid) not null,
	clientid int foreign key references clients(cid) not null,
	amount float not null,
	odate date not null
)

create table warehouses (
	wid int primary key identity,
	waddress varchar(60) not null,
	capacity float not null
)

create table stocks (
	amount float null,
	mushroomid int foreign key references mushrooms(mid) not null,
	warehouseid int foreign key references warehouses(wid) not null,
	constraint pk_Stocks primary key(mushroomid, warehouseid)
)



