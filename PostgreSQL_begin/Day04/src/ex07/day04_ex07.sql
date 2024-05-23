CREATE VIEW pizzeria_min_800 AS
SELECT pizzeria.name AS pizzeria_name
FROM pizzeria
JOIN menu ON  pizzeria.id = menu.pizzeria_id
WHERE price < 800;

CREATE VIEW mv_dmitriy_visits_and_eats2 AS
SELECT pizzeria.name AS pizzeria_name
FROM pizzeria
WHERE name IN (SELECT * FROM pizzeria_min_800) AND name NOT IN (SELECT * FROM mv_dmitriy_visits_and_eats);

INSERT INTO person_visits
VALUES ((SELECT MAX(id) + 1 FROM person_visits), (SELECT id FROM person WHERE name = 'Dmitriy'),
        (SELECT pizzeria.id FROM mv_dmitriy_visits_and_eats2 JOIN pizzeria ON pizzeria.name = mv_dmitriy_visits_and_eats2.pizzeria_name LIMIT 1), '2022-01-08');

REFRESH MATERIALIZED VIEW mv_dmitriy_visits_and_eats;