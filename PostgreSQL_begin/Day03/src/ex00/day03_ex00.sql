SELECT menu.pizza_name, menu.price, pizzeria.name AS pizzeria_name, person_visits.visit_date
FROM pizzeria
JOIN menu ON menu.pizzeria_id = pizzeria.id
JOIN person_visits ON person_visits.pizzeria_id = pizzeria.id
JOIN person ON person.id = person_visits.person_id
WHERE (price BETWEEN 800 AND 1000) AND person.name = 'Kate'
ORDER BY menu.pizza_name ASC, menu.price ASC, pizzeria_name ASC;