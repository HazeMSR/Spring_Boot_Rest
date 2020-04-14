--1
UPDATE Storage
SET transfered_to = 'Invalid_destination'
WHERE transfered_to = '100';


--2
SELECT * FROM Product
WHERE maker = 'Dell';


--3
DELETE FROM Storage
WHERE transfered_to = 'Invalid_destination';


--4
WITH
print AS (
    SELECT pro.maker,pri.price, pri.model 
    FROM Product pro, Printer pri
    WHERE pro.model = pri.model
    ORDER BY pri.price DESC
    FETCH FIRST 1 ROW ONLY
),
lap AS (    
    SELECT pro.maker,l.price, l.model
    FROM Product pro, Laptop l
    WHERE pro.model = l.model
    ORDER BY l.price DESC
    FETCH FIRST 1 ROW ONLY
),
p AS (
    SELECT pro.maker,pc.price, pc.model 
    FROM Product pro, PC pc
    WHERE pro.model = pc.model
    ORDER BY pc.price DESC
    FETCH FIRST 1 ROW ONLY
) SELECT * FROM print
UNION SELECT * FROM lap
UNION SELECT * FROM p;


--5
SELECT COUNT(*) FROM (
    SELECT l.model
    FROM Laptop l, Product p
    WHERE l.model = p.model
    AND p.maker = 'Hitachi'
) ct;


--6
SELECT pri.serial_number,pri.price, pro.maker, pro.type
FROM Printer pri, Product pro
WHERE pro.model = pri.model
UNION
SELECT l.serial_number,l.price, pro.maker, pro.type
FROM Laptop l, Product pro
WHERE pro.model = l.model
UNION
SELECT pc.serial_number,pc.price, pro.maker, pro.type
FROM PC pc, Product pro
WHERE pro.model = pc.model
ORDER BY price DESC;


--7
WITH
pri AS(
    SELECT ctpri.maker AS maker,COUNT(*) AS cpri FROM(
        SELECT p.maker
        FROM Printer pri, Product p
        WHERE pri.model = p.model
    ) ctpri
    GROUP BY ctpri.maker
),
l AS(
    SELECT ctl.maker,COUNT(*) AS cl FROM(
        SELECT p.maker
        FROM Laptop l, Product p
    WHERE l.model = p.model
    ) ctl
	GROUP BY ctl.maker
),
pc AS(
    SELECT ctpc.maker,COUNT(*) AS cpc FROM(
        SELECT p.maker
        FROM PC pc, Product p
        WHERE pc.model = p.model
    ) ctpc
    GROUP BY ctpc.maker
) SELECT pri.maker
    FROM pri, pc, l
    WHERE pri.cpri > pc.cpc
    AND pri.cpri > l.cl;


--8
WITH
cd AS(
	SELECT pc.serial_number,pc.price, pro.maker
	FROM PC pc, Product pro
	WHERE pro.model = pc.model
	AND pc.cd IS NULL
	ORDER BY price DESC
),
comp AS(
    SELECT DISTINCT maker FROM cd
    WHERE maker NOT IN (
        SELECT DISTINCT pro.maker
        FROM Printer pri, Laptop l, Product pro
        WHERE ( pri.model = pro.model
        	OR l.model = pro.model)
    	AND pro.maker IN (SELECT DISTINCT maker FROM cd)
    )
)
select * from comp;