SELECT COALESCE(person.name, '-') AS person_name, day.visit_date, COALESCE(pizzeria.name, '-') AS pizzeria_name
FROM (SELECT * FROM person_visits WHERE person_visits.visit_date BETWEEN '20220101' AND '20220103') AS day
FULL JOIN person ON day.person_id = person.id
FULL JOIN pizzeria ON pizzeria.id = day.pizzeria_id
ORDER BY person_name, visit_date, pizzeria_name ASC