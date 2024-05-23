SELECT order_date AS order_date, CONCAT (person.name, ' (age:', age, ')') AS person_information
FROM person
NATURAL JOIN (SELECT person_id AS id, order_date FROM person_order) AS foo
ORDER BY order_date ASC, person_information ASC