WITH orderp AS (SELECT pizzeria.name, count(person_order.order_date) AS count
    FROM person_order
    JOIN menu ON person_order.menu_id = menu.id
    JOIN pizzeria ON pizzeria.id = menu.pizzeria_id
    GROUP BY pizzeria.name
    ORDER BY count DESC, pizzeria.name ASC),
    visit AS (SELECT pizzeria.name, count(person_visits.visit_date) AS count
    FROM pizzeria
    JOIN person_visits ON person_visits.pizzeria_id = pizzeria.id
    GROUP BY pizzeria.name
    ORDER BY count DESC, pizzeria.name ASC)

SELECT pizzeria.name, COALESCE(orderp.count, 0) + COALESCE(visit.count, 0) AS total_count
FROM pizzeria
FULL JOIN visit ON visit.name = pizzeria.name
FULL JOIN orderp ON orderp.name = pizzeria.name
ORDER BY total_count DESC, name ASC;