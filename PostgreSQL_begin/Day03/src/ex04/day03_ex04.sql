WITH female(pizzeria_name) AS(
	SELECT pizzeria.name
	FROM pizzeria
	JOIN menu ON menu.pizzeria_id = pizzeria.id
	JOIN person_order ON person_order.menu_id = menu.id
	JOIN person ON person.id = person_order.person_id
	WHERE person.gender = 'female'),
male(pizzeria_name) AS(
	SELECT pizzeria.name
	FROM pizzeria
	JOIN menu ON menu.pizzeria_id = pizzeria.id
	JOIN person_order ON person_order.menu_id = menu.id
	JOIN person ON person.id = person_order.person_id
	WHERE person.gender = 'male')
SELECT pizzeria_name
FROM female
EXCEPT
SELECT pizzeria_name
FROM male
UNION
(SELECT pizzeria_name
FROM female
EXCEPT
SELECT pizzeria_name
FROM male);