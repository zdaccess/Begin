SELECT pizzeria.name
FROM pizzeria
WHERE pizzeria.name NOT IN (SELECT pizzeria.name FROM pizzeria JOIN person_visits ON person_visits.pizzeria_id = pizzeria.id)

SELECT pizzeria.name
FROM pizzeria
WHERE NOT EXISTS (SELECT pizzeria_id FROM person_visits WHERE person_visits.pizzeria_id = pizzeria.id)