INSERT INTO person_discounts
SELECT row_number() over() AS id, person_id, pizzeria_id, 
(
	CASE WHEN COUNT(person_order.person_id) = 1 THEN 10.5
	WHEN COUNT(person_order.person_id) = 2 THEN 22
	ELSE 30 END
)
FROM person_order
JOIN (SELECT id, pizzeria_id FROM menu) AS foo
ON foo.id = person_order.menu_id
GROUP BY person_order.person_id, foo.pizzeria_id;