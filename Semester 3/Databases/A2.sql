use mushroom_farm
go

insert into mushroom_types values('Comestible'), ('Toxic'), ('Medicinal')
insert into mushrooms values(1, 'Boletus edulis', 'Penny bun'),
							(1, 'Pleurotus ostreatus', 'Oyster mushroom'),
							(1, 'Psilocybe semilanceata', 'Liberty cap'),
							(2, 'Amanita phalloides', 'Death Cap'),
							(2, 'Boletus satanas', 'Evil penny bun'),
							(3, 'Ganoderma lingzhi', 'Reishi')

insert into farms values('Romania, Negulesti, str. Statiunii, no. 32'),
						('Romania, Negulesti, str. Statiunii, no. 33'),
						('Romania, Luminis, str. Luminii, no. 90')

insert into employees values('Dorian', 'Hornea', '5010269254121'),
							('Edward', 'Iakab', '50525230014'),
							('Aurelian', 'Iancu', '50331104404'),
							('Marian', 'Guceanu', '50909231010'),
							('Ana', 'Maria', '60254221215')

insert into clients values('Cristi Ifrim'), ('Popescu Ion'), ('Vasile Vasi'), ('Ciobanu Gheorghe'), ('Sava Bogdan')

insert into salaries values(2715, 1), (3000, 2), (2640, 3), (2976, 4) --, (5000, 1)

insert into plantations values  (1, 1, 1), (2, 3, 1), (3, 2, 4),
								(1, 3, 3), (2, 1, 1), (2, 6, 4),
								(3, 4, 3), (3, 2, 2), (2, 2, 2),
								(1, 5, 3), (1, 4, 4), (2, 3, 3)

insert into warehouses values	('Romania, Roznov, str. Roznovanu, no. 101', 2000),
								('Romania, Piatra Neamt, str. Maratei, no. 55', 5000),
								('Romania, Piatra Neamt, str. Darmanesti, no. 69', 4000),
								('Romania, Cluj Napoca, str. B.P. Hasdeu, no 90', 8000),
								('England, Luton, str. Luton, no, 55', 0)

insert into stocks values (500, 1, 1), (700, 1, 2), (300, 1, 4),
						  (700, 2, 3), (900, 2, 4),
						  (50, 3, 1), (200, 3, 2),
						  (20, 4, 4), (200, 5, 3),
						  (300, 5, 2), (200, 5, 4),
						  (150, 5, 1)

insert into gatherings values (1000, 1, '25-Sep-2022'), (300, 2, '26-Sep-2022'), (50, 5, '30-Sep-2022')

insert into orders values (1, 1, 20, '15-Nov-2022'),
						  (5, 5, 10, '11-Oct-2022'),
						  (3, 3, 1, '18-Nov-2022'),
						  (2, 5, 2, '12-Sep-2022') -- a murit saracu la 13 ca a mancat toxice

update salaries set income = income * 1.1 where employeeid between 2 and 4 or income < 2900
update stocks set amount = amount - 120 where warehouseid in (1, 4, 5)
update orders set amount = amount * 1.2 where clientid not in (3, 4, 5)


delete from warehouses where waddress like 'England%'
delete from employees where firstname + ' ' + surname like 'A%ia'



/*
 * a) 2 queries with the union operation; use UNION [ALL] and OR;
 */

/* or */
/* SELECT EMPLOYEES THAT HAVE THEIR INCOME 3000 OR MINIMUM SALARY */
select E.firstname, E.surname, S.income
from employees E, salaries S 
where E.eid = S.employeeid and (S.income = 3000 or S.income in 
(SELECT MIN(income) + 0
from salaries))
order by E.firstname
/* union */
/* SELECT EMPLOYEES THAT HAVE THEIR SALARY GREATER THAN 2800 or EMPLOYEES THAT GATHERED Liberty Cap mushrooms */
select E.firstname, E.surname
from employees E, plantations P, salaries S
where S.income > 2800 and S.employeeid = E.eid
union
select E2.firstname, E2.surname
from employees E2, plantations P2, mushrooms M2
where P2.mushroomid = M2.mid and M2.commonname = 'Liberty cap' and P2.employeeid = E2.eid

/*
 * b) 2 queries with the intersection operation; use INTERSECT and IN;
 */

 /*SELECT WAREHOUSES THAT HAVE AT LEAST 2.9 KG OF A MEDICINAL MUSHROOM*/
 select distinct W.waddress, W.capacity
 from warehouses W, stocks S
 where W.wid = S.warehouseid and S.amount > '2.9' and S.mushroomid in 
 (select M2.mid 
  from mushrooms M2, mushroom_types T
  where T.name = 'Medicinal')
  order by W.capacity desc

/*SELECT FARMS THAT HAD PLANTATIONS OF MUSHROOM 1 AND MUSHROOM 5*/
 select F.faddress as 'Farms Address'  
 from farms F, plantations P
 where P.farmid = F.fid and P.mushroomid = '1'
 intersect
 select F2.faddress
 from farms F2, plantations P2
 where P2.farmid = F2.fid and P2.mushroomid = '5'

 /*
  *  c) 2 queries with the difference operation; use EXCEPT and NOT IN;
  */

  /*SELECT DISTINCT FARMS THAT HAD PLANTATIONS OF MUSHROOM 1 BUT NOT MUSHROOM 5*/
 select distinct F.faddress as 'Farms Address'
 from farms F, plantations P
 where P.farmid = F.fid and P.mushroomid = '1'
 except
 select distinct F2.faddress
 from farms F2, plantations P2
 where P2.farmid = F2.fid and P2.mushroomid = '5'

  /*SELECT TOP 5 WAREHOUSES THAT HAVE AT LEAST 2.9 KG OF A NON-MEDICINAL MUSHROOM*/
 select distinct top 5 W.waddress as 'Warehouse address'
 from warehouses W, stocks S
 where W.wid = S.warehouseid and S.amount > '2.9' and S.mushroomid not in 
 (select M2.mid 
  from mushrooms M2, mushroom_types T
  where M2.mtype = T.mtid and T.name = 'Medicinal')
 order by S.amount

 /*
  *  d) 4 queries with INNER JOIN, LEFT JOIN, RIGHT JOIN, and FULL JOIN (one query per operator); one query will join at least 3 tables, while another one will join at least two many-to-many relationships;
  */

-- SELECT TOP 5 EMPLOYEES THAT GATHERED AT LEAST 2.9 KG OF A TYPE OF MUSHROOM IN A FARM
select top 5 E.firstname + ' ' + E.surname as 'Full Name', F.faddress, G.amount * 1.0 as Amount
from employees E
inner join plantations P on P.employeeid = E.eid
inner join gatherings G on G.amount > 2.9 * 1 and G.plantationid - P.pid = 0
inner join farms F on f.fid = P.farmid

-- SELECT MUSHROOMS NAME, DATE AND AMOUNT THAT WERE ORDERED IN A QUANTITY BIGGER THAN 1.5 AND THOSE WHO WERENT ORDERED
select M.scientificname, M.commonname, O.odate, O.amount
from mushrooms M
left join orders O on m.mid = O.mushroomid and O.amount > '1.5'


-- SELECT MUSHROOMS NAME, DATE AND AMOUNT THAT WERE ORDERED IN A QUANTITY BIGGER THAN 1.5 AND THE REST OF THE ORDERS
select M.scientificname, M.commonname, O.odate, O.amount
from mushrooms M
right join orders O on m.mid = O.mushroomid and O.amount > '1.5'

-- SELECT ALL CLIENTS and ALL orders, ESPECIALLY THOSE WHO ORDERED AT LEAST 5 KGS OF MUSHROOMS
select C.cname, O.amount 
from clients C
full join orders O on O.clientid = C.cid and O.amount >= '5'

/*
 * e)  2 queries with the IN operator and a subquery in the WHERE clause; in at least one case, the subquery must include a subquery in its own WHERE clause;
 */
 
 -- SELECT WAREHOUSES THAT HAVE STOCKS OF MEDICINAL OR COMESTIBLE MUSHROOMS
 select W.waddress as 'Warehouse address'
 from warehouses W, stocks S
 where W.wid = S.warehouseid and S.mushroomid in
 (select M.mid
  from mushrooms M
  where M.mtype in (
					select mtid 
					from mushroom_types
					where name = 'Medicinal' or name = 'Comestible'))

-- SELECT EMPLOYEES THAT HAVE THEIR INCOME OF 3000 OR MAX SALARY
select E.firstname + ' ' + E.surname as 'Full Name'
from employees E, salaries S 
where E.eid = S.employeeid and (S.income = 3000 or S.income in 
(SELECT MAX(income)
from salaries))

/*
 * f) 2 queries with the EXISTS operator and a subquery in the WHERE clause;
 */
 
 -- SELECT CLIENTS THAT ORDERED BETWEEN 11 NOV - 18 NOV
 SELECT cname as 'Client name'
 from clients
 where exists(
 select * 
 from orders
 where odate between '11-Nov-2022' and '18-Nov-2022' and clientid = clients.cid)

 -- SELECT FARMS THAT HAD GATHERINGS BETWEEN 29 OCT - 5 NOV
  SELECT faddress as 'Farm Address'
 from farms
 where exists(
 select * 
 from gatherings, plantations
 where gdate between '29-Oct-2022' and '05-Nov-2022' and plantationid = plantations.pid and plantations.farmid = farms.fid)

 /*
  * g) 2 queries with a subquery in the FROM clause;      
  */
  --SELECT EMPLOYEES THAT HAVE THEIR INCOME GREATER THAN THE AVG SALARY
  select E.firstname + ' ' + E.surname as 'Full name', S.income as 'Full name' from
	(select avg(income) as averageIncome from salaries) as salary, employees E, salaries S
  where E.eid = S.employeeid and S.income > salary.averageIncome

    --SELECT CLIENTS THAT HAVE THEIR NUMBER OF ORDER GREATER THAN AVERAGE STOCK OF MUSHROOM WITH ID 2
  select C.cname from
	(select avg(amount) as averageAmount from stocks where mushroomid = '2') as amount,
	(select count(*) as ordersCount from orders, clients where clientid = clients.cid) as clientOrders,
	clients C
  where clientOrders.ordersCount > amount.averageAmount

 /*
  * h) 4 queries with the GROUP BY clause, 3 of which also contain the HAVING clause; 2 of the latter will also have a subquery in the HAVING clause; use the aggregation operators: COUNT, SUM, AVG, MIN, MAX; 
  */

  -- SELECT HOW MANY MUSHROOMS ARE OF EACH TYPE
  select T.name, COUNT(M.mid) as 'Types of mushrooms'
  from mushroom_types T, mushrooms M
  where M.mtype = T.mtid
  group by T.name

  -- SELECT MUSHROOMS HAVING AT LEAST 3 STOCKS
  select M.commonname, count(S.mushroomid) as NumberOfStocks
  from mushrooms M, stocks S
  where S.mushroomid = M.mid
  group by M.commonname
  having count(s.mushroomid) >= 3
  order by NumberOfStocks

  -- SELECT EMPLOYEES THAT WORKED ON AT LEAST 50% OF THE PLANTATIONS
  select distinct E.firstname + ' ' + E.surname as 'Full Name', count(P.pid) as NumberOfPlantations
  from employees E, plantations P
  where E.eid = P.employeeid
  group by E.firstname + ' ' + E.surname
  having count(P.pid) >= (SELECT COUNT(pid) from plantations) / 2
  order by NumberOfPlantations

  -- SELECT FARMS THAT HAD AT MOST THE AVERAGE OF ALL GATHERINGS
  select distinct F.faddress, count(G.plantationid) as NumberOfGatherings
  from farms F, gatherings G, plantations P
  where F.fid = P.pid and G.plantationid = P.pid
  group by F.faddress
  having count(G.plantationid) <= (SELECT AVG(plantationid) from gatherings)


 /*
  * i) 4 queries using ANY and ALL to introduce a subquery in the WHERE clause (2 queries per operator); rewrite 2 of them with aggregation operators, and the other 2 with IN / [NOT] IN. 
  */


  -- select employees that have their salary greater than some employee called Dorian
  select E.firstname + ' ' + E.surname as 'Full Name'
  from employees E, salaries S
  where S.employeeid = E.eid and S.income > any (
		select S2.income 
		from salaries S2, employees E2
		where S2.employeeid = E2.eid and E2.firstname = 'Dorian')

-- same as above but with aggregation
select E.firstname + ' ' + E.surname as 'Full Name'
from employees E, salaries S
where S.employeeid = E.eid and S.income >
			(select min(S2.income) from salaries S2, employees E2 where S2.employeeid = E2.eid and E2.firstname = 'Dorian')

 -- select mushrooms that have their stocks greater than some mushrooms called Death cap
	select distinct M.commonname as 'Mushroom name'
	from mushrooms M, stocks S
	where S.mushroomid = M.mid and S.amount > ALL(
		select amount from stocks, mushrooms where stocks.mushroomid = mushrooms.mid and mushrooms.commonname = 'Death Cap')

 -- same as above but with aggregation
	select distinct M.commonname
	from mushrooms M, stocks S
	where S.mushroomid = M.mid and S.amount > (
		select MAX(amount) from stocks, mushrooms where stocks.mushroomid = mushrooms.mid and mushrooms.commonname = 'Death Cap')

 -- select orders that have their amount bigger than any command of client 'Cristi Ifrim'
 select * 
 from orders O, clients C
 where O.amount > all(
	select O2.amount from orders O2, clients C2
	where O2.clientid = C2.cid and C2.cname = 'Cristi Ifrim')

 -- same as above but with not in
  select * 
 from orders O, clients C
 where O.amount not in (
	SELECT amount from orders where amount <= (
		SELECT MAX(amount)
		from orders, clients
		where clients.cid = orders.clientid and clients.cname = 'Cristi Ifrim'))

 -- select top 5 employees that have gathered on at least one plantation with toxic mushrooms
 select distinct top 5 E.firstname + ' ' + E.surname as 'Full Name'
 from employees E, plantations P
 where P.employeeid = E.eid and P.mushroomid = any(
												select mid from mushrooms, mushroom_types T where T.name = 'Toxic')

 -- same as above but with in

 select distinct top 5 E.firstname + ' ' + E.surname as 'Full Name'
 from employees E, plantations P
 where P.employeeid = E.eid and P.mushroomid in (
												select mid from mushrooms, mushroom_types T where T.name = 'Toxic')


 














