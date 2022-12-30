use mushroom_farm
go


/*
 * Drop tables
 */

--drop table mushrooms
--drop table clients
--drop table orders


/*
 *	Create tables
 */

/*create table Ta (
	aid int primary key identity,
	a2 int unique,
	searchKey int
)*/

-- Ta will be clients

/*
create table clients (
	cid int primary key identity,
	cname varchar(60) unique not null,
	cnp bigint
)

-- foreign keys do not create indexes
create table mushrooms (
	mid int primary key identity,
	mtype int foreign key references mushroom_types(mtid) not null,
	scientificname varchar(50) not null,
	commonname varchar(50)
)

create table orders (
	oid int primary key identity,
	mushroomid int foreign key references mushrooms(mid) not null,
	clientid int foreign key references clients(cid) not null,
	amount float not null,
	odate date not null
) */

/* create table Tb (
	bid int primary key identity,
	b2 int
) */

-- Tb will be mushrooms

/* create table Tc(
	cid int primary key identity,
	aid int foreign key references Ta(aid),
	bid int foreign key references Tb(bid)
) */
-- Tc will be orders

-- execute


/*
 * Insert data in tables
 */


create or alter procedure insertDataInTa
(@n int)
as
begin
	declare @i int = 0
	while @i < @n
	begin
		insert into clients values (CONCAT('Name', @i), 502020225043 + @i)
		set @i = @i + 1
	end
end
go

create or alter procedure insertDataInTb
(@n int)
as
begin
	declare @i int = 0
	while @i < @n
	begin
		insert into mushrooms values (@i % 3 + 1, concat('SC', @i), concat('CM', @i))
		set @i = @i + 1
	end
end
go


create or alter procedure insertDataInTc
(@n int)
as
begin
	declare crossJoinCursor cursor for
	select cid, mid
	from clients cross join mushrooms
	
	declare @aId int
	declare @bId int
	declare @i int = 0

	open crossJoinCursor
	
	while @i < @n
	begin
		fetch crossJoinCursor into @aId, @bId
		insert into orders(clientid, mushroomid, amount, odate) values (@aId, @bId, -69.420 + @i, '2023-02-25')
		set @i = @i + 1
	end

	close crossJoinCursor
	deallocate crossJoinCursor
end
go

exec insertDataInTa 133769
exec insertDataInTb 42069
exec insertDataInTc 69420
go


/*
 *	a. Write queries on Ta such that their execution plans contain the following operators:
 *		clustered index scan;
 *		clustered index seek;
 *		nonclustered index scan;
 *		nonclustered index seek;
 *		key lookup.
 */

-- a primary key is a clustered index -> 3 clustered indexes(aid, bid, cid)
-- a unique key is a non clustered index -> 1 non clustered index(a2)

-- clustered index scan
select * from clients  -- estimated cost: 0.568946

-- clustered index seek
select * from clients where cid = 5 -- estimated cost: 0.0032831

-- nonclustered index scan

select cname from clients order by cname -- estimated cost: 0.611909

-- nonclustered index seek
select cname from clients where cname like 'Name5%' -- estimated cost: 0.0532761

-- key lookup
select cnp from clients where cname = 'Name69' -- estimated seek cost: 0.0032831, estimated lookup cost: 0.0032831

/*
 * b) Write a query on table Tb with a WHERE clause of the form WHERE b2 = value and analyze its execution plan.
 * Create a nonclustered index that can speed up the query. Examine the execution plan again.
 */

select * from mushrooms where scientificname = 'SC2' -- estimated cost: 0.190299

drop index Tb_b2_index on mushrooms
create nonclustered index Tb_b2_index on mushrooms(scientificname)

go

-- estimated cost for select statement from above but with non clustered index: 0.0065704

/*
 * c) Create a view that joins at least 2 tables. Check whether existing indexes are helpful; if not, reassess existing indexes / examine the cardinality of the tables.
 */
create or alter view joinTables as
	select A.cid, B.mid, C.oid
	from orders C
	inner join clients A on A.cid = C.clientid
	inner join mushrooms B on B.mid = C.mushroomid
	where b.scientificname > 'SC2' and A.cnp < 5020227200010
go

select * from joinTables

-- With primary keys(clustered) and unique(non clustered): 2.02959
-- When nonclustered index on cnp and existing indexes: 1.79536
-- Without the nonclustered index on b2 and the nonclustered index on searchKey: 2.14422
-- Original indexes + nonclustered index on searchKey + nonclustered index on (aid, bid) from Tc: 1.70721

DROP INDEX Ta_nonclustered_index ON clients
CREATE NONCLUSTERED INDEX Ta_nonclustered_index ON clients(cnp)

DROP INDEX Tc_index ON orders
CREATE NONCLUSTERED INDEX Tc_index ON orders(clientid, mushroomid)