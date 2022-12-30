use mushroom_farm
go

create or alter procedure setGatheringsAmountAsFloat as
    alter table gatherings alter column amount float
go

create or alter procedure setGatheringsAmountAsReal as
    alter table gatherings alter column amount real
go

create or alter procedure addCountryToClients as
    alter table clients add ccountry varchar(max)
go

create or alter procedure removeCountryFromClients as
    alter table clients drop column ccountry
go

create or alter procedure addDefaultToGatheringsAmount as
    alter table gatherings add constraint def_Gatherings default 0.0 for amount
go

create or alter procedure removeDefaultFromGatheringsAmount as
    alter table gatherings drop constraint def_Gatherings
go

create or alter procedure createPricesTable as
	IF EXISTS (SELECT * FROM sysobjects WHERE name='prices' and xtype='U')
		drop table prices
		create table prices (
			mushroomid int not null,
			price float,
			--constraint pk_Prices primary key(mushroomid)
		)
go

create or alter procedure dropPricesTable as
	if exists (SELECT * FROM sysobjects where name='prices' and xtype='U')
		drop table prices
go

create or alter procedure addPrimaryKeyConstraintToPrices as
    alter table prices
        add constraint pk_Prices primary key (mushroomid)
go

create or alter procedure removePrimaryKeyConstraintFromPrices as
    alter table prices
        drop constraint pk_Prices
go

create or alter procedure addCandidateKeyToMushrooms as
    alter table mushrooms
        add constraint cd_Mushrooms unique (scientificname, commonname)
go

create or alter procedure dropCandidateKeyFromMushrooms as
    alter table mushrooms
        drop constraint cd_Mushrooms
go

create or alter procedure addForeignKeyToPrices as
    alter table prices
        add constraint fk_Prices foreign key(mushroomid) references mushrooms(mid)
go

create or alter procedure dropForeignKeyFromPrices as
    alter table prices drop constraint fk_Prices
go

IF EXISTS (SELECT * FROM sysobjects WHERE name='versionTable' and xtype='U')
	drop table versionTable
	create table versionTable (
		currentVersion int
	)
GO

insert into versionTable values (1) -- initial version

if exists ( select * from sysobjects where name='proceduresTable' and xtype='U')
	drop table proceduresTable
	create table proceduresTable (
		downgrade int,
		upgrade int,
		primary key (downgrade, upgrade),
		procedureToBeExecuted varchar(max)
	)
go

insert into proceduresTable values  (1, 2, 'setGatheringsAmountAsReal'), (2, 1, 'setGatheringsAmountAsFloat') ,(2, 3, 'addCountryToClients'),
									(3, 2, 'removeCountryFromClients'), (3, 4, 'addDefaultToGatheringsAmount'), (4, 3, 'removeDefaultFromGatheringsAmount'),
									(4, 5, 'createPricesTable'), (5, 4, 'dropPricesTable'), (5, 6, 'addPrimaryKeyConstraintToPrices'),
									(6, 5, 'removePrimaryKeyConstraintFromPrices'), (6, 7, 'addCandidateKeyToMushrooms'), (7, 6, 'dropCandidateKeyFromMushrooms'),
									(7, 8, 'addForeignKeyToPrices'), (8, 7, 'dropForeignKeyFromPrices')
go

create or alter procedure upgradeToVersion(@newVersion int) as
    declare @curr int
    declare @proc varchar(max)
    select @curr=currentVersion from versionTable

    if @newVersion > (select max(upgrade) from proceduresTable)
        raiserror ('ERROR: Invalid version!', 10, 1)

    while @curr > @newVersion begin
        select @proc=procedureToBeExecuted from proceduresTable where downgrade=@curr and upgrade=@curr-1
        exec (@proc)
        set @curr=@curr-1
    end

    while @curr < @newVersion begin
        select @proc=procedureToBeExecuted from proceduresTable where downgrade=@curr and upgrade=@curr+1
        exec (@proc)
        set @curr=@curr+1
    end

    update versionTable set currentVersion=@newVersion
go


execute upgradeToVersion 1

select * from versionTable