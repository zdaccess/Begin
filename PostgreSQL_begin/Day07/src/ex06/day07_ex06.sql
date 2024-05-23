SELECT pizzeria.name, count(menu.price) AS count_of_orders, ROUND((SUM(menu.price)/count(menu.price)),2)::float AS average_price, MAX(menu.price)  AS max_price, MIN(menu.price) AS min_price
FROM person_order
JOIN menu ON person_order.menu_id = menu.id
JOIN pizzeria ON pizzeria.id = menu.pizzeria_id
GROUP BY pizzeria.name
ORDER BY pizzeria.name ASC;