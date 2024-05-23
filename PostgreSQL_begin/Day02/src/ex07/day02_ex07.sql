SELECT pizzeria.name AS pizzeria_name
FROM person_visits
JOIN person ON person.id = person_visits.person_id
JOIN pizzeria ON person_visits.pizzeria_id = pizzeria.id
JOIN menu ON  pizzeria.id = menu.pizzeria_id
WHERE person.name = 'Dmitriy' AND person_visits.visit_date = '20220108' AND price < 800