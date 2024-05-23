SELECT person.name, menu.pizza_name, menu.price, cast((menu.price -(menu.price *person_discounts.discount/100)) AS INT) AS discount_price, pizzeria.id AS pizzeria_name
FROM person_order
JOIN person ON person.id = person_order.person_id
JOIN menu ON menu.id = person_order.menu_id
JOIN pizzeria ON pizzeria.id = menu.pizzeria_id
JOIN person_discounts ON person_discounts.person_id = person.id AND  person_discounts.pizzeria_id = pizzeria.id
ORDER BY person.name, menu.pizza_name;