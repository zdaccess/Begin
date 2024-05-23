SELECT person.name, count(person_visits.person_id) AS count_of_visits
FROM pizzeria
JOIN person_visits ON person_visits.pizzeria_id = pizzeria.id
JOIN person ON person.id = person_visits.person_id
GROUP BY person.name
HAVING count(person.name) > 3;