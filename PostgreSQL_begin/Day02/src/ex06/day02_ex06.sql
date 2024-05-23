SELECT menu.pizza_name, pizzeria.name AS pizzeria_name
FROM menu
JOIN person_order ON person_order.menu_id = menu.id
JOIN pizzeria ON  pizzeria.id = menu.pizzeria_id
JOIN person ON person.id = person_order.person_id
WHERE person.name IN ('Denis', 'Anna')
ORDER BY menu.pizza_name, pizzeria_name ASC