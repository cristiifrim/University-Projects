use mushroom_farm
go



create or alter procedure addMushroomTypes
@n int
as
begin
	declare @i int=0
	while @i < @n
	begin
		insert into mushroom_types values (CONCAT('Type ', @i))
		set @i = @i + 1
	end
end
go

create or alter procedure deleteMushroomTypes
as
begin
	delete from mushroom_types where name like 'Type %'
end
go

create or alter procedure addMushrooms
@n int
as
begin
	declare @i int=0
	while @i < @n
	begin
		declare @type int = (select top 1 mtid from mushroom_types where name like 'Type %' order by NEWID())
		insert into mushrooms values (@type, CONCAT('ScientificName ', @i), CONCAT('CommonName ', @i)) 
		set @i = @i + 1
	end
end
go

create or alter procedure deleteMushrooms
as
begin
	delete from mushrooms where scientificname like 'ScientificName %'
end
go


create or alter procedure addWarehouses
@n int
as
begin
	declare @i int=0
	while @i < @n
	begin
		insert into warehouses values (CONCAT('Address ', @i), 3000)
		set @i = @i + 1
	end
end
go

create or alter procedure deleteWarehouses
as
begin
	delete from warehouses where waddress like 'Address %'
end
go


create or alter procedure addStocks
@n int
as
begin
	declare @i int = 0
	declare @warehouse int
	declare @mushroom int

	DECLARE TestDataCursor CURSOR FOR
	SELECT t.wid, m.mid
	FROM (select wid from warehouses where waddress like 'Address %') t 
		 cross join 
		 (select mid from mushrooms where scientificname like 'ScientificName %') m

	OPEN TestDataCursor
	FETCH TestDataCursor
	INTO @warehouse, @mushroom

	while @i < @n
	begin
		insert into stocks (amount, warehouseid, mushroomid) values (-5.0, @warehouse, @mushroom)
		set @i = @i + 1

		FETCH TestDataCursor
		INTO @warehouse, @mushroom
	end

	CLOSE TestDataCursor
	DEALLOCATE TestDataCursor
end
go

create or alter procedure deleteStocks
as
begin
	delete from stocks where amount = -5.0
end
go


---

create or alter view showMushroomTypes
as
	select name from mushroom_types where name like 'Type %'
go

create or alter view showMushrooms
as
	select m.scientificname as 'Scientific Name', m.commonname as 'Common Name', mt.name as 'Type'
	from mushrooms m inner join mushroom_types mt on mt.mtid = m.mtype
go

create or alter view showStocks
as
	select s.amount as 'Amount', m.scientificname as 'Scientific name', m.commonname as 'Common name', w.waddress as 'Warehouse address'
	from stocks s right join mushrooms m on s.mushroomid = m.mid inner join warehouses w on s.warehouseid = w.wid	
go


create or alter procedure selectView
(@name varchar(100))
as
begin
	declare @sql varchar(250)='select * from ' + @name
	exec(@sql)
end
go

---

insert into Tests(Name) values	('addMushroomTypes'), ('deleteMushroomTypes'),
								('addMushrooms'), ('deleteMushrooms'),
								('addWarehouses'), ('deleteWarehouses'),
								('addStocks'), ('deleteStocks'),
								('selectView')
go

insert into Tables(Name) values ('mushroom_types'), ('mushrooms'), ('warehouses'), ('stocks')
go

insert into Views(Name) values ('showMushroomTypes'), ('showMushrooms'), ('showStocks')
go

insert into TestViews(TestID, ViewID) values (9, 1), (9, 2), (9, 3)
go
insert into TestTables(TestID, TableID, NoOfRows, Position) values	(2, 1, 75, 4), (1, 1, 75, 1),
																	(4, 2, 1000, 3), (3, 2, 1000, 2),
																	(6, 3, 5000, 2), (5, 3, 5000, 3),
																	(8, 4, 10000, 1), (7, 4, 10000, 4)
go
---

create or alter procedure runDeleteTests
as
begin
	declare @testId int
	declare @cmd varchar(max)

	declare fetchDeleteTests cursor
	for select TT.TestID, T.Name from TestTables TT join Tests T on TT.TestID = T.TestID where T.Name like 'delete%' order by Position asc

	open fetchDeleteTests
	fetch fetchDeleteTests into @testId, @cmd
	while @@FETCH_STATUS = 0
	begin
		exec @cmd

		fetch fetchDeleteTests into @testId, @cmd
	end
	close fetchDeleteTests
	deallocate fetchDeleteTests
end
go

create or alter procedure runInsertTests
(@runTestId int)
as
begin
	declare @testId int
	declare @tableId int
	declare @numOfRows int
	declare @cmd varchar(max)

	declare fetchInsertTests cursor
	for select TT.TestID, TT.TableID, TT.NoOfRows, T.Name from TestTables TT join Tests T on TT.TestID = T.TestID where T.Name like 'add%' order by Position asc

	open fetchInsertTests
	fetch fetchInsertTests into @testId, @tableId, @numOfRows, @cmd
	while @@FETCH_STATUS = 0
	begin
		declare @startTime datetime = GETDATE()

		exec (@cmd + ' ' + @numOfRows)

		declare @endTime datetime = GETDATE()
		insert into TestRunTables(TestRunID, TableID, StartAt, EndAt) values (@runTestId, @tableId, @startTime, @endTime)

		fetch fetchInsertTests into @testId, @tableId, @numOfRows, @cmd
	end

	close fetchInsertTests
	deallocate fetchInsertTests
end
go

create or alter procedure runViewTests
(@runTestId int)
as
begin
	declare @testId int
	declare @viewId int

	declare fetchViewTests cursor for
	select * from TestViews

	open fetchViewTests
	fetch fetchViewTests into @testId, @viewId
	while @@FETCH_STATUS = 0
	begin
		declare @cmd varchar(MAX) = (select Name from Tests where TestID = @testId)
		declare @args varchar(MAX) = (select Name from Views where ViewID = @viewId)
		declare @startTime datetime = GETDATE()
		
		exec (@cmd + ' ' + @args)

		declare @endTime datetime = GETDATE()
		insert into TestRunViews(TestRunID, ViewID, StartAt, EndAt) values (@runTestId, @viewId, @startTime, @endTime)

		fetch fetchViewTests into @testId, @viewId
	end

	close fetchViewTests
	deallocate fetchViewTests
end
go

create or alter procedure main
as 
begin 
	insert into TestRuns(startAt) values(getdate())
	declare @testId int = SCOPE_IDENTITY()
	exec runDeleteTests
	exec runInsertTests @testId
	exec runViewTests @testId

	update TestRuns set Description = 'delete + insert + view', EndAt = GETDATE() where TestRunID = @testId
end
go

create or alter procedure runTests
(@n int)
as
begin
	declare @i int = 0
	while @i < @n
	begin
		exec main
		set @i = @i + 1
	end

	select * from TestRunTables
	select * from TestRunViews
	select * from TestRuns
end
go

exec runTests 1
