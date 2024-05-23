WITH female(pizzeria_name) AS(
    SELECT pizzeria.name
    FROM pizzeria
    JOIN person_visits ON person_visits.pizzeria_id = pizzeria.id
    JOIN person ON person.id = person_visits.person_id
    WHERE person.gender = 'female'),
male(pizzeria_name) AS(
    SELECT pizzeria.name
    FROM pizzeria
    JOIN person_visits ON person_visits.pizzeria_id = pizzeria.id
    JOIN person ON person.id = person_visits.person_id
    WHERE person.gender = 'male')
SELECT pizzeria_name
FROM male
EXCEPT ALL
SELECT pizzeria_name
FROM female
UNION ALL
(SELECT pizzeria_name
FROM female
EXCEPT ALL
SELECT pizzeria_name
FROM male)
ORDER BY pizzeria_name ASC;