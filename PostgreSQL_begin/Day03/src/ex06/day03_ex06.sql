SELECT menu.pizza_name, piz2.name AS pizzeria_name1, piz1.name pizzeria_name2, menu.price
FROM menu
CROSS JOIN (SELECT * FROM menu) AS foo
JOIN pizzeria AS piz1 ON piz1.id = menu.pizzeria_id
JOIN pizzeria AS piz2 ON piz2.id = foo.pizzeria_id
WHERE menu.price = foo.price AND menu.pizza_name = foo.pizza_name AND menu.id < foo.id
ORDER BY menu.pizza_name ASC;