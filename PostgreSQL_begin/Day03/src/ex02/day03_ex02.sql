WITH CTE AS(
	SELECT id AS menu_id
	FROM menu
	EXCEPT
	SELECT menu_id 
	FROM person_order
	ORDER BY menu_id ASC)
SELECT menu.pizza_name, menu.price, pizzeria.name AS pizzeria_name
FROM pizzeria
JOIN menu ON menu.pizzeria_id = pizzeria.id
JOIN (SELECT * FROM CTE) AS pizza ON pizza.menu_id = menu.id
ORDER BY menu.pizza_name ASC, menu.price ASC;