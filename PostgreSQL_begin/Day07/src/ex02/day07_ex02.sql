(SELECT pizzeria.name, count(person_order.order_date) AS count, 'order' AS action_type
FROM person_order
JOIN menu ON person_order.menu_id = menu.id
JOIN pizzeria ON pizzeria.id = menu.pizzeria_id
GROUP BY pizzeria.name
ORDER BY action_type ASC, count DESC
LIMIT 3)
UNION
SELECT pizzeria.name, count(person_visits.visit_date) AS count, 'visit' AS action_type
FROM pizzeria
JOIN person_visits ON person_visits.pizzeria_id = pizzeria.id
GROUP BY pizzeria.name
ORDER BY action_type ASC, count DESC
LIMIT 6;