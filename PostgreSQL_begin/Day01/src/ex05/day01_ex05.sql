SELECT person.id AS "person.id", person.name AS "person.name", age, gender, address, pizzeria.id AS "pizzeria.id", pizzeria.name AS "pizzeria.name", rating
FROM person, pizzeria
UNION
SELECT person.id AS "person.id", person.name AS "person.name", age, gender, address, pizzeria.id AS "pizzeria.id", pizzeria.name AS "pizzeria.name", rating
FROM person, pizzeria
ORDER BY "person.id", "pizzeria.id" ASC